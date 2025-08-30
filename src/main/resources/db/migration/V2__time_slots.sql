CREATE TABLE working_hours (
                               doctor_id INTEGER NOT NULL,
                               start_time TIMESTAMP NOT NULL,
                               end_time TIMESTAMP NOT NULL,
                               day_of_week INTEGER GENERATED ALWAYS AS (EXTRACT(DOW FROM start_time)) STORED,

                               CONSTRAINT fk_doctor
                                   FOREIGN KEY (doctor_id)
                                       REFERENCES doctor(id),

                               CHECK (end_time > start_time),
                               UNIQUE (doctor_id, day_of_week)
);

