package domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table (name = "patient" )
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)

public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "full_name" ,  nullable = false , length = 150)
    private String fullName;

    @Column (name = "email" ,  nullable = false , length = 150)
    private String email;

    @Column(name = "phone" ,  length = 50 )
    private String phone;

    @CreatedDate
    @Column (name = "created_at"  , updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    @Column (name = "updated_at"  )
    private LocalDateTime updated_at;


    public Patient(String fullName , String email , String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public Patient() {}



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Patient patient)) return false;
        return Objects.equals ( getId ( ) , patient.getId ( ) );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode ( getId ( ) );
    }
}
