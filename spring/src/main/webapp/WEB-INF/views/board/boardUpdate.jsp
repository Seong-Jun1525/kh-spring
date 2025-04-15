<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.spring.member.model.vo.Member, com.kh.spring.board.model.vo.Board" %>
<%
	Member m = (Member) request.getSession().getAttribute("loginMember");
	Board b = (Board)request.getAttribute("board");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 수정하기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    
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
        
        #updateForm > table {width:100%;}
        #updateForm > table * {margin:5px;}
    </style>    
</head>
<body>
    <jsp:include page="../common/header.jsp" />

    <div class="outer">
        <br><br>
        <div class="inner-area">
            <h2>게시글 수정하기</h2>
            <br><br>

			<%--
				enctype 요청형식
				
				=> TCP School 참조
					<form> 태그의 enctype 속성은 폼 데이터(form data)가 서버로 제출될 때 해당 데이터가 인코딩되는 방법을 명시합니다.
					이 속성은 <form> 요소의 method 속성값이 “post”인 경우에만 사용할 수 있습니다.
					
					application/x-www-form-urlencoded | 기본값으로, 모든 문자들은 서버로 보내기 전에 인코딩됨을 명시함.
					--------------------------------------------------------------------------------------------------------------------------
					multipart/form-data				  | 모든 문자를 인코딩하지 않음을 명시함.
														이 방식은 <form> 요소가 파일이나 이미지를 서버로 전송할 때 주로 사용함.
					--------------------------------------------------------------------------------------------------------------------------
					text/plain						  | 공백 문자(space)는 "+" 기호로 변환하지만, 나머지 문자는 모두 인코딩되지 않음을 명시함.
			 --%>

            <form id="updateForm" method="post" action="/board/update?boardNo=${board.boardNo }" enctype="multipart/form-data">
                <table align="center" class="table">
                    <tr>
                        <th width="100"><label for="title">제목</label></th>
                        <td>
                            <input type="text" name="boardTitle" id="title" class="form-control" value="${ board.boardTitle }" required />
                        </td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td>
                            <input type="text" name="boardWriter" id="writer" class="form-control" value="${ board.boardWriter }" readonly />
                        </td>
                    </tr>
                    <tr>
                        <th><label for="upfile">첨부파일</label></th>
                        <td>
                            <input type="file" name="upfile" id="upfile" class="form-control-file border"><br>
                            <% if(b.getOriginName() != null) { %>
	                            첨부 파일 : <a href="${board.changeName }" download>${board.originName }</a>
	                            <input type="hidden" name="originName" value="${board.originName }" />
	                            <input type="hidden" name="changeName" value="${board.changeName }" />
                        	<%} %>
                        </td>
                    </tr>
                    <tr>
                        <th colspan="2"><label for="content">내용</label></th>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <textarea name="boardContent" id="content" class="form-control" rows="10" style="resize:none;" required>${ board.boardContent }</textarea>
                        </td>
                    </tr>
                </table>
                <br>

                <div align="center">
                    <!-- 작성자와 로그인한 계정이 동일한 경우만 표시 -->
                    <% if(m.getUserId().equals(b.getBoardWriter())) { %>
	                    <button type="submit" class="btn btn-primary">수정</button>
	                    <button type="button" class="btn btn-danger" onclick="history.go(-1);">이전</button>
                    <% } %>
                </div>
                <br><br>
            </form>
        </div>


    </div>

    <%-- footer --%>
    <jsp:include page="../common/footer.jsp" /> 
</body>
</html>