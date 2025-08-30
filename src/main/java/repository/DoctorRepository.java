package repository;

import domain.Appointment;
import domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    Optional<Doctor> findById(Long id);

    Optional<Doctor> findBySpecialty(String name);

    List<Doctor> findDoctorBySpecialty(String specialty);

    Optional<Doctor> findDoctorByName(String name);

    List<Doctor> streamDoctorsById(Long id);


    void deleteDoctorById(Long id);

    boolean existsByNameIgnoreCaseAndSpecialtyIgnoreCase(String name , String specialty);


    boolean existsByNameIgnoreCaseAndSpecialtyIgnoreCaseAndIdNot(String name , String specialty , Long id);

    boolean existsDoctorById(Long id);


}
