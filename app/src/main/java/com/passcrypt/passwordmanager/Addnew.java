package com.passcrypt.passwordmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Addnew extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);

        //Actionbar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Statusbar colour
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#0336ff"));

        EditText title = findViewById(R.id.textView4an);
        EditText web = findViewById(R.id.editTextTextPersonNamean);
        EditText user = findViewById(R.id.editTextTextPersonName2an);
        EditText pass = findViewById(R.id.editTextTextPasswordanan);
        EditText note = findViewById(R.id.editTextTextMultiLine4an);
        FloatingActionButton save = findViewById(R.id.floatingActionButton3an);
        //Create new
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(Addnew.this);
                if (dbHandler.readval(title.getText().toString()) && !(title.getText().toString().isEmpty()) ){
                    Tileobj tileobj = new Tileobj("",title.getText().toString(),web.getText().toString(),user.getText().toString(),pass.getText().toString(),note.getText().toString());
                    dbHandler.addval(tileobj);
                    //Go Back
                    Toast.makeText(Addnew.this, "Added", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Addnew.this);
                    builder.setCancelable(true);
                    builder.setTitle("Alert!");
                    builder.setMessage(title.getText().toString()+" is already present");
                    //YES
                    builder.setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (title.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Addnew.this);
                    builder.setCancelable(true);
                    builder.setTitle("Alert!");
                    builder.setMessage("Title cannot be Empty!");
                    //YES
                    builder.setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });
    }
}