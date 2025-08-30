package Mapper;
import DTO.AppointmentDTO;
import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import org.springframework.stereotype.Component;
import repository.DoctorRepository;
import repository.PatientRepository;
import java.util.Optional;


@Component

public class AppointmentMapper {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentMapper(DoctorRepository doctorRepository , PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;

        this.patientRepository = patientRepository;
    }

    public  AppointmentDTO toAppointmentDTO(Appointment appointment) {

        return new AppointmentDTO (

                appointment.getDoctor ().getId (),
                appointment.getDoctor ().getName (),
                appointment.getPatient ().getId (),
                appointment.getPatient ().getFullName (),
                appointment.getStartTime (),
                appointment.getEndTime (),
                appointment.getStatus (),
                appointment.getNote ()) ;

    }


    public  Appointment toAppointment(AppointmentDTO appointmentDTO) {

        Long doc_id = appointmentDTO.doctor_id ();
        Long pat_id = appointmentDTO.patient_id ();

        Appointment appointment = new Appointment ();


        Optional <Doctor> doc = doctorRepository.findById ( doc_id );
        Optional<Patient> pat = patientRepository.findById ( pat_id );

        if (doc.isEmpty () || pat.isEmpty ()) {
            throw new RuntimeException ("Doctor or Patient not found");
        }

        doc.ifPresent ( appointment::setDoctor );
        pat.ifPresent ( appointment::setPatient );
        appointment.setDoctor ( doc.get () );
        appointment.setPatient ( pat.get () );
        appointment.setStartTime ( appointmentDTO.startTime () );
        appointment.setEndTime ( appointmentDTO.endTime () );
        appointment.setNote ( appointmentDTO.note ( ) );

        return appointment;



    }
}
