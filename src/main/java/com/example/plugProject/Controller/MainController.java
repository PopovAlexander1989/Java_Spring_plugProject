package com.example.plugProject.Controller;

import com.example.plugProject.Service.AnswerService;
import com.example.plugProject.Model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1")

public class MainController {

    private final AnswerService service;

    public MainController(AnswerService service) {
        this.service = service;
    }

    @GetMapping("/getRequest")
    public ResponseEntity<String> getRequest(@RequestParam int id, @RequestParam String name) {
        try {
            if (id <= 10 || name.length() <= 5) {
                throw new RuntimeException("id должен быть > 10, а name длинее 5 символов");
            }

            Thread.sleep(id < 50 ? 1000 : 500); // задержка

            return ResponseEntity.ok(service.buildGetResponse(id, name));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/postRequest")
    public ResponseEntity<String> postRequest(@RequestBody Person p) {
        try {
            if (isNullOrEmpty(p.getName()) || isNullOrEmpty(p.getSurname()) || p.getAge() == null) {
                throw new RuntimeException("Все поля обязательны и не могут быть пустыми");
            }

            return ResponseEntity.ok(service.buildPostResponse(
                    p.getName(), p.getSurname(), p.getAge()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}