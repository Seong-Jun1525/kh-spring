window.addEventListener("load", function() {
	const btn1 = document.getElementById("btn1");
	btn1.addEventListener(("click"), () => {
		console.log("클릭");
//		getAirPollution();
		getAirPollutionUpgrade();
	});
});

// 강사님하고 한 페이징처리
const getAirPollutionUpgrade = async (currentPageNo = 1) => {
	// [GET] /air-pollutions?location="선택된지역정보"&currentPageNo=페이지번호
	let locationName = document.getElementById("location").value;
	
	const response = await fetch("/air-pollutions?locationName=" + locationName + "&currentPageNo=" + currentPageNo);

	const data = await response.json();
	
	console.log(data);
	
	displayAirPollutionData(data.items);
	
	displayPagination(data.totalCount, data.pageNo, data.numOfRows);
}

const displayAirPollutionData = (data) => {
	// 전달된 데이터가 배열인지 아닌지 체크
	if (!Array.isArray(data)) {
		// console.log("data is not array.");
		console.error("data is not array.");
		return;
	}
	
	// tbody 요소에 접근
	const target = document.querySelector("#air-list tbody");
	
	// 전달된 데이터를 활용하여 tr 요소 생성
//	const keyArr = ['stationName', 'dataTime', 'khaiValue', 'pm10Value', 'so2Value', 'coValue', 'no2Value', 'o3Value'];
	console.log(data[0]);
	const keys = Object.keys(data[0]);
	
	console.log(keys);

	if(target.childElementCount > 0) target.textContent = "";

	for (const item of data) {
		let trEle = document.createElement("tr");
		
		for(let i = 0; i < keys.length; i++) {
			let tdEle = document.createElement("td");
			tdEle.textContent = item[keys[i]];
			trEle.appendChild(tdEle);
		}
		
		target.appendChild(trEle);
	}
}

const displayPagination = (totalCount, pageNo, numOfRows) => {
	const totalPage = Math.ceil(totalCount / numOfRows);
	
	const paginationArea = document.getElementById("paginationArea");
	
	// 해당 요소가 있을 경우 비움처리
	if(paginationArea.childElementCount > 0) paginationArea.textContent = "";
	
	// 이전 / 다음 버튼 생성 및 속성 추가
	let prevEle = createPaginationEle(pageNo, totalPage, "Previous");
	let nextEle = createPaginationEle(pageNo, totalPage, "Next");

	// 이전 버튼 요소 추가
	paginationArea.appendChild(prevEle);
	
	// 번호 요소 생성 및 추가
	for(let i = 1; i <= totalPage; i++) {
		let pageNoEle = createPaginationEle(pageNo, totalPage, i);
		
		paginationArea.appendChild(pageNoEle);
	}

	// 다음 버튼 요소 추가
	paginationArea.appendChild(nextEle);
}

const createPaginationEle = (pageNo, totalPage, btnText) => {
	let liEle = document.createElement("li");
	let linkEle = document.createElement("a");

	if(btnText === "Previous") liEle.setAttribute("class", `page-item ${pageNo === 1 ? "disabled" : ""}`);
	else if(btnText === "Next") liEle.setAttribute("class", `page-item ${pageNo === totalPage ? "disabled" : ""}`);
	else liEle.setAttribute("class", `page-item ${pageNo === btnText ? "active" : ""}`);
	
	linkEle.setAttribute("class", "page-link");
	linkEle.setAttribute("href", "javascript:void(0);");

	if(btnText === "Previous") linkEle.setAttribute("onclick", `getAirPollutionUpgrade(${pageNo - 1});`);
	else if(btnText === "Next") linkEle.setAttribute("onclick", `getAirPollutionUpgrade(${pageNo + 1});`);
	else linkEle.setAttribute("onclick", `getAirPollutionUpgrade(${btnText});`);
	
	linkEle.textContent = btnText;
	
	liEle.appendChild(linkEle);
	
	return liEle;
}

// 대기오염 정보 조회 (내가 한거..)
//const getAirPollution = (currentPageNo) => {
//	let locationList = [];
//	// [GET] /air-pollutions?location="선택된지역정보"&currentPageNo=페이지번호
//	
//	// 요청 전 작업 => 선택된 지역정보값을 가져와야함
//	
//	let locationName = document.getElementById("location").value;
//	let pageInfo = null;
////	let currentPageNo = document.querySelector("").value;
//	
//	// 지역 정보를 담아 조회 요청 => 비동기 요청(fetch)
//	fetch("/air-pollutions?locationName=" + locationName + "&currentPageNo=" + currentPageNo)
//		// response.text() => 문자열, 숫자, ... 응답데이터추출
//		// response.json() => 객체 형태로 응답데이터추출
//		.then((response) => { return response.json(); })
//		.then((data) => {
//			console.log(data); // 응답 결과 확인
//			pageInfo = data.pageInfo;
//			console.log(pageInfo)
//			
//			for(item of data.list) {
//				console.log(item.stationName)
//				locationList.push(item)
//			}
//		})
//		.then(() => {
//			const tbody = document.querySelector("tbody");
//			const pagination = document.querySelector(".pagination");
//			let html = "";
//			let navItem = "";
//			for(let i = 0; i < locationList.length; i++) {
//				html += "<tr>";
//				html += "<td>" + locationList[i].stationName + "</td>"
//						+ "<td>" + locationList[i].dataTime + "</td>"
//								+ "<td>" + locationList[i].khaiValue + "</td>"
//										+ "<td>" + locationList[i].pm10Value + "</td>"
//												+ "<td>" + locationList[i].coValue + "</td>"
//														+ "<td>" + locationList[i].no2Value + "</td>"
//																+ "<td>" + locationList[i].so2Value + "</td>"
//																		+ "<td>" + locationList[i].o3Value + "</td>"
//				html += "</tr>";
//			}
//			
//			navItem += '<li class="page-item"><a class="page-link data-curr="' + (pageInfo.currentPageNo - 1) + '">Prev</a></li>';
//			console.log(pageInfo.endPageNo);
//			for(let i = pageInfo.startPageNo; i <= pageInfo.endPageNo; i++) {
//				console.log(i);
//				navItem += '<li class="page-item">'
//						+ '<a class="page-link" href="#" data-curr="'+ i +'">' + i + '</a>'
//						+ '</li>';
//				
//				console.log(navItem)
//			    
//			}
//			navItem += '<li class="page-item"> <a class="page-link" href="#">Next</a></li>';
//			
//			tbody.innerHTML = html;
//			pagination.innerHTML = navItem;
//		})
//		.then(() => {
//			let pagingArr = document.querySelectorAll(".page-link");
//			console.log(pagingArr);
//			if(pagingArr != null) {
//				for(let i = 1; i <= pageInfo.pageLimit; i++) {
//					pagingArr[i].onclick = function() {
//						let currentPageNo = pagingArr[i].getAttribute("data-curr");
//						getAirPollution(currentPageNo);
//					}
//				}
//			}
//		})
//		.catch((error) => { console.log(error); }); // 오류 확인
//}