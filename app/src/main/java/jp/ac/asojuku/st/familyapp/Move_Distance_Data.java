package jp.ac.asojuku.st.familyapp;

/**
 * Created by Len-R on 2016/10/28.
 */

public class Move_Distance_Data {

    private int number;
    private int addition;
    private String comment;

    public Move_Distance_Data(int number, int addition, String comment) {
        this.number = number;
        this.addition = addition;
        this.comment = comment;
    }

    public int getnumber() {
        return number;
    }

    public int getAddition() {
        return addition;
    }

    public String getComment() {
        return comment;
    }

}