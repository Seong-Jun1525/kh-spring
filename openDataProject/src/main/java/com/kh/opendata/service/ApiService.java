package com.kh.opendata.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import com.kh.opendata.model.dto.AirResponseDTO;
import com.kh.opendata.model.vo.AirVO;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ApiService {
	@Value("${service.key}")
	private String serviceKey;
	
	public int getAirPollutionListCount(String sidoName) throws IOException {
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
//							System.out.println(line);
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
		
		return totalCount;
	}
	
	/**
	 * 공공데이터 API를 사용하여 대기오염 정보 조회
	 * @param currentPageNo 
	 * @param 지역명
	 * @return 대기오염 조회 결과
	 */
	public List<AirVO> getAirPollution(String sidoName, int currentPageNo) throws IOException {
		// 반환 데이터
		List<AirVO> list = null;
		
		// 요청주소 + 파라미터
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + serviceKey;
		url += "&sidoName=" + URLEncoder.encode(sidoName, "UTF-8");
		url += "&pageNo=" + currentPageNo;
		url += "&returnType=json";
		
		// (1) 요청주소와 함께 URL 객체 생성
		URL requestURL = new URL(url);
		
		// 응답데이터 읽어오기
		// (2) URL 객체를 통해서 HttpURLConnection 객체 생성
		HttpURLConnection urlConn = (HttpURLConnection) requestURL.openConnection();
		
		// (3) 요청 시 필요한 헤더 설정
		urlConn.setRequestMethod("GET");
		
		// (4) 공공데이터 API 서버로 요청을 보낸 후
		// 		입력 스트립을 통해서 응답 데이터 읽기
		BufferedReader br;
		// 통신 성공 여부 체크
		if(urlConn.getResponseCode() == HttpServletResponse.SC_OK) {
			list = new ArrayList<>();
			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String line;
			StringBuilder responseData = new StringBuilder();
			while((line = br.readLine()) != null) {
				responseData.append(line);
			}

			System.out.println(responseData);
			// JsonObject, JsonArray, JsonElement 이용해서 파싱 가능
			// Gson 라이브러리 사용!
			JsonObject responseObj = JsonParser.parseString(responseData.toString()).getAsJsonObject();

			// 응답 데이터에서 "response", "body" 키에 해당하는 데이터 추출
			JsonArray items = responseObj.getAsJsonObject("response").getAsJsonObject("body").getAsJsonArray("items");
			
			for(int i = 0; i < items.size(); i++) {
				JsonObject item = items.get(i).getAsJsonObject();
				System.out.println("item : " + item);
				
				AirVO air = new AirVO();
				
				air.setStationName(getValue(item, "stationName"));
				air.setDataTime(getValue(item, "dataTime"));
				air.setKhaiValue(getValue(item, "khaiValue"));
				air.setCoValue(getValue(item, "coValue"));
				air.setNo2Value(getValue(item, "no2Value"));
				air.setO3Value(getValue(item, "o3Value"));
				air.setPm10Value(getValue(item, "pm10Value"));
				air.setSo2Value(getValue(item, "so2Value"));
				
				list.add(air);
			}
        } else {
        	// 통신 실패 시 
        	br = new BufferedReader(new InputStreamReader(urlConn.getErrorStream()));
        	
        	StringBuilder responseData = new StringBuilder();
        	String line = "";
        	
        	while((line = br.readLine()) != null) {
				responseData.append(line);
			}
        	
        	System.out.println("responseData err : " + responseData);
        }
		
		// (5) 사용한 자원 반납
		br.close();
		urlConn.disconnect();
		
		for(AirVO av : list) {
			System.out.println(av);
		}
		
		return list;
	}

	/**
	 * 공공데이터 API를 사용하여 대기오염 정보 조회
	 * @param locationName
	 * @param currentPageNo
	 * @return
	 * @throws IOException 
	 */
	public AirResponseDTO getAirPollutionUpgrade(String locationName, int currentPageNo) throws IOException {
		// 요청 URL
		String url = makeAirPollutionURL(locationName, currentPageNo); // 만약 매개변수가 다르지만 같은 메서드를 호출해야할 경우에는 메서드를 오버로딩해서 하나 더 만든다!
		
		// 응답데이터
		String responseData = getAirPollutionResponse(url);
		
		JsonObject bodyObj = JsonParser.parseString(responseData.toString()).getAsJsonObject().getAsJsonObject("response").getAsJsonObject("body");
		
		AirResponseDTO airResponseDTO = new AirResponseDTO();
		airResponseDTO.setTotalCount(bodyObj.get("totalCount").getAsInt());
		airResponseDTO.setNumOfRows(bodyObj.get("numOfRows").getAsInt());
		airResponseDTO.setPageNo(bodyObj.get("pageNo").getAsInt());
		
		List<AirVO> list = setListAirVO(bodyObj.getAsJsonArray("items"));
		
		airResponseDTO.setItems(list);
		
		return airResponseDTO;
	}
	
	/**
	 * 시도별 실시간 대기오염 요청 URL 생성
	 * @param sidoName 지역 정보
	 * @param pageNo 페이지 번호
	 * @return 요청 URL
	 * @throws UnsupportedEncodingException 
	 */
	private String makeAirPollutionURL(String locationName, int currentPageNo) throws UnsupportedEncodingException {
		// 요청주소 + 파라미터
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + serviceKey;
		url += "&sidoName=" + URLEncoder.encode(locationName, "UTF-8");
		url += "&pageNo=" + currentPageNo;
		url += "&returnType=json";
		
		return url;
	}

	/**
	 * 요청 후 응답데이터 반환
	 * @param url 요청주소
	 * @return 성공 시 응답데이터 문자열, 실패 시 null
	 * @throws IOException 
	 */
	private String getAirPollutionResponse(String url) throws IOException {
		// 요청 전송 URL, HttpURLConnection
		URL requestURL = new URL(url);
		
		// 응답데이터 읽어오기
		// (2) URL 객체를 통해서 HttpURLConnection 객체 생성
		HttpURLConnection urlConn = (HttpURLConnection) requestURL.openConnection();
		
		BufferedReader br;
		// 통신 성공 여부 체크
		if(urlConn.getResponseCode() == HttpServletResponse.SC_OK) {
			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(urlConn.getErrorStream()));
		}
		
		// 응답 결과를 변수에 저장

		String line; // 하나 씩 읽는 용도
		StringBuilder responseData = new StringBuilder(); // 실제로 데이터를 저장할 곳. 응답데이터 결과가 클 시 특정 메모리영역에 저장하는것이 효율적이므로 StringBuilder 사용
		// String을 사용 시 상수 풀에 저장되므로 비효율적
		while((line = br.readLine()) != null) {
			responseData.append(line);
		}

		// 자원반납
		br.close();
		urlConn.disconnect();

		if(urlConn.getResponseCode() == HttpServletResponse.SC_OK) {
			return responseData.toString();
		} else {
			System.out.println(responseData); // 실제 서비스에서는 로그로..
			return null;
		}
	}

	private List<AirVO> setListAirVO(JsonArray items) {
		List<AirVO> list = new ArrayList<>();
		for(int i = 0; i < items.size(); i++) {
			JsonObject item = items.get(i).getAsJsonObject();
			System.out.println("item : " + item);
			
			AirVO air = new AirVO();
			
			air.setStationName(getValue(item, "stationName"));
			air.setDataTime(getValue(item, "dataTime"));
			air.setKhaiValue(getValue(item, "khaiValue"));
			air.setCoValue(getValue(item, "coValue"));
			air.setNo2Value(getValue(item, "no2Value"));
			air.setO3Value(getValue(item, "o3Value"));
			air.setPm10Value(getValue(item, "pm10Value"));
			air.setSo2Value(getValue(item, "so2Value"));
			
			list.add(air);
		}
		
		return list;
	}
	
	// 객체 내의 특정 값에 대하여 안전하게 추출
	private String getValue(JsonObject obj, String key) {
		return obj.has(key) && obj.get(key) != null && !obj.get(key).isJsonNull() ? obj.get(key).getAsString() : "";
	}
}
