package com.kh.api.model.dto; // model : 데이터 저장용

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String name;
	private int age;
	private String phone;
}
