package model;

import lombok.Data;

import java.util.List;

@Data
public class User {

    private Integer id;

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private String password;

    private List<Role> roles;

    private List<UserBook> books;
}
