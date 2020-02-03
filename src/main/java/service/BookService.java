package service;

import dao.BookDaoInterface;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements BookServiceInterface {

    BookDaoInterface bookDao;

    @Autowired
    public void setSectorDao(BookDaoInterface bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Book findBookById(Integer id) {
        return bookDao.findBookById(id);
    }

    @Override
    public void saveOrUpdate(Book book) {
        if (book.getId() == null) {
            bookDao.save(book);
        } else {
            bookDao.update(book);
        }
    }

    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }
}
