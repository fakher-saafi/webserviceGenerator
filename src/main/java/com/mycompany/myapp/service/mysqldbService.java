package com.mycompany.myapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class mysqldbService {
   private  Connection con = null;
   private  Statement stmt = null;
   private  ResultSet rs = null;
   private  List<String> tables=null;
   private  Map<String,String> colomns=null;

    public mysqldbService() {
    tables=new ArrayList<String>();
        colomns=new HashMap<String,String>();
    }

    public Connection getCon() {
        return con;
    }

    public Statement getStmt() {
        return stmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    public List<String> getTables() {
        String query ="show tables";
        try {
            stmt = con.prepareStatement(query);

        //Resultset returned by query

            rs = stmt.executeQuery(query);

            while(rs.next()){ String tablename = rs.getString(1);
                System.out.println("table : " + tablename);
                tables.add(tablename);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tables;
    }

    public Map<String, String> getColomns() {
        return colomns;
    }

    @Override
    public String toString() {
        return "mysqldbService{" +
            "con=" + con +
            ", stmt=" + stmt +
            ", rs=" + rs +
            ", tables=" + tables +
            ", colomns=" + colomns +
            '}';
    }

    public boolean testConnection(String path,String username,String password){
        boolean test=false;

        try {

            con = DriverManager.getConnection(path, username, password);
            if (con != null) { System.out .println("Successfully connected to MySQL database ");
                test=true;
            }
        } catch (SQLException ex) {
            System.out .println("pathhhhhhh "+path);
            //System.out .println("An error occurred while connecting "+webservice.getDatabaseProduct().toString()+" database");
            ex.printStackTrace();

        }
       return test;
    }
    public Map<String,String> getTableColumns(String table){
        String query="SHOW COLUMNS from "+table;
        try {
            stmt = con.prepareStatement(query);

            //Resultset returned by query

            rs = stmt.executeQuery(query);

            while(rs.next()){
                String name = rs.getString(1);
                String type = rs.getString(2);
              //  System.out.println("name : " + name+" type : " + type);

                colomns.put(name,type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colomns;
    }

}
