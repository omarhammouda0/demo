package repository;

import domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findPatientByEmail(String email);

    Optional<Patient> findPatientByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    boolean existsByEmailAndIdNot(String email , Long id);

    boolean existsPatientById(Long id);
}
