package com.example.demo.factory.listener;

public class ApplicationStartingEvent implements ApplicationEvent{
    private String text;

    public ApplicationStartingEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
