package dao;

import model.Role;
import model.User;
import model.UserBook;

import java.util.List;

public interface UserDaoInterface {

    void save(User user);

    List<UserBook> findAllUserBooks(Integer id);

    List<UserBook> findAllUserBooksThatAreDue(Integer id);

    List<Role> findAllUserRoles(Integer id);

    List<User> findAll();

    List<User> findAllUsersInDebt();

    User findUserById(Integer id);

    void update(User user);

    void delete(Integer id);
}
