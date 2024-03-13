$(document).ready(function() {
    // 문서가 준비되면 실행될 함수
    console.log("제이쿼리가 준비되었습니다.");
    
    $('#buttonContainer1 button').each(function() {
        // 현재 버튼 요소에서 categoryName을 가져와 출력
        var categoryName = $(this).find('#cateName').text();
        console.log("bu"+categoryName);
    });

    $('.carousel-item').each(function() {
        var itemId = $(this).attr('id'); // 각 carousel-item의 id를 가져옴
        console.log("car"+itemId);
    });
    
    for (var i = 0; i < buttonCategories.length; i++) {
        for (var j = 0; j < carouselItems.length; j++) {
            if (buttonCategories[i] === carouselItems[j]) {
                console.log("버튼에서 가져온 categoryName: " + buttonCategories[i] + ", carousel-item에서 가져온 itemId: " + carouselItems[j]);
            }
        }
    }
    
    
});

// 카테고리모달
function openModal() {
	  var buttonContainer = document.getElementById('buttonContainer1'); 
	    if (!buttonContainer) {
	        console.log("버튼 컨테이너를 찾을 수 없습니다.");
	   	 $('#myModal').modal('show');
	        return;
	    }

	    var existingButtons = buttonContainer.querySelectorAll('button'); 
	    if (existingButtons.length === 0) {
	        console.log("버튼이 없습니다.");
	   	 $('#myModal').modal('show');
	        return;
	    }
	    

	    existingButtons.forEach(function(button) {
	        var spanText = button.querySelector('span').textContent;
	        console.log(spanText);
	        // 예를 들어, 모달 내의 특정 div에 버튼 텍스트를 추가할 경우

	    });
	    
	    var buttonContainer = document.getElementById('buttonContainer1');
	    var buttonsToCopy = buttonContainer.innerHTML; // buttonContainer 내의 모든
														// HTML 가져오기

	    var buttonContainer1 = document.getElementById('buttonContainer');
	    buttonContainer1.innerHTML = buttonsToCopy;

	    var myModal = document.getElementById('myModal');
	    var modal = new bootstrap.Modal(myModal);
	  
	 $('#myModal').modal('show');
}

// 카테고리 모달창 버튼누를때 선택항목
function showButtonById(buttonId) {
    var buttonContainer = document.getElementById('buttonContainer'); 
    var newButton = document.createElement('button'); 
    newButton.className = 'btn btn-success'; 
    newButton.innerHTML = '<img src="./img/cataimg.png" width="50px">' + '<br>' + buttonId;
    newButton.style.margin ='2px';
    newButton.style.width = '150px';
    
    var existingButtons = buttonContainer.querySelectorAll('button'); 
    
    if(existingButtons) { // existingButtons가 null이 아닌지 확인
        var exists = false; // exists 변수 선언 및 초기화

        existingButtons.forEach(function(button) {
        	var spanText = button.querySelector('span[id="cateName"]');
            if(spanText && spanText.textContent) {
                var spanTextArray = spanText.textContent.split(','); // spanText를
																		// 배열로
																		// 변환
                if (spanTextArray.includes(buttonId)) { // 배열에 buttonId가 포함되어
														// 있는지 확인
                    exists = true; 
                }
            }
        });
        
        // 중복이 아니라면 새로운 버튼 추가
        if (!exists) {
            newButton.id = buttonId; 
            buttonContainer.appendChild(newButton); 
            
            // 서버로 버튼 ID 값 전송
            $.ajax({
                url: '/sendButtonId',
                type: 'POST',
                data: { buttonId: buttonId },
                dataType: 'json',
                success: function(response) {
                    console.log('버튼 ID 값 전송 완료:', response);
                },
                error: function(xhr, status, error) {
                    console.error('버튼 ID 값 전송 실패:', error);
                }
            });
            
        } else {
        	alert('이미 존재하는 카테고리입니다.');
        }  
    } else {
        console.error('existingButtons가 null입니다.');
    }
}


function copyButtons() {

    $('#myModal').modal('hide'); // 모달을 닫는다.
    window.location.reload();
}

// 카테고리삭제
function deleteCategory(button) {
	 var no = $(button).find('span#cateNo').text().trim();
	 var categoryName = $(button).find('span').eq(1).text().trim();
    var confirmDelete = confirm('"' + categoryName + '" 카테고리를 삭제하시겠습니까?');
    
    console.log(categoryName);
    
    if (confirmDelete) {
        $(button).remove(); // 버튼 삭제

        // 서버로 삭제 요청을 보내는 등의 작업을 수행할 수 있습니다.
        $.ajax({
            url: '/deleteCategory/'+no,
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                console.log('카테고리 삭제 요청 완료:', response);
                window.location.reload();
            },
            error: function(xhr, status, error) {
                console.error('카테고리 삭제 요청 실패:', error);
                window.location.reload();
            }
         
        });
    }
}


