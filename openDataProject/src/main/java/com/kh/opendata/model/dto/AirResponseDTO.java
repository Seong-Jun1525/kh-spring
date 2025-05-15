package com.kh.opendata.model.dto;

import java.util.List;

import com.kh.opendata.model.vo.AirVO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirResponseDTO {
	private List<AirVO> items;
	private int numOfRows;
	private int pageNo;
	private int totalCount;
}
