package org.example;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class ConnectionManager {

    public static final String DB_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:mem://localhost/~/jdbc-practice:MODE=MySQL;DB_CLOSE_DELAY=-1";
    public static final String DB_USER_NAME = "sa";
    public static final String DB_PASSWORD = "";
    private static final int MAX_POOL_SIZE = 10;

    private static final DataSource ds;

    static {
        final HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(DB_DRIVER);
        hikariDataSource.setJdbcUrl(DB_URL);
        hikariDataSource.setUsername(DB_USER_NAME);
        hikariDataSource.setPassword(DB_PASSWORD);
        hikariDataSource.setMaximumPoolSize(MAX_POOL_SIZE);
        hikariDataSource.setMinimumIdle(MAX_POOL_SIZE);

        ds = hikariDataSource;
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }
}
