package com.passcrypt.passwordmanager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class Login extends AppCompatActivity {
    private String col = "#0336ff";
    private String filepath1 = null;
    private String pass1 = null;
    private String filepath2 = null;
    private String pass2 = null;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            //Force lignt mode
            AppCompatDelegate.setDefaultNightMode(1);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            //Actionbar colour
            ActionBar actionBar = getSupportActionBar();
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(col));
            actionBar.setBackgroundDrawable(colorDrawable);
            actionBar.setTitle("Login");
            //Statusbar colour
            Window window = this.getWindow();
            window.setStatusBarColor(Color.parseColor(col));

            //Check premission
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 2);
            //All files permission
            if ( !Environment.isExternalStorageManager() ){
                manageextsto();
            }

            //Select db file icom (open file)
            ImageButton imagebutton = findViewById(R.id.imageButton);
            imagebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("application/octet-stream");
                    startActivityForResult(i, 1);
                }
            });

//        //Create new db icon (open folder)
            ImageButton imagebutton12 = findViewById(R.id.imageButton12);
            imagebutton12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                    startActivityForResult(intent, 2);
                }
            });

            //Select database
            Button go1 = findViewById(R.id.button2);
            EditText editText = findViewById(R.id.editTextTextPassword3);
            go1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText.getText().toString().length() == 16) {
                        pass1 = editText.getText().toString();
                    } else {
                        Toast.makeText(Login.this, "Password must be 16 characters long", Toast.LENGTH_SHORT).show();
                        editText.setText("");
                    }
                    if ((pass1 != null) && (filepath1 != null)) {
                        SharedPreferences sharedpreferences = getSharedPreferences("Temp_pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("Spath", filepath1);
                        editor.putString("Spass", pass1);
                        editor.putString("Cpath", null);
                        editor.putString("Cpass", null);
                        editor.putString("name", null);
                        editor.commit();
                        if (checkpass(pass1)){
                            pass1 = null;
                            editText.setText("");
                            Intent i = new Intent(Login.this, MainActivity.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                            editor.clear();
                            editor.commit();
                        }

                    }
                }
            });

            //Create database
            Button go2 = findViewById(R.id.button);
            EditText editText2 = findViewById(R.id.editTextTextPassword2);
            go2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText2.getText().toString().length() == 16) {
                        pass2 = editText2.getText().toString();
                    } else {
                        Toast.makeText(Login.this, "Password must be 16 characters long", Toast.LENGTH_SHORT).show();
                        editText2.setText("");
                    }
                    if ((pass2 != null) && (filepath2 != null)) {
                        SharedPreferences sharedpreferences = getSharedPreferences("Temp_pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("Spath", null);
                        editor.putString("Spass", null);
                        editor.putString("Cpath", filepath2);
                        editor.putString("Cpass", pass2);
                        EditText name = findViewById(R.id.editTextTextPersonName3);
                        editor.putString("name", name.getText().toString());
                        editor.commit();
                        pass2 = null;
                        editText2.setText("");
                        name.setText("");
                        String cv = (new SP()).path(Login.this);
                        File file = new File(cv);
                        if (!file.exists()){
                            cv = null;
                            file = null;
                            Intent i = new Intent(Login.this, MainActivity.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(Login.this, "File already exists", Toast.LENGTH_SHORT).show();
                            cv = null;
                            file = null;
                        }

                    }
                }
            });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            switch (requestCode) {
                case 1:
                    if (resultCode == RESULT_OK) {
                        String res = data.getData().getPath();
                        if ((res.substring(Math.max(res.length() - 2, 0))).equals("db")) {
                            TextView textView = findViewById(R.id.textView12);
                            //String modification for compatable file paths
                            String a = StringUtils.substringBetween(res, "/document/", ":");
                            if (a.equals("primary")) {
                                String b = StringUtils.substringAfter(res, ":");
                                String c = "/storage/self/primary/" + b;
                                filepath1 = c;
                                textView.setText(c);
                            } else {
                                String bb = StringUtils.substringAfter(res, ":");
                                String aa = "/storage/" + a + "/" + bb;
                                filepath1 = aa;
                                textView.setText(aa);
                            }
                        } else {
                            Toast.makeText(Login.this, "Please select database file (.db)", Toast.LENGTH_SHORT).show();
                        }

                    }
                case 2:

                    if (resultCode == RESULT_OK) {
                        String res_ = data.getData().getPath();
                        TextView textView_ = findViewById(R.id.textView17);
                        //String modification for compatable file paths
                        String a_ = StringUtils.substringBetween(res_, "/tree/", ":");
                        if (a_.equals("primary")) {
                            String b_ = StringUtils.substringAfter(res_, ":");
                            String c_ = "/storage/self/primary/" + b_;
                            filepath2 = c_;
                            textView_.setText(c_);
                        } else {
                            String bb_ = StringUtils.substringAfter(res_, ":");
                            String aa_ = "/storage/" + a_ + "/" + bb_;
                            filepath2 = aa_;
                            textView_.setText(aa_);
                        }
                    }
                    break;
            }

        }catch (Exception e){
            Log.d("ttt", "crash at login activity"+e.toString());
        }

        }





    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(Login.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(Login.this, new String[] { permission }, requestCode);
//            Toast.makeText(Login.this, "now granted", Toast.LENGTH_SHORT).show();
        }
        else {
//            Toast.makeText(Login.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void manageextsto(){
        try {
            Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
            startActivity(intent);
        } catch (Exception ex){
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            startActivity(intent);
        }
    }

    //Check Password
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean checkpass(String s){
        Hash h = new Hash();
        String hash = h.gethash(s);
        DBHandler dbHandler = new DBHandler(Login.this);
        String hashdb = dbHandler.gethashfromdb();
        if (hash.equals(hashdb)){
            return true;
        }else {
            return false;
        }

    }
}