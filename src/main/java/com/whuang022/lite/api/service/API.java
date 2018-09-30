package com.whuang022.lite.api.service;

import org.json.JSONObject;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * API 抽象類別定義
 *
 * @author whuang022
 */
public abstract class API extends HttpServlet {


    private String routePath = "";
    private String serverName = "";
    private String apiVersion = "";
    private String apiOperation = "";
    private boolean timeStamp = true;
    private boolean mockAPI = false;

    public String httpServletRequestToString(HttpServletRequest request) throws Exception {
        ServletInputStream mServletInputStream = request.getInputStream();
        byte[] httpInData = new byte[request.getContentLength()];
        int retVal = -1;
        StringBuilder stringBuilder = new StringBuilder();

        while ((retVal = mServletInputStream.read(httpInData)) != -1) {
            for (int i = 0; i < retVal; i++) {
                stringBuilder.append(Character.toString((char) httpInData[i]));
            }
        }
        return stringBuilder.toString();
    }

    public JSONObject getPOSTRequest(HttpServletRequest request) {
        String requestJSONStr = "";
        try {
            request.setCharacterEncoding("UTF-8");
            requestJSONStr = httpServletRequestToString(request);
            JSONObject requestObj = new JSONObject(requestJSONStr);
            return requestObj;
        } catch (Exception ex) {
            Logger.getLogger(APITest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JSONObject getGETRequest(HttpServletRequest request) {
        HashMap<String, String[]> queryParameters = new HashMap<String, String[]>();

        JSONObject requestObj = new JSONObject();
        String queryString = request.getQueryString();

        if (StringUtils.isEmpty(queryString)) {
            return null;
        }

        String[] parameters = queryString.split("&");

        for (String parameter : parameters) {
            String[] keyValuePair = parameter.split("=");
            String[] values = queryParameters.get(keyValuePair[0]);
            values = ArrayUtils.add(values, keyValuePair.length == 1 ? "" : keyValuePair[1]); //length is one if no value is available.
            queryParameters.put(keyValuePair[0], values);
            requestObj.put("" + keyValuePair[0], "" + values[0]);//GET only get one key for one value
        }

        return requestObj;
    }

    /**
     * API POST (With Auth)
     *
     * @param request from Client JSON
     * @return response to Client JSON
     */
    public JSONObject apiPOST(JSONObject request) {
        if (processAuthPOST(request)) {
            return processHeaderServer(processHeaderPOST(processLogicPOST(request)));
        } else return request.put("Code", "4444");
    }

    /**
     * API GET (With Auth)
     *
     * @param request from Client JSON
     * @return response to Client JSON
     */
    public JSONObject apiGET(JSONObject request) {
        if (processAuthGET(request)) {
            return processHeaderServer(processHeaderGET(processLogicGET(request)));
        } else return request.put("Code", "4444");
    }

    public JSONObject processHeaderServer(JSONObject request) {
        JSONObject response = new JSONObject(request.toString());
        response.put("From", serverName);
        response.put("APIVersion", apiVersion);
        return response;
    }

    public boolean isTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(boolean timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    public String getApiOperation() {
        return apiOperation;
    }

    public void setApiOperation(String apiOperation) {
        this.apiOperation = apiOperation;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public boolean isMockAPI() {
        return mockAPI;
    }

    public void setMockAPI(boolean mockAPI) {
        this.mockAPI = mockAPI;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        JSONObject requestObj = getPOSTRequest(request);
        JSONObject responseObj = apiPOST(requestObj);
        response.getWriter().print(responseObj.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        JSONObject requestObj = getGETRequest(request);
        JSONObject responseObj = apiGET(requestObj);
        response.getWriter().print(responseObj.toString());
    }

    public abstract boolean processAuthPOST(JSONObject request);

    public abstract JSONObject processLogicPOST(JSONObject request);

    public abstract JSONObject processHeaderPOST(JSONObject request);

    public abstract boolean processAuthGET(JSONObject request);

    public abstract JSONObject processLogicGET(JSONObject request);

    public abstract JSONObject processHeaderGET(JSONObject request);

}
