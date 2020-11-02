package cn.itcast.domain.system;

import java.io.Serializable;
import java.util.Date;

public class Email implements Serializable {
    private String emailId;
    private String userId;
    private Date emailTime;
    private String emailTitle;
    private String emailContent;

    public Email() {
    }

    public Email(String emailId, String userId, Date emailTime, String emailTitle, String emailContent) {
        this.emailId = emailId;
        this.userId = userId;
        this.emailTime = emailTime;
        this.emailTitle = emailTitle;
        this.emailContent = emailContent;
    }


    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getEmailTime() {
        return emailTime;
    }

    public void setEmailTime(Date emailTime) {
        this.emailTime = emailTime;
    }


    public String getEmailTitle() {
        return emailTitle;
    }


    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }


    public String getEmailContent() {
        return emailContent;
    }


    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailId='" + emailId + '\'' +
                ", userId='" + userId + '\'' +
                ", emailTime=" + emailTime +
                ", emailTitle='" + emailTitle + '\'' +
                ", emailContent='" + emailContent + '\'' +
                '}';
    }
}
