package domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table (name = "doctor" )
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)

public class doctor implements java.io.Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank(message = "Name is Required")
    @Column (name = "name" , length = 100, nullable = false )
    private String name;


    @NotBlank(message = "Specialty is Required")
    @Column (name = "specialty" ,  length = 100, nullable = false )
    private String specialty;

    @CreatedDate
    @Column (name = "created_at"  , updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    @Column (name = "updated_at" )
    private LocalDateTime updated_at;


    public doctor(String name , String specialty ) {
        this.name = name;
        this.specialty = specialty;

    }

    public doctor() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }



    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof doctor doctor)) return false;
        return Objects.equals ( getId ( ) , doctor.getId ( ) );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode ( getId ( ) );
    }
}
