package dao;

import model.Role;
import model.User;
import model.UserBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Repository
public class UserDao implements UserDaoInterface {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO \"user\"(firstName, lastName, userName, email, password) " +
                "VALUES (:firstName, :lastName, :userName, :email, :password)";

        namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(user), keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        saveUserRole(user.getId());
    }

    private void saveUserRole(Integer user_id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("user_id", user_id);
        params.put("role_id", 2);
        String sql = "INSERT INTO user_roles(role_id, user_id) VALUES (:role_id, :user_id)";
        namedParameterJdbcTemplate.update(sql, params);
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM \"user\"";
        return namedParameterJdbcTemplate.query(sql, new UserMapper());
    }

    public List<User> findAllUsersInDebt() {
        String sql = "SELECT u.id, u.firstName, u.lastName, u.userName, u.email, u.password " +
                "FROM book b, user_books ub, \"user\" u " +
                "WHERE b.id = ub.book_id AND u.id = ub.user_id AND ub.returned_on IS NULL AND ub.due_date < CURDATE()";

        return namedParameterJdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User findUserById(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "SELECT * FROM \"user\" WHERE id = :id";

        User user = null;
        try {
            user = namedParameterJdbcTemplate.queryForObject(sql, params, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing, return null
        }
        return user;
    }

    @Override
    public List<UserBook> findAllUserBooks(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "SELECT b.id, b.title, b.author, b.description, ub.took_on, ub.due_date, ub.returned_on " +
                "FROM book b, user_books ub, \"user\" u " +
                "WHERE b.id = ub.book_id AND u.id = ub.user_id AND ub.user_id = :id";

        List<UserBook> books = null;
        try {
            books = namedParameterJdbcTemplate.query(sql, params, new UserBookMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing, return null
        }
        return books;
    }

    @Override
    public List<UserBook> findAllUserBooksThatAreDue(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "SELECT b.id, b.title, b.author, b.description, ub.took_on, ub.due_date, ub.returned_on " +
                "FROM book b, user_books ub, \"user\" u " +
                "WHERE b.id = ub.book_id AND u.id = ub.user_id AND ub.returned_on IS NULL " +
                "AND ub.due_date < CURDATE() AND ub.user_id = :id";

        List<UserBook> books = null;
        try {
            books = namedParameterJdbcTemplate.query(sql, params, new UserBookMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing, return null
        }
        return books;
    }

    @Override
    public List<Role> findAllUserRoles(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "SELECT r.id, r.name FROM role r, user_roles ur, \"user\" u " +
                "WHERE r.id = ur.role_id AND u.id = ur.user_id AND ur.user_id = :id";

        List<Role> roles = null;
        try {
            roles = namedParameterJdbcTemplate.query(sql, params, new UserRoleMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing, return null
        }
        return roles;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE \"user\" SET firstName = :firstName, lastName = :lastName, " +
                "userName = :userName, email = :email, password = :password WHERE id = :id";

        namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(user));
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM \"user\" WHERE id = :id";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    private static final class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setUserName(rs.getString("userName"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }

    private static final class UserRoleMapper implements RowMapper<Role> {
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setName(rs.getString("name"));
            return role;
        }
    }

    private static final class UserBookMapper implements RowMapper<UserBook> {
        public UserBook mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserBook userBook = new UserBook();
            userBook.setId(rs.getInt("id"));
            userBook.setTitle(rs.getString("title"));
            userBook.setAuthor(rs.getString("author"));
            userBook.setDescription(rs.getString("description"));
            userBook.setTookOn(rs.getString("took_on"));
            userBook.setDueDate(rs.getString("due_date"));
            userBook.setReturnedOn(rs.getString("returned_on"));
            userBook.setDaysOverDueDate();
            return userBook;
        }
    }

    private SqlParameterSource getSqlParameterByModel(User user) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", user.getId());
        paramSource.addValue("firstName", user.getFirstName());
        paramSource.addValue("lastName", user.getLastName());
        paramSource.addValue("userName", user.getUserName());
        paramSource.addValue("email", user.getEmail());
        paramSource.addValue("password", user.getPassword());
        return paramSource;
    }
}
