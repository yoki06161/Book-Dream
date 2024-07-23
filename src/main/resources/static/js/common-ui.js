function init() {
   // sessionStorage에서 저장된 뱃지 숫자를 가져와서 설정
   if (sessionStorage.getItem('badgeCount')) {
      document.getElementById('badge').textContent = sessionStorage.getItem('badgeCount');
   }
}

// DOMContentLoaded 이벤트 리스너를 사용하여 문서가 완전히 로드된 후 실행하는 함수 호출
document.addEventListener('DOMContentLoaded', init);

// 장바구니 추가 함수
function aa() {
   let book_id = parseInt(document.querySelector('.book_id').value);    // int형으로 담기
   let count = parseInt(document.querySelector('.bcount').value);     // int형으로 담기
   let countPrice = document.querySelector('.result').textContent;
   //console.log(book_id);

   // 세션 스토리지에서 'book_ids' 배열을 불러오기 (없으면 빈 배열로 초기화)
   let sessionBookIds = JSON.parse(sessionStorage.getItem('book_ids')) || [];
   console.log(sessionBookIds);
   
   // 세션 스토리지에 저장하기 위해 객체로 묶어 JSON 문자열로 변환
   let dataToStore = {
      book_id: book_id,
       count: count,
       countPrice: countPrice
   };
   
   
   
   // book_id가 세션 스토리지에 없는 경우 추가하고 뱃지 숫자 업데이트
   if (!sessionBookIds.includes(book_id)) {
       sessionBookIds.push(book_id); // book_id를 배열에 추가
       sessionStorage.setItem('book_ids', JSON.stringify(sessionBookIds)); 
      sessionStorage.setItem('formData', JSON.stringify(dataToStore));      // 배열을 세션 스토리지에 저장
       let formData = JSON.parse(sessionStorage.getItem('formData'));
      console.log(formData);
      alert('장바구니에 추가되었습니다.');

       // 뱃지 숫자 변경
       var badge = parseInt(document.getElementById('badge').textContent);
       var newBadgeCount = badge + 1;
       document.getElementById('badge').textContent = newBadgeCount;

       // 뱃지 숫자를 sessionStorage에 저장
       sessionStorage.setItem('badgeCount', newBadgeCount);
   } else {
       alert('이미 장바구니에 있습니다.');
   }
}
