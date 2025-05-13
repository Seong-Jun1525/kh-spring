package com.kh.tour.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {
	private String addr;
	private String areaCode;
	private String mapx;
	private String mapy;
}
