package kz.edu.zhanabergen.studentapi;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
            .map(StudentResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public StudentResponse findById(Long id) {
        return StudentResponse.from(getStudent(id));
    }

    @Transactional
    public StudentResponse create(StudentRequest request) {
        Student student = new Student(request.fullName(), request.email(), request.major(), request.course());
        return StudentResponse.from(repository.save(student));
    }

    @Transactional
    public StudentResponse update(Long id, StudentRequest request) {
        Student student = getStudent(id);
        student.update(request.fullName(), request.email(), request.major(), request.course());
        return StudentResponse.from(repository.save(student));
    }

    @Transactional
    public void delete(Long id) {
        Student student = getStudent(id);
        repository.delete(student);
    }

    private Student getStudent(Long id) {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }
}
