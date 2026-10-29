package kz.edu.zhanabergen.studentapi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private Integer course;

    protected Student() { }

    public Student(String fullName, String email, String major, Integer course) {
        this.fullName = fullName;
        this.email = email;
        this.major = major;
        this.course = course;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getMajor() { return major; }
    public Integer getCourse() { return course; }

    public void update(String fullName, String email, String major, Integer course) {
        this.fullName = fullName;
        this.email = email;
        this.major = major;
        this.course = course;
    }
}
