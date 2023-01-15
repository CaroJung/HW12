package com.example.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.datamodel.Book;
import com.example.bookstore.service.BookService;

@RequestMapping("/bookStore")
@RestController
public class BookController {
	
	@Autowired
	BookService bookSer;
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllbooks(){
		
		List<Book> books = new ArrayList<Book> ();
		books = bookSer.findBooks();
		
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id){
		
		return new ResponseEntity<>(bookSer.fetchBook(id).get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Book> addBookt(@RequestBody Book book){
		
		bookSer.addBook(book);
		
		return new ResponseEntity<>(book, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Book> removeBookById(@PathVariable int id){
		
		Book book = bookSer.fetchBook(id).get();
		
		if(bookSer.deleteBook(id)) {
			return new ResponseEntity<>(book, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(book, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/oldestBook")
	public ResponseEntity<Book> getOldestBook(){
		List<Book> books = new ArrayList<Book> ();
		books = bookSer.findBooks();
		Book oldestBook;
		
		if(books.size() == 0) {
			oldestBook = null;
		}else {
			oldestBook = books.get(0);
			for(Book book : books) {
				if(book.getDateYear() < oldestBook.getDateYear()) {
					oldestBook = book;
				}
			}
		}
		
		return new ResponseEntity<>(oldestBook, HttpStatus.OK);
	}
	
	@GetMapping("/newestBook")
	public ResponseEntity<Book> getNewestBook(){
		List<Book> books = new ArrayList<Book> ();
		books = bookSer.findBooks();
		Book newestBook = null;
		
		if(books.isEmpty()) {
			newestBook = null;
		}else {
			newestBook = books.get(0);
			for(Book book : books) {
				if(book.getDateYear() > newestBook.getDateYear()) {
					newestBook = book;
				}
			}
		}
		
		return new ResponseEntity<>(newestBook, HttpStatus.OK);
	}

}
