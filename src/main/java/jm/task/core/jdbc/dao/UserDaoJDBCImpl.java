package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users ( " +
                    "id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(30), lastName VARCHAR(35), age INT)");
            System.out.println("Таблица создана");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("не удалось создать таблицу");
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE users");
            System.out.println("Таблица удалена");

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Не удалось удалить таблицу");
        }

    }

    public void saveUser(String name, String lastName, byte age)  {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate( "INSERT INTO users (name, lastName, age) VALUES ('" + name + "', '" + lastName + "', " + age + ")");
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println ("Не удалось добавить пользователя");
        }

    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users WHERE id = " + id + "");
            System.out.println("Пользователь удален");

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Не удалось удалить пользователя");
        }

    }

    public List<User> getAllUsers() {
        List <User> allUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT name, lastName, age FROM users");
            while (resultSet.next()){
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                allUsers.add (user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось получить данные о всех пользователях");
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE users");
            System.out.println("Данные удалены");

        } catch (SQLException e){
           e.printStackTrace();
           System.out.println("Не удалось удалить данные");
        }

    }
}
