package com.kh.opendata.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.opendata.model.vo.AirVO;

@Service
public class ApiService {
	@Value("${service.key}")
	private String serviceKey;
	
	/**
	 * 공공데이터 API를 사용하여 대기오염 정보 조회
	 * @param 지역명
	 * @return 대기오염 조회 결과
	 */
	public List<AirVO> getAirPollution(String sidoName) throws IOException {
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + serviceKey;
		
		url += "&sidoName=" + URLEncoder.encode(sidoName, "UTF-8");
		
		url += "&returnType=json";
		
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
//					System.out.println(line);
			responseText += line;
		}
		
		// (5) 사용한 자원 반납
		br.close();
		urlConn.disconnect();
		
		// 응답 데이터 확인
		System.out.println("responseText : " + responseText);
		
		// JsonObject, JsonArray, JsonElement 이용해서 파싱 가능
		// Gson 라이브러리 사용!
		
		// JsonParser를 사용하여 문자열 형태의 데이터를 JsonObject 형태로 변환
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
		
		// 응답 데이터에서 "response", "body" 키에 해당하는 데이터 추출
		JsonObject bodyObj = totalObj.getAsJsonObject("response").getAsJsonObject("body");
		System.out.println(bodyObj);
		
		int totalCount = bodyObj.get("totalCount").getAsInt(); // getAsInt 메서드로 형변환
//				System.out.println(totalCount);
		
		JsonArray items = bodyObj.getAsJsonArray("items");
//				System.out.println(items);
		List<AirVO> list = new ArrayList<>();
		
		for(int i = 0; i < items.size(); i++) {
			JsonObject item = items.get(i).getAsJsonObject();
			System.out.println(item);
			
			AirVO air = new AirVO();
			try {
				air.setStationName(item.get("stationName").getAsString());
				air.setDataTime(item.get("dataTime") != null ? item.get("dataTime").getAsString() : "");
				air.setKhaiValue(item.get("khaiValue") != null ? item.get("khaiValue").getAsString() : "");
				air.setCoValue(item.get("coValue") != null ? item.get("coValue").getAsString() : "");
				air.setNo2Value(item.get("no2Value") != null ? item.get("no2Value").getAsString() : "");
				air.setO3Value(item.get("o3Value") != null ? item.get("o3Value").getAsString() : "");
				air.setPm10Value(item.get("pm10Value") != null ? item.get("pm10Value").getAsString() : "");
				air.setSo2Value(item.get("so2Value") != null ? item.get("so2Value").getAsString() : "");
				System.out.println(air);
				
				list.add(air);
			} catch(UnsupportedOperationException e) {
				e.printStackTrace();
			}
			
		}
		
		return list;
	}
	
}
