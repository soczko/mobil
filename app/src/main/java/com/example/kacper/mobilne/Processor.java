package com.example.kacper.mobilne;

public class Processor {
    private String company;
    private String model;
    private String cores;
    private String threads;
    private String clock;

    public void setCompany(String s){
        company = s;
    }

    public void setModel(String s){
        model = s;
    }

    public void setCores(String s){
        cores = s;
    }

    public void setThreads(String s){
        threads = s;
    }

    public void setClock(String s){
        clock = s;
    }

    public String getCompany(){
        return company;
    }

    public String getModel(){
        return model;
    }

    public String getCores(){
        return cores;
    }

    public String getThreads(){
        return threads;
    }

    public String getClock(){
        return clock;
    }
}
