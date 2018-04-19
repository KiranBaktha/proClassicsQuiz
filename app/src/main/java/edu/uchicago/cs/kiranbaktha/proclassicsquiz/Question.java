package edu.uchicago.cs.kiranbaktha.proclassicsquiz;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Question implements Serializable {

    //this overrides the serializable id
    private static final long serialVersionUID = 6546546516546843135L;

    private String mWord;
    private String mAnswer;
    private Set<String> mWrongAnswers = new HashSet<String>();

    public Question(String word, String answer) {
        this.mWord = word;
        this.mAnswer = answer;
    }


    public String getTranslation() {
        return mAnswer;
    }

    public String getWord() {
        return mWord;
    }

    public Set<String> getWrongAnswers() {
        return mWrongAnswers;
    }

    public boolean addWrongAnswer(String wrongAnswer){
        return mWrongAnswers.add(wrongAnswer);
    }

    public String getQuestionText(){
        return "Which is  " + mWord+ "?";
    }
}

