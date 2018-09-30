/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whuang022.lite.api.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DB Auth Handler
 * @author whuang022
 */
public class DBAuthHandler extends DBHandler{

    public DBAuthHandler(){
        super();
    }
     public DBAuthHandler(String DB_Name){
        super(DB_Name);
    }
     
    @Override
    public Object DBProcess(Object... arguments) {
        boolean isPassed=true;//remember to change state to defult =false
        //implement your db auth code here
        return isPassed;
    }
    
}
