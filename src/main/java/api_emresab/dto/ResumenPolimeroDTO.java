package api_emresab.dto;

public class ResumenPolimeroDTO {

    private String tipoPolimero;
    private long cantidadLotes;
    private double totalKilos;

    public ResumenPolimeroDTO(String tipoPolimero, long cantidadLotes, double totalKilos) {
        this.tipoPolimero = tipoPolimero;
        this.cantidadLotes = cantidadLotes;
        this.totalKilos = totalKilos;
    }

    public String getTipoPolimero() {
        return tipoPolimero;
    }

    public void setTipoPolimero(String tipoPolimero) {
        this.tipoPolimero = tipoPolimero;
    }

    public long getCantidadLotes() {
        return cantidadLotes;
    }

    public void setCantidadLotes(long cantidadLotes) {
        this.cantidadLotes = cantidadLotes;
    }

    public double getTotalKilos() {
        return totalKilos;
    }

    public void setTotalKilos(double totalKilos) {
        this.totalKilos = totalKilos;
    }
}