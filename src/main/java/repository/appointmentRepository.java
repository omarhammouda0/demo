package repository;

import domain.model.appointment;
import domain.model.doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository

public interface appointmentRepository extends JpaRepository<appointment, Long> {

    Optional<appointment> findAppointmentById(Long id);

    List<appointment> findAppointmentByDoctor(doctor doctor);

    List<appointment> findAppointmentByStatus(appointment.Status status);

    List<appointment> findByDoctorAndStartTimeBetween(doctor doctor , LocalDateTime start , LocalDateTime end);

    boolean existsByDoctorIdAndStartTimeLessThanAndEndTimeGreaterThan(Long id , LocalDateTime start , LocalDateTime end);

    boolean existsByDoctorIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(Long doctor_id , LocalDateTime startTime , LocalDateTime endTime , Long id);


    List<appointment> findByDoctor_Id(Long doctorId);


    @Query(value = """
            SELECT * FROM appointments a
            WHERE a.doctor_id = :doctorId
              AND DATE(a.start_time) = :day
            ORDER BY a.start_time
            """, nativeQuery = true)
    List<appointment> findDoctorSchedule(@Param("doctorId") Long doctorId , @Param("day") LocalDate day);

}

