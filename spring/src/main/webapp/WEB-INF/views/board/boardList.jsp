<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.spring.board.model.vo.Board, java.util.ArrayList, com.kh.spring.common.PageInfo, com.kh.spring.member.model.vo.Member" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 목록</title>
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

        #boardList {text-align: center;}
        #boardList>tbody>tr:hover{cursor:pointer;}

        #pagingArea {width:fit-content; margin:auto;}

        #searchForm {width:80%; margin: auto;}
        #searchForm>* {float:left; margin:5px;}
        
        .select {width:25%;}
        .text {width:48%;}
        .searchBtn{width:20%;}
    </style>
</head>
<body>
	<%
		ArrayList<Board> bList = (ArrayList<Board>)request.getAttribute("bList");
		PageInfo pi = (PageInfo) request.getAttribute("pi");
	%>
    <%-- header --%>
    <%-- <jsp:include page="../common/header.jsp" /> --%>
    <%@ include file="../common/header.jsp" %>
    
    <%--
    	header.jsp 안에 이미 로그인 정보가 있으므로 
    	액션태그가 아닌 include 지시어를 사용하면
    	header.jsp안에 선언한 변수를 사용 가능하다
     --%>

    <div class="outer">
        <br><br>
        <div class="innerOuter" style="padding: 5% 10%">
            <h2>자유 게시판</h2>
            <br>

            <%-- 로그인 시에만 글쓰기 버튼 표시 --%>
            <% if(loginMember != null) { %>
            	<a href="/board/enrollForm" class="btn btn-secondary" style="float:right">글쓰기</a>
            <% } %>
            <br>
            
            <br>
            <table id="boardList" class="table table-hover" align="center">
                <thead>
                    <tr>
                    	<th>글번호</th>
	                    <th>제목</th>
	                    <th>작성자</th>
	                    <th>조회수</th>
	                    <th>작성일</th>
	                    <th>첨부파일</th>
                    </tr>
                </thead>

                <tbody>
                	<% if(bList != null) { %>
	                	<% for(Board b : bList) { %>
		                    <tr>
		                        <td><%= b.getBoardNo() %></td>
		                        <td><%= b.getBoardTitle() %></td>
		                        <td><%= b.getBoardWriter() %></td>
		                        <td><%= b.getCount() %></td>
		                        <td><%= b.getCreateDate() %></td>
		                        <td>
		                        	<% if(b.getOriginName() != null ) { %>
		                        		■
		                        	<% } %>
		                        </td>
		                    </tr>
	                    <% } %>
                    <% } else { %>
                    	<tr><td colspan="6">조회된 데이터가 없습니다.</td></tr>
                    <% } %>                                        
                </tbody>
            </table>
            <br>
            
            <%
            	int currPage = 0, startPage = 0, endPage = 0, maxPage = 0;
            
            	if(pi != null) {
            		currPage = pi.getCurrPage();
            		startPage = pi.getStartPage();
            		endPage = pi.getEndPage();
            		maxPage = pi.getMaxPage();
            	}
            %>

            <div id="pagingArea">
                <ul class="pagination">
                <% if(currPage == 1) { %>
                		<li class="page-item disabled"><a class="page-link">Prev</a></li>
                <% } else {%>
                		<%--
                		 <li class="page-item"><a href="/board/list?cpage=<%= currPage - 1 %>&condition=${condition}&keyword=${keyword}" class="page-link">Prev</a></li>
                		--%>
                		<li class="page-item"><a data-current="<%= currPage - 1 %>" class="page-link">Prev</a></li>
                		<% } %>
                <% for(int idx = startPage; idx <= endPage; idx++) { %>
                		<%--
                		 <li class="page-item"><a href="/board/list?cpage=<%= idx %>&condition=${condition}&keyword=${keyword}" class="page-link <% if (currPage == idx) { %>active<% } %>"><%= idx %></a></li>
                		--%>
                		<li class="page-item">
                			<a data-current="<%= idx %>" class="page-link <% if (currPage == idx) { %>active<% } %>"><%= idx %></a>
                		</li>
                		<% } %>
                <% if(currPage == maxPage) { %>
                    	<li class="page-item disabled"><a class="page-link">Next</a></li>
                <% } else { %>
                    	<%-- 
                    	<li class="page-item"><a href="/board/list?cpage=<%= currPage + 1 %>&condition=${condition}&keyword=${keyword}" class="page-link">Next</a></li>
                    	 --%>
          	            <li class="page-item"><a data-current="<%= currPage + 1 %>" class="page-link">Next</a></li>
                <% } %>
                </ul>
            </div>

            <br clear="both">

            <form action="/board/list" id="searchForm">
                <div class="select">
                    <select name="condition" id="" class="custom-select form-select">
                        <option value="writer">작성자</option>
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                    </select>
                </div>
                <div class="text">
                    <input type="text" class="form-control" value="${ keyword }" name="keyword">
                </div>
                <button class="searchBtn btn btn-secondary">검색</button>
            </form>
            <br><br>
        </div>
    </div>

    <%-- footer --%>
    <jsp:include page="../common/footer.jsp" /> 
    
    <script>
    	/*
    	onload = 함수
    	이렇게 하면 마지막 onload만 실행되는 문제가 발생됨
    	*/
    	window.addEventListener("load", () => {
    		/* 자유게시판 상세페이지 이동 */
    		const boardList = document.querySelectorAll("#boardList tbody tr");
    		
    		console.log(boardList);
    		
    		for(let boardItem of boardList) {
    			boardItem.addEventListener("click", () => {
    				location.href = "/board/detail?boardNo=" + boardItem.children[0].innerText;
    			});
    		}
    		
    		/* 검색 조건 초기화 */
    		const condition = "${condition}";
    		// console.log(condition);
    		
    		if(condition !== "") {
    			const optionList = document.querySelectorAll("#searchForm select[name=condition] option");
    		
    			for(const ele of optionList) {
    				// console.log(ele.value);
    				if(condition === ele.value) {
    					ele.setAttribute("selected", true);
    					break;
    				}
    			}
    		}
    		
    		const linkList = document.querySelectorAll("#pagingArea a[data-current]"); // 비활성화된 요소도 선택되므로 [data-current] 추가
    		// console.log(linkList);
    		
    		for(let l of linkList) {
    			l.addEventListener("click", () => {
    				// console.log("click!");
    				
    				let keyword = "${keyword}";
    				
    				let requestUrl = "/board/list?cpage=" + l.getAttribute("data-current");
    				
    				if(keyword !== "") {
    					requestUrl += "&condition=" + document.querySelector("select[name=condition]").value + "&keyword=" + keyword;
    				}
    				
    				l.href = requestUrl;
    			});
    		}
    	});
    </script>
</body>
</html>