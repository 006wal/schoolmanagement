/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SchoolmanagementSystem.SchoolmanagementSystem.pagecontroller;

import SchoolmanagementSystem.SchoolmanagementSystem.model.Grade;
import SchoolmanagementSystem.SchoolmanagementSystem.model.User;
import SchoolmanagementSystem.SchoolmanagementSystem.repository.GradeRepository;
import SchoolmanagementSystem.SchoolmanagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @GetMapping("/dashboard")
    public String studentDashboard(Model model, Principal principal) {
        // Récupérer l'élève connecté via son email
        String email = principal.getName();
        User student = userRepository.findByEmail(email);

        // Récupérer UNIQUEMENT les notes de cet élève
        List<Grade> myGrades = gradeRepository.findByStudent(student);

        // Calculer la moyenne générale simple
        double average = myGrades.stream()
                                 .mapToDouble(Grade::getScore)
                                 .average()
                                 .orElse(0.0);

        model.addAttribute("student", student);
        model.addAttribute("grades", myGrades);
        model.addAttribute("average", Math.round(average * 100.0) / 100.0); // Arrondi à 2 décimales
        
        return "student-dashboard";
    }
}