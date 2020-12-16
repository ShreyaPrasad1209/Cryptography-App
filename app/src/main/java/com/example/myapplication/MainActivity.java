package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    EditText inputText,inputPassword;
    TextView outputText;
    Button encBtn,decBtn;
    String outputString;
    String AES="AES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText=(EditText)findViewById(R.id.inputText);
        inputPassword=(EditText)findViewById(R.id.inputPassword);
        outputText=(TextView)findViewById(R.id.outputText);
        encBtn=(Button)findViewById(R.id.encBtn);
        decBtn=(Button)findViewById(R.id.decBtn);
        encBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputString=encrypt(inputText.getText().toString(),inputPassword.getText().toString());
                    System.out.println(outputString);
                    outputText.setText(outputString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputString=decrypt(outputString,inputPassword.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                outputText.setText(outputString);
            }
        });
    }

    private String decrypt(String outputString, String password) throws Exception{
        SecretKeySpec key=generateKey(password);
        Cipher c= Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodedValue=Base64.decode(outputString,Base64.DEFAULT);
        byte[] devValue=c.doFinal(decodedValue);
        String decryptedValue=new String(devValue);
        return decryptedValue;
    }

    private String encrypt(String Data, String password) throws Exception{
        SecretKeySpec key=generateKey(password);
        Cipher c= Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal=c.doFinal(Data.getBytes());
        String encryptedValue= Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest= MessageDigest.getInstance("SHA-256");
        byte[] bytes=password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key=digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }
}

//comment