package com.example.plugProject.Service;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    private String getTemplate;
    private String postTemplate;

    @PostConstruct
    public void loadTemplates() {
        getTemplate = readFile("/getAnswer.json");
        postTemplate = readFile("/postAnswer.json");
    }

    private String readFile(String path) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(path), StandardCharsets.UTF_8))
        ) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "{}";
        }
    }

    public String buildGetResponse(int id, String name) {
        return getTemplate
                .replace("{id}", String.valueOf(id))
                .replace("{name}", name);
    }

    public String buildPostResponse(String name, String surname, int age) {
        return postTemplate
                .replace("{age}*2", String.valueOf(age * 2))
                .replace("{age}", String.valueOf(age))
                .replace("{name}", name)
                .replace("{surname}", surname);
    }
}