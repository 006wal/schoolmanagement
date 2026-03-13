/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SchoolmanagementSystem.SchoolmanagementSystem.repository;


import SchoolmanagementSystem.SchoolmanagementSystem.model.Grade;
import SchoolmanagementSystem.SchoolmanagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    // Trouver toutes les notes d'un étudiant spécifique
    List<Grade> findByStudent(User student);

    // Trouver toutes les notes données par un professeur spécifique
    List<Grade> findByTeacher(User teacher);

    // Trouver les notes par matière (utile pour les statistiques)
    List<Grade> findBySubject(String subject);
    
    // Trouver les notes d'un étudiant pour une matière précise
    List<Grade> findByStudentIdAndSubject(Long studentId, String subject);
}