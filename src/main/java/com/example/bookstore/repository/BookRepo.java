package com.example.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.datamodel.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {

}
