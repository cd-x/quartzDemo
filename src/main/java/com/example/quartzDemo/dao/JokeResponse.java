package com.example.quartzDemo.dao;


public class JokeResponse {
	
	private String id;
	private String joke;
	private int status;
	public JokeResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JokeResponse(String id, String joke, int status) {
		super();
		this.id = id;
		this.joke = joke;
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJoke() {
		return joke;
	}
	public void setJoke(String joke) {
		this.joke = joke;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Joke [id=" + id + ", joke=" + joke + ", status=" + status + "]";
	}
	
	
	
}
