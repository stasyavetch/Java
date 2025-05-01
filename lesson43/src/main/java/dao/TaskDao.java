package dao;

import entity.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;

public class TaskDao {
  private final DataSource dataSource;

  public TaskDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public Task save(Task task) {
    // get connection
    // create statement
    // set params
    // execute
    // get id
    // set id
    String sql = "INSERT INTO task (title, finished, created_date) VALUES (?, ?, ?)";
    try(
      Connection connection = dataSource.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
      ) {

      statement.setString(1, task.getTitle());
      statement.setBoolean(2, task.getFinished());
      statement.setTimestamp(3, java.sql.Timestamp.valueOf(task.getCreatedDate()));
      statement.executeUpdate();

      try(ResultSet resultSet = statement.getGeneratedKeys()) {
        if (resultSet.next()) {
          task.setId(resultSet.getInt(1));
        }
      }

    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }

    return task;
  }

  public List<Task> findAll() {
    List<Task> tasks = new ArrayList<>();
    String sql = "SELECT task_id, title, finished, created_date FROM task ORDER BY task_id";
    try(Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)
        ) {

      while(resultSet.next()) {
        final Task task = new Task(
          resultSet.getString(2),
          resultSet.getBoolean(3),
          resultSet.getTimestamp(4).toLocalDateTime()
        );
        task.setId(resultSet.getInt(1));
        tasks.add(task);
      }

    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }

    return tasks;
  }

  public int deleteAll() {
    String sql = "DELETE FROM task";
    try(
            Connection con = dataSource.getConnection();
            Statement statement = con.createStatement();
            ) {
      int countRows = statement.executeUpdate(sql);

      return countRows;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  public Task getById(Integer id) {

    String sql = "SELECT task_id, title, finished, created_date FROM task WHERE task_id = " + id;
    try(Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)
    ) {

      if (resultSet.next()) {
        final Task task = new Task(
                resultSet.getString(2),
                resultSet.getBoolean(3),
                resultSet.getTimestamp(4).toLocalDateTime()
        );
        task.setId(resultSet.getInt(1));
        return task;
      }

    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }

    return null;
  }

  public List<Task> findAllNotFinished() {
    List<Task> tasks = new ArrayList<>();
    String sql = "SELECT task_id, title, finished, created_date FROM task WHERE finished = false";
    try(Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)
    ) {

      while (resultSet.next()) {
        final Task task = new Task(
                resultSet.getString(2),
                resultSet.getBoolean(3),
                resultSet.getTimestamp(4).toLocalDateTime()
        );
        task.setId(resultSet.getInt(1));
        tasks.add(task);

      }
      return tasks;

    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }

//    return Collections.emptyList();
  }

  public List<Task> findNewestTasks(Integer numberOfNewestTasks) {
    List<Task> tasks = new ArrayList<>();
    String sql = "SELECT task_id, title, finished, created_date FROM task ORDER BY created_date DESC LIMIT " + numberOfNewestTasks;
    try(Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)
    ) {

      while (resultSet.next()) {
        final Task task = new Task(
                resultSet.getString(2),
                resultSet.getBoolean(3),
                resultSet.getTimestamp(4).toLocalDateTime()
        );
        task.setId(resultSet.getInt(1));
        tasks.add(task);

      }
      return tasks;

    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }
//    return Collections.emptyList();
  }

  public Task finishTask(Task task) {
    String sql = "UPDATE task SET finished = true WHERE task_id = " + task.getId();
    try(
            Connection con = dataSource.getConnection();
            Statement statement = con.createStatement();
    ) {
      int countRows = statement.executeUpdate(sql);

//      System.out.println(countRows);
      task.setFinished(true);
      return task;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  public void deleteById(Integer id) {
    String sql = "DELETE FROM task WHERE task_id = " + id;
    try(
            Connection con = dataSource.getConnection();
            Statement statement = con.createStatement();
    ) {
      int countRows = statement.executeUpdate(sql);

      System.out.println(countRows);
//      return countRows;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
