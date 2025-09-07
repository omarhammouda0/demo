package domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.Objects;



@Entity
@Table (name = "appointment" )
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)

public class Appointment {


    public enum Status {BOOKED, CANCELLED, COMPLETED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;



    @ManyToOne ( fetch = FetchType.EAGER , optional = false )
    @JoinColumn (name = "doctor_id" ,  nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler" ,  "appointments"})
    private Doctor doctor;


    @ManyToOne ( fetch = FetchType.EAGER ,  optional = false )
    @JoinColumn(name = "patient_id" , nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler" ,  "appointments"})

    // omat tries to push




    private Patient patient;

    @Column (name = "start_time" , nullable = false)
    private LocalDateTime startTime;

    @Column (name = "end_time" ,  nullable = false )
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false)
    private Status status = Status.BOOKED; // default

    @Column (name = "note"  , length = 255)
    private String note;

    @CreatedDate
    @Column (name = "created_at"  , updatable = false )
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column (name = "updated_at" )
    private LocalDateTime updatedAt;

    public Appointment(Doctor doctor , Patient patient , LocalDateTime startTime ,
                       LocalDateTime endTime , Status status , String note) {

        this.doctor = doctor;
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.note = note;
    }

    public Appointment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


    public Status getStatus() { return status; }


    public void setStatus(Status status) { this.status = status; }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Appointment that)) return false;
        return Objects.equals ( getId ( ) , that.getId ( ) );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode ( getId ( ) );
    }
}
