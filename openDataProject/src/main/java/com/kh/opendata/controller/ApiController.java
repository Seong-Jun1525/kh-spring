package com.kh.opendata.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.opendata.model.dto.AirResponseDTO;
import com.kh.opendata.service.ApiService;

//@RequiredArgsConstructor
@Controller
public class ApiController {
	
	// @RequiredArgsConstructor를 사용 시 final을 붙여야한다
	private ApiService apiService;
	
	public ApiController(ApiService apiService) {
		this.apiService = apiService;
	}
	
	
	
//	// [GET] /air-pollutions?locationName=선택된지역명
//	@GetMapping("air-pollutions")
//	@ResponseBody
//	public List<AirVO> getAirPollution(@RequestParam(value="locationName", defaultValue="서울") String locationName) {
//		System.out.println(locationName);
//		
//		List<AirVO> list = null;
//		
//		try {
//			list = apiService.getAirPollution(locationName);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return list;
//	}
	
	// [GET] /air-pollutions?locationName=선택된지역명
//	@GetMapping("air-pollutions") // 리눅스 환경에서는 / 가 있을 시 문제가 있을 수 있어서 이 부분에서 /는 생략도 가능하다
//	@ResponseBody
//	public ListDTO getAirPollution(@RequestParam(value = "currentPageNo", defaultValue="1") int currentPageNo, @RequestParam(value="locationName", defaultValue="서울") String locationName) {
//		
//		ListDTO listDTO = new ListDTO();
//		System.out.println(locationName);
//		System.out.println(currentPageNo);
//		
//		int totalCount = 0;
//		
//		try {
//			totalCount = apiService.getAirPollutionListCount(locationName);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(totalCount);
//
//		// 보여줄 최대 행수와 페이지 수
//		int pageLimit = 10;
//		int itemLimitCount = 10;
//		
//		PageInfo pi = new PageInfo(totalCount, currentPageNo, pageLimit, itemLimitCount);
//		listDTO.setPageInfo(pi);
//		
//		List<AirVO> list = null;
//		
//		try {
//			list = apiService.getAirPollution(locationName, currentPageNo);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		listDTO.setList(list);
//		return listDTO;
//	}
	
	// 강사님하고 진행한 페이징 처리
	// [GET] /air-pollutions?locationName=선택된지역명&currentPageNo=페이지번호
	// @return AirResponse { 대기오염 정보, 한 페이지 결과 수, 페이지 번호, 전체 결과 수  }
	@GetMapping("air-pollutions")
	@ResponseBody
	public AirResponseDTO getAirPollution(@RequestParam(value = "currentPageNo", defaultValue="1") int currentPageNo, @RequestParam(value="locationName", defaultValue="서울") String locationName) {
		try {
			return apiService.getAirPollutionUpgrade(locationName, currentPageNo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
