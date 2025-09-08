package controller;

import domain.dto.doctorDto;
import mapper.doctorMapper;
import domain.model.doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.doctorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/doctors")

public class doctorController {

    private final doctorService doctorService;
    private final doctorMapper doctorMapper;

    @Autowired
    public doctorController(doctorService doctorService , doctorMapper doctorMapper) {
        this.doctorService = doctorService;
        this.doctorMapper = doctorMapper;
    }



    @GetMapping
    public List<doctorDto> getAllDoctors() {
        return doctorService.getAllDoctors ()
                .stream ()
                .map ( doctorMapper::toDoctorDTO )
                .collect ( Collectors.toList () );
    }

    @GetMapping("/{id}")
    public doctorDto getDoctorById(@PathVariable Long id) {

        doctor doctor = doctorService.getDoctorById (id);
        return doctorMapper.toDoctorDTO ( doctor );
    }

    @PostMapping
    public doctorDto createDoctor(@RequestBody doctorDto doctor) {

        doctor toSave = doctorMapper.toDoctor ( doctor );
        doctor saved = doctorService.createDoctor ( toSave );
        return  doctorMapper.toDoctorDTO ( saved );

    }

    @PutMapping ("/{id}")
    public doctorDto updateDoctor(@PathVariable Long id , @RequestBody doctorDto doctor) {

        doctor toSave = doctorMapper.toDoctor ( doctor );
        doctor saved = doctorService.updateDoctor ( id ,toSave );
        return  doctorMapper.toDoctorDTO ( saved );

    }

    @DeleteMapping ("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
       doctorService.deleteDoctorById ( id );
    }

}
