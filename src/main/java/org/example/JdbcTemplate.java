package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {

    public void executeUpdate(User user, String sql, PreparedStatementSetter pss) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        try (conn; ps) {
            pss.setter(ps);
            ps.executeUpdate();
        }
    }

    public <T> T executeQuery(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws SQLException {
        final Connection conn = ConnectionManager.getConnection();
        final PreparedStatement ps = conn.prepareStatement(sql);
        pss.setter(ps);
        final ResultSet rs = ps.executeQuery();

        try (conn; ps; rs) {
            T t = null;
            if (rs.next()) {
                t = rowMapper.map(rs);
            }
            return t;
        }
    }
}
