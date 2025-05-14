package com.kh.opendata.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.opendata.common.PageInfo;
import com.kh.opendata.model.dto.ListDTO;
import com.kh.opendata.model.vo.AirVO;
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
	@GetMapping("air-pollutions")
	@ResponseBody
	public ListDTO getAirPollution(@RequestParam(value = "currentPageNo", defaultValue="1") int currentPageNo, @RequestParam(value="locationName", defaultValue="서울") String locationName) {
		
		ListDTO listDTO = new ListDTO();
		System.out.println(locationName);
		System.out.println(currentPageNo);
		
		int totalCount = 0;
		
		try {
			totalCount = apiService.getAirPollutionListCount(locationName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(totalCount);

		// 보여줄 최대 행수와 페이지 수
		int pageLimit = 10;
		int itemLimitCount = 10;
		
		PageInfo pi = new PageInfo(totalCount, currentPageNo, pageLimit, itemLimitCount);
		listDTO.setPageInfo(pi);
		
		List<AirVO> list = null;
		
		try {
			list = apiService.getAirPollution(locationName, currentPageNo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		listDTO.setList(list);
		return listDTO;
	}
}
