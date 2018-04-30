package com.example.potatos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.common.REST.QuizREST;

public class CreateQuiz extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createquiz);
    }
    public  void onAddquestionClick(View v) {
        if(v.getId() == R.id.Baddquestion) {

            Intent i = new Intent(CreateQuiz.this, TypeofQuestion.class);
            startActivity(i);
        }

    }

    public  void onSaveQuizClick(View v) {
        if(v.getId() == R.id.bSaveQuiz) {

            Intent i = new Intent(CreateQuiz.this, ClassQuizzes.class);
            startActivity(i);
        }

    }
}