package boss.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "departments")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    private String image;

    @ToString.Exclude
    @ManyToMany(mappedBy = "departments",cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    },fetch = FetchType.EAGER)
    private List<Doctor> doctors;

    @ManyToOne(fetch = FetchType.EAGER)
    private Hospital hospital;

}