// 내약등록 모달
function openModal2() {
	
	 $('#myModal2').modal('show');
}

$(document).ready(function() {
    console.log("찍히니?");

    // 검색 input 엘리먼트에 대한 keypress 이벤트 핸들러 추가
    $('#searchModelInput2').keypress(function(event) {
        if (event.which === 13) {
            event.preventDefault(); // 기본 동작 중지
            search(); // 엔터 키를 눌렀을 때 검색 실행
        }
    });

    // 검색 버튼에 대한 클릭 이벤트 핸들러 추가
    $('#searchModal').click(function() {
        search(); // 검색 버튼을 클릭했을 때 검색 실행
    });

    // 검색 함수
    function search() {
        var input = $('#searchModelInput2').val(); // 입력된 검색어 가져오기

        $.ajax({
            url: '/search/searchModal',
            type: 'POST',
            data: { keyword: input },
            dataType: 'json',
            success: function(response) {
                var htmlContent = '<table class="table table-bordered table-hover">';
                htmlContent += '<thead>';
                htmlContent += '<tr>';
                htmlContent += '<th scope="col">사진</th>';
                htmlContent += '<th scope="col">품명</th>';
                htmlContent += '<th scope="col">증상</th>';
                htmlContent += '<th scope="col">담기</th>';
                htmlContent += '</tr>';
                htmlContent += '</thead>';
                htmlContent += '<tbody>';

                response.forEach(function(data) {
                	console.log(data)
                    var row = '<tr>';
                    row += '<td><img ';
                    if (data.item.itemImage != null) {
                        row += 'src="' + data.item.itemImage + '" alt="Product Image" style="width: 100px; height: 80px"';
                    } else {
                    	row += 'src="/img/noimg.png" alt="No Image" style="width: 100px; height: 80px"'; // 기본
																											// 이미지
																											// 경로
                    }
                    row += '/></td>';
                    row += '<td>' +data.item.itemName + '</td>';
                    row += '<td>' +data.item2.ee_DOC_DATA + '</td>';
                    row += '<td><button class="btn btn-primary btn-sm" id="addButton" style="width: 35px;" onclick="openModal6(this)">+</button></td>';
                    row += '</tr>';
                    htmlContent += row;
                });

                htmlContent += '</tbody>';
                htmlContent += '</table>';

                // 모달 내용 업데이트
                $('#modalContent2').html(htmlContent);

                // 모달 열기
                $('#myModal2').modal('show');
            },
            error: function(xhr, status, error) {
                console.error(xhr.responseText);
            }
        });
    }
});

// 내약품리스트 삭제
function deleteWish(button) {
    var row = button.parentNode.parentNode;
    var no = parseInt(row.cells[0].textContent.trim());
    
    console.log(no);
    // 여기에 해당 번호를 서버로 보내는 코드 추가
    
    
    $.ajax({
        url: "/deletewish/" + no,
        type: "GET",
        success: function(data) {
            console.log("삭제 요청이 성공적으로 완료되었습니다.");
            // 추가적인 로직이 필요하다면 여기에 작성하세요.
        },
        error: function(xhr, status, error) {
            console.error("오류 발생:", error);
            window.location.reload();
        }
    });
}





// 구급약품 등록 모달
function openModal3() {
	$('#myModal3').modal('show');
}

// 구급 검색창
function search() {    
    $('#myModal4').modal('show');
}

function openModal5() {
	$('#myModal5').modal('show');
}




/* 구급의약품 */
function addToList(button) {
  var row = button.parentNode.parentNode; // 버튼이 있는 행 가져오기
  var category = row.cells[0].getAttribute('data-category');
  var itemName = row.cells[1].innerText; // 품명 가져오기
  var packagingUnit = row.cells[2].innerText; // 포장단위 가져오기

  // 구성리스트에 추가
  var compositionTable = document.getElementById("compositionTable").getElementsByTagName('tbody')[0];
  var newRow = compositionTable.insertRow();
    newRow.innerHTML = "<td>" + category + "</td><td>" + itemName + "</td><td>" + packagingUnit + "</td><td><button type='button' class='btn btn-danger' onclick='removeFromList(this)'>-</button></td>";
  
    // 추가한 항목의 버튼을 비활성화
    button.disabled = true;

}

