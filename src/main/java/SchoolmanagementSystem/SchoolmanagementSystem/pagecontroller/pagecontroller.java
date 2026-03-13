
package SchoolmanagementSystem.SchoolmanagementSystem.pagecontroller;

import SchoolmanagementSystem.SchoolmanagementSystem.model.User;
import SchoolmanagementSystem.SchoolmanagementSystem.model.Role;
import SchoolmanagementSystem.SchoolmanagementSystem.model.Subject;
import SchoolmanagementSystem.SchoolmanagementSystem.repository.UserRepository;
import SchoolmanagementSystem.SchoolmanagementSystem.repository.RoleRepository;
import SchoolmanagementSystem.SchoolmanagementSystem.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

@Controller
public class pagecontroller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    // --- AUTHENTIFICATION ---

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // --- DASHBOARDS ---

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        long totalStudents = userRepository.countByRoleName("ROLE_STUDENT");
        long totalTeachers = userRepository.countByRoleName("ROLE_TEACHER");
        long totalSubjects = subjectRepository.count();
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalTeachers", totalTeachers);
        model.addAttribute("totalSubjects", totalSubjects);
        return "admin-dashboard";
    }

    // --- GESTION DES ÉTUDIANTS ---

    @GetMapping("/admin/students")
    public String listStudents(Model model) {
        model.addAttribute("students", userRepository.findByRoleName("ROLE_STUDENT"));
        return "students-list";
    }

    @GetMapping("/admin/add-student")
    public String showAddStudentForm(Model model) {
        model.addAttribute("user", new User());
        return "add-student";
    }

    @GetMapping("/admin/students/edit/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id invalide:" + id));
        model.addAttribute("user", user);
        return "edit-student";
    }

    @PostMapping("/admin/save-student")
    public String saveStudent(@ModelAttribute("user") User user) {
        if (user.getId() != null) {
            User existingUser = userRepository.findById(user.getId()).get();
            user.setPassword(existingUser.getPassword());
            user.setRoles(existingUser.getRoles());
            user.setEnabled(existingUser.isEnabled());
        } else {
            user.setEnabled(true);
            Role studentRole = roleRepository.findByName("ROLE_STUDENT");
            if (studentRole != null) {
                user.setRoles(Collections.singleton(studentRole));
            }
        }
        userRepository.save(user);
        return "redirect:/admin/students?success";
    }

    @GetMapping("/admin/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/students?deletedSuccess";
    }

    // --- GESTION DES PROFESSEURS ---

    @GetMapping("/admin/teachers")
    public String listTeachers(Model model) {
        model.addAttribute("teachers", userRepository.findByRoleName("ROLE_TEACHER"));
        return "teachers-list";
    }

    @GetMapping("/admin/add-teacher")
    public String showAddTeacherForm(Model model) {
        model.addAttribute("user", new User());
        return "add-teacher";
    }

    @GetMapping("/admin/teachers/edit/{id}")
    public String showEditTeacherForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id invalide:" + id));
        model.addAttribute("user", user);
        return "edit-teacher";
    }

    @PostMapping("/admin/save-teacher")
    public String saveTeacher(@ModelAttribute("user") User user) {
        if (user.getId() != null) {
            User existingUser = userRepository.findById(user.getId()).get();
            user.setPassword(existingUser.getPassword());
            user.setRoles(existingUser.getRoles());
            user.setEnabled(existingUser.isEnabled());
        } else {
            user.setEnabled(true);
            Role teacherRole = roleRepository.findByName("ROLE_TEACHER");
            if (teacherRole != null) {
                user.setRoles(Collections.singleton(teacherRole));
            }
        }
        userRepository.save(user);
        return "redirect:/admin/teachers?success";
    }

    @GetMapping("/admin/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/teachers?deletedSuccess";
    }

    // --- GESTION DES MATIÈRES ---

    @GetMapping("/admin/subjects")
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectRepository.findAll());
        return "subjects-list";
    }

    @GetMapping("/admin/add-subject")
    public String showAddSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "add-subject";
    }

    @PostMapping("/admin/save-subject")
    public String saveSubject(@ModelAttribute("subject") Subject subject) {
        subjectRepository.save(subject);
        return "redirect:/admin/subjects?success";
    }

    @GetMapping("/admin/subjects/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectRepository.deleteById(id);
        return "redirect:/admin/subjects?deletedSuccess";
    }
}