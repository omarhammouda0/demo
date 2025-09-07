package DTO;

import domain.Appointment;

import java.time.LocalDateTime;

public record AppointmentDTO(

        Long Id ,
        Long doctor_id,
        String doctor_name ,
        Long patient_id,
        String patient_name ,
        LocalDateTime startTime ,
        LocalDateTime endTime ,
        Appointment.Status status ,
        String note
) {
}
