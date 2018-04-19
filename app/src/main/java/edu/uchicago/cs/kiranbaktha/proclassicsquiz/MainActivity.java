package edu.uchicago.cs.kiranbaktha.proclassicsquiz;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private String Language; // Sets the language for the quiz
    private EditText mNameEditText;
    private Toolbar ActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNameEditText = (EditText) findViewById(R.id.Name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    public void LatinButton(View view){
        Language = "Latin";
        startQuiz(view); // Start Quiz
    }

    public void GreekButton(View view){
        Language = "Greek";
        startQuiz(view);  // Start Quiz
    }

    public void MixedButton(View view){
        Language = "Mixed";
        startQuiz(view);
    }

    public void Exit(View view){
        finish();
        System.exit(0);
    }

    public void startQuiz(View view){
        Intent intent = new Intent(this, StartQuiz.class);
        intent.putExtra("quiz_language",Language);  // Send the message to intent activity
        QuizTracker.getInstance().setQuestionNum(1);
        String mName = mNameEditText.getText().toString();
        QuizTracker.getInstance().setName(mName);
        startActivity(intent);
        finish();
    }

    public void startme(){ // Starting from Menu bar
        Intent intent = new Intent(this, StartQuiz.class);
        intent.putExtra("quiz_language",Language);  // Send the message to intent activity
        QuizTracker.getInstance().setQuestionNum(1);
        String mName = mNameEditText.getText().toString();
        QuizTracker.getInstance().setName(mName);
        startActivity(intent);
    }

    //these are for the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuExit:
                finish();
                return true;
            case R.id.menu_latin:
                Language = "Latin";
                startme();
                return true;
            case R.id.menu_greek:
                Language = "Greek";
                startme();
                return true;
            case R.id.menu_mixed:
                Language = "Mixed";
                startme();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
