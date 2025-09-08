package domain.dto;

import domain.model.appointment;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record appointmentDto(


        Long Id ,
        Long doctor_id,
        String doctor_name ,
        Long patient_id,
        String patient_name ,
        LocalDateTime startTime ,
        LocalDateTime endTime ,
        appointment.Status status ,
        String note
) {
}
