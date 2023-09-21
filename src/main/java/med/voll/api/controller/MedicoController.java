package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    //Metodo POST
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(),
                        medico.getDireccion().getUrbanizacion(),
                        medico.getDireccion().getCodigoPostal(),
                        medico.getDireccion().getProvincia()
                ));
        // Return 201 created
        // Return URL donde encontrar al medico
        // http://localhost.8080/medico/xx
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);


    }
/* Metodo original para devolver todos los datos de la BD en la tabla Medico
    @GetMapping
    public List<Medico> listar() {
        return repository.findAll();
    }
    */

    // Metodo GET
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 12) Pageable paginacion){ //Cambie 2 a 12 para que me muestre todos los medicos (sino me mostraba solo 2)
       // return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new); // Para que me traiga todos los medicos
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new)); // Para que me traiga solo los medicos activos
    }

    // Metodo PUT (actualizar)
    @PutMapping
    @Transactional //Cuando termine este metodo, la transaccion se va a a liberar
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString(),
        new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(),
                medico.getDireccion().getCiudad(),
                medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento(),
                medico.getDireccion().getUrbanizacion(),
                medico.getDireccion().getCodigoPostal(),
                medico.getDireccion().getProvincia()
        )));
    }
// DELETE LOGICO

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }
// DELETE en BD
//    public void eliminarMedico(@PathVariable Long id) {
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//
//    }

    //Metodo GET para retornar un medico especifico
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(),
                        medico.getDireccion().getUrbanizacion(),
                        medico.getDireccion().getCodigoPostal(),
                        medico.getDireccion().getProvincia()
                ));
        return ResponseEntity.ok(datosMedico);
    }


}
