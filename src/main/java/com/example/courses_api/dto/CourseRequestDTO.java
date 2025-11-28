package com.example.courses_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CourseRequestDTO {
    @NotBlank(message = "Course name is required")
    @Size(min = 2, max = 100)
    private String name;


    @NotBlank(message = "Course description is required")
    @Size(min = 10, max = 500)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
