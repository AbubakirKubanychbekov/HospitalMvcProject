package boss.repositories;

import boss.entities.Hospital;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class HospitalRepo {
    @PersistenceContext
    private final EntityManager entityManager;

    public HospitalRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Hospital> findAll() {
        return entityManager.createQuery("select h from Hospital h", Hospital.class).getResultList();
    }

    public void save(Hospital hospital) {
        entityManager.persist(hospital);
    }

    public void  merge (Hospital hospital){
        entityManager.merge(hospital);
    }

    public Optional<Hospital> findById(Long hospitalId) {
        return Optional.ofNullable(
                entityManager.createQuery("select h from Hospital h where h.id=:hospitalID", Hospital.class)
                        .setParameter("hospitalID",hospitalId).getSingleResult()
        );
    }

    public void deleteById(Long id) {
        entityManager.createQuery("delete from Hospital h where h.id=:id")
                .setParameter("id",id)
                .executeUpdate();
    }
}
