package com.springbootstudy.app.domain;


import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Community {
	private int no;
	private String title;
	private String writer;
	private String content;
	private Timestamp regDate;
	private int readCount;
	private String pass;
	private String file1;
	

}
