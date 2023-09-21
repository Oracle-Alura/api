package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Embeddable
@Getter // Crea todos los getters
@NoArgsConstructor //Crea un constructor vacio
@AllArgsConstructor //Crea un constructor con argumentos
public class Direccion {
    private String calle;
    private String numero; // para no tener problemas con la BD uso String
    private String complemento;
    private String distrito;
    private String ciudad;
    private String urbanizacion;
    private String codigoPostal;
    private String provincia;

    public Direccion(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.distrito = direccion.distrito();
        this.complemento = direccion.complemento();
        this.ciudad = direccion.ciudad();
        this.urbanizacion = direccion.urbanizacion();
        this.codigoPostal = direccion.codigoPostal();
        this.provincia = direccion.provincia();
    }

    public Direccion actualizarDatos(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.distrito = direccion.distrito();
        this.complemento = direccion.complemento();
        this.ciudad = direccion.ciudad();
        this.urbanizacion = direccion.urbanizacion();
        this.codigoPostal = direccion.codigoPostal();
        this.provincia = direccion.provincia();
        return this;

    }
}
