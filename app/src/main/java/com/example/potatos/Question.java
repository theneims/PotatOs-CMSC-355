package com.example.potatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.Answer;
import com.example.common.Quiz;
import com.example.common.User;

import java.util.ArrayList;

public class Question extends AppCompatActivity {

    String json;
    String jsonClass;
    String jsonQuiz;
    String jsonQuestion;
    String jsonNewQuestion;
    User user;
    Quiz userQuiz;
    Answer[] answerList;
    ArrayList<com.example.common.Question> questionList;
    com.example.common.Question userQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        json = getIntent().getStringExtra("com.example.potatos.logIn");
        jsonClass = getIntent().getStringExtra("com.example.potatos.CLASS");
        jsonQuiz = getIntent().getStringExtra("com.example.potatos.QUIZ");
        jsonQuestion = getIntent().getStringExtra("com.example.potatos.QUESTION");
        user = User.fromJson(json);
        userQuiz = Quiz.fromJson(jsonQuiz);
        userQuestion = com.example.common.Question.fromJson(jsonQuestion);
        questionList = userQuiz.getQuestions();
        answerList = userQuestion.getAnswers();

        //Propagate the answerText and questionText
        TextView questionText = findViewById(R.id.questionTextView);
        TextView[] answerTextView = new TextView[4];
        answerTextView[0] = findViewById(R.id.answerTextViewA);
        answerTextView[1] = findViewById(R.id.answerTextViewB);
        answerTextView[2] = findViewById(R.id.answerTextViewC);
        answerTextView[3] = findViewById(R.id.answerTextViewD);

        //Put the text of the question and answers in the TextViews
        questionText.setText(userQuestion.getPrompt());
        for (int i = 0; i < answerList.length; i++){
            answerTextView[i].setText(answerList[i].getAnswerText());
        }

        //Run the Buttons and allow the user to select them.
        setupNextSelectedButton();
        setupPrevSelectedButton();
    }

    public void setupNextSelectedButton() {
        Button button = findViewById(R.id.nextQuestion);
        final int position = getIntent().getIntExtra(
                "com.example.potatos.QUESTION_INDEX", 0);
        if (position == questionList.size()) {
            button.setText(R.string.returnQuiz);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup selection = findViewById(R.id.radio_group_selection);
                int idOfSelected = selection.getCheckedRadioButtonId();
                RadioButton selectedLetter = findViewById(idOfSelected);
                //First clear any previously selected answers if this is a return question
                setAllAnswersFalse();
                //send the selectedLetter to the QuizClass and check isAnswered();
                String letter = selectedLetter.getText().toString();
                for (int i = 0; i < answerList.length; i++) {
                    if (letter.equals(answerList[i].getLetter())){
                        answerList[i].setSelected(true);
                    }
                }
                //Move to the next question in the Quiz
                if (position < questionList.size()) { //makes sure we don't go out of bounds
                    jsonNewQuestion = questionList.get(position + 1).toJson();

                    Intent nextQuestion = new Intent(getApplicationContext(), Question.class);
                    Bundle extras = new Bundle();
                    extras.putInt("com.example.potatos.QUESTION_INDEX", position + 1);
                    extras.putInt("com.example.potatos.QUIZ_INDEX",
                            getIntent().getIntExtra("com.example.potatos.QUIZ_INDEX", 0));
                    extras.putString("com.example.potatos.logIn", json);
                    extras.putString("com.example.potatos.CLASS", jsonClass);
                    extras.putString("com.example.potatos.QUIZ", jsonQuiz);
                    extras.putString("com.example.potatos.QUESTION", jsonNewQuestion);

                    nextQuestion.putExtras(extras);
                    startActivity(nextQuestion);
                    finish();
                }
                //if we are at the last question, replace button with return to quiz.
                else {
                    Intent returnToQuiz = new Intent(getApplicationContext(), Quiz.class);
                    Bundle extras = new Bundle();
                    extras.putInt("com.example.potatos.QUIZ_INDEX",
                            getIntent().getIntExtra("com.example.potatos.QUIZ_INDEX", 0));
                    extras.putString("com.example.potatos.logIn", json);
                    extras.putString("com.example.potatos.CLASS", jsonClass);
                    extras.putString("com.example.potatos.QUIZ", jsonQuiz);

                    returnToQuiz.putExtras(extras);
                    startActivity(returnToQuiz);
                    finish();
                }
            }
        });
    }

    public void setupPrevSelectedButton() {
        Button button = findViewById(R.id.prevQuestion);
        final int position = getIntent().getIntExtra(
                "com.example.potatos.QUESTION_INDEX", 0);
        if (position == 0) {
            button.setText(R.string.returnQuiz);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup selection = findViewById(R.id.radio_group_selection);
                int idOfSelected = selection.getCheckedRadioButtonId();
                RadioButton selectedLetter = findViewById(idOfSelected);
                //First clear any previously selected answers if this is a return question
                setAllAnswersFalse();
                //send the selectedLetter to the QuizClass and check isAnswered();
                String letter = selectedLetter.getText().toString();
                for (int i = 0; i < answerList.length; i++) {
                    if (letter.equals(answerList[i].getLetter())){
                        answerList[i].setSelected(true);
                    }
                }
                //Move to the next question in the Quiz
                if (position > 0) {
                    jsonNewQuestion = questionList.get(position - 1).toJson();

                    Intent prevQuestion = new Intent(getApplicationContext(), Question.class);
                    Bundle extras = new Bundle();
                    extras.putInt("com.example.potatos.QUESTION_INDEX", position - 1);
                    extras.putString("com.example.potatos.logIn", json);
                    extras.putString("com.example.potatos.CLASS", jsonClass);
                    extras.putString("com.example.potatos.QUIZ", jsonQuiz);
                    extras.putString("com.example.potatos.QUESTION", jsonNewQuestion);

                    prevQuestion.putExtras(extras);
                    startActivity(prevQuestion);
                    finish();
                }
                else {
                    Intent returnToQuiz = new Intent(getApplicationContext(), Quiz.class);
                    Bundle extras = new Bundle();
                    extras.putInt("com.example.potatos.QUIZ_INDEX",
                            getIntent().getIntExtra("com.example.potatos.QUIZ_INDEX", 0));
                    extras.putString("com.example.potatos.logIn", json);
                    extras.putString("com.example.potatos.CLASS", jsonClass);
                    extras.putString("com.example.potatos.QUIZ", jsonQuiz);

                    returnToQuiz.putExtras(extras);
                    startActivity(returnToQuiz);
                    finish();
                }
            }
        });
    }

    public void setAllAnswersFalse() {
        for (int i = 0; i < answerList.length; i++) {
                answerList[i].setSelected(false);
        }
    }
}
