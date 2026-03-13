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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/teacher") // Toutes les routes commencent par /teacher
public class TeacherController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GradeRepository gradeRepository;

    // 1. Afficher le Dashboard du Professeur
    @GetMapping("/dashboard")
    public String teacherDashboard(Model model, Principal principal) {
        // Principal permet de récupérer le prof actuellement connecté
        String email = principal.getName();
        User teacher = userRepository.findByEmail(email);
        
        // On récupère les notes qu'il a déjà saisies
        List<Grade> myGrades = gradeRepository.findByTeacher(teacher);
        
        model.addAttribute("teacher", teacher);
        model.addAttribute("grades", myGrades);
        return "teacher-dashboard"; 
    }

    // 2. Afficher le formulaire de saisie de note
    @GetMapping("/add-grade")
    public String showAddGradeForm(Model model) {
        // On envoie un objet Grade vide au formulaire
        Grade grade = new Grade();
        grade.setStudent(new User()); // Initialise l'objet étudiant interne
        
        // On récupère uniquement les utilisateurs qui ont le rôle ROLE_STUDENT
        List<User> students = userRepository.findAll(); // Tu pourras filtrer par rôle plus tard
        
        model.addAttribute("grade", grade);
        model.addAttribute("students", students);
       return "add-grade"; // ✅
    }

    // 3. Enregistrer la note dans la base de données
    @PostMapping("/save-grade")
    public String saveGrade(@ModelAttribute("grade") Grade grade, Principal principal) {
        // On récupère le prof connecté pour l'associer à la note
        User teacher = userRepository.findByEmail(principal.getName());
        grade.setTeacher(teacher);
        
        // On récupère l'élève complet depuis l'ID sélectionné dans le formulaire
        User student = userRepository.findById(grade.getStudent().getId()).get();
        grade.setStudent(student);

        // Sauvegarde dans MySQL
        gradeRepository.save(grade);
        
        return "redirect:/teacher/dashboard?successGrade";
    }
}