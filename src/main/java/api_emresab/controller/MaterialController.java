package api_emresab.controller;

import api_emresab.model.Material;
import api_emresab.repository.MaterialRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@CrossOrigin(origins = "*")
public class MaterialController {

    private final MaterialRepository repository;

    public MaterialController(MaterialRepository repository) {
        this.repository = repository;
    }

    // 1. Obtener todo el inventario recolectado
    @GetMapping
    public List<Material> obtenerTodos() {
        return repository.findAll();
    }

    // 2. Obtener un registro específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Material> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Filtrar lote por tipo de polímero (Ej: /api/materiales/buscar?tipo=HDPE)
    @GetMapping("/buscar")
    public List<Material> obtenerPorTipo(@RequestParam String tipo) {
        return repository.findByTipoPolimero(tipo.toUpperCase());
    }

    // 4. Guardar un nuevo lote de polímero
    @PostMapping
    public Material guardar(@RequestBody Material material) {
        return repository.save(material);
    }

    // 5. Actualizar los datos de un lote existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Material> actualizar(@PathVariable Long id, @RequestBody Material detalles) {
        return repository.findById(id)
                .map(materialExistente -> {
                    materialExistente.setTipoPolimero(detalles.getTipoPolimero());
                    materialExistente.setPesoKilos(detalles.getPesoKilos());
                    materialExistente.setOrigen(detalles.getOrigen());
                    Material actualizado = repository.save(materialExistente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 6. Eliminar un registro de inventario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}