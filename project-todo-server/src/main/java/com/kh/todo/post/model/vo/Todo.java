package com.kh.todo.post.model.vo;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Todo { // 여기서는 vo(value object)로 분리했지만 실제로는 domain이라는 패키지로 구분 한다.

	private int no;
	private String userId;
	private String content;
	private int status;
	private Date createDate;
	
}
