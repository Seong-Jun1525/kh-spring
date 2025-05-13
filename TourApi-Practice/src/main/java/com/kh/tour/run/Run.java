package com.kh.tour.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.tour.model.vo.Accommodation;

public class Run {
	@Value("${service.key}")
	private static String serviceKey;
	
	
	public static void main(String args[]) throws IOException {
		String url = "https://apis.data.go.kr/B551011/KorService1/searchStay1";
		
		url += "?MobileOS=ETC";
		url += "&MobileApp=practice";
		url += "&_type=json";
		url += "&serviceKey=" + serviceKey;
		
		
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
//		System.out.println(responseText);
		
		// JsonObject, JsonArray, JsonElement 이용해서 파싱 가능
		// Gson 라이브러리 사용!
		
		// JsonParser를 사용하여 문자열 형태의 데이터를 JsonObject 형태로 변환
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
		
		// 응답 데이터에서 "response", "body" 키에 해당하는 데이터 추출
		JsonObject bodyObj = totalObj.getAsJsonObject("response").getAsJsonObject("body");
//		System.out.println(bodyObj);
		
		JsonArray items = bodyObj.getAsJsonObject("items").getAsJsonArray("item");
//		System.out.println(items);
		ArrayList<Accommodation> list = new ArrayList<>();
		
		for(int i = 0; i < items.size(); i++) {
			JsonObject item = items.get(i).getAsJsonObject();
//			System.out.println(item);
			
			Accommodation accom = new Accommodation();

			accom.setAddr(item.get("addr1") != null ? item.get("addr1").getAsString() : "");
			accom.setAreaCode(item.get("areacode") != null ? item.get("areacode").getAsString() : "");
			accom.setMapx(item.get("mapx") != null ? item.get("mapx").getAsString() : "");
			accom.setMapy(item.get("mapy") != null ? item.get("mapy").getAsString() : "");
			
			System.out.println(accom);
			
			list.add(accom);
		}
		
	}
}
