package controller;

import domain.dto.appointmentDto;
import domain.model.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.appointmentService;
import mapper.appointmentMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/appointments")

public class appointmentController {

   private final appointmentService appointmentService;
   private final appointmentMapper appointmentMapper;

   @Autowired
    public appointmentController(appointmentService appointmentService , appointmentMapper appointmentMapper) {
       this.appointmentService = appointmentService;
       this.appointmentMapper = appointmentMapper;
   }


    @GetMapping

    public List<appointmentDto> getAppointments() {
        return appointmentService.getAllAppointments ()
                .stream ()
                .map ( appointmentMapper::toAppointmentDTO )
                .collect ( Collectors.toList () );
    }

    @GetMapping("/{id}")
    public appointmentDto getAppointmentById (@PathVariable Long id ) {
       return appointmentMapper.toAppointmentDTO ( appointmentService.getAppointmentById ( id ) ) ;
    }


    @PostMapping
    public appointmentDto createAppointment(@RequestBody appointmentDto appointmentDTO) {

        appointment toSave = appointmentMapper.toAppointment (appointmentDTO);
        appointment saved  = appointmentService.createAppointment (toSave);
        return appointmentMapper.toAppointmentDTO (saved);


    }

    @PutMapping("/{id}")
    public appointmentDto updateAppointment(@PathVariable Long id , @RequestBody appointmentDto appointmentDTO) {

        appointment toSave = appointmentMapper.toAppointment (appointmentDTO);
        appointment saved  = appointmentService.updateAppointment (id ,toSave);
        return appointmentMapper.toAppointmentDTO (saved);

    }

    @DeleteMapping ("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
       appointmentService.deleteAppointmentById(id);
    }

}
