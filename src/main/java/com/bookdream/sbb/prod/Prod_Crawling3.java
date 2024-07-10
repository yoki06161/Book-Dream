package com.bookdream.sbb.prod;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.Getter;

@Service
@Getter
// 이거 클래스 명이 자바 클래스랑 같지 않아서 실행 안됐었음
public class Prod_Crawling3 {

	// 빨라지는거라는데 모르겠음
	@Cacheable("bookList")
	// 상품 리스트 크롤링
	public static List<Prod_Books> getc_Datas() {
		// 구글 드라이버 등록. 경로는 절대경로로 해서 수정필요.
		System.setProperty("webdriver.chrome.driver", "C:/Users/TJ/git/Book-Dream/driver/chromedriver.exe");
		
        ChromeOptions c_option = new ChromeOptions();
        // 새창을 숨김. 그냥 --headless라쓰면 오류떠서 =new같이 쓴다.
        c_option.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(c_option);
        List<Prod_Books> book_list = new ArrayList<>();
        
        try {
        	// 연결할 url. book_id는 선택한 id에 맞춰 사이트 크롤링
            String url = "https://product.kyobobook.co.kr/bestseller/online?period=001";
            driver.get(url);

//            // 스크롤 내리기용 자바스크립트
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            // 웹페이지 100까지 스크롤 내림. 원본 사이트가 js로 스크롤 내려야 내용이 나오게 돼있어서.
//            js.executeScript("window.scrollTo(0, 100)");
            
            // 페이지 실행시 로딩 최대 5초 기다리게
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            // 내용 넣기
            // Element랑 같은건데 조건문이 가능한듯. 이건 wait.until이라고 조건 맞췄고. until은 기다리는거. ExpectedCondition은 조건을 나타내는 인터페이스. visibilityOfElementLocated는 요소가 화면에 보일때까지 기다리는거.
            // div:nth-child(17)때문에 17번째에 없는건 그냥 오류뜨는데 그렇다고 17을 없애면 다른 div들 이름이 겹쳐서 첫번째꺼가 출력된다...
           
            List<WebElement> book_title = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tabRoot > div.view_type_list.switch_prod_wrap > ol:nth-child(1) > li > div.prod_area.horizontal > div.prod_info_box > div.auto_overflow_wrap.prod_name_group > div > div > a")));
            // presenceOfAllElementsLocatedBy는 페이지 내의 모든 요소가 로드될떄까지 기다리는거. 위의 wait길게 해도 이게 아니면 오류뜸.
            List<WebElement> book_img = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#tabRoot > div.view_type_list.switch_prod_wrap > ol:nth-child(1) > li > div.prod_area.horizontal > div.prod_thumb_box.size_lg > a > span > img")));
            List<WebElement> book_price = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tabRoot > div.view_type_list.switch_prod_wrap > ol:nth-child(1) > li > div.prod_area.horizontal > div.prod_info_box > div.prod_price > span.price_normal > s")));
            List<WebElement> book_writer = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tabRoot > div.view_type_list.switch_prod_wrap > ol:nth-child(1) > li > div.prod_area.horizontal > div.prod_info_box > span")));
            List<WebElement> book_intro = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tabRoot > div.view_type_list.switch_prod_wrap > ol:nth-child(1) > li > div.prod_area.horizontal > div.prod_info_box > p")));
            
            for(int i = 0; i < book_title.size(); i++) {
            	// 리스트로 저장했기에 리스트의 i번째를 텍스트를 title에 저장
            	String title = book_title.get(i).getText();
            	String img = book_img.get(i).getAttribute("src");
            	String price = book_price.get(i).getText();
            	String writer = book_writer.get(i).getText();
            	String intro = book_intro.get(i).getText();
            	
//            	System.out.println(i + "타이틀: " + title);
//            	System.out.println("작가: " + writer);
//            	System.out.println("이미지: " + img);
//            	System.out.println();
            	
            	Prod_Books books = Prod_Books.builder()
            			.book_title(title)
            			.book_img(img)
            			.book_price(price)
            			.book_writer(writer)
            			.book_intro(intro)
            			.build();
//            	System.out.println("북스" + books);
            	
            	
            	book_list.add(books);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
		return book_list;
	}
	
}
