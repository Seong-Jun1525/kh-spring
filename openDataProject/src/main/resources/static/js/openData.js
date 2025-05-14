window.addEventListener("load", function() {
	const btn1 = document.getElementById("btn1");
	btn1.addEventListener(("click"), () => {
		console.log("클릭");
		getAirPollution();
	});
});

// 대기오염 정보 조회
const getAirPollution = (currentPageNo) => {
	console.log(currentPageNo)
	currentPageNo = currentPageNo !== undefined ? currentPageNo : "";
	let locationList = [];
	// [GET] /air-pollutions?location="선택된지역정보"
	
	// 요청 전 작업 => 선택된 지역정보값을 가져와야함
	
	let locationName = document.getElementById("location").value;
	let pageInfo = null;
//	let currentPageNo = document.querySelector("").value;
	
	// 지역 정보를 담아 조회 요청 => 비동기 요청(fetch)
	fetch("/air-pollutions?locationName=" + locationName + "&currentPageNo=" + currentPageNo)
		// response.text() => 문자열, 숫자, ... 응답데이터추출
		// response.json() => 객체 형태로 응답데이터추출
		.then((response) => { return response.json(); })
		.then((data) => {
			console.log(data); // 응답 결과 확인
			pageInfo = data.pageInfo;
			console.log(pageInfo)
			
			for(item of data.list) {
				console.log(item.stationName)
				locationList.push(item)
			}
		})
		.then(() => {
			const tbody = document.querySelector("tbody");
			const pagination = document.querySelector(".pagination");
			let html = "";
			let navItem = "";
			for(let i = 0; i < locationList.length; i++) {
				html += "<tr>";
				html += "<td>" + locationList[i].stationName + "</td>"
						+ "<td>" + locationList[i].dataTime + "</td>"
								+ "<td>" + locationList[i].khaiValue + "</td>"
										+ "<td>" + locationList[i].pm10Value + "</td>"
												+ "<td>" + locationList[i].coValue + "</td>"
														+ "<td>" + locationList[i].no2Value + "</td>"
																+ "<td>" + locationList[i].so2Value + "</td>"
																		+ "<td>" + locationList[i].o3Value + "</td>"
				html += "</tr>";
			}
			
			navItem += '<li class="page-item"><a class="page-link data-curr="' + (pageInfo.currentPageNo - 1) + '">Prev</a></li>';
			console.log(pageInfo.endPageNo);
			for(let i = pageInfo.startPageNo; i <= pageInfo.endPageNo; i++) {
				console.log(i);
				navItem += '<li class="page-item">'
						+ '<a class="page-link" href="#" data-curr="'+ i +'">' + i + '</a>'
						+ '</li>';
				
				console.log(navItem)
			    
			}
			navItem += '<li class="page-item"> <a class="page-link" href="#">Next</a></li>';
			
			tbody.innerHTML = html;
			pagination.innerHTML = navItem;
		})
		.then(() => {
			let pagingArr = document.querySelectorAll(".page-link");
			console.log(pagingArr);
			if(pagingArr != null) {
				for(let i = 1; i <= pageInfo.pageLimit; i++) {
					pagingArr[i].onclick = function() {
						let currentPageNo = pagingArr[i].getAttribute("data-curr");
						getAirPollution(currentPageNo);
					}
				}
			}
		})
		.catch((error) => { console.log(error); }); // 오류 확인
}