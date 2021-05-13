package com.jdbcexample.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter

public class Usuario {

	private int id;
	private String userName;
	private String password;
	
	public Usuario() {
		super();
	}
	
	public Usuario(int id, String userName, String password) {
		this.id = id;
		this.userName = userName;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
}
