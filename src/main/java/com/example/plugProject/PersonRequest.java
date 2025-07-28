package com.example.plugProject;

import jakarta.validation.constraints.*;

public class PersonRequest {

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @NotBlank(message = "Фамилия не должна быть пустой")
    private String surname;

    @NotNull(message = "Возраст обязателен")
    private Integer age;

    public PersonRequest() {}

    public PersonRequest(String name, String surname, Integer age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
