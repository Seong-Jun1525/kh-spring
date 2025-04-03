<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.spring.member.model.vo.Member, com.kh.spring.notice.model.vo.Notice" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항 상세보기</title>
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
        
        table {width:100%;}
        table * {margin:5px;}
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
            <h2>공지사항 상세보기</h2>
            <br>
            <a href="javascript:history.back();" class="btn btn-secondary" style="float:right;">목록보기</a>
            <br><br>

            <table align="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">
                        ${notice.noticeTitle }
                    </td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>
                        ${notice.noticeWriter }
                    </td>
                    <th>작성일</th>
                    <td>
                        ${notice.createDate }
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <p style="height:150px;">
                        	${notice.noticeContent }
                        </p>
                    </td>
                </tr>
            </table>
            <br>

            <div align="center">
                <!-- 작성자와 로그인한 계정이 동일한 경우만 표시 -->
	            <% if(m != null && m.getUserId().equals(n.getNoticeWriter())) { %>
		            <a href="updateNoticePage?noticeNo=${notice.noticeNo }" class="btn btn-primary">수정</a>
		            <a href="deleteNotice?noticeNo=${notice.noticeNo}" class="btn btn-danger">삭제</a>
            	<% } %>
            
            </div>
        </div>
        <br><br>

    </div>

    <%-- footer --%>
    <jsp:include page="../common/footer.jsp" />
</body>
</html>