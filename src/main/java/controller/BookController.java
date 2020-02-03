package controller;

import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.BookServiceInterface;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {

    private BookServiceInterface bookService;

    @Autowired
    public void setBookService(BookServiceInterface bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @PostMapping("/api/books")
    @ResponseStatus(HttpStatus.CREATED)
    public void newBook(@RequestBody Book book) {
        bookService.saveOrUpdate(book);
    }

    @GetMapping("/api/books/{id}")
    public Book findBookById(@PathVariable Integer id) {
        return bookService.findBookById(id);
    }

    @PutMapping("/api/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void saveOrUpdate(@RequestBody Book book, @PathVariable Integer id) {
        bookService.saveOrUpdate(book);
    }

    @DeleteMapping("/api/books/delete/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookService.delete(id);
    }
}
