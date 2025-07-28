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

@RestController
@RequestMapping("/app/v1")
public class MainController {

    @GetMapping("/getRequest")
    public ResponseEntity<String> getRequest(@RequestParam int id, @RequestParam String name) {
        if (id <= 10) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка: id должен быть больше 10");
        }

        if (name.length() <= 5) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка: длина name должна быть больше 5 символов");
        }

        try {
            ClassPathResource resource = new ClassPathResource("getAnswer.txt");
            String template = Files.readString(resource.getFile().toPath());

            String response = template
                    .replace("{id}", String.valueOf(id))
                    .replace("{name}", name);

            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка чтения шаблона: " + e.getMessage());
        }
    }
    @PostMapping("/postRequest")
    public ResponseEntity<String> postRequest(@RequestBody @Valid PersonRequest request) {
        try {

            ClassPathResource resource = new ClassPathResource("postAnswer.txt");
            String template = Files.readString(resource.getFile().toPath());


            String name = request.getName();
            String surname = request.getSurname();
            int age = request.getAge();
            int doubleAge = age * 2;

            template = template.replace("{age}*2", String.valueOf(doubleAge));


            String response = template
                    .replace("{name}", name)
                    .replace("{surname}", surname)
                    .replace("{age}", String.valueOf(age));

            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка чтения шаблона: " + e.getMessage());
        }
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder("Ошибки валидации:\n");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.append("- ").append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n")
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errors.toString());
    }
    @GetMapping("/getRequest2")
    public ResponseEntity<String> getRequest2(
            @RequestParam int id,
            @RequestParam String name) {

        // Валидация
        if (id <= 10 || name.length() <= 5) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка: id должен быть > 10, длина name > 5");
        }

        try {

            if (id > 10 && id < 50) {
                Thread.sleep(1000);
            } else {
                Thread.sleep(500);
            }

            ClassPathResource resource = new ClassPathResource("getAnswer.txt");
            String template = Files.readString(resource.getFile().toPath());

            String response = template
                    .replace("{id}", String.valueOf(id))
                    .replace("{name}", name);

            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при обработке запроса: " + e.getMessage());
        }
    }
}