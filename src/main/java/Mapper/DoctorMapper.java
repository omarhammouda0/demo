package Mapper;

import DTO.DoctorDTO;
import domain.Doctor;
import org.springframework.stereotype.Component;

@Component

public class DoctorMapper {

    public DoctorDTO toDoctorDTO(Doctor doctor) {

        if (doctor == null) {
            throw new IllegalArgumentException ( "doctor can not be null" );
        }

        if (doctor.getId () == null) {
            throw new IllegalArgumentException ( "doctor id can not be null" );
        }

        return new DoctorDTO (
                doctor.getName ( ) ,
                doctor.getSpecialty ( ) );
    }


    public Doctor toDoctor(DoctorDTO doctorDTO) {

        if (doctorDTO == null) {
            throw new IllegalArgumentException ( "Doctor  can not be null" );
        }


        Doctor doctor = new Doctor ( );

        doctor.setName ( doctorDTO.name ( ) );
        doctor.setSpecialty ( doctorDTO.specialty ( ) );

        return doctor;

    }
}


