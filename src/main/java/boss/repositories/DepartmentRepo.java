package boss.repositories;

import boss.entities.Department;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DepartmentRepo {

   private final EntityManager entityManager;

    public DepartmentRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Department> findAll(String name) {
        return entityManager.createQuery("select d from Department d where d.name= :name", Department.class)
                .getResultList();
    }

    public List<Department> getDepartmentsByHospitalId(Long hospitalId){
        return entityManager.createQuery("select d from Department d where d.hospital.id = :hospitalId",
                Department.class)
                .setParameter("hospitalId",hospitalId)
                .getResultList();
    }



    public void save(Department department) {
        entityManager.persist(department);
    }


    public Optional<Department> findById(Long departmentId) {
        return Optional.ofNullable(
                entityManager.createQuery("select d from Department d where d.id= :departmentId",
                        Department.class).setParameter("departmentId",departmentId)
                        .getSingleResult()
        );
    }

    public void deleteById(Long id){
        entityManager.createQuery("delete from Department d where d.id= :id")
                .setParameter("id",id)
                .executeUpdate();
    }
}
