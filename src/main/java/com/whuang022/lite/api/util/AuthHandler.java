package com.whuang022.lite.api.util;

import com.whuang022.lite.api.db.DBAuthHandler;
import com.whuang022.lite.api.db.DBHandler;
import java.security.MessageDigest;

public class AuthHandler extends Handler {

    private DBHandler dbAuth=new DBAuthHandler("my_db");
    
    public AuthHandler(boolean mock) {
        super(mock);
    }

    public AuthHandler() {
        super();
    }

    public Object processMock(Object... arguments) {
        boolean login;
        if (arguments[0].equals("test") && arguments[1].equals(sha256("test"))) { //change your test usr&pwd here
            login = true;
        } else {
            login = false;
        }
        return login;
    }

    public Object processReal(Object... arguments) {
        return dbAuth.DBProcess(arguments);
    }

    /**
     * SHA-256 (Hex)
     *
     * @param input The String Want To Encrypt
     * @return Sting Encrypt by SHA-256
     */
    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
