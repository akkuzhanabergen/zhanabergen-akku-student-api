package kz.edu.zhanabergen.studentapi;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @GetMapping("/api/info")
    public Map<String, String> info() {
        return Map.of("application", "Student CRUD API", "student", "Zhanabergen Akku",
                      "version", "1.0.0");
    }
}
