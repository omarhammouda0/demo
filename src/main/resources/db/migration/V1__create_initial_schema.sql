
CREATE TABLE doctor (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        specialty VARCHAR(100) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);


CREATE TABLE patient (
                         id BIGSERIAL PRIMARY KEY,
                         full_name VARCHAR(150) NOT NULL,
                         email VARCHAR(150) UNIQUE NOT NULL,
                         phone VARCHAR(50),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);


CREATE TABLE appointment (
                             id BIGSERIAL PRIMARY KEY,
                             doctor_id BIGINT NOT NULL REFERENCES doctor(id) ON DELETE CASCADE,
                             patient_id BIGINT NOT NULL REFERENCES patient(id) ON DELETE CASCADE,
                             start_time TIMESTAMP NOT NULL,
                             end_time TIMESTAMP NOT NULL,
                             status VARCHAR(20) NOT NULL CHECK (status IN ('BOOKED', 'CANCELLED', 'COMPLETED')),
                             note TEXT,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                             CONSTRAINT chk_time CHECK (end_time > start_time)
);


CREATE INDEX idx_appointment_doctor_start ON appointment (doctor_id, start_time);
CREATE INDEX idx_appointment_patient_start ON appointment (patient_id, start_time);
