window.addEventListener("load", function() {
	const btn1 = document.getElementById("btn1");
	btn1.addEventListener(("click"), () => {
		console.log("클릭");
		getAirPollution();
	});
});

// 대기오염 정보 조회
const getAirPollution = () => {
	let locationList = [];
	// [GET] /air-pollutions?location="선택된지역정보"
	
	// 요청 전 작업 => 선택된 지역정보값을 가져와야함
	
	const locationArea = document.getElementById("location");
	let locationName = "";
	
	locationName = locationArea.value;
	
	// 지역 정보를 담아 조회 요청 => 비동기 요청(fetch)
	fetch("/air-pollutions?locationName=" + locationName)
		// response.text() => 문자열, 숫자, ... 응답데이터추출
		// response.json() => 객체 형태로 응답데이터추출
		.then((response) => { return response.json(); })
		.then((data) => {
			console.log(data); // 응답 결과 확인
			
			for(item of data) {
				console.log(item.stationName)
				locationList.push(item)
			}
		})
		.then(() => {
			const tbody = document.querySelector("tbody");
			let html = "";
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
			
			tbody.innerHTML = html;
		})
		.catch((error) => { console.log(error); }); // 오류 확인
		
	
}