package com.example.quartzDemo.dao;

public class QuoteResponse {

	private String author;
	private int id;
	private String quote;
	private String permalink;
	public QuoteResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public QuoteResponse(String author, int id, String quote, String permalink) {
		super();
		this.author = author;
		this.id = id;
		this.quote = quote;
		this.permalink = permalink;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	public String getPermalink() {
		return permalink;
	}
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	@Override
	public String toString() {
		return "Quote [author=" + author + ", id=" + id + ", quote=" + quote + ", permalink=" + permalink + "]";
	}
	
}
