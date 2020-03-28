package com.steer.data.test;

import java.sql.Connection;
import java.sql.SQLException;

public class SqlServerTest2 {

        public static Connection getConn(){
            String DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String URL="jdbc:sqlserver://172.16.21.185:1433;databaseName=成品秤;user=super;password=zdh0328";
            Connection conn=null;
            try {
                java.lang.Class.forName(DRIVER);
                conn=java.sql.DriverManager.getConnection(URL);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return conn;
        }

    public static void main(String[] args) {
        System.out.println(getConn());

    }

}
