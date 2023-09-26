package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    // Metodo POST
    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrar(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
                                                            UriComponentsBuilder uriComponentsBuilder) {
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento(),
                        paciente.getDireccion().getUrbanizacion(),
                        paciente.getDireccion().getCodigoPostal(),
                        paciente.getDireccion().getProvincia()
                ));
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaPaciente);
    }

    // Metodo GET (listar paciente)
    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> listar(@PageableDefault( size = 5, sort = {"nombre"}) Pageable paginacion) {
        return ResponseEntity.ok(pacienteRepository.findAll(paginacion).map(DatosListadoPaciente::new));
        //return pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new);
    }

    // Metodo PUT (actualizar)
    @PutMapping
    @Transactional
    public ResponseEntity actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento(),
                        paciente.getDireccion().getUrbanizacion(),
                        paciente.getDireccion().getCodigoPostal(),
                        paciente.getDireccion().getProvincia()
                )));
    }

    // DELETE LOGICO

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    //Metodo GET para retornar un paciente especifico
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> retornarPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);

        var datosPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento(),
                        paciente.getDireccion().getUrbanizacion(),
                        paciente.getDireccion().getCodigoPostal(),
                        paciente.getDireccion().getProvincia()
                ));
        return ResponseEntity.ok(datosPaciente);
    }
}


