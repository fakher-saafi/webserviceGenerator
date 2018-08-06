package com.mycompany.myapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.domain.MyPojo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class mysqldbService {
    private String path;
    private String username;
    private String password;
   private  Connection con = null;
   private  Statement stmt = null;
   private  ResultSet rs = null;
   private  List<String> tables=null;
   private  Map<String,String> colomns=null;
   private ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
   private MyPojo pojo ;

    public mysqldbService() {
    tables=new ArrayList<String>();
    colomns=new HashMap<String,String>();
    pojo = mapper.convertValue(colomns, MyPojo.class);

    }
    public mysqldbService(String path,String username, String password) {
        //testConnection();
        tables=new ArrayList<String>();
        colomns=new HashMap<String,String>();
        pojo = mapper.convertValue(colomns, MyPojo.class);

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

    public MyPojo getPojo() {
        return pojo;
    }

    public void setPojo(MyPojo pojo) {
        this.pojo = pojo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public boolean testConnection(){
        boolean test=false;

        try {

            con = DriverManager.getConnection(this.path, this.username, this.password);
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
                //pojo = mapper.convertValue(colomns, MyPojo.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colomns;
    }


    public List<Map> findAll(String table){
        String query="SELECT * from "+table;

        try {
            stmt = con.prepareStatement(query);


        //Resultset returned by query


            rs = stmt.executeQuery(query);
            return convertToMap(rs);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return null;
    }

    public  List<Map> convertToMap(ResultSet resultSet)
        throws Exception {
        List<Map> list=new ArrayList<>();
        while (resultSet.next()) {
            Map hashMap = new HashMap();
            int total_rows = resultSet.getMetaData().getColumnCount();
            for (int i = 0; i < total_rows; i++) {

                hashMap.put(resultSet.getMetaData().getColumnLabel(i + 1)
                    .toLowerCase(), resultSet.getObject(i + 1));

            }
            list.add(hashMap);
        }
        return list;
    }


   // public List<Map> FindOneBy(String table,String column,String columnValue){
     //   List<Map> listmap=findAll(table);
       // Map<String,String> map=new HashMap<>();
  //      listmap.stream().filter(l->{
    //         l.entrySet().stream()
      //          .filter(x -> x.getKey().contains(column) && x.getValue().contains(columnValue) )
        //        .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
 //            return true;
   //     }).collect(Collectors.toList());
   //  return listmap;
   // }

    public List<Map> FindOneBy(String table,String column,String columnValue){
        String query="SELECT * FROM `"+table+"` WHERE "+column+"='"+columnValue+"'";

        try {
            stmt = con.prepareStatement(query);

            //Resultset returned by query


            rs = stmt.executeQuery(query);
            return convertToMap(rs);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return null;
    }

    public void Add(Map map,String table)throws SQLException{
        Map<String,String> tableStruct =getTableColumns(table);
        String column="";
        String value="";
        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();

            if(tableStruct.containsKey(entry.getKey())&& tableStruct.get(entry.getKey()).contains("varchar")){
                column+=entry.getKey();
                value+="'"+entry.getValue()+"'";
            }else {
                column+=entry.getKey();
                value+=entry.getValue();
            }

          //  System.out.println("Key = " + key + ", Value = " + value);
            if (entries.hasNext()) {
                column+=",";
                value+=",";
            }
        }


        String query="INSERT INTO "+table+" ("+column+") VALUES ("+value+")";
        System.out.println(query);

            stmt = con.prepareStatement(query);
            stmt.executeUpdate(query);

            con.close();


    }

}
