package api_emresab.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "materiales")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de polímero es obligatorio (ej. HDPE, PP, LDPE)")
    @Column(name = "tipo_polimero", nullable = false, length = 50)
    private String tipoPolimero;

    @NotNull(message = "El peso en kilos es obligatorio")
    @Positive(message = "El peso en kilos debe ser mayor a cero")
    @Column(name = "peso_kilos", nullable = false)
    private Double pesoKilos;

    @NotBlank(message = "El origen o punto de acopio es obligatorio")
    @Column(nullable = false, length = 100)
    private String origen;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
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
