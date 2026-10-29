package kz.edu.zhanabergen.studentapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StudentApiIntegrationTest {
    @Autowired private MockMvc mvc;
    @Autowired private StudentRepository repository;

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    void createAndReadStudent() throws Exception {
        String payload = """
            {"fullName":"Zhanabergen Akku","email":"akku@example.com", "major":"Information Systems","course":3}
            """;
        mvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON).content(payload))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.fullName").value("Zhanabergen Akku"));
        mvc.perform(get("/api/students"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].email").value("akku@example.com"));
    }

    @Test
    void updateStudent() throws Exception {
        Long id = repository.save(new Student("Before", "before@example.com", "Computer Science", 2)).getId();
        String payload = """
            {"fullName":"Zhanabergen Akku","email":"updated@example.com","major":"Data Science","course":3}
            """;
        mvc.perform(put("/api/students/{id}", id).contentType(MediaType.APPLICATION_JSON).content(payload))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.major").value("Data Science"));
        mvc.perform(get("/api/students/{id}", id))
            .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    void deleteStudent() throws Exception {
        Long id = repository.save(new Student("Before", "before@example.com", "IT", 2)).getId();
        mvc.perform(delete("/api/students/{id}", id)).andExpect(status().isNoContent());
        mvc.perform(get("/api/students/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    void missingStudentReturns404() throws Exception {
        mvc.perform(get("/api/students/{id}", 99999))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Student with ID 99999 not found"));
    }

    @Test
    void invalidEmailReturns400() throws Exception {
        String payload = """
            {"fullName":"Zhanabergen Akku","email":"bad-email","major":"IT","course":3}
            """;
        mvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON).content(payload))
            .andExpect(status().isBadRequest());
    }

    @Test
    void infoContainsStudentName() throws Exception {
        mvc.perform(get("/api/info"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.student").value("Zhanabergen Akku"));
    }
}
