package com.kh.todo.user.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {
	private String userId;
	private String userPwd;
	private String nickname;
	private String email;

}
