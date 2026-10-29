package kz.edu.zhanabergen.studentapi;

public record StudentResponse(Long id, String fullName, String email, String major, Integer course) {
    public static StudentResponse from(Student student) {
        return new StudentResponse(student.getId(), student.getFullName(), student.getEmail(),
                                   student.getMajor(), student.getCourse());
    }
}
