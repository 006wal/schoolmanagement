
package SchoolmanagementSystem.SchoolmanagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "SchoolmanagementSystem") // Force le scan de TOUT le projet
public class SchoolmanagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolmanagementSystemApplication.class, args);
        System.out.println("------------------------------------------------");
        System.out.println("   DESIGN ACTIVÉ SUR http://localhost:8080/login");
        System.out.println("------------------------------------------------");
    }
}