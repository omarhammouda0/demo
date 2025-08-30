package repository;

import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findAppointmentById(Long id);

    List<Appointment> findAppointmentByDoctor(Doctor doctor);

    List<Appointment> findAppointmentByStatus(Appointment.Status status);

    List<Appointment> findByDoctorAndStartTimeBetween(Doctor doctor , LocalDateTime start , LocalDateTime end);

    boolean existsByDoctorIdAndStartTimeLessThanAndEndTimeGreaterThan(Long id , LocalDateTime start , LocalDateTime end);

    boolean existsByDoctorIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(Long doctor_id , LocalDateTime startTime , LocalDateTime endTime , Long id);


    List<Appointment> findByDoctor_Id(Long doctorId);


    @Query(value = """
            SELECT * FROM appointments a
            WHERE a.doctor_id = :doctorId
              AND DATE(a.start_time) = :day
            ORDER BY a.start_time
            """, nativeQuery = true)
    List<Appointment> findDoctorSchedule(@Param("doctorId") Long doctorId , @Param("day") LocalDate day);

}

