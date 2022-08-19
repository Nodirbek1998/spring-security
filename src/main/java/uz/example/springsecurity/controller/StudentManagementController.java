package uz.example.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/management/api/student")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1L, "Nodirbek"),
            new Student(2L, "Azizber"),
            new Student(3L, "Asqar")
    );


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    public List<Student> getAllStudents(){
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student){
        System.out.println(student);
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void delete(@PathVariable Integer studentId){
        System.out.println(studentId);
    }

    @GetMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable Integer studentId, @RequestBody Student student){
        System.out.println(String.format("%s %s",studentId, student));
    }

}
