package com.steer.data.common.utils;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * localhost:96/h2-console/
 * <p>
 * h2是支持事务的
 * h2是支持主键和索引的，可以手动的创建索引来提升查询效率，并且支持 auto_increment 自动增长功能等
 */
public class H2ConnectionPool {
    private static H2ConnectionPool cp = null;
    private JdbcConnectionPool jdbcCP = null;

    private H2ConnectionPool() {
        String dbPath = "./h2db/test";
        jdbcCP = JdbcConnectionPool.create("jdbc:h2:" + dbPath, "sa", "");
        jdbcCP.setMaxConnections(50);
    }

    public static H2ConnectionPool getInstance() {
        if (cp == null) {
            cp = new H2ConnectionPool();
        }
        return cp;
    }

    public Connection getConnection() throws SQLException {
        return jdbcCP.getConnection();
    }
}