// 구성리스트 삭제
function removeFromList(button) {
  var row = button.parentNode.parentNode; // 버튼이 있는 행 가져오기
  var itemName = row.cells[1].innerText.trim(); // 품명 가져오기
  
  row.parentNode.removeChild(row); // 행 삭제
  
  // 추가 버튼 활성화 함수 호출
  activateAddButton(itemName);
    
}
// 기존리스트 버튼활성화
function activateAddButton(itemName) {
	  var addButton = document.querySelector("button[data-item='" + itemName + "']");
	    if (addButton) {
	        addButton.disabled = false; // 해당 아이템에 대한 추가 버튼 활성화
	    }
}

// 담기버튼 데이터 보내기
function addkitList(button) {
    // 해당 행에서 아이템 정보 추출
	console.log("아");
	var row = button.parentNode.parentNode;
	var itemCategory = row.cells[0].textContent.trim();
	var itemName = row.cells[1].textContent.trim();
	var packagingUnit = row.cells[2].textContent.trim();
	var currentDate = new Date();
	
    // Ajax 요청을 위한 데이터 설정
    var data = {
        'itemCategory': itemCategory,
        'itemName': itemName,
        'packagingUnit': packagingUnit,
        'registerDate':currentDate
    };
    
    // Ajax 요청
    $.ajax({
        url: '/enroll', 
        method: 'POST',
        async: true, 
        dataType: 'json', 
        contentType: 'application/json', // 요청의 Content-Type을 JSON으로 설정
        data: JSON.stringify(data), // 데이터를 JSON 문자열로 직렬화하여 전달
        success: function() {
        },
        error: function(xhr, status, error) {
            // 오류 시 처리할 내용
        }
    });
    console.log("닫자");
	  $('#myModal3').modal('hide'); // 모달을 닫는다.
	  window.location.reload();
}

// 구급약품 삭제
function deleteKit(button) {
    var row = button.parentNode.parentNode;
    var no = parseInt(row.cells[0].textContent.trim());
    
    console.log(no);
    // 여기에 해당 번호를 서버로 보내는 코드 추가
    
    
    $.ajax({
        url: "/delete/" + no,
        type: "GET",
        success: function(data) {
            console.log("삭제 요청이 성공적으로 완료되었습니다.");
            // 추가적인 로직이 필요하다면 여기에 작성하세요.
        },
        error: function(xhr, status, error) {
            console.error("오류 발생:", error);
            window.location.reload();
        }
    });
}



var memImg, wishName, wishFun;

// 기간등록모달
function openModal6(button) {
    console.log("openModal6");
	 // 클릭한 + 버튼의 부모 행(tr) 요소를 찾습니다.
	var row = button.parentNode.parentNode;

    // 행에서 각 셀(td)의 텍스트를 가져와서 전역 변수에 저장합니다.
    memImg =row.cells[0].querySelector('img').getAttribute('src');
    wishName = row.cells[1].textContent.trim();
    wishFun =  row.cells[2].textContent.trim();
    
	
    console.log(memImg);
    console.log(wishName);
    console.log(wishFun);
    
  
		 $('#myModal6').modal('show');
	}



// 기간선택(데이터넘기기)
function select() {
    console.log("select");

    // 모달에서 필요한 정보 추출
    var endDate = document.getElementById("myDate").value;
    var count = document.getElementById("quantity").value;

    console.log(memImg);
    console.log(wishName);
    console.log(wishFun);
    
    
    
    var data = {
        'endDate': endDate,
        'count': count,
        'memImg': memImg,
        'wishName': wishName,
        'wishFun': wishFun
    };

    $.ajax({
        type: "POST",
        url: "/select", // 실제 저장을 담당하는 컨트롤러의 엔드포인트
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function(response) {
            // 저장 성공 시 수행할 작업
            console.log("Data saved successfully!");
            // 모달 닫기
        },
        error: function(xhr, status, error) {
            // 저장 실패 시 수행할 작업
            console.error("Error occurred while saving data:", error);
        }
    });
    console.log("닫자");
    $('#myModal6').modal('hide');

}

// 내약 저장버튼 업데이트
function Update() {
	window.location.reload();
}

// 이미지
var element = document.querySelector('.col-md-2.ps-0');

// 스크롤 이벤트 감지
window.addEventListener('scroll', function() {
    // 스크롤된 거리만큼 요소의 위치를 조정
    element.style.top = (50 + window.scrollY) + 'px';
});







