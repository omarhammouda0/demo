package service;

import domain.model.appointment;
import domain.model.doctor;
import domain.model.patient;
import org.springframework.stereotype.Service;
import repository.doctorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import repository.appointmentRepository;
import repository.patientRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



@Service

public class appointmentService {

    private final doctorRepository doctorRepository;
    private final appointmentRepository appointmentRepository;
    private final patientRepository patientRepository;

    public appointmentService(doctorRepository doctorRepository , appointmentRepository appointmentRepository , patientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    private void validateAppointmentFields(appointment appointment) {

        if (appointment.getDoctor ( ) == null || appointment.getDoctor ( ).getId ( ) == null ||
                appointment.getDoctor ().getId () <= 0
        ) {
            throw new IllegalArgumentException ( "Invalid doctor id "  );
        }

        if   (appointment.getPatient ( ) == null || appointment.getPatient ( ).getId ( ) == null ||
                appointment.getPatient ( ).getId ( ) <= 0) {

            throw new IllegalArgumentException ( "Invalid patient id "  );
        }

        if      (!doctorRepository.existsDoctorById ( appointment.getDoctor ( ).getId ( ) ) ||
                !patientRepository.existsPatientById ( appointment.getPatient ( ).getId ( ) )) {
            throw new EntityNotFoundException ( "Patient or doctor can not be found" );
        }

        if (appointment.getStartTime () == null || appointment.getEndTime () == null ) {
            throw new IllegalArgumentException ( "Start and end times can not be empty" );
        }


    }

    public doctor checkAppointmentMapperDoctor (Long doc_id  ) {

        return doctorRepository.findById ( doc_id ).orElseThrow
                (() -> new EntityNotFoundException ( "This doctor can not be found" ));

    }


    public patient checkAppointmentMapperPatient (Long pat_id ) {

        return patientRepository.findById ( pat_id ).orElseThrow
                (() -> new EntityNotFoundException ( "This patient can not be found" ));

    }



    private void validateAppointmentTimes (appointment appointment) {

        LocalDateTime now = LocalDateTime.now ( );

        if (appointment.getStartTime ().isBefore (now) || appointment.getEndTime ().isBefore (now)) {
            throw new IllegalArgumentException ( " Start time and end time can not be in the past" );
        }

        if (!appointment.getEndTime () .isAfter (appointment.getStartTime ())) {
            throw new IllegalArgumentException ( " End time can not be before start time" );
        }

        Duration duration = Duration.between(appointment.getStartTime(), appointment.getEndTime());

        if  ( duration.toMinutes () < 15 || duration.toHours () > 4 ) {
            throw new IllegalArgumentException
                    ( " Appointment time can not be less than 15 minutes or more than 4 hours" );
        }
    }


    private void CreateConflictCheck (appointment appointment) {

        if (appointmentRepository.existsByDoctorIdAndStartTimeLessThanAndEndTimeGreaterThan (
                appointment.getDoctor ().getId (), appointment.getEndTime () , appointment.getStartTime () )) {

            throw new IllegalArgumentException ("Doctor already has an appointment during this time." +
                    " Please choose different times or a different doctor" );
        }

    }


    private void UpdateConflictCheck(Long Id , appointment appointment) {
        if (appointmentRepository.existsByDoctorIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot (
                appointment.getDoctor ( ).getId ( ) , appointment.getEndTime ( ) ,
                appointment.getStartTime ( ) , Id )) {

            throw new IllegalArgumentException("Another appointment already exists for this doctor" +
                    " during the requested time" );
        }
    }

    // Read

    @Transactional(readOnly = true)
    public List<appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public appointment getAppointmentById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException ( id + " is not a valid input" );
        }

        return appointmentRepository.findAppointmentById ( id ).orElseThrow
                ( () -> new EntityNotFoundException ( "This appointment is not found" ) );
    }

    @Transactional(readOnly = true)
    public List <appointment> getDoctorSchedule (Long doctorId , LocalDate day ) {

        if (doctorId == null || doctorId <= 0 || day == null) {
            throw new IllegalArgumentException (  " Invalid input" );
        }

        doctor existed = doctorRepository.findById (  doctorId ).orElseThrow (
                ()-> new EntityNotFoundException ( "This doctor is not found" )
        );


        return appointmentRepository.findDoctorSchedule ( doctorId , day);
    }


    // Create

    @Transactional
    public appointment createAppointment(appointment appointment) {

        if (appointment == null) {
            throw new IllegalArgumentException (  " appointment cannot be empty" );
        }

        if (appointment.getDoctor () == null || appointment.getPatient () == null) {
            throw new IllegalArgumentException (  " doctor and patient cannot be empty" );
        }

        if (appointment.getDoctor ().getId () == null || appointment.getDoctor ().getId () <= 0) {
            throw new IllegalArgumentException (  " Invalid doctor id" );
        }

        if (appointment.getPatient ().getId () == null || appointment.getPatient ().getId () <= 0) {
            throw new IllegalArgumentException (  " Invalid patient id" );
        }

        doctor doctor = doctorRepository.findById (appointment.getDoctor().getId())
                .orElseThrow ( ()-> new EntityNotFoundException ( "This doctor is not found" ) );

        patient patient = patientRepository.findById (appointment.getPatient().getId())
                .orElseThrow ( ()-> new EntityNotFoundException ( "This patient is not found" ) );

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        validateAppointmentFields ( appointment );
        validateAppointmentTimes ( appointment );
        CreateConflictCheck (  appointment );

        return appointmentRepository.save ( appointment );
    }

    // Update

    @Transactional
    public appointment updateAppointment(Long Id , appointment appointment) {

        if (appointment == null || Id == null || Id <= 0) {
            throw new IllegalArgumentException (  " Invalid input" );
        }

        appointment existed = appointmentRepository.findAppointmentById ( Id )
                .orElseThrow (()-> new EntityNotFoundException ( "This appointment is not found" ) );

        doctor doctor = doctorRepository.findById (appointment.getDoctor().getId())
                .orElseThrow ( ()-> new EntityNotFoundException ( "This doctor is not found" ) );

        patient patient = patientRepository.findById (appointment.getPatient().getId())
                .orElseThrow ( ()-> new EntityNotFoundException ( "This patient is not found" ) );

        existed.setDoctor ( doctor );
        existed.setPatient ( patient );
        existed.setStartTime ( appointment.getStartTime ( ) );
        existed.setEndTime ( appointment.getEndTime ( ) );
        existed.setNote ( appointment.getNote ( ) == null ? null : appointment.getNote ( ).trim ( ) );
        existed.setStatus ( appointment.getStatus ( )  );

        validateAppointmentFields ( existed );
        validateAppointmentTimes ( existed );
        UpdateConflictCheck ( Id , existed );


        return appointmentRepository.save ( existed );

    }

    // Delete

    @Transactional
    public void deleteAppointmentById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException (  " Invalid input" );
        }

        appointment existed = appointmentRepository.findAppointmentById ( id ).orElseThrow
                (  () -> new EntityNotFoundException ( "This appointment is not found" ) );

         appointmentRepository.delete ( existed );

    }



}
