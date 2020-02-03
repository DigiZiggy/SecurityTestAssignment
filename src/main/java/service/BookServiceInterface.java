package service;

import model.Book;

import java.util.List;

public interface BookServiceInterface {

    List<Book> findAll();

    Book findBookById(Integer id);

    void saveOrUpdate(Book book);

    void delete(int id);
}
