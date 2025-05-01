package com.kh.todo.member.model.vo;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {
	private String userId;
	private String userPwd;
	private String nickname;
	private String email;
	private Date joinDate;
}
