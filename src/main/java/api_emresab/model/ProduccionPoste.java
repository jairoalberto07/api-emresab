package api_emresab.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "produccion_postes")
public class ProduccionPoste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La cantidad de postes fabricados es obligatoria")
    @Min(value = 1, message = "Debe registrar al menos 1 poste")
    @Column(name = "cantidad_postes", nullable = false)
    private Integer cantidadPostes;

    @NotNull(message = "El peso total de plástico consumido es obligatorio")
    @Min(value = 1, message = "El peso consumido debe ser mayor a 0")
    @Column(name = "peso_consumido_kg", nullable = false)
    private Double pesoConsumidoKg;

    @NotBlank(message = "Debe especificar la receta de la mezcla (ej. 70% HDPE - 30% PP)")
    @Column(name = "receta_mezcla", nullable = false, length = 100)
    private String recetaMezcla;

    @Column(name = "fecha_produccion")
    private LocalDateTime fechaProduccion;

    @PrePersist
    public void prePersist() {
        this.fechaProduccion = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getCantidadPostes() { return cantidadPostes; }
    public void setCantidadPostes(Integer cantidadPostes) { this.cantidadPostes = cantidadPostes; }

    public Double getPesoConsumidoKg() { return pesoConsumidoKg; }
    public void setPesoConsumidoKg(Double pesoConsumidoKg) { this.pesoConsumidoKg = pesoConsumidoKg; }

    public String getRecetaMezcla() { return recetaMezcla; }
    public void setRecetaMezcla(String recetaMezcla) { this.recetaMezcla = recetaMezcla; }

    public LocalDateTime getFechaProduccion() { return fechaProduccion; }
    public void setFechaProduccion(LocalDateTime fechaProduccion) { this.fechaProduccion = fechaProduccion; }
}