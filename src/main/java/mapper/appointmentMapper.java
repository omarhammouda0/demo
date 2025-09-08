package mapper;
import domain.dto.appointmentDto;
import domain.model.appointment;
import domain.model.doctor;
import domain.model.patient;
import org.springframework.stereotype.Component;



@Component

public class appointmentMapper {




    public appointmentMapper() {

    }

    public appointmentDto toAppointmentDTO(appointment appointment) {

        return new appointmentDto (


                appointment.getId(),
                appointment.getDoctor ().getId (),
                appointment.getDoctor ().getName (),
                appointment.getPatient ().getId (),
                appointment.getPatient ().getFullName (),
                appointment.getStartTime (),
                appointment.getEndTime (),
                appointment.getStatus (),
                appointment.getNote ()) ;

    }


    public appointment toAppointment(appointmentDto appointmentDTO) {


        appointment appointment = new appointment ();

        doctor doctor = new doctor ();
        doctor.setId ( appointmentDTO.doctor_id () );
        appointment.setDoctor ( doctor );

        patient patient = new patient ();
        patient.setId ( appointmentDTO.patient_id () );
        appointment.setPatient ( patient );

        appointment.setStartTime ( appointmentDTO.startTime () );
        appointment.setEndTime ( appointmentDTO.endTime () );
        appointment.setNote ( appointmentDTO.note ( ) );

        return appointment;



    }
}
