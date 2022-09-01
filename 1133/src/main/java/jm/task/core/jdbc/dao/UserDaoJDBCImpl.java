package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
//    Statement state;
//
//    {
//        try (Statement state = Util.getConnection().createStatement())
//
//            {
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
    //ddl запросы через statment, dml через prepared.
    String creatTable = "CREATE TABLE users (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(40), Lastname VARCHAR(40), Age INT)";
    String getAll = "SELECT* FROM users";
    String saveUser = "INSERT Users(Name, Lastname, Age) VALUES (?, ?, ?)";
    String delfAll = "TRUNCATE TABLE Users";
    String idRemove = "DELETE FROM Users WHERE Id=?";
    String delTab = "DROP table users";



    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connect = Util.getConnection();
             Statement statement = connect.createStatement()) {
            statement.executeUpdate(creatTable);

//        try {
//            //Util.connection.setAutoCommit(false);
//            state.executeUpdate(creatTable);
//            Util.getConnection().commit();
//            //Util.connection.rollback();

        } catch (SQLException e) {
            //rolbackMethod();
            // throw new RuntimeException(e);
        }
    }



        public void dropUsersTable() {
            try (Connection connect = Util.getConnection();
                 Statement statement = connect.createStatement()) {
                statement.executeUpdate(delTab);
            Util.getConnection().commit();
        } catch (SQLException e) {
            //rolbackMethod();
            //throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connect = Util.getConnection()) {
            try {
                connect.setAutoCommit(false);
                try (PreparedStatement ps = connect.prepareStatement(saveUser)) {
                    ps.setString(1, name);
                    ps.setString(2, lastName);
                    ps.setByte(3, age);
                    ps.executeUpdate();
                }
                connect.commit();
                System.out.println("User with the name: " + name + " added to the database");
            } catch (SQLException e) {
                connect.rollback();
            }
        } catch (SQLException e) {
    }
}

    public void removeUserById(long id) {
        try (Connection connect = Util.getConnection()) {
            try {
                connect.setAutoCommit(false);
                try (PreparedStatement preparedStatement = connect.prepareStatement(idRemove)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            }
            } catch (SQLException e) {
                connect.rollback();
            }
        } catch (SQLException e) {

        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connect = Util.getConnection();
             Statement statement = connect.createStatement()) {
            ResultSet rs = statement.executeQuery(getAll);
            while (rs.next()) {
                User user = new User(rs.getString("NAME"), rs.getString("LAST_NAME")
                        , rs.getByte("AGE"));
                user.setId(rs.getLong("ID"));
                users.add(user);
            }
        } catch (SQLException e) {

        }
        return users;
    }

        public void cleanUsersTable() {
            try (Connection connect = Util.getConnection();
                 Statement statement = connect.createStatement()) {
                statement.executeUpdate(delfAll);
            Util.getConnection().commit();
            //Util.connection.rollback();
        } catch (SQLException e) {
//            rolbackMethod();
//            throw new RuntimeException(e);
        }
    }

//    public void rolbackMethod(){
//        try {
//            Util.getConnection().rollback();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}