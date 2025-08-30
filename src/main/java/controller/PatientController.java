package controller;
import DTO.PatientDTO;
import Mapper.PatientMapper;
import domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.PatientService;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;


    @Autowired
    public PatientController(PatientService patientService , PatientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @GetMapping
    public List<PatientDTO> getPatients() {
        return patientService.getAllPatients().stream ()
                .map ( patientMapper::toPatientDTO )
                .collect ( Collectors.toList () );
    }

    @GetMapping( "/{id}")
    public PatientDTO getPatientById(@PathVariable Long id) {
        return patientMapper.toPatientDTO ( patientService.getPatientById (id) );
    }

    @PostMapping
    public PatientDTO createPatient(@RequestBody PatientDTO patientDTO) {
        Patient toSave = patientMapper.toPatient(patientDTO);
        Patient saved  = patientService.createPatient(toSave);
        return patientMapper.toPatientDTO(saved);
    }

    @PutMapping("/{id}")
    public PatientDTO updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        Patient patient = patientMapper.toPatient (patientDTO);
        Patient updated = patientService.updatePatient(id, patient);
        return patientMapper.toPatientDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

}