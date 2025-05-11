package org.example;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the database")
    @ApiResponse(responseCode = "200", description = "successfully retrieved all books")
    @GetMapping(value = "/books", produces = "application/json")
    public ResponseEntity<List<BookEntity>> getAllBooks() {
        List<BookEntity> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get book by ID", description = "Retrieve a book by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the book")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @GetMapping(value = "/book/{id}", produces = "application/json")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        BookEntity book =  bookService.getBook(id);
        return ResponseEntity.ok(book);
    }


    @Operation(summary = "Add a new book", description = "Create a new book entry in the database")
    @ApiResponse(responseCode = "201", description = "Successfully added the book")
    @PostMapping(value = "/book", consumes = "application/json")
    public ResponseEntity<String> addBook(@RequestBody BookEntity bookEntity) {
        bookService.saveBook(bookEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Book added successfully with ID: " + bookEntity.getId());

    }

}
