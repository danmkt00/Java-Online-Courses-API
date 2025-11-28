package com.example.courses_api.controller;

import com.example.courses_api.dto.CourseRequestDTO;
import com.example.courses_api.dto.CourseResponseDTO;
import com.example.courses_api.exception.InvalidTokenException;
import com.example.courses_api.model.User;
import com.example.courses_api.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController
{
    private final CourseService courseService;
    public CourseController(CourseService courseService)
    {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses()
    {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody CourseRequestDTO dto,
            @AuthenticationPrincipal User user
            )
    {
        if(user == null)
        {
            throw  new InvalidTokenException("Invalid token");
        }

        CourseResponseDTO responseDTO = courseService.createCourse(dto, user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>  updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequestDTO dto,
            @AuthenticationPrincipal User user
    )
    {

        CourseResponseDTO responseDTO = courseService.updateCourse(id, dto, user);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    )
    {
        courseService.deleteCourse(id, user);
        return ResponseEntity.ok().body("Product deleted successfully");
    }
}

