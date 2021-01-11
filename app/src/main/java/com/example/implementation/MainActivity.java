package com.example.implementation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    TextView lblPath ;
    Button btn_choose ,btn_enc , btn_dec;
    String fileName ;
    private static final int REQUST_CODE = 43;
    Intent myFileIntent ;
    Encrypt E = new Encrypt();
    Decrypt D = new Decrypt();

    String password = "New256_Encryption_And_Decryption";

    KeyExpansion KE = new KeyExpansion();
    byte [] word = KE.GetWordKey(password.getBytes());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblPath = findViewById(R.id.labl);
        btn_choose = findViewById(R.id.chooseFile);
        btn_enc = findViewById(R.id.encrypt);
        btn_dec = findViewById(R.id.decrypt);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            String [] perm = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,perm,1);
            Toast.makeText(MainActivity.this,"Permision Done",Toast.LENGTH_LONG).show();
        }
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
                //"/storage/emulated/0/Application/Decryption";
            }
        });
        btn_enc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(lblPath.getText().toString().substring(0,19).equals("/storage/emulated/0") )
                  lblPath.setText("/storage/66DA-E92C/Application/Encryption");
                else
                    Toast.makeText(MainActivity.this,"External",Toast.LENGTH_LONG).show();*/
                if(lblPath.getText().equals("Hello World!"))
                    Toast.makeText(MainActivity.this,"You Should Choose file first",Toast.LENGTH_LONG).show();
                else
                {

                    File fin = new File(fileName);
                    if( E.encrypt(fileName,password) )
                        Toast.makeText(MainActivity.this,"Encryption is Done",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this,"File Not Found",Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this,fin.getParent()+"/Enc"+fin.getName(),Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lblPath.getText().equals("Hello World!"))
                    Toast.makeText(MainActivity.this,"You Should Choose file first",Toast.LENGTH_LONG).show();
                else
                {

                    if( D.decrypt(fileName,password))
                        Toast.makeText(MainActivity.this,"Decryption is Done",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this,"File Not Found",Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public void search()
    {
        myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        myFileIntent.setType("*/*");
        myFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(myFileIntent,REQUST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUST_CODE && resultCode == Activity.RESULT_OK){
            if(data != null)
            {
                Uri u = data.getData();
                fileName = FileUtils.getPath(MainActivity.this,u);
                //String path = u.getPath();
                lblPath.setText(fileName);
            }
        }
    }

}