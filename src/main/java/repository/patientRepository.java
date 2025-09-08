package repository;

import domain.model.patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface patientRepository extends JpaRepository<patient, Long> {

    Optional<patient> findPatientByEmail(String email);

    Optional<patient> findPatientByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    boolean existsByEmailAndIdNot(String email , Long id);

    boolean existsPatientById(Long id);
}
