package controller;

import DTO.DoctorDTO;
import Mapper.DoctorMapper;
import domain.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.DoctorService;
import service.PatientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/doctors")

public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @Autowired
    public DoctorController(DoctorService doctorService , DoctorMapper doctorMapper) {
        this.doctorService = doctorService;
        this.doctorMapper = doctorMapper;
    }



    @GetMapping
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors ()
                .stream ()
                .map ( doctorMapper::toDoctorDTO )
                .collect ( Collectors.toList () );
    }

    @GetMapping("/{id}")
    public DoctorDTO getDoctorById(@PathVariable Long id) {

        Doctor doctor = doctorService.getDoctorById (id);
        return doctorMapper.toDoctorDTO ( doctor );
    }

    @PostMapping
    public DoctorDTO createDoctor(@RequestBody DoctorDTO doctor) {

        Doctor toSave = doctorMapper.toDoctor ( doctor );
        Doctor saved = doctorService.createDoctor ( toSave );
        return  doctorMapper.toDoctorDTO ( saved );

    }

    @PutMapping ("/{id}")
    public DoctorDTO updateDoctor(@PathVariable Long id , @RequestBody DoctorDTO doctor) {

        Doctor toSave = doctorMapper.toDoctor ( doctor );
        Doctor saved = doctorService.updateDoctor ( id ,toSave );
        return  doctorMapper.toDoctorDTO ( saved );

    }

    @DeleteMapping ("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
       doctorService.deleteDoctorById ( id );
    }

}
