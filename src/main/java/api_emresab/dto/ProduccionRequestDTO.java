package api_emresab.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProduccionRequestDTO {

    @NotNull(message = "La cantidad de postes es obligatoria")
    @Min(value = 1, message = "Debe registrar al menos 1 poste")
    private Integer cantidadPostes;

    @NotNull(message = "El peso consumido es obligatorio")
    @Min(value = 1, message = "El peso debe ser mayor a cero")
    private Double pesoConsumidoKg;

    @NotBlank(message = "Debe especificar el polímero consumido (ej. HDPE, PP)")
    private String polimeroBase;

    // Getters y Setters
    public Integer getCantidadPostes() { return cantidadPostes; }
    public void setCantidadPostes(Integer cantidadPostes) { this.cantidadPostes = cantidadPostes; }

    public Double getPesoConsumidoKg() { return pesoConsumidoKg; }
    public void setPesoConsumidoKg(Double pesoConsumidoKg) { this.pesoConsumidoKg = pesoConsumidoKg; }

    public String getPolimeroBase() { return polimeroBase; }
    public void setPolimeroBase(String polimeroBase) { this.polimeroBase = polimeroBase; }
}