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
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivity extends AppCompatActivity {
    TextView textView;
    EditText editText1,editText2,editText3,editText4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Actionbar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Statusbar colour
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#0336ff"));

        textView = findViewById(R.id.textView4);
        editText1 = findViewById(R.id.editTextTextPersonName);
        editText2 = findViewById(R.id.editTextTextPersonName2);
        editText3 = findViewById(R.id.editTextTextPasswordchanged);
        editText4 = findViewById(R.id.editTextTextMultiLine4);

        //Transfer info object from mainactivity to editactivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //This "parcable" is for passing objects between activities
            Tileobj tileobj = (Tileobj) extras.getParcelable("Tileobj");
            textView.setText(tileobj.getText1());
            editText1.setText(tileobj.getweb());
            editText2.setText(tileobj.getuser());
            editText3.setText(tileobj.getpass());
            editText4.setText(tileobj.getnote());
        }
        //Delete Icon & Prompt
        ImageButton del = findViewById(R.id.imageView5);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Delete");
                builder.setMessage(textView.getText().toString()+" will be deleted!");
                //YES
                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHandler dbHandler = new DBHandler(EditActivity.this);
                                dbHandler.delval(textView.getText().toString());
                                finish();
                            }
                        });
                //NO
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        //Update values
        FloatingActionButton save = findViewById(R.id.floatingActionButton3);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Update");
                builder.setMessage(textView.getText().toString()+" will be updated!");
                //YES
                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHandler dbHandler = new DBHandler(EditActivity.this);
                                Tileobj tileobj = new Tileobj("",textView.getText().toString(),editText1.getText().toString(),editText2.getText().toString(),editText3.getText().toString(),editText4.getText().toString());
                                dbHandler.updateval(textView.getText().toString(),tileobj);
                                finish();
                            }
                        });
                //NO
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}