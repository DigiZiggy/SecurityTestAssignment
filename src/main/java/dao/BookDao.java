package dao;

import model.Book;
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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BookDao implements BookDaoInterface {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
            throws DataAccessException {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void save(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        book.setAvailable(true);

        String sql = "INSERT INTO book(title, author, description, amount, added_to_stock, available) " +
                "VALUES (:title, :author, :description, :amount, :added_to_stock, :available)";

        namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(book), keyHolder);
        book.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        return namedParameterJdbcTemplate.query(sql, new BookMapper());
    }

    @Override
    public Book findBookById(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "SELECT * FROM book WHERE id = :id";

        Book book = null;
        try {
            book = namedParameterJdbcTemplate.queryForObject(sql, params, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing, return null
        }
        return book;
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE book SET title = :title, author = :author, description = :description, " +
                "amount = :amount, added_to_stock = :added_to_stock, available = :available WHERE id = :id";

        namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(book));
    }

    @Override
    public void updateUserBook(Integer user_id, Integer book_id) {
        Book book = findBookById(book_id);
        Integer amount = book.getAmount();
        book.setAmount(amount+1);
        book.setAvailable(true);
        update(book);

        LocalDate today = LocalDate.now();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("book_id", book_id);
        params.put("user_id", user_id);
        params.put("returned_on", today);

        String sql = "UPDATE user_books SET returned_on = :returned_on " +
                "WHERE user_id = :user_id AND book_id = :book_id";

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void saveUserBook(Integer user_id, Integer book_id) {
        Book book = findBookById(book_id);
        Integer amount = book.getAmount();
        book.setAmount(amount-1);
        update(book);

        Integer weeksToLendOut = book.getLendingTimeInWeeks();
        LocalDate today = LocalDate.now();
        LocalDate lendingTime = today.plus(weeksToLendOut, ChronoUnit.WEEKS);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("book_id", book_id);
        params.put("user_id", user_id);
        params.put("took_on", today);
        params.put("due_date", lendingTime);

        String sql = "INSERT INTO user_books (book_id, user_id, took_on, due_date) " +
                "VALUES (:book_id, :user_id, :took_on, :due_date)";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM book WHERE id = :id";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    private static final class BookMapper implements RowMapper<Book> {
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setDescription(rs.getString("description"));
            book.setAmount(rs.getInt("amount"));
            book.setAddedToStock(rs.getString("added_to_stock"));
            book.setAvailable(rs.getBoolean("available"));
            book.setLendingTimeInWeeks();
            return book;
        }
    }

    private SqlParameterSource getSqlParameterByModel(Book book) {
        LocalDate today = LocalDate.now();
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", book.getId());
        paramSource.addValue("title", book.getTitle());
        paramSource.addValue("author", book.getAuthor());
        paramSource.addValue("description", book.getDescription());
        paramSource.addValue("amount", book.getAmount());
        if (book.getAddedToStock() == null) {
            paramSource.addValue("added_to_stock", today);
        } else {
            paramSource.addValue("added_to_stock", book.getAddedToStock());
        }
        paramSource.addValue("available", book.isAvailable());
        return paramSource;
    }
}
