package com.kh.opendata.model.dto;

import java.util.List;

import com.kh.opendata.common.PageInfo;
import com.kh.opendata.model.vo.AirVO;

import lombok.Data;

@Data
public class ListDTO {
	private PageInfo pageInfo;
	private List<AirVO> list;
}	
