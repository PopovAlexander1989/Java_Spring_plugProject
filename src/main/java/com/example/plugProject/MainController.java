package com.example.plugProject;

import jakarta.validation.Valid;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/v1")
public class MainController {

    @GetMapping("/getRequest")
    public ResponseEntity<Map<String, Object>> getRequest(
            @RequestParam int id,
            @RequestParam String name) {

        if (id <= 10 || name.length() <= 5) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "id должен быть > 10 и name длиннее 5 символов"));
        }

        try {
            if (id > 10 && id < 50) {
                Thread.sleep(1000);
            } else {
                Thread.sleep(500);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("Person1", Map.of(
                    "id", id,
                    "name", name
            ));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ошибка: " + e.getMessage()));
        }
    }
    @PostMapping("/postRequest")
    public ResponseEntity<Map<String, Person>> postRequest(@RequestBody Person input) {

        if (input.getName() == null || input.getSurname() == null || input.getAge() <= 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        Person person1 = new Person();
        person1.setName(input.getName());
        person1.setSurname(input.getSurname());
        person1.setAge(input.getAge());

        Person person2 = new Person();
        person2.setName(input.getSurname());
        person2.setSurname(input.getName());
        person2.setAge(input.getAge() * 2);

        Map<String, Person> response = new HashMap<>();
        response.put("Person1", person1);
        response.put("Person2", person2);

        return ResponseEntity.ok(response);
    }
}