package service;

import model.User;

import java.util.List;

public interface UserServiceInterface {

    User findUserById(Integer id);

    List<User> findAll();

    List<User> findAllUsersInDebt();

    void saveOrUpdate(User user);

    void delete(int id);

    void saveUserBook(Integer user_id, Integer book_id);

    void updateUserBook(Integer user_id, Integer book_id);
}
