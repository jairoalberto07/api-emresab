package api_emresab.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "materiales")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoPolimero; // HDPE, PP, LDPE

    @Column(nullable = false)
    private Double pesoKilos;

    private String origen; // Ej: Recolección Sincé

    private LocalDateTime fechaRegistro;

    public Material() {
        this.fechaRegistro = LocalDateTime.now();
    }

    public Material(String tipoPolimero, Double pesoKilos, String origen) {
        this.tipoPolimero = tipoPolimero;
        this.pesoKilos = pesoKilos;
        this.origen = origen;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoPolimero() { return tipoPolimero; }
    public void setTipoPolimero(String tipoPolimero) { this.tipoPolimero = tipoPolimero; }

    public Double getPesoKilos() { return pesoKilos; }
    public void setPesoKilos(Double pesoKilos) { this.pesoKilos = pesoKilos; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}