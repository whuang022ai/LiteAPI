package com.whuang022.lite.api.service;

import org.joda.time.DateTime;

import org.json.JSONObject;



public class APITest extends API {
    public APITest() {
        this.setRoutePath("/test");
        this.setApiOperation("test");
        this.setApiVersion("v1-Server");
        this.setServerName("Test-API-Server");

    }

    public boolean processAuthPOST(JSONObject request) {
        return true;
    }

    public JSONObject processLogicPOST(JSONObject request) {
        JSONObject response = new JSONObject(request.toString());
        response.put("Code", "0000");
        return response;
    }

    public JSONObject processHeaderPOST(JSONObject request) {
        JSONObject response = new JSONObject(request.toString());
        String currentTime = new DateTime().toString("yyyy:MM:dd HH:mm:ss");
        if (isTimeStamp()) {
            response.put("Time", currentTime);
        }
        response.put("Operation", getApiOperation() + "/post");
        return response;
    }

    public boolean processAuthGET(JSONObject request) {
        return true;
    }

    public JSONObject processLogicGET(JSONObject request) {
        JSONObject response = new JSONObject(request.toString());
        response.put("Code", "0000");
        return response;
    }

    public JSONObject processHeaderGET(JSONObject request) {
        JSONObject response = new JSONObject(request.toString());
        String currentTime = new DateTime().toString("yyyy:MM:dd HH:mm:ss");
        if (isTimeStamp()) {
            response.put("Time", currentTime);
        }
        response.put("Operation", getApiOperation() + "/get");
        return response;
    }


}
