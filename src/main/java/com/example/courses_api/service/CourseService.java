package com.example.courses_api.service;


import com.example.courses_api.dto.CourseRequestDTO;
import com.example.courses_api.dto.CourseResponseDTO;
import com.example.courses_api.exception.CourseNotFoundException;
import com.example.courses_api.mapper.CourseMapper;
import com.example.courses_api.model.Course;
import com.example.courses_api.model.User;
import com.example.courses_api.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(courseMapper::convertToDTO).toList();
    }

    public CourseResponseDTO createCourse(CourseRequestDTO dto, User user) {
        Course course = courseMapper.convertToEntity(dto);
        course.setUser(user);
        Course savedProduct = courseRepository.save(course);
        return courseMapper.convertToDTO(savedProduct);
    }


    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO dto, User user) {
        Course course = courseRepository
                .findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));

        if (!course.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You don't owe this course");
        }
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());

        Course updatedCourse = courseRepository.save(course);
        return courseMapper.convertToDTO(updatedCourse);
    }

    public void deleteCourse(Long id, User user) {
        Course course = courseRepository
                .findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));

        if (!course.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You don't owe this product");
        }
        courseRepository.delete(course);
    }
}
