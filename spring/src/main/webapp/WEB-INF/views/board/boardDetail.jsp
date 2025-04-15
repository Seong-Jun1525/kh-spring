<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.spring.board.model.vo.Reply, com.kh.spring.board.model.vo.Board, java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 상세보기</title>
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
    <%@ include file="../common/header.jsp" %>
    <% 
		ArrayList<Reply> replyList = (ArrayList<Reply>) request.getAttribute("reply");
    	Board board = (Board)request.getAttribute("board");
    	String memberId = board.getBoardWriter();
    %>

    <div class="outer">
        <br><br>
        <div class="inner-area">
            <h2>게시글 상세보기</h2>
            <br>
            <a href="/board/list" class="btn btn-secondary" style="float:right;">목록보기</a>
            <br><br>

            <table align="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">
                        ${ board.boardTitle }
                    </td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>
                        ${ board.boardWriter }
                    </td>
                    <th>작성일</th>
                    <td>
                        ${ board.createDate }
                    </td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                        <%--${ board.originName } --%>
                        <a href="${board.changeName }" download><img src="${ board.changeName }" alt="${board.changeName }"></a>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <p style="height:150px;">
							${ board.boardContent }
                        </p>
                    </td>
                </tr>
            </table>
            <br>

            <!-- 작성자와 로그인한 계정이 동일한 경우만 표시 -->
            <% if(loginMember.getUserId().equals(memberId)) { %>
	            <div align="center">
		                <a href="/board/update-page?boardNo=${ board.boardNo }" class="btn btn-primary">수정</a>
		                <a id="deleteBtn" class="btn btn-danger">삭제</a>
	            </div>
            <% } %>
            <br><br>

            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                        <th colspan="2">
                            <textarea name="replyContent" id="content" cols="55" rows="2" class="form-control" style="resize: none;"></textarea>
                        </th>
                        <th style="vertical-align:middle;">
                            <button id="replyBtn" class="btn btn-secondary">등록</button>
                        </th>
                    </tr>
                    <tr>
                        <%--
                        <td colspan="3">댓글 (<span id="rcount"><%= replyList.size() %></span>)</td>
                         --%>
                    </tr>
                </thead>
                <tbody>
                	<%--
                	<% for(Reply r : replyList) { %>
                    <tr>
                        <th><%= r.getReplyWriter() %></th>
                        <td><%= r.getReplyContent() %></td>
                        <td><%= r.getCreateDate() %></td>
                    </tr>                        
                    <% } %>
                	 --%>
                </tbody>
            </table>     
            <br><br>
        </div>


    </div>

    <%-- footer --%>
    <jsp:include page="../common/footer.jsp" />  
    
    <script>
    	window.addEventListener("load", () => {
    		deleteBoard();
    		insertReply();
    		selectReplyList();
    	});
    	
    	const deleteBoard = () => {
    		$("#deleteBtn").click(() => {
    			Swal.fire({
    				title: "게시글 삭제",
    				icon: "question",
    				text: "게시글을 삭제하시겠습니까?",
    				confirmButtonColor: "#F00",
    				confirmButtonText: "삭제",
    				showCancelButton: "true",
    				cancelButtonText: "취소"
    			}).then((result) => {
	    			if (result.isConfirmed) {
    					location.href = "/board/delete?boardNo=${board.boardNo }";
	    			}
    			});
    		});
    	}
    	
    	// 모든 요소가 로드되었을 때 버튼을 찾을 수 있음
    	// 기본적으로는 onload안에 작성한다 하지만 페이지가 많을경우 가장 마지막의 onload가 실행되므로
    	// window.addEventListener("load", () => {}) 메서드를 사용한다
    	// js를 외부 방식으로 할 경우 스크립트가 먼저 실행될 수도 있으므로 load 안에 작성해야한다
    	
    	const insertReply = () => {
    		$("#replyBtn").click(() => {
    			// 입력된 댓글 내용을 추가 요청 => 비동기 통신	
    			// console.log("c");
    			addReply();
    		});
    	}
    	
    	
    	function addReply() {
    		// 입력된 값이 없을 경우 메시지 표시하고 요청 막기
    		if($("#replyArea #content").val() === "") {
    			Swal.fire({
    				title: "댓글 등록",
    				icon: "wraning",
    				text: "댓글 작성 후 등록 가능합니다."
    			}).then((result) => {
    				return;
    			});
    		}
    	
    		// 비동기 통신 => ajax
    		$.ajax({
    			url: "/api/board/reply",
    			method: "post", // 혹은 type
    			data: {
    				replyContent: $("#replyArea #content").val(), // 댓글 내용
    				replyWriter: "${loginMember.userId}", // 로그인 사용자의 아이디
    				refBno: ${board.boardNo}
    			},
    			success: (result) => {
    				console.log(result);
    				
    				if(result === "success") {
    					selectReplyList();
    					$("#replyArea #content").val("");
    				}
    				
    			},
    			error: (error) => {
    				console.log(error);
    			}
    		});
    	}
    	
    	function selectReplyList() {
    		$.ajax({
    			url: "/api/board/reply",
    			method: "get",
    			data: {
    				boardNo: ${board.boardNo}
    			},
    			success: (list) => {
    				console.log(list);
    				$("#replyArea #rcount").text(list.length);
    				
    				// 댓글 목록 업뎃
    				let replyData = "";
    				for(const r of list) {
    					replyData += "<tr>"
    						+ "<th>" + r.replyWriter + "</th>"
    						+ "<td>" + r.replyContent + "</td>"
    						+ "<td>" + r.createDate + "</td>"
    						+ "</tr>";
    				}
    				
    				$("#replyArea tbody").html(replyData);
    			}
    		});
    	}
    </script>
</body>
</html>