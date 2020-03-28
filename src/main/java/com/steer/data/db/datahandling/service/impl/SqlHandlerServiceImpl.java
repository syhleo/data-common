package com.steer.data.db.datahandling.service.impl;


import com.steer.data.common.utils.DateUtil;
import com.steer.data.db.datahandling.mapper.SqlHandlerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

@Service
public class SqlHandlerServiceImpl {

    @Autowired
    private SqlHandlerMapper sqlHandlerMapper;

    public String doWork(String wrk_date){

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
            ResultSet rs=null;
            if(wrk_date==null){
                String dateStr=DateUtil.formatDate(DateUtil.getDateBefore(new Date(),1),"yyyy-MM-dd");
                rs = stmt.executeQuery("select * from  修正班报表 where convert(varchar(10),日期,121) = '"+dateStr+"'");
            }else{
                rs = stmt.executeQuery("select * from  修正班报表 where convert(varchar(10),日期,121) >='"+wrk_date +"'");
            }

            while(rs.next()){
                //如果对象中有数据，就会循环打印出来
                System.out.println("炉号="+rs.getString("炉号")+"序号="+rs.getInt("序号")+"日期="+rs.getDate("日期"));
               sqlHandlerMapper.deleteByDate(rs.getDate("日期"),rs.getInt("序号"),rs.getString("班次"),rs.getString("炉号"));
               sqlHandlerMapper.insertData(rs.getInt("ID"),rs.getInt("序号"),
                        rs.getDate("日期"),rs.getString("班次"),
                        rs.getString("品名"),rs.getString("炉号"),
                        rs.getInt("定尺件数"),rs.getFloat("定尺重量"),
                        rs.getInt("非定尺件数"),rs.getFloat("非定尺重量"),
                        rs.getInt("次品件数"),rs.getFloat("次品重量")
                        );
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
            return "SQL Server失败！";
        }

       return "成功";
    }

}
