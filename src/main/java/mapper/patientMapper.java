package mapper;

import domain.dto.patientDto;
import domain.model.patient;
import org.springframework.stereotype.Component;

@Component



public class patientMapper {

    public patient toPatient(patientDto patientDTO) {

        if(patientDTO == null)
            throw new IllegalArgumentException ("Patient DTO can not be null");

        patient patient = new patient ();

        patient.setId ( patientDTO.id ( ) );
        patient.setFullName (  patientDTO.fullName ( ) );
        patient.setEmail (   patientDTO.email ( ) );
        patient.setPhone (  patientDTO.phone ( ) );


        return patient;
    }

    public patientDto toPatientDTO(patient patient) {

        if(patient == null)
            throw new IllegalArgumentException ("Patient can not be null");

        if(patient.getId () == null)
            throw new IllegalArgumentException ("Patient ID can not be null");

        return new patientDto ( patient.getId () ,
                patient.getFullName (),
                patient.getEmail (),
                patient.getPhone ());
    }

}




