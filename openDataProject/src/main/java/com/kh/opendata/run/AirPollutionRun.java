package com.kh.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.opendata.model.vo.AirVO;

public class AirPollutionRun {
	@Value("${service.key}")
	private static String serviceKey;
	
	private static String[] areaArr = {"전국", "서울", "부산", "대구", "인천", "광주", "대전", "울산", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종"};
	
	public static void main(String[] args) throws IOException {
		// * 요청주소
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		
		// * 전달 데이터(request parameter)
		url += "?serviceKey=" + serviceKey;
		// => SERVICE_KEY_IS_NOT_REGISTERED_ERROR 오류나면 서비스키 확인하기
		
		// 전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종
		System.out.println((int)Math.random() * (areaArr.length));
		url += "&sidoName=" + URLEncoder.encode(areaArr[(int)Math.random() * (areaArr.length)], "UTF-8"); // 한글이 포함된 경우 문제가 발생할 수 있으므로 인코딩 처리를 해줘야 한다.
		
		url += "&returnType=json";
		
//		System.out.println(url);
		
		// (1) 요청주소와 함께 URL 객체 생성
		URL requestURL = new URL(url);
		
		// (2) URL 객체를 통해서 HttpURLConnection 객체 생성
		HttpURLConnection urlConn = (HttpURLConnection) requestURL.openConnection();
		
		// (3) 요청 시 필요한 헤더 설정
		urlConn.setRequestMethod("GET");
		
		// (4) 공공데이터 API 서버로 요청을 보낸 후
		// 		입력 스트립을 통해서 응답 데이터 읽기
		BufferedReader br;
		if(urlConn.getResponseCode() >= 200 && urlConn.getResponseCode() <= 300) {
			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        } else {
        	br = new BufferedReader(new InputStreamReader(urlConn.getErrorStream()));
        }
		
		String line;
		String responseText = "";
		while((line = br.readLine()) != null) {
//			System.out.println(line);
			responseText += line;
		}
		
		// (5) 사용한 자원 반납
		br.close();
		urlConn.disconnect();
		
		// 응답 데이터 확인
		System.out.println(responseText);
		
		// JsonObject, JsonArray, JsonElement 이용해서 파싱 가능
		// Gson 라이브러리 사용!
		
		// JsonParser를 사용하여 문자열 형태의 데이터를 JsonObject 형태로 변환
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
		
		// 응답 데이터에서 "response", "body" 키에 해당하는 데이터 추출
		JsonObject bodyObj = totalObj.getAsJsonObject("response").getAsJsonObject("body");
		System.out.println(bodyObj);
		
		int totalCount = bodyObj.get("totalCount").getAsInt(); // getAsInt 메서드로 형변환
//		System.out.println(totalCount);
		
		JsonArray items = bodyObj.getAsJsonArray("items");
//		System.out.println(items);
		ArrayList<AirVO> list = new ArrayList<>();
		
		for(int i = 0; i < items.size(); i++) {
			JsonObject item = items.get(i).getAsJsonObject();
			System.out.println(item);
			
			AirVO air = new AirVO();
			air.setStationName(item.get("stationName").getAsString());
			air.setDataTime(item.get("dataTime").getAsString());
			air.setKhaiValue(item.get("khaiVakue") != null ? item.get("khaiVakue").getAsString() : "");
			air.setCoValue(item.get("coValue").getAsString());
			air.setNo2Value(item.get("no2Value").getAsString());
			air.setO3Value(item.get("o3Value").getAsString());
			air.setPm10Value(item.get("pm10Value").getAsString());
			air.setSo2Value(item.get("so2Value").getAsString());
			
			System.out.println(air);
			
			list.add(air);
		}
	}

}
