package com.passcrypt.passwordmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecrypt {
    private String pass = null;
    //get key from sharedprefrences
    public EncryptDecrypt(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("Temp_pref", Context.MODE_PRIVATE);
        String s = sharedpreferences.getString("Spass","");
        String c = sharedpreferences.getString("Cpass","");
        if (!s.isEmpty()){
            pass = s;
        }
        if (!c.isEmpty()){
            pass = c;
        }
    }
    //Encrypt
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(String message){
        try{
            SecretKeySpec key = new SecretKeySpec(pass.getBytes(), "AES");
            byte[] messageInBytes = message.getBytes();
            Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            encryptionCipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] asd = encryptionCipher.getIV();
            byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
            String soo = Base64.getEncoder().encodeToString(encryptedBytes);
            String iiv = Base64.getEncoder().encodeToString(asd);
            return iiv+soo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //Decrypt
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decrypt(String dbmessage){
        try{
            String i = dbmessage.substring(0,16);
            String en = dbmessage.substring(16);
            byte[] data = Base64.getDecoder().decode(en);
            byte[] ivv = Base64.getDecoder().decode(i);
            Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(128,ivv);
            SecretKeySpec key = new SecretKeySpec(pass.getBytes(), "AES");
            decryptionCipher.init(Cipher.DECRYPT_MODE,key,spec);
            byte[] decryptedBytes = decryptionCipher.doFinal(data);
            String ss2 = new String(decryptedBytes);
            return ss2;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
