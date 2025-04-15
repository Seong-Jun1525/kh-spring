<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Spring Project</title>
	<style>
		main {
			height: 600px;
		}
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
	<jsp:include page="common/header.jsp" />
	<main class="outer">
		<br><br>
		<div class="inner-area">
			<h4>게시글 Top 5</h4>
	          <br>
	          <table id="top5-board-list" class="table table-hover" align="center" style="text-align: center;">
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
	              <tbody align="center">
	                  <tr>
	                      <td colspan="6" rowspan="4" align="center">
	                          <div class="spinner-border text-primary"></div>
	                      </td>
	                  </tr>
	              </tbody>
	          </table>			
		</div>		
	</main>
	<jsp:include page="common/footer.jsp" />
	
	<script>
		window.addEventListener("load", () => {
			console.log("준비!");
			selectboardRank();
		});
		
		function selectboardRank() {
			$.ajax({
				url: "/api/board/top5",
				method: "get",
				success: (result) => {
					console.log(result);
					
					// 글 목록 업뎃
    				let data = "";
    				for(const r of result) {
    					data += "<tr>"
    						+ "<td>" + r.boardNo + "</td>"
    						+ "<td>" + r.boardTitle + "</td>"
    						+ "<td>" + r.boardWriter + "</td>"
    						+ "<td>" + r.count + "</td>"
    						+ "<td>" + r.createDate + "</td>"
    						+ "<td>" + (r.originName === null ? "" : "■") + "</td>"
    						+ "</tr>";
    				}
    				
    				$("#top5-board-list tbody").html(data);
				},
				error: (error) => {
					console.log(error);
				}
			});
		}
	</script>
</body>
</html>