package service;

import domain.model.doctor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.doctorRepository;
import repository.appointmentRepository;

import java.util.List;

@Service
public class doctorService {


    private final doctorRepository doctorRepository;
    private final appointmentRepository appointmentRepository;

    public doctorService(doctorRepository doctorRepository , appointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }


    // Read

    @Transactional(readOnly = true)
    public List<doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }


    @Transactional(readOnly = true)
    public doctor getDoctorById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("invalid doctor id");
        }

        return doctorRepository.findById(id).orElseThrow ( ()
                -> new EntityNotFoundException ( "Doctor with id " + id + " not found" ) );
    }



    // Read

    @Transactional
    public doctor createDoctor(doctor doctor) {

        if (doctor == null){
            throw new IllegalArgumentException("invalid input");
        }

        String name = doctor.getName() == null ? "" : doctor.getName().trim();
        String specialty = doctor.getSpecialty() == null ? "" : doctor.getSpecialty().trim();

        if ( name.isEmpty () || specialty.isEmpty ()) {
            throw new IllegalArgumentException("name and specialty are required");
        }

        if (name.length () > 100 || specialty.length () > 100  ) {
            throw new IllegalArgumentException( " fields can not be greater than 100 characters");
        }

        if  (doctorRepository.existsByNameIgnoreCaseAndSpecialtyIgnoreCase( name, specialty ))

        {
            throw new IllegalArgumentException ( "Doctor  " + name + " already exists" ); }

        doctor.setId(null);
        doctor.setName(name);
        doctor.setSpecialty(specialty);

        return doctorRepository.save(doctor);
    }



    // Update

    @Transactional
    public doctor updateDoctor(Long id , doctor doctor) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException ( "invalid doctor id" );
        }

        if (doctor == null){
            throw new IllegalArgumentException("invalid input");
        }

       doctor existingDoctor = doctorRepository.findById(id).orElseThrow
               (()-> new EntityNotFoundException ( "Doctor with id " + id + " not found"));

        String name = doctor.getName() == null ? "" : doctor.getName().trim().replaceAll("\\s+", " ");
        String specialty = doctor.getSpecialty() == null ? "" : doctor.getSpecialty().trim().replaceAll("\\s+", " ");

        if (name.isEmpty() || specialty.isEmpty()) {
            throw new IllegalArgumentException("name and specialty are required");
        }
        if (name.length() > 100 || specialty.length() > 100) {
            throw new IllegalArgumentException("fields cannot be greater than 100 characters");
        }


        if (doctorRepository.existsByNameIgnoreCaseAndSpecialtyIgnoreCaseAndIdNot(name, specialty, id)) {
            throw new IllegalArgumentException("Doctor " + name + " already exists");
        }


        existingDoctor.setName(name);
        existingDoctor.setSpecialty(specialty);


        return doctorRepository.save(existingDoctor);

    }

    // Delete

    @Transactional
    public void deleteDoctorById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("invalid doctor id" );
        }

        doctor existingDoctor = doctorRepository.findById(id).orElseThrow
                (()-> new EntityNotFoundException ( "Doctor with id " + id + " not found"));

        doctorRepository.deleteDoctorById(id);
    }

}
