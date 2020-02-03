package service;

import dao.BookDaoInterface;
import dao.UserDaoInterface;
import model.Role;
import model.User;
import model.UserBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    UserDaoInterface userDao;

    @Autowired
    public void setUserDao(UserDaoInterface userDao) {
        this.userDao = userDao;
    }

    BookDaoInterface bookDao;

    @Autowired
    public void setBookDao(BookDaoInterface bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public User findUserById(Integer id) {
        User user = userDao.findUserById(id);
        findUserRoles(Collections.singletonList(user));
        findUserBooks(Collections.singletonList(user));
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userDao.findAll();
        findUserRoles(users);
        findUserBooks(users);
        return users;
    }

    @Override
    public List<User> findAllUsersInDebt() {
        List<User> users = userDao.findAllUsersInDebt();
        findUserRoles(users);
        findUserBooksThatAreDue(users);
        return users;
    }

    @Override
    public void saveOrUpdate(User user) {
        if (user.getId() == null) {
            userDao.save(user);
        } else {
            userDao.update(user);
        }
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public void saveUserBook(Integer user_id, Integer book_id) {
        bookDao.saveUserBook(user_id, book_id);
    }

    @Override
    public void updateUserBook(Integer user_id, Integer book_id) {
        bookDao.updateUserBook(user_id, book_id);
    }

    private void findUserBooks(List<User> users) {
        for (User user : users) {
            List<UserBook> books = userDao.findAllUserBooks(user.getId());
            user.setBooks(books);
        }
    }

    private void findUserBooksThatAreDue(List<User> users) {
        for (User user : users) {
            List<UserBook> books = userDao.findAllUserBooksThatAreDue(user.getId());
            user.setBooks(books);
        }
    }

    private void findUserRoles(List<User> users) {
        for (User user : users) {
            List<Role> roles = userDao.findAllUserRoles(user.getId());
            user.setRoles(roles);
        }
    }
}
