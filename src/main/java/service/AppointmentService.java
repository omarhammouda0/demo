package service;

import domain.Appointment;
import domain.Doctor;
import org.springframework.stereotype.Service;
import repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import repository.AppointmentRepository;
import repository.PatientRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service

public class AppointmentService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(DoctorRepository doctorRepository , AppointmentRepository appointmentRepository , PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    private void validateAppointmentFields(Appointment appointment) {

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

    private void validateAppointmentTimes (Appointment appointment) {

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


    private void CreateConflictCheck (Appointment appointment) {

        if (appointmentRepository.existsByDoctorIdAndStartTimeLessThanAndEndTimeGreaterThan (
                appointment.getDoctor ().getId (), appointment.getEndTime () , appointment.getStartTime () )) {

            throw new IllegalArgumentException ("Doctor already has an appointment during this time." +
                    " Please choose different times or a different doctor" );
        }

    }


    private void UpdateConflictCheck(Long Id , Appointment appointment) {
        if (appointmentRepository.existsByDoctorIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot (
                appointment.getDoctor ( ).getId ( ) , appointment.getEndTime ( ) ,
                appointment.getStartTime ( ) , Id )) {

            throw new IllegalArgumentException("Another appointment already exists for this doctor" +
                    " during the requested time" );
        }
    }

    // Read

    @Transactional(readOnly = true)
    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Appointment getAppointmentById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException ( id + " is not a valid input" );
        }

        return appointmentRepository.findAppointmentById ( id ).orElseThrow
                ( () -> new EntityNotFoundException ( "This appointment is not found" ) );
    }

    @Transactional(readOnly = true)
    public List <Appointment> getDoctorSchedule (Long doctorId , LocalDate day ) {

        if (doctorId == null || doctorId <= 0 || day == null) {
            throw new IllegalArgumentException (  " Invalid input" );
        }

        Doctor existed = doctorRepository.findById (  doctorId ).orElseThrow (
                ()-> new EntityNotFoundException ( "This doctor is not found" )
        );


        return appointmentRepository.findDoctorSchedule ( doctorId , day);
    }


    // Create

    @Transactional
    public Appointment createAppointment(Appointment appointment) {

        if (appointment == null) {
            throw new IllegalArgumentException (  " appointment cannot be empty" );
        }

        validateAppointmentFields ( appointment );
        validateAppointmentTimes ( appointment );
        CreateConflictCheck (  appointment );

        return appointmentRepository.save ( appointment );
    }

    // Update

    @Transactional
    public Appointment updateAppointment(Long Id , Appointment appointment) {

        if (appointment == null || Id == null || Id <= 0) {
            throw new IllegalArgumentException (  " Invalid input" );
        }

        Appointment existed = appointmentRepository.findAppointmentById ( Id )
                .orElseThrow (()-> new EntityNotFoundException ( "This appointment is not found" ) );


        validateAppointmentFields ( appointment );
        validateAppointmentTimes ( appointment );
        UpdateConflictCheck ( Id , appointment );

        existed.setDoctor ( appointment.getDoctor ( ) );
        existed.setPatient ( appointment.getPatient () );
        existed.setStartTime ( appointment.getStartTime () );
        existed.setEndTime ( appointment.getEndTime () );
        existed.setNote(appointment.getNote() == null ? null : appointment.getNote().trim());
        existed.setStatus (   appointment.getStatus () );


        return appointmentRepository.save ( existed );

    }

    // Delete

    @Transactional
    public void deleteAppointmentById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException (  " Invalid input" );
        }

        Appointment existed = appointmentRepository.findAppointmentById ( id ).orElseThrow
                (  () -> new EntityNotFoundException ( "This appointment is not found" ) );

         appointmentRepository.delete ( existed );

    }



}
