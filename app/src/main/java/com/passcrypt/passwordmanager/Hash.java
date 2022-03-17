package com.passcrypt.passwordmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hash {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String h(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("Temp_pref", Context.MODE_PRIVATE);
        String s = sharedpreferences.getString("Cpass","");
        String password = s;
        byte[] messageDigest;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            messageDigest = md.digest(password.getBytes());
            String hash = Base64.getEncoder().encodeToString(messageDigest);
            return hash;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String gethash(String s){
        String password = s;
        byte[] messageDigest;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            messageDigest = md.digest(password.getBytes());
            String hash = Base64.getEncoder().encodeToString(messageDigest);
            return hash;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
