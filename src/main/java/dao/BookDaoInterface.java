package dao;

import model.Book;

import java.util.List;

public interface BookDaoInterface {

    void save(Book book);

    void saveUserBook(Integer user_id, Integer book_id);

    void updateUserBook(Integer user_id, Integer book_id);

    List<Book> findAll();

    Book findBookById(Integer id);

    void update(Book book);

    void delete(Integer id);
}
