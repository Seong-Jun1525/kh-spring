package com.kh.thymeleaf.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {
	private String name;
	private int age;
	private String birth;
	private String[] hobby;

	public UserDTO(String name, int age, String birth) {
		this.name = name;
		this.age = age;
		this.birth = birth;
	}
}
