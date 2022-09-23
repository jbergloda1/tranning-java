package com.dc24.tranning.controller;

import com.dc24.tranning.entity.CoursesEntity;
import com.dc24.tranning.entity.bookEntity.Book;
import com.dc24.tranning.service.Book.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:8080"})
@RequestMapping("/api/v1/book")
@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @PostMapping("/create")
    public Book addBook(@RequestBody Book book){
        return bookService.save(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        logger.info("process success!!");
        try {
            logger.info("Book Info");
            List<Book> book = (List<Book>) bookService.findAll();
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
