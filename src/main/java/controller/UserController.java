package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.UserServiceInterface;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    private UserServiceInterface userService;

    @Autowired
    public void setUserService(UserServiceInterface userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/api/users/debts")
    public List<User> findAllUsersInDebt() {
        return userService.findAllUsersInDebt();
    }

    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void newUser(@RequestBody User user) {
        userService.saveOrUpdate(user);
    }

    @GetMapping("/api/users/{id}")
    public User findUserById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @PutMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void saveOrUpdate(@RequestBody User user, @PathVariable Integer id) {
        userService.saveOrUpdate(user);
    }

    @DeleteMapping("/api/users/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.delete(id);
    }

    @PostMapping("/api/users/{user_id}/books/{book_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUserBook(@PathVariable Integer user_id, @PathVariable Integer book_id) {
        userService.saveUserBook(user_id, book_id);
    }

    @PutMapping("/api/users/{user_id}/books/{book_id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserBook(@PathVariable Integer user_id, @PathVariable Integer book_id) {
        userService.updateUserBook(user_id, book_id);
    }
}
