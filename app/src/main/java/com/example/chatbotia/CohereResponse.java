package com.example.chatbotia;

import java.util.List;

public class CohereResponse {
    private List<Generation> generations;

    public List<Generation> getGenerations() {
        return generations;
    }
}

class Generation {
    private String text;

    public String getText() {
        return text;
    }
}
