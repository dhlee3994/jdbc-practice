package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public void create(final User user) throws SQLException {
        String sql = "insert into users values(?, ?, ?, ?)";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        try (conn; ps) {
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());

            ps.executeUpdate();
        }
    }

    private Connection getConnection() {
        String url = "jdbc:h2:mem://localhost/~/jdbc-practice:MODE=MySQL;DB_CLOSE_DELAY=-1";
        String username = "sa";
        String password = "";

        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            return null;
        }
    }

    public User findByUserId(final String userId) throws SQLException {

        try (
            final Connection conn = getConnection();
            final PreparedStatement ps = createPreparedStatement(conn, userId);
            final ResultSet rs = ps.executeQuery()
        ) {

            User user = null;
            if (rs.next()) {
                user = new User(
                    rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email")
                );
            }

            return user;
        }
    }

    private PreparedStatement createPreparedStatement(Connection conn, String userId) throws SQLException {
        String sql = "select userId, password, name, email from users where userId = ?";
        final PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, userId);
        return ps;
    }
}
