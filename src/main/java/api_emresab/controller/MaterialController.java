package api_emresab.controller;

import api_emresab.dto.ResumenPolimeroDTO;
import api_emresab.model.Material;
import api_emresab.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@CrossOrigin(origins = "*")
@Tag(name = "Inventario de Polímeros", description = "Operaciones CRUD, métricas y consultas de lotes de material reciclable")
public class MaterialController {

    private final MaterialService service;

    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todo el inventario", description = "Retorna todos los lotes de polímeros registrados en la base de datos.")
    public List<Material> obtenerTodos() {
        return service.listarTodos();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Filtrar por tipo de polímero", description = "Filtra los lotes registrados según el código del material (ej. HDPE, PP, LDPE).")
    public List<Material> obtenerPorTipo(@RequestParam String tipo) {
        return service.buscarPorTipo(tipo);
    }

    @GetMapping("/resumen")
    @Operation(summary = "Resumen de inventario por polímero", description = "Calcula la cantidad de lotes y el peso total en kilogramos agrupado por tipo de polímero.")
    public List<ResumenPolimeroDTO> obtenerResumen() {
        return service.obtenerResumenInventario();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar lote por ID", description = "Obtiene el detalle de un registro específico mediante su identificador numérico.")
    public ResponseEntity<Material> obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo lote", description = "Valida, normaliza y almacena un nuevo ingreso de material reciclable en MySQL.")
    public Material guardar(@Valid @RequestBody Material material) {
        return service.guardar(material);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar lote existente", description = "Modifica y normaliza el tipo de polímero, peso en kilos u origen de un registro existente.")
    public ResponseEntity<Material> actualizar(@PathVariable Long id, @Valid @RequestBody Material detalles) {
        return service.actualizar(id, detalles)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar lote", description = "Elimina permanentemente un registro de inventario por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}