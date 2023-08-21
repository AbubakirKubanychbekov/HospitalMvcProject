package boss.service;

import boss.entities.Hospital;
import boss.entities.Patient;
import boss.enums.Gender;
import boss.repositories.HospitalRepo;
import boss.repositories.PatientRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepo patientRepo;
    private final HospitalRepo hospitalRepo;


    public PatientService(PatientRepo patientRepo, HospitalRepo hospitalRepo) {
        this.patientRepo = patientRepo;
        this.hospitalRepo = hospitalRepo;
    }

    public List<Patient> findAll(String firstName, String lastName, String phoneNumber, Gender gender, String email) {
        return patientRepo.findAll(firstName,lastName,phoneNumber,gender,email);
    }



    public List<Patient> getPatientsByHospitalId(Long hospitalId) {
        return patientRepo.getPatientsByHospitalId(hospitalId);
    }

    public void save(Long hospitalId, Patient patient) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElse(null);
        patient.setHospital(hospital);
        patientRepo.save(patient);

        if (hospital.getPatients() !=null){
            hospital.getPatients().add(patient);
        }else {
            hospital.setPatients(new ArrayList<>(List.of(patient)));
        }
        hospitalRepo.merge(hospital);

    }

    public Patient findById(Long patientId){
        return patientRepo.findById(patientId).orElseThrow(()-> new RuntimeException("Patient with id: "+patientId+"not found..."));
    }
}
