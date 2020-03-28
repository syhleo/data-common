package com.steer.data.test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlServerTest {

    public static void main(String[] args){
        String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL="jdbc:sqlserver://172.16.21.185:1433;DatabaseName=成品秤";
        String userName="super";
        String userPwd="zdhbzdhs";
        try
        {
         Class.forName(driverName);
           System.out.println("加载驱动成功！");
        }catch(Exception e){
              e.printStackTrace();
               System.out.println("加载驱动失败！");
        }
        try{
               Connection dbConn= DriverManager.getConnection(dbURL,userName,userPwd);
                   System.out.println("连接数据库成功！");
             Statement stmt =dbConn.createStatement();
             //ResultSet rs = stmt.executeQuery("select * from  修正班报表 where 序号=2");
             ResultSet rs = stmt.executeQuery("select * from  修正班报表 where convert(varchar(10),日期,121) ='2019-12-30'  ");

             while(rs.next()){
              //如果对象中有数据，就会循环打印出来
              System.out.println("炉号="+rs.getString("炉号")+"序号="+rs.getInt("序号")+"日期="+rs.getDate("日期"));
              System.out.println("班次="+rs.getString("班次"));

              //System.out.println(rs.getInt("教师编号")+","+rs.getString("姓名")+","+rs.getInt("专业"));
             }
             if (rs != null) {
                 rs.close();
                 rs = null;
             }
             if (stmt != null) {
                 stmt.close();
                 stmt = null;
             }
             if (dbConn != null) {
                 dbConn.close();
                 dbConn = null;
             }

         }catch(Exception e) {
                 e.printStackTrace();
                 System.out.print("SQL Server失败！");
         }

    }

}
