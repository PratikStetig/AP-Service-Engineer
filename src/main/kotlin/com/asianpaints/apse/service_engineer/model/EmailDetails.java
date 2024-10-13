package com.asianpaints.apse.service_engineer.model;

public class EmailDetails {
    private String toRecipient;
    private String ccRecipient;
    private String bccRecipient;
    private String msgBody;
    private String subject;

    public String getToRecipient() {
        return toRecipient;
    }

    public void setToRecipient(String toRecipient) {
        this.toRecipient = toRecipient;
    }

    public String getCcRecipient() {
        return ccRecipient;
    }

    public void setCcRecipient(String ccRecipient) {
        this.ccRecipient = ccRecipient;
    }

    public String getBccRecipient() {
        return bccRecipient;
    }

    public void setBccRecipient(String bccRecipient) {
        this.bccRecipient = bccRecipient;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
