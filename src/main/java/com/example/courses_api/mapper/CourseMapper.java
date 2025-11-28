package com.example.courses_api.mapper;

import com.example.courses_api.dto.CourseRequestDTO;
import com.example.courses_api.dto.CourseResponseDTO;
import com.example.courses_api.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public Course convertToEntity(CourseRequestDTO dto)
    {
        Course course = new Course();
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        return course;
    }

    public CourseResponseDTO convertToDTO(Course course)
    {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        dto.setUserId(course.getUser().getId());
        return dto;
    }
}
