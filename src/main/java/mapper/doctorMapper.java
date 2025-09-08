package mapper;

import domain.dto.doctorDto;
import domain.model.doctor;
import org.springframework.stereotype.Component;

@Component

public class doctorMapper {

    public doctorDto toDoctorDTO(doctor doctor) {

        if (doctor == null) {
            throw new IllegalArgumentException ( "doctor can not be null" );
        }

        if (doctor.getId () == null) {
            throw new IllegalArgumentException ( "doctor id can not be null" );
        }

        return new doctorDto (
                doctor.getName ( ) ,
                doctor.getSpecialty ( ) );
    }


    public doctor toDoctor(doctorDto doctorDTO) {

        if (doctorDTO == null) {
            throw new IllegalArgumentException ( "Doctor  can not be null" );
        }


        doctor doctor = new doctor ( );

        doctor.setName ( doctorDTO.name ( ) );
        doctor.setSpecialty ( doctorDTO.specialty ( ) );

        return doctor;

    }
}


