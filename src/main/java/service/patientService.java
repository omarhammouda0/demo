package service;

import domain.model.patient;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.patientRepository;
import java.util.List;
import java.util.Locale;




@Service
public class patientService {



    private final patientRepository patientRepository;



    public patientService(patientRepository patientRepository ) {

        this.patientRepository = patientRepository;

    }

    public String StringNormalization (String string) {
        return
                string == null ? "" : string.trim ().toLowerCase( Locale.ROOT ).replaceAll("\\s+", " ");
    }

    // Read

    // 1

    @Transactional (readOnly = true)
    public List<patient> getAllPatients() {
        return patientRepository.findAll() ;
    }


    // 2

    @Transactional (readOnly = true)
    public patient getPatientById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException ( "Invalid id!" );
        }

        return patientRepository.findById ( id )

                .orElseThrow (  () -> new EntityNotFoundException ( "Patient not found!") );


    }

    // Create



    @Transactional
    public patient createPatient(patient patient) {

        if (patient == null) {
            throw new IllegalArgumentException ( "patient cannot be null !" );
        }

        String name = StringNormalization (patient.getFullName ()) ;
        String email = StringNormalization (patient.getEmail()) ;
        String phone = StringNormalization (patient.getPhone()) ;

        if (name.isEmpty () || email.isEmpty () || phone.isEmpty ()) {
        throw new IllegalArgumentException ( "Patient name and email and phone cannot be null !" );}


        if (name.length () > 150 ) {
            throw new IllegalArgumentException ( "Patient name cannot be longer than 150 characters !" );
        }

        if (email.length () > 150) {
            throw new IllegalArgumentException ( "Patient email cannot be longer than 150 characters !" );
        }

        if (phone.length () > 50) {
            throw new IllegalArgumentException ( "Patient phone cannot be longer than 50 characters !" );
        }

        if (patientRepository.existsByEmail ( email )) {
            throw new IllegalArgumentException (  "Patient with email already exists !" );
        }

        patient.setId ( null );
        patient.setFullName ( name );
        patient.setPhone (  phone );
        patient.setEmail ( email );

        return patientRepository.save( patient );

    }

    // Update

    @Transactional
    public patient updatePatient(Long id, patient patient) {
        if (id == null || id <= 0 ) {
            throw new IllegalArgumentException ( "Invalid id!" );
        }

        if (patient == null) {
            throw new IllegalArgumentException ( "Patient  cannot be null!" );
        }

        patient exisitedPatient = patientRepository.findById ( id ).orElseThrow ( ()
                -> new EntityNotFoundException ( "Patient not found!") );


        String name = StringNormalization (patient.getFullName ()) ;
        String email = StringNormalization (patient.getEmail()) ;
        String phone = StringNormalization (patient.getPhone()) ;

        if (name.isEmpty () || email.isEmpty () || phone.isEmpty ()) {
            throw new IllegalArgumentException ( "Patient name and email and phone cannot be null !" );
        }

        if (name.length () > 150 || email.length () > 150 || phone.length () > 50 ) {
            throw new IllegalArgumentException ( "Fields exceed the limit !" );
        }


        if (patientRepository.existsByEmailAndIdNot ( email , id )) {
            throw new IllegalArgumentException ( "Patient with email already exists !" );
        }

        exisitedPatient.setFullName ( name );
        exisitedPatient.setEmail ( email );
        exisitedPatient.setPhone (  phone );
        return patientRepository.save( exisitedPatient );

    }


    // Delete

    @Transactional
    public void deletePatient(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException ( "Invalid id!" );
        }

        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException ( "Patient with id " + id + " not found" );
        }

        patientRepository.deleteById ( id );

    }

}
