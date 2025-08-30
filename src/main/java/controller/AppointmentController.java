package controller;

import DTO.AppointmentDTO;
import domain.Appointment;
import domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.AppointmentService;
import Mapper.AppointmentMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/appointments")

public class AppointmentController {

   private final AppointmentService appointmentService;
   private final AppointmentMapper appointmentMapper;

   @Autowired
    public AppointmentController(AppointmentService appointmentService , AppointmentMapper appointmentMapper) {
       this.appointmentService = appointmentService;
       this.appointmentMapper = appointmentMapper;
   }


    @GetMapping
    public List<AppointmentDTO> getAppointments() {
        return appointmentService.getAllAppointments ()
                .stream ()
                .map ( appointmentMapper::toAppointmentDTO )
                .collect ( Collectors.toList () );
    }

    @GetMapping("/{id}")
    public AppointmentDTO getAppointmentById ( @PathVariable Long id ) {
       return appointmentMapper.toAppointmentDTO ( appointmentService.getAppointmentById ( id ) ) ;
    }

    @PostMapping
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO appointmentDTO) {

        Appointment toSave = appointmentMapper.toAppointment (appointmentDTO);
        Appointment saved  = appointmentService.createAppointment (toSave);
        return appointmentMapper.toAppointmentDTO (saved);


    }

    @PutMapping("/{id}")
    public AppointmentDTO updateAppointment( @PathVariable Long id , @RequestBody AppointmentDTO appointmentDTO) {

        Appointment toSave = appointmentMapper.toAppointment (appointmentDTO);
        Appointment saved  = appointmentService.updateAppointment (id ,toSave);
        return appointmentMapper.toAppointmentDTO (saved);

    }

    @DeleteMapping ("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
       appointmentService.deleteAppointmentById(id);
    }

}
