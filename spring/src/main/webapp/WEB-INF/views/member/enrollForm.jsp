<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EnrollForm</title>
    <style>

        .outer {
            background-color: #e7e7e7;
            width: 80%;
            margin: auto;
        }
        .inner-area {
            border: 1px solid #000025;
            width: 80%;
            margin: auto;
            padding: 5% 15%;
            background: #e7ecf7;
        }
    </style>
</head>
<body>
    <%-- header --%>
	<jsp:include page="../common/header.jsp" />

    <div class="outer">
        <br><br>
        <div class="inner-area">
            
            <h2>회원가입</h2>
            <br>
            <!-- 
            	상대경로 => 마지막 / 뒤에 요청 url이 붙어져서 요청됨
            	절대경로 => 포트번호 뒤에 요청 url이 붙어져서 요청됨
             -->
            <form action="/member/regist" method="post">

                <div class="form-group">
                    <label for="userId">* ID </label>
                    <input type="text" class="form-control mb-3" name="userId" id="userId" placeholder="Enter ID.." required>

                    <label for="userPwd">* Password </label>
                    <input type="password" class="form-control mb-3" name="userPwd" id="userPwd" placeholder="Enter Password.." required>

                    <label for="checkPwd">* Password Check </label>
                    <input type="password" class="form-control mb-3" id="checkPwd" placeholder="Enter Password.." required>
                    
                    <label for="userName">* Name </label>
                    <input type="text" class="form-control mb-3" name="userName" id="userName" placeholder="Enter Name.." required>

                    <label for="email"> &nbsp; Email </label>
                    <input type="email" class="form-control mb-3" name="email" id="email" placeholder="Enter Email..">  
                    
                    <label for="age"> &nbsp; Age </label>
                    <input type="number" class="form-control mb-3" name="age" id="age" placeholder="Enter Age..">  
                    
                    <label for="phone"> &nbsp; Phone </label>
                    <input type="tel" class="form-control mb-3" name="phone" id="phone" placeholder="Enter Phone(-제외)..">  
                    
                    <label for="address"> &nbsp; Address </label>
                    <input type="text" class="form-control mb-3" name="address" id="address" placeholder="Enter Address..">

                    <label for=""> &nbsp; Gender</label> &nbsp;&nbsp;
                    <input type="radio" class="mb-3" name="gender" id="Male" value="M">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" class="mb-3" name="gender" id="Female" value="F">
                    <label for="Female">여자</label><br>

                </div>
                <br>
                <div class="btns"  align="center">
                    <button type="submit" class="btn btn-primary">회원가입</button>
                    <button type="button" class="btn btn-danger" onclick="resetAlert();">초기화</button>
                </div>
            </form>
        </div>
        <br><br>
    </div>
    
    <script>
    	const resetAlert = () => {
    		Swal.fire({
    			title: "초기화",
    			icon: "warning",
    			text: "초기화 시 입력한 데이터가 전부 지워집니다.",
    			showConfirmButton: false,
    			showDenyButton: true,
    			denyButtonText: `초기화`,
    			showCancelButton: true
    		}).then((result) => {
    			if(result.isDenied) {
    				document.querySelectorAll("input").map((_, i) => (
    					i = ""
    				));
    			}
    		});
    	}
    </script>

    <%-- footer --%>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>