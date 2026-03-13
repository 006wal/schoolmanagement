/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SchoolmanagementSystem.SchoolmanagementSystem.model;



import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double score; // La valeur de la note (ex: 18.5)

    @Column(nullable = false)
    private String subject; // La matière (Maths, Physique, etc.)

    private String comments; // Remarques du prof (ex: "Très bon travail")

    private LocalDateTime dateAdded; // Date de saisie de la note

    // Relation avec l'élève (qui est un User avec ROLE_STUDENT)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // Relation avec le prof (qui est un User avec ROLE_TEACHER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    // --- CONSTRUCTEURS ---
    public Grade() {
        this.dateAdded = LocalDateTime.now(); // Définit la date actuelle par défaut
    }

    public Grade(Double score, String subject, User student, User teacher) {
        this.score = score;
        this.subject = subject;
        this.student = student;
        this.teacher = teacher;
        this.dateAdded = LocalDateTime.now();
    }

    // --- GETTERS ET SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public LocalDateTime getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDateTime dateAdded) { this.dateAdded = dateAdded; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public User getTeacher() { return teacher; }
    public void setTeacher(User teacher) { this.teacher = teacher; }
}