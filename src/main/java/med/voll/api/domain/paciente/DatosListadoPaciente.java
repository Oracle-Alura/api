package med.voll.api.domain.paciente;

public record DatosListadoPaciente(Long Id, String nombre, String documento, String email) {

    public DatosListadoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getDocumentoIdentidad(), paciente.getEmail());
    }
}
