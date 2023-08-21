package boss.entities;

import boss.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Pattern(regexp = "^(?!\\+996)\\d+$",message = "Phone Number must Not start with +996")
    @Size(max = 13,message = "Phone number most not 13 character")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Email
    @NotNull(message = "Email column is empty")
    @Size(max = 100,message = "Email column size few character")
    private String email;

    @ManyToOne
    private Hospital hospital;

    @ToString.Exclude
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Appointment> appointments;

}
