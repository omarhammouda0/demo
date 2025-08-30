package Mapper;
import DTO.AppointmentDTO;
import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import org.springframework.stereotype.Component;



@Component

public class AppointmentMapper {




    public AppointmentMapper() {

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


        Appointment appointment = new Appointment ();

        Doctor doctor = new Doctor();
        doctor.setId ( appointmentDTO.doctor_id () );
        appointment.setDoctor ( doctor );

        Patient patient = new Patient();
        patient.setId ( appointmentDTO.patient_id () );
        appointment.setPatient ( patient );

        appointment.setStartTime ( appointmentDTO.startTime () );
        appointment.setEndTime ( appointmentDTO.endTime () );
        appointment.setNote ( appointmentDTO.note ( ) );

        return appointment;



    }
}
