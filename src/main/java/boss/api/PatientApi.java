package boss.api;

import boss.entities.Patient;
import boss.enums.Gender;
import boss.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.Name;
import java.util.List;

@Controller
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientApi {

    private final PatientService patientService;

    @GetMapping
    String findAll(Model model,
                   @RequestParam(name = "firstName",required = false)String firstName,
                   @RequestParam(name = "lastName",required = false)String lastName,
                   @RequestParam(name = "phoneNumber",required = false)String phoneNumber,
                   @RequestParam(name = "gender",required = false)Gender gender,
                   @RequestParam(name = "email",required = false)String email){
        model.addAttribute("firstName",firstName);
        model.addAttribute("lastName",lastName);
        model.addAttribute("phoneNumber",phoneNumber);
        model.addAttribute("gender",gender);
        model.addAttribute("email",email);
       List<Patient> patients= patientService.findAll(firstName,lastName,phoneNumber,gender,email);
        model.addAttribute("patients",patients);
        return "patients/findAll";
    }

    @GetMapping("/{hospitalId}")
    String findAllPatientsByHospitalId(Model model,@PathVariable Long hospitalId){
        model.addAttribute("hospitalId",hospitalId);
        model.addAttribute("patients",patientService.getPatientsByHospitalId(hospitalId));
        return "patients/findPatientsByHospital";
    }


    @GetMapping("/create/{hospitalId}")
    String createPatientByHospitalId(@PathVariable Long hospitalId,Model model){
        model.addAttribute("hospitalId",hospitalId);
        model.addAttribute("newPatient",new Patient());
        return "patients/savePatientByHospital";
    }

    @PostMapping("/save/{hospitalId}")
    String savePatientByHospitalId(@PathVariable Long hospitalId,
                                   @ModelAttribute Patient patient){
        patientService.save(hospitalId,patient);
        return "redirect:/patients/"+hospitalId;
    }
}
