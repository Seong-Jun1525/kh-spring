package com.kh.opendata.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.opendata.model.vo.AirVO;
import com.kh.opendata.service.ApiService;

@Controller
public class ApiController {
	
	private ApiService apiService;
	
	public ApiController(ApiService apiService) {
		this.apiService = apiService;
	}
	
	// [GET] /air-pollutions?locationName=선택된지역명
	@GetMapping("air-pollutions")
	@ResponseBody
	public List<AirVO> getAirPollution(@RequestParam(value="locationName", defaultValue="서울") String locationName) {
		System.out.println(locationName);
		
		List<AirVO> list = null;
		try {
			list = apiService.getAirPollution(locationName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}
