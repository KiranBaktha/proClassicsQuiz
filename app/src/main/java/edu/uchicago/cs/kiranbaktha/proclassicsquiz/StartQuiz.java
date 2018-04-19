package edu.uchicago.cs.kiranbaktha.proclassicsquiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.lang.*;

import java.util.Random;

public class StartQuiz extends AppCompatActivity {
    private String Language; // Language of the quiz
    private TextView Question;
    public static final String QUESTION = "edu.uchicago.cs.kiranbaktha.proclassicsquiz.QUESTION";
    private static final String DELIMITER = "\\|";
    private static final int NUM_ANSWERS = 5;
    private static final int WORD = 0;
    private static final int ANSWER = 1;


    private Random mRandom;

    private Question mQuestion;
    private String[] mWordTranslations;
    private boolean mItemSelected = false;
    //make these members
    private TextView mQuestionNumberTextView;
    private RadioGroup mQuestionRadioGroup;
    private TextView mQuestionTextView;
    private Button mSubmitButton;
    private Button mQuitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__question);
        Intent intent = getIntent();  // get the intent
        Language = intent.getStringExtra("quiz_language");
        //Question = (TextView)findViewById(R.id.questionText);
        //Question.setText(Language);

        //Allocate words
        mWordTranslations = getResources().getStringArray(R.array.classic_words);

        //get refs to inflated members
        mQuestionNumberTextView = (TextView) findViewById(R.id.questionNumber);
        mQuestionTextView = (TextView) findViewById(R.id.questionText);
        mSubmitButton = (Button) findViewById(R.id.submitButton);
        mQuitButton = (Button) findViewById(R.id.quitButton);
        mQuestionRadioGroup = (RadioGroup) findViewById(R.id.radioAnswers);

        //init the random
        mRandom = new Random();
        // Set listener for submit button
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        //set quit button action
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayResult();
            }
        });
        // Disallow submitting until an answer is selected. When the checked stated of the radio group changes this is triggered.
        mQuestionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mSubmitButton.setEnabled(true);
                mItemSelected = true;
            }
        });

        // fireQuestion();
        fireQuestion(savedInstanceState);

    }
        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            //pass the question into the bundle when I have a config change
            outState.putSerializable(StartQuiz.QUESTION, mQuestion);
        }

        private void fireQuestion(){
            mQuestion = getQuestion();
            populateUserInterface();
        }

    //overloaded to take savedInstanceState
    private void fireQuestion(Bundle savedInstanceState){

        if (savedInstanceState == null ){
            mQuestion = getQuestion();
        } else {
            mQuestion = (Question) savedInstanceState.getSerializable(StartQuiz.QUESTION);
        }
        populateUserInterface();
    }


    private void submit() {
        Button checkedButton = (Button) findViewById(mQuestionRadioGroup.getCheckedRadioButtonId());
        String guess = checkedButton.getText().toString();
        //see if they guessed right
        if (mQuestion.getTranslation().equals(guess)) {
            QuizTracker.getInstance().answeredRight();
        } else {
            QuizTracker.getInstance().answeredWrong();
        }
        if (QuizTracker.getInstance().getTotalAnswers() < Integer.MAX_VALUE) {
            //increment the question number
            QuizTracker.getInstance().incrementQuestionNumber();
            fireQuestion();
        } else {
            displayResult();
        }
    }

    private void populateUserInterface() {
        //take care of button first
        mSubmitButton.setEnabled(false);
        mItemSelected = false;

        //populate the QuestionNumber textview
        String questionNumberText = getResources().getString(R.string.questionNumberText);
        int number = QuizTracker.getInstance().getQuestionNum();
        mQuestionNumberTextView.setText(String.format(questionNumberText, number));

        //set question text
        mQuestionTextView.setText(mQuestion.getQuestionText());

        //will generate a number 0-4 inclusive
        int randomPosition = mRandom.nextInt(NUM_ANSWERS);
        int counter = 0;
        mQuestionRadioGroup.removeAllViews();
        //for each of the 5 wrong answers
        for (String wrongAnswer : mQuestion.getWrongAnswers()) {
            if (counter == randomPosition) {
                //insert the cor answer
                addRadioButton(mQuestionRadioGroup, mQuestion.getTranslation());
            } else {
                addRadioButton(mQuestionRadioGroup, wrongAnswer);
            }
            counter++;
        }
    }
    private void addRadioButton(RadioGroup questionGroup, String text) {
        RadioButton button = new RadioButton(this);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setButtonDrawable(android.R.drawable.btn_radio);
        questionGroup.addView(button);
    }

    private Question getQuestion() {
        //generate corr answer
        String[] strAnswers = getRandomQuestion();
        mQuestion = new Question(strAnswers[WORD], strAnswers[ANSWER]);
        //generates 5 wrong answers
        while (mQuestion.getWrongAnswers().size() < NUM_ANSWERS) {
            String[] strNewQuestion = getRandomQuestion();
            //if we pick the same word OR      (Handles Mixed Case as well)
            //if we already picked this one
            while (strNewQuestion[WORD].equals(strAnswers[WORD]) ||
                    mQuestion.getWrongAnswers().contains(strNewQuestion[ANSWER])){
                //then we need pick another one
                strNewQuestion = getRandomQuestion();
            }
            mQuestion.addWrongAnswer(strNewQuestion[ANSWER]);
        }
        return mQuestion;
    }

    private String[] getRandomQuestion() {
        int index = mRandom.nextInt(mWordTranslations.length);
        String[] temp = new String[2];
        String[] temp2 = mWordTranslations[index].split(DELIMITER);
        temp[0] = temp2[0];
        if(Language.equals("Latin")){ // Latin
            temp[1] = temp2[1];
        }
        else if(Language.equals("Greek")){ // Greek
            temp[1] = temp2[2];
        }
        else if(Language.equals("Mixed")){
            int choice = (Math.random() <= 0.5) ? 1 : 2; // Choose either Greek or Latin translation with equal probability
            temp[1] = temp2[choice];
        }
        return temp;
    }

    private void displayResult(){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("quiz_language",Language);  // Send the message to intent activity
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuQuit:
                displayResult();
                return true;
            case R.id.menuSubmit:
                if(mItemSelected){
                    submit();
                }
                else{
                    Toast toast = Toast.makeText(this, getResources().getText(R.string.pleaseSelectAnswer), Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}