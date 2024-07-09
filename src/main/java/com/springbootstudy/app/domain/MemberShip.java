package com.springbootstudy.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberShip {

	private int no;

	private String name;

	private String birthdate;

	private String id;

	private String email;

	private String mobile;

	private String zipcode;

	private String address1;

	private String pass;

	private String profile;

	private boolean emailGet;

	private boolean gerdonalGet;

}
