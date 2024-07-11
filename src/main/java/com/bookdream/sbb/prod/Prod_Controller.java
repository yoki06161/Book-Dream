package com.bookdream.sbb.prod;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor.mode_return;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.bookdream.sbb.prod_repo.*;


// 스프링 실행시 로그인창 안뜨게한다
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// final필드 자동 생성용
@RequiredArgsConstructor
@Controller
public class Prod_Controller {

	// @Autowired 이미 생성된 빈을 가져온단 뜻
	@Autowired
	private final Prod_Service prodService;
//	private final Prod_Crawling crawling;
//	private final Prod_Review p_review;
	
	// 리스트
	// prod로 들어오는 주소 여기로
	@GetMapping("/prod")
	// 자바에서 html로 데이터 전달할때 쓰는게 model
	public String prod_list(Model model) throws IOException {
		// 키밸류라 생각하면 된다. 여기서 설정한 Prod_Books가 html에서 불리는용, book_list는 여기의 값
		List<Prod_Books> book_list = Prod_Crawling.getc_Datas();
//		// 크롤링된 데이터를 데이터베이스에 저장합니다.
//      prodService.saveBooks(book_list);

//      // 데이터베이스에서 저장된 데이터를 가져와서 모델에 추가합니다.(위 코드랑 같이 쓸 경우 최초 1회만 작동)
      model.addAttribute("C_Books", prodService.getAllBooks());
      // 크롤링된 데이터 그대로 출력 
//		model.addAttribute("C_Books", book_list);
//		System.out.println("모델값");
//		System.out.println(model);
		return "prod/prod_list";
		//푸시되라
	}
//	
//	// 제품 상세보기
//	@GetMapping("/prod/detail")
//	public String prod_book(@RequestParam("l_title") String title, @RequestParam("l_img") String img,
//			@RequestParam("l_price") String price, @RequestParam("l_writer") String writer,
//			@RequestParam("l_intro") String intro,
//			HttpSession session) throws IOException {
////		System.out.println("상세페이지에 출력될 타이틀 " + title);
////		System.out.println("상세페이지에 출력될 이미지 " + img);
////		System.out.println("상세페이지에 출력될 가격 " + price);
//		
////		session.setAttribute("s_title", title);
////		session.setAttribute("s_img", img);
////		session.setAttribute("s_price", price);
////		session.setAttribute("s_writer", writer);
////		session.setAttribute("s_intro", intro);
//		return "prod/prod_detail";
//	}
	
	
	// 제품 상세보기. 소미씨가 해주신거.
	// @PathVariable은 url에 있는 변수 인식하는거.
	@GetMapping("/prod/detail/{book_id}")
	public String prod_book(Model model, @PathVariable("book_id") Integer book_id) throws IOException{
		Prod_Books book = prodService.getProdBooks(book_id);
		model.addAttribute("book", book);
		
		return "prod/prod_detail";
	}
	
	// db리스트 테스트
//		@GetMapping("prod/list_test")
//		public String list_t(Model model) {
//			List<Prod_Review> p_list = this.p_review.findAll();
//			model.addAttribute("p_list", p_list);
//			return "prod/list_test";
//		}
	
}