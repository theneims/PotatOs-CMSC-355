package com.example.potatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.common.Class;
import com.example.common.Quiz;
import com.example.common.User;

import java.util.ArrayList;

public class ClassQuizzes_Teacher extends AppCompatActivity {
    Toolbar toolbar;
    ListView quizListView;
    String json;
    String jsonClass;
    String jsonQuiz;
    User user;
    Class userClass;
    ArrayList<Quiz> quizList;
    String[] quizzes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_quizzes__teacher);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Class Name");

        createDisplayList();

        quizListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showQuizQuestions = new Intent(getApplicationContext(), QuizQuestions.class);
                Bundle extras = new Bundle();
                extras.putInt("com.example.potatos.QUIZ_INDEX", position);
                extras.putString("com.example.potatos.logIn", json);
                extras.putString("com.example.potatos.CLASS", jsonClass);
                jsonQuiz = quizList.get(position).toJson();
                extras.putString("com.example.potatos.QUIZ", jsonQuiz);
                showQuizQuestions.putExtras(extras);
                startActivity(showQuizQuestions);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //todo change option_menu to custom menu for quiz management
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //todo change these to the new option_menu_X
        switch (item.getItemId()){
            case R.id.joinClass:
                //Go to the join class Activity
                return true;
            case R.id.logOut:
                //return to Main Activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createDisplayList() {
        json = getIntent().getStringExtra("com.example.potatos.logIn");
        user = User.fromJson(json);
        jsonClass = getIntent().getStringExtra("com.example.potatos.CLASS");
        userClass = Class.fromJson(jsonClass);

        quizList = userClass.getQuizzes();
        int i = 0;
        for (Quiz object: quizList){
            quizzes[i] = object.getQuizName();
            i++;
        }


        AdapterQuizTeacher quizAdapterTeacher = new AdapterQuizTeacher(this, quizzes, false);
        quizListView.setAdapter(quizAdapterTeacher);
    }
}
