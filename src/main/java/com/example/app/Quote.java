package com.example.app;

import java.sql.Date;

public class Quote {
    private int id;
    private String quote;
    private String last_name;
    private String first_name;
    private String second_name;
    private String subject;
    private Date date;
    private int user_id;

    public Quote(int id, String quote, String last_name,  String first_name, String second_name,
                 String subject, Date date, int user_id){
        this.id = id;
        this.quote = quote;
        this.last_name = last_name;
        this.first_name = first_name;
        this.second_name = second_name;
        this.subject = subject;
        this.date = date;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date day) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
