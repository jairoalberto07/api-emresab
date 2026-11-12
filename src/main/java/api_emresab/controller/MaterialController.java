package api_emresab.controller;

import api_emresab.model.Material;
import api_emresab.repository.MaterialRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@CrossOrigin(origins = "*")
@Tag(name = "Inventario de Polímeros", description = "Operaciones CRUD y consultas de lotes de material reciclable")
public class MaterialController {

    private final MaterialRepository repository;

    public MaterialController(MaterialRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar todo el inventario", description = "Retorna todos los lotes de polímeros registrados en la base de datos.")
    public List<Material> obtenerTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar lote por ID", description = "Obtiene el detalle de un registro específico mediante su identificador numérico.")
    public ResponseEntity<Material> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Filtrar por tipo de polímero", description = "Filtra los lotes registrados según el código del material (ej. HDPE, PP, LDPE).")
    public List<Material> obtenerPorTipo(@RequestParam String tipo) {
        return repository.findByTipoPolimero(tipo.toUpperCase());
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo lote", description = "Valida y almacena un nuevo ingreso de material reciclable en MySQL.")
    public Material guardar(@Valid @RequestBody Material material) {
        return repository.save(material);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar lote existente", description = "Modifica el tipo de polímero, peso en kilos u origen de un registro existente.")
    public ResponseEntity<Material> actualizar(@PathVariable Long id, @Valid @RequestBody Material detalles) {
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

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar lote", description = "Elimina permanentemente un registro de inventario por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}