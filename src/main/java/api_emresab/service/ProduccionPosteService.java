package api_emresab.service;

import api_emresab.dto.ProduccionRequestDTO;
import api_emresab.model.Material;
import api_emresab.model.ProduccionPoste;
import api_emresab.repository.MaterialRepository;
import api_emresab.repository.ProduccionPosteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProduccionPosteService {

    private final ProduccionPosteRepository produccionRepository;
    private final MaterialRepository materialRepository;

    public ProduccionPosteService(ProduccionPosteRepository produccionRepository, MaterialRepository materialRepository) {
        this.produccionRepository = produccionRepository;
        this.materialRepository = materialRepository;
    }

    public List<ProduccionPoste> listarProduccion() {
        return produccionRepository.findAll();
    }

    @Transactional // Garantiza que si falla algo, no se guarde a medias
    public ProduccionPoste registrarProduccion(ProduccionRequestDTO request) {
        String tipoPolimero = request.getPolimeroBase().trim().toUpperCase();
        double kilosNecesarios = request.getPesoConsumidoKg();

        // 1. Consultar inventario del polímero solicitado
        List<Material> lotes = materialRepository.findByTipoPolimero(tipoPolimero);
        double kilosDisponibles = lotes.stream().mapToDouble(Material::getPesoKilos).sum();

        // 2. Validar que haya suficiente plástico
        if (kilosDisponibles < kilosNecesarios) {
            throw new IllegalArgumentException("Inventario insuficiente de " + tipoPolimero + ". Disponible: " + kilosDisponibles + " Kg.");
        }

        // 3. Descontar de los lotes (Algoritmo FIFO)
        for (Material lote : lotes) {
            if (kilosNecesarios <= 0) break;

            if (lote.getPesoKilos() <= kilosNecesarios) {
                // Se consume todo el lote
                kilosNecesarios -= lote.getPesoKilos();
                materialRepository.delete(lote); 
            } else {
                // Se consume solo una parte del lote
                lote.setPesoKilos(lote.getPesoKilos() - kilosNecesarios);
                materialRepository.save(lote);
                kilosNecesarios = 0;
            }
        }

        // 4. Guardar el registro de los postes fabricados
        ProduccionPoste produccion = new ProduccionPoste();
        produccion.setCantidadPostes(request.getCantidadPostes());
        produccion.setPesoConsumidoKg(request.getPesoConsumidoKg());
        produccion.setRecetaMezcla("Base 100% " + tipoPolimero);
        
        return produccionRepository.save(produccion);
    }
}