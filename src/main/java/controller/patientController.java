package controller;
import domain.dto.patientDto;
import mapper.patientMapper;
import domain.model.patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.patientService;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/patients")
public class patientController {

    private final patientService patientService;
    private final patientMapper patientMapper;


    @Autowired
    public patientController(patientService patientService , patientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @GetMapping
    public List<patientDto> getPatients() {
        return patientService.getAllPatients().stream ()
                .map ( patientMapper::toPatientDTO )
                .collect ( Collectors.toList () );
    }

    @GetMapping( "/{id}")
    public patientDto getPatientById(@PathVariable Long id) {
        return patientMapper.toPatientDTO ( patientService.getPatientById (id) );
    }

    @PostMapping
    public patientDto createPatient(@RequestBody patientDto patientDTO) {
        patient toSave = patientMapper.toPatient(patientDTO);
        patient saved  = patientService.createPatient(toSave);
        return patientMapper.toPatientDTO(saved);
    }

    @PutMapping("/{id}")
    public patientDto updatePatient(@PathVariable Long id, @RequestBody patientDto patientDTO) {
        patient patient = patientMapper.toPatient (patientDTO);
        patient updated = patientService.updatePatient(id, patient);
        return patientMapper.toPatientDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

}