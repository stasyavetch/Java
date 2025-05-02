package com.greatbit.dao;

import com.greatbit.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BookDaoImpl implements BookDao {

    private final DataSource dataSource;

    @Autowired
    public BookDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAll() {
        final String selectSql = "SELECT book_id, pages, name, author FROM book";
        ArrayList<Book> books = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql)
        ) {
            while (rs.next()) {
                final Book book = new Book(rs.getString(1), rs.getString(3), rs.getString(4), rs.getInt(2));
                books.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    @Override
    public Book save(Book book) {
        String insertSQL = "INSERT INTO book (pages, name, author) VALUES (?, ?, ?)";

        try(Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, book.getPages());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.executeUpdate();

            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                book.setId(rs.getString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return book;
    }

    @Override
    public Book getById(String bookId) {
        String getByIdSql = "SELECT book_id, pages, name, author FROM book WHERE book_id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getByIdSql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, Integer.parseInt(bookId));



            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(!rs.next()) {
                    throw new RuntimeException(String.format("book with id %s was not found", bookId));
                }
                return new Book(rs.getString(1), rs.getString(3), rs.getString(4), rs.getInt(2));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Book update(Book book) {
        if(Objects.isNull(book.getId())) {
            throw new RuntimeException("Can't updated unsaved book");
        }

        String updateSql = "UPDATE book SET pages = ?, name = ?, author = ? WHERE book_id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setInt(1, book.getPages());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getId());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return book;
    }

    @Override
    public void delete(Book book) {
        String deleteByIdSql = "DELETE FROM book WHERE book_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteByIdSql)
        ) {
            statement.setString(1, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "TRUNCATE TABLE book";
        try(Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
