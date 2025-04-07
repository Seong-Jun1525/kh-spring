<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%--
	회원 객체를 필요로 하므로 import를 해야함
	클래스를 import 하기위해 page 지시어와 함께 import에 추가할 클래스의 경로를 작성한다
--%>
<%@ page import="com.kh.spring.member.model.vo.Member"  %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MenuBar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        #top-area { display: flex; justify-content: space-around; padding: 1em;}

        a { text-decoration: none; color: black; }

        nav {margin:0;padding:0;}
        nav div {font-weight: bold; height: 50px; line-height: 50px; }
        nav div:hover, nav div.active { cursor: pointer;}
        nav div.active { background-color: #485ba6; color: white; }
    </style>
</head>
<body>
	<%
		// 스크립트릿 태그 내부
		// 세션에 저장된 값들을 getAttribute("키값") 으로 가져와서 변수에 저장한다
		// 이때 반환 값은 Object이므로 다운캐스팅을 해줘야 한다
		String alertTitle = (String) session.getAttribute("alertTitle");
		String alertMsg = (String) session.getAttribute("alertMsg");
		String alertIcon = (String) session.getAttribute("alertIcon");
		Member loginMember = (Member) session.getAttribute("loginMember");
	%>
    <div id="top-area">
        <img src="https://khedu.co.kr/resources/images/main/logo.svg" alt="kh_logo" width="150" />
        <div></div>
        <!-- 로그인 전 보여질 화면 -->
        <%
        	if(loginMember == null) {
        %>
        <div>
            <a href="member/enrollForm">회원가입</a> &nbsp;|&nbsp;
            <a href="#" data-bs-toggle="modal" data-bs-target="#loginModal">로그인</a>
        </div>
		<%
        	} else {
		%>
        <!-- 로그인 후 보여질 화면 -->
        <div>
            <label><%= loginMember.getUserName() %></label> &nbsp;&nbsp;
            <a href="member/myPage">마이페이지</a>
            <button type="button" class="btn btn-primary btn-sm" onclick="logoutAlert();">로그아웃</button>        
        </div>
        <% } %>
    </div>
    <hr>
    <nav class="container text-center">
        <div class="row">
            <div class="active col-3" data-url="">HOME</div>
            <div class="col" data-url="notice">공지사항</div>
            <div class="col" data-url="board">자유게시판</div>
            <div class="col" data-url="picture">사진게시판</div>
        </div>
    </nav>
    <!-- 로그인 모달 -->
	<div class="modal fade" id="loginModal">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5">LOGIN</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="/member/login" method="post">
                <div class="modal-body">
                    <div class="mb-3">
                    <label for="userId" class="col-form-label">ID:</label>
                    <input type="text" class="form-control" placeholder="Enter ID.." name="userId" id="userId" />
                    </div>
                    <div class="mb-3">
                    <label for="userPwd" class="col-form-label">PASSWORD:</label>
                    <input type="password" class="form-control" placeholder="Enter Password.." name="userPwd" id="userPwd" />
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">로그인</button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">취소</button>
                </div>
            </form>
          </div>
        </div>
	</div>
    <script>
			<% if(alertMsg != null) { %>
					Swal.fire({
						title: "<%= alertTitle %>",
						text: "<%= alertMsg %>",
						icon: "<%= alertIcon %>",
						confirmButtonText: "확인"
					});
			<% 
					// session 객체는 JSP에 내장되어 있으므로 따로 import 하지 않아도 사용가능!!
					if(session.getAttribute("alertMsg") != null) {
						session.removeAttribute("alertMsg");
					}
				}
			%>
      
		    window.onload = () => {            
		        const menuList = document.querySelectorAll("nav div[data-url]");
		        showMenu(menuList);
		        addMenuClickEvent(menuList);
		        
		    }
		    const removeActiveClass = (list) => {
		        list.forEach((menu)=>{
		            menu.classList.remove("active");
		        });
		    }
		    const addMenuClickEvent = (list) => {
		    	list.forEach((menu)=>{              
		            menu.addEventListener('click', (ev)=>{   
		            	sessionStorage.setItem("menu", ev.target.innerText);
		            	showMenu(list);
		            	
		            	// 메뉴 클릭 시 페이지 이동 요청(특정 페이지 요청)
		            	// html5 에서 지원. 사용자 정의 데이터 속성 : data-url
		            	// console.log(ev.target.getAttribute("data-url"));
		            	
		            	let requestUrl = "/" + ev.target.getAttribute("data-url");
		            	
		            	if(requestUrl !== "/") requestUrl += "/list";
		            	
		            	location.href =  requestUrl
		            });
		        });
		    }
		    const showMenu = (list) => {
		    	const selMenu = sessionStorage.getItem("menu");
		    	if (selMenu) {
					list.forEach((menu)=>{
					         	if (menu.innerText == selMenu) {              
					      			removeActiveClass(list);         
					             	menu.classList.add("active");
					             	return;
					             }       
					         });        
		        }
		    }
		    
		    const logoutAlert = () => {
		    	Swal.fire({
		    		title: "로그아웃",
		    		icon: "warning",
		    		text: "정말 로그아웃 하시겠습니까?",
		    		confirmButtonText: "로그아웃",
		    		showCancelButton: true
		    	}).then((result) => {
		    		if(result.isConfirmed) location.href = "/member/logout";
		    	});
		    }
      </script>
</body>
</html>