package ca.nait.dmit.dmit2504.chattergen2;

public class Chatter {

    private String loginName;
    private String message;
    private String date;

    public Chatter() {
    }

    public Chatter(String loginName, String message, String date) {
        this.loginName = loginName;
        this.message = message;
        this.date = date;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
