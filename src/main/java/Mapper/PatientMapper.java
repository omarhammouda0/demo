package Mapper;

import DTO.PatientDTO;
import domain.Patient;
import org.springframework.stereotype.Component;

@Component



public class PatientMapper {

    public  Patient toPatient(PatientDTO patientDTO) {

        if(patientDTO == null)
            throw new IllegalArgumentException ("Patient DTO can not be null");

        Patient patient = new Patient();

        patient.setId ( patientDTO.id ( ) );
        patient.setFullName (  patientDTO.fullName ( ) );
        patient.setEmail (   patientDTO.email ( ) );
        patient.setPhone (  patientDTO.phone ( ) );


        return patient;
    }

    public  PatientDTO toPatientDTO(Patient patient) {

        if(patient == null)
            throw new IllegalArgumentException ("Patient can not be null");

        if(patient.getId () == null)
            throw new IllegalArgumentException ("Patient ID can not be null");

        return new PatientDTO ( patient.getId () ,
                patient.getFullName (),
                patient.getEmail (),
                patient.getPhone ());
    }

}




