<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.spring.member.model.vo.Member, com.kh.spring.notice.model.vo.Notice" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항 수정하기</title>
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
        #updateForm>table td {padding-right: 1.2rem;}
    </style>
</head>
<body>
    <%-- header --%>
    <jsp:include page="../common/header.jsp" />
    <%
    	Member m = (Member) session.getAttribute("loginMember");
    	Notice n = (Notice) request.getAttribute("notice");
    %>

    <div class="outer">
        <br><br>
        <div class="inner-area">
            <h2>공지사항 수정하기</h2>
            <br><br>

            <form id="updateForm" method="post" action="updateNotice?noticeNo=${notice.noticeNo}" enctype="">
                <table align="center" class="table">
                    <tr>
                        <th width="100"><label for="title">제목</label></th>
                        <td>
                            <input type="text" name="noticeTitle" id="title" class="form-control" value="${notice.noticeTitle}" required />
                        </td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td>
                            <input type="text" name="noticeWriter" id="writer" class="form-control" value="${notice.noticeWriter}" readonly />
                        </td>
                    </tr>
                    <tr>
                        <th colspan="2"><label for="content">내용</label></th>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <textarea name="noticeContent" id="content" class="form-control" rows="10" style="resize:none;" required>${notice.noticeContent}</textarea>
                        </td>
                    </tr>
                </table>
                <br>

                <div align="center">
	                <!-- 작성자와 로그인한 계정이 동일한 경우만 표시 -->
		            <% if(m != null && m.getUserId().equals(n.getNoticeWriter())) { %>
	                    <button type="submit" class="btn btn-primary">수정</button>
	                    <button type="button" class="btn btn-danger" onclick="history.go(-1);">이전</button>
	            	<% } %>
                </div>
            </form>
        </div>
        <br><br>

    </div>

    <%-- footer --%>
    <jsp:include page="../common/footer.jsp" />
</body>
</html>