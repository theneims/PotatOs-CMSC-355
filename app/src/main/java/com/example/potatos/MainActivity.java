package com.example.potatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.common.User;

import static com.example.common.REST.UserREST.getByEmailPass;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button logInBtn = findViewById(R.id.logInBtn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText = findViewById(R.id.emailEditText);
                EditText passwordEditText = findViewById(R.id.passwordEditText);
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                try{
                    User user = getByEmailPass(email, password);
                    if (user != null) {
                        if (user.getIsTeacher()){
                            String requestJson = user.toJson();

                            Intent overviewTeacher = new Intent(getApplicationContext(), TeacherOverview.class);
                            overviewTeacher.putExtra("com.example.potatos.logIn", requestJson);
                            startActivity(overviewTeacher);
                        }
                        else {
                            String requestJson = user.toJson();

                            Intent overviewStudent = new Intent(getApplicationContext(), StudentOverview.class);
                            overviewStudent.putExtra("com.example.potatos.logIn", requestJson);
                            startActivity(overviewStudent);
                        }
                    }
                }
                catch (Exception e) {
                    Toast.makeText(MainActivity.this,
                            "The App is currently not working", Toast.LENGTH_LONG).show();
                }


            }
        });

        Button newAccountBtn = findViewById(R.id.newAccountBtn);
        newAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccount = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(createAccount);
            }
        });


    }

}
