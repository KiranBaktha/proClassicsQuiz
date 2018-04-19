package edu.uchicago.cs.kiranbaktha.proclassicsquiz;

public class QuizTracker {
    private int mCorrectAnswerNum = 0;
    private int mIncorrectAnswerNum = 0;
    private String mName;
    private int mQuestionNum = 1;
    //2. provide a static member of the same type of the enclosing class
    private  static QuizTracker sQuizTracker;
    // Override it's default constructor
    private QuizTracker(){}

    public static QuizTracker getInstance(){
        if (sQuizTracker == null){
            sQuizTracker = new QuizTracker();
            return sQuizTracker;
        }
        else {
            return sQuizTracker;
        }
    }

    public void setIncorrectAnswerNum(int incorrectAnswerNum) {
        mIncorrectAnswerNum = incorrectAnswerNum;
    }

    public void setCorrectAnswerNum(int correctAnswerNum) {
        mCorrectAnswerNum = correctAnswerNum;
    }

    public void reset(){
        setName("");
        setQuestionNum(1);
        setIncorrectAnswerNum(0);
        setCorrectAnswerNum(0);
    }

    public void again(){
        setQuestionNum(1);
        setIncorrectAnswerNum(0);
        setCorrectAnswerNum(0);
    }

    public int getQuestionNum() {
        return mQuestionNum;
    }

    public void setQuestionNum(int questionNum) {
        mQuestionNum = questionNum;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }


    public void answeredWrong(){
        mIncorrectAnswerNum++;
    }

    public void answeredRight(){
        mCorrectAnswerNum++;
    }

    public void incrementQuestionNumber(){
        mQuestionNum++;
    }

    public int getCorrectAnswerNum(){
        return mCorrectAnswerNum;
    }

    public int getIncorrectAnswerNum(){
        return mIncorrectAnswerNum;
    }

    public int getTotalAnswers(){
        return mCorrectAnswerNum + mIncorrectAnswerNum;
    }
}