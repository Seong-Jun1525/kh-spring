<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.spring.notice.model.vo.Notice, com.kh.spring.member.model.vo.Member, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>공지사항 목록</title>
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

        #noticeList {text-align: center;}
        #noticeList>tbody>tr:hover{cursor:pointer;}

        #pagingArea {width:fit-content; margin:auto;}

        #searchForm {width:80%; margin: auto;}
        #searchForm>* {float:left; margin:5px;}
        
        .text {width:75%;}
        .searchBtn{width:20%;}
    </style>
</head>
<body>
    <%-- header --%>
    <jsp:include page="../common/header.jsp" />
    
    <%
    	ArrayList<Notice> nList = (ArrayList<Notice>)request.getAttribute("nList");
    %>

    <div class="outer">
        <br><br>
        <div class="innerOuter" style="padding: 5% 10%">
            <h2>공지사항</h2>
            <br>

            <%-- admin 계정일때만 글쓰기 버튼 표시 --%>
            <%
				Member m = (Member) session.getAttribute("loginMember");
            	if(m != null && m.getUserId().equals("admin")) {
            %>
            		<a href="/notice/write" class="btn btn-secondary" style="float:right">글쓰기</a>
            <%
            	}
            %>
            <br>
            
            <br>
            <table id="noticeList" class="table table-hover" align="center">
                <thead>
                	<tr>
	                    <th>글번호</th>
	                    <th>제목</th>
	                    <th>작성자</th>
	                    <th>작성일</th>
                	</tr>
                </thead>

                <tbody>
                	<% if(nList != null) {
                			for(Notice n : nList) {
                	%>
	                    		<tr>
	                    			<td><%= n.getNoticeNo() %></td>
	                    			<%-- <td><a href="noticeDetail?noticeNo=<%= n.getNoticeNo() %>"><%= n.getNoticeTitle() %></a></td> --%>
	                    			<td><%= n.getNoticeTitle() %></td>
	                    			<td><%= n.getNoticeWriter() %></td>
	                    			<td><%= n.getCreateDate() %></td>
	                    		</tr>
                    <% 
                			}
               			} else {%>
                    	<tr>
                    		<td colspan="4">조회된 데이터가 없습니다.</td>
                    	</tr>
                    <% } %>                                      
                </tbody>
            </table>
            <br>

            <div id="pagingArea">
                <ul class="pagination">
                    <li class="page-item"><a href="" class="page-link">Prev</a></li>
                    <li class="page-item"><a href="" class="page-link">1</a></li>
                    <li class="page-item"><a href="" class="page-link">2</a></li>
                    <li class="page-item"><a href="" class="page-link">3</a></li>
                    <li class="page-item"><a href="" class="page-link">4</a></li>
                    <li class="page-item"><a href="" class="page-link">5</a></li>
                    <li class="page-item"><a href="" class="page-link">Next</a></li>
                </ul>
            </div>

            <br clear="both">

            <form action="" id="searchForm">
                <div class="text">
                    <input type="text" class="form-control" name="keyword" placeholder="검색할 제목을 입력하세요.">
                </div>
                <button class="searchBtn btn btn-secondary">검색</button>
            </form>
        </div>
        <br><br>
    </div>
    
    <script>
    	onload = function() {
    		const noticeTrArr = document.querySelectorAll("#noticeList tbody tr");
    		
    		for(const ele of noticeTrArr) {
    			ele.onclick = function() {
	    			// console.log("get!");
	    			location.href = "/notice/noticeDetail?noticeNo=" + ele.children[0].innerText;
	    		}
    		}
    	}
    </script>

    <%-- footer --%>
    <jsp:include page="../common/footer.jsp" />
</body>
</html>