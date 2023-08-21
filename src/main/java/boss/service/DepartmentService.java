package boss.service;

import boss.entities.Department;
import boss.entities.Hospital;
import boss.repositories.DepartmentRepo;
import boss.repositories.HospitalRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final HospitalRepo hospitalRepo;

    public DepartmentService(DepartmentRepo departmentRepo, HospitalRepo hospitalRepo) {
        this.departmentRepo = departmentRepo;
        this.hospitalRepo = hospitalRepo;
    }

    public List<Department>findAll(String name){
        return departmentRepo.findAll(name);
    }

    public List<Department> getDepartmentsByHospitalId(Long hospitalId) {
        return departmentRepo.getDepartmentsByHospitalId(hospitalId);
    }

    public void save(Long hospitalId, Department department) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElse(null);
        department.setHospital(hospital);
        departmentRepo.save(department);

        if (hospital.getDepartments() != null){
           hospital.getDepartments().add(department);
       }else {
            hospital.setDepartments(new ArrayList<>(List.of(department)));
        }
        hospitalRepo.merge(hospital);


    }

    public Department findById(Long departmentId){
      return departmentRepo.findById(departmentId).orElseThrow(()-> new RuntimeException("Department with id "+departmentId+"not found"));
    }


    @Transactional
    public void update(Long departmentId, Department newDepartment) {
        Department dep = findById(departmentId);
        dep.setName(newDepartment.getName());
        dep.setImage(newDepartment.getImage());
    }

    public void deleteById(Long departmentId) {
        departmentRepo.deleteById(departmentId);

    }
}
