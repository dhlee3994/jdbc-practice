package org.example;

import java.sql.SQLException;

public class UserDao {

    public void create(final User user) throws SQLException {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "insert into users values(?, ?, ?, ?)";
        jdbcTemplate.executeUpdate(user, sql, preparedStatement -> {
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
        });
    }

    public User findByUserId(final String userId) throws SQLException {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "select userId, password, name, email from users where userId = ?";
        return jdbcTemplate.executeQuery(sql,
            preparedStatement -> preparedStatement.setString(1, userId),
            rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"))
        );
    }
}
