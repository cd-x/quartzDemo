package com.example.quartzDemo.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class QuoteTable {

	@Id
	private int id;
	private String quote;
	private String author;
	public QuoteTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	public QuoteTable(int id, String quote, String author) {
		super();
		this.id = id;
		this.quote = quote;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Override
	public String toString() {
		return "QuoteTable [id=" + id + ", quote=" + quote + ", author=" + author + "]";
	}
	
	
}
