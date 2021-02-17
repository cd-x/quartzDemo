package com.example.quartzDemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<QuoteTable, Integer> {

}
