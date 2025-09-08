package repository;

import domain.model.doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface doctorRepository extends JpaRepository<doctor,Integer> {

    Optional<doctor> findById(Long id);

    Optional<doctor> findBySpecialty(String name);

    List<doctor> findDoctorBySpecialty(String specialty);

    Optional<doctor> findDoctorByName(String name);

    List<doctor> streamDoctorsById(Long id);


    void deleteDoctorById(Long id);

    boolean existsByNameIgnoreCaseAndSpecialtyIgnoreCase(String name , String specialty);


    boolean existsByNameIgnoreCaseAndSpecialtyIgnoreCaseAndIdNot(String name , String specialty , Long id);

    boolean existsDoctorById(Long id);


}
