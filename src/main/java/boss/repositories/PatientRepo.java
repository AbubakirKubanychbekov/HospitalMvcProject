package boss.repositories;

import boss.entities.Patient;
import boss.enums.Gender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PatientRepo {
    private final EntityManager entityManager;

    public PatientRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Patient> findAll(String firstName, String lastName, String phoneNumber, Gender gender, String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Patient> criteriaQuery = criteriaBuilder.createQuery(Patient.class);
        Root<Patient> root = criteriaQuery.from(Patient.class);
        List<Predicate> patients = new ArrayList<>();
        if (firstName != null) {
            patients.add(criteriaBuilder.equal(root.get("firstName"), firstName));
        }
        if (lastName != null) {
            patients.add(criteriaBuilder.equal(root.get("lastName"), lastName));
        }
        if (phoneNumber != null) {
            patients.add(criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber));
        }
        if (gender != null) {
            patients.add(criteriaBuilder.equal(root.get("gender"), gender));
        }
        if (email != null) {
            patients.add(criteriaBuilder.equal(root.get("email"), email));
        }

        criteriaQuery.where(patients.toArray(new Predicate[0]));
        TypedQuery<Patient> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }


    public List<Patient> getPatientsByHospitalId(Long hospitalId) {
        return entityManager.createQuery("select p from Patient p where p.hospital.id=: hospitalId", Patient.class)
                .setParameter("hospitalId",hospitalId)
                .getResultList();
    }

    public Optional<Patient> findById(Long patientId) {
        return Optional.ofNullable(
                entityManager.createQuery("select p from Patient p where p.id= :patientId", Patient.class)
                        .setParameter("patientId",patientId)
                        .getSingleResult()
        );
    }

    public void save(Patient patient) {
        entityManager.persist(patient);
    }
}
