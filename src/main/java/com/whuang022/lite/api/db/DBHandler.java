
package com.whuang022.lite.api.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DB Handler 
 * @author whuang022
 */
public abstract class DBHandler {
    
    private String DB_Name="mysql";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String DB_URL ="";
    private static final String DBType="mysql";//DB Type
    private static final String USER = "";//DB User
    private static final String PASS = "";//DB Pass
    private static final String PORT = "3306";//DB PORT
    private static final String IP    ="localhost";//DB IP
    private  static final String ENCODE="utf-8";
    private boolean connStatus=false;
    protected Connection conn = null;
    
    public DBHandler() {
        DBInit();
   }
   
    public DBHandler(String DB_Name) {
        this.DB_Name=DB_Name;
        DBInit();
   }
   
   private void DBInit(){
    DB_URL = "jdbc:"+DBType+"://"+IP+":"+PORT+"/"+DB_Name+"?useUnicode=true&characterEncoding="+ENCODE;//DB URL
    try{
         Class.forName(JDBC_DRIVER);
         conn = DriverManager.getConnection(DB_URL, USER, PASS);
         connStatus=true;
     }catch (ClassNotFoundException | SQLException ex) {
         Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
     } 
   }
   
   public void DBCloseConnection(){
        try {
            conn.close();
            connStatus=false;
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void DBResumeConnection(){
        if(connStatus==false){
            DBInit();
        }
   }
   
   public abstract Object DBProcess(Object... arguments);
}

