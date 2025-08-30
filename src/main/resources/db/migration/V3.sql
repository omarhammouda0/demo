-- Insert working hours for doctors

drop TABLE working_hours;

CREATE TABLE working_hours (
                               doctor_id INTEGER NOT NULL,
                               start_time TIME NOT NULL,
                               end_time TIME NOT NULL,
                               day_of_week INTEGER ,

                               CONSTRAINT fk_doctor
                                   FOREIGN KEY (doctor_id)
                                       REFERENCES doctor(id),

                               CHECK ( day_of_week between 0 and 7 ) ,
                               CHECK (end_time > start_time),
                               UNIQUE (doctor_id, day_of_week)
);


INSERT INTO working_hours (doctor_id, day_of_week, start_time, end_time)
VALUES (2, 1, '09:00:00',
        '17:00:00'), -- Doctor 1 on Monday
       (2, 2, '09:00:00',
        '17:00:00'), -- Doctor 1 on Tuesday
       (2, 3, '08:00:00',
        '16:00:00'), -- Doctor 2 on Wednesday
       (2, 4, '08:00:00',
        '16:00:00'), -- Doctor 2 on Wednesday
       (2, 5, '10:00:00', '14:00:00'); -- Doctor 2 on Friday