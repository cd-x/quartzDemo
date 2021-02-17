package com.example.quartzDemo.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JokeTable {

	@Id
	private int id;
	private String content;
	
	
	public JokeTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public JokeTable(int id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Model [id=" + id + ", content=" + content + "]";
	}
	
}
