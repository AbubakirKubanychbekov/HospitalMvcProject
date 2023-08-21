package boss.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "doctors")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private String position;
    private String email;

    @ManyToMany
    private List<Department>departments;

    @ToString.Exclude
    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Appointment>appointments;

}
