package api_emresab.service;

import api_emresab.dto.ResumenPolimeroDTO;
import api_emresab.model.Material;
import api_emresab.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MaterialService {

    private final MaterialRepository repository;

    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
    }

    public List<Material> listarTodos() {
        return repository.findAll();
    }

    public Optional<Material> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Material> buscarPorTipo(String tipo) {
        return repository.findByTipoPolimero(tipo.trim().toUpperCase());
    }

    public Material guardar(Material material) {
        normalizarDatos(material);
        return repository.save(material);
    }

    public Optional<Material> actualizar(Long id, Material detalles) {
        return repository.findById(id).map(materialExistente -> {
            materialExistente.setTipoPolimero(detalles.getTipoPolimero());
            materialExistente.setPesoKilos(detalles.getPesoKilos());
            materialExistente.setOrigen(detalles.getOrigen());
            normalizarDatos(materialExistente);
            return repository.save(materialExistente);
        });
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ResumenPolimeroDTO> obtenerResumenInventario() {
        List<Material> materiales = repository.findAll();
        Map<String, ResumenPolimeroDTO> agrupado = new LinkedHashMap<>();

        for (Material m : materiales) {
            String tipo = m.getTipoPolimero().trim().toUpperCase();
            ResumenPolimeroDTO resumen = agrupado.get(tipo);
            if (resumen == null) {
                resumen = new ResumenPolimeroDTO(tipo, 0L, 0.0);
                agrupado.put(tipo, resumen);
            }
            resumen.setCantidadLotes(resumen.getCantidadLotes() + 1);
            resumen.setTotalKilos(resumen.getTotalKilos() + m.getPesoKilos());
        }

        return new ArrayList<>(agrupado.values());
    }

    private void normalizarDatos(Material material) {
        if (material.getTipoPolimero() != null) {
            material.setTipoPolimero(material.getTipoPolimero().trim().toUpperCase());
        }
        if (material.getOrigen() != null) {
            material.setOrigen(material.getOrigen().trim());
        }
    }
}