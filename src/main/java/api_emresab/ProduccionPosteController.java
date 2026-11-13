package api_emresab.controller;

import api_emresab.dto.ProduccionRequestDTO;
import api_emresab.model.ProduccionPoste;
import api_emresab.service.ProduccionPosteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produccion")
@CrossOrigin(origins = "*")
@Tag(name = "Producción de Postes", description = "Módulo de transformación de madera plástica y descuento de inventario")
public class ProduccionPosteController {

    private final ProduccionPosteService service;

    public ProduccionPosteController(ProduccionPosteService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar historial de producción", description = "Retorna todos los registros de fabricación de postes agrícolas.")
    public List<ProduccionPoste> obtenerHistorial() {
        return service.listarProduccion();
    }

    @PostMapping
    @Operation(summary = "Registrar manufactura de postes", description = "Registra los postes creados y descuenta automáticamente los kilos del inventario de material.")
    public ProduccionPoste registrar(@Valid @RequestBody ProduccionRequestDTO request) {
        return service.registrarProduccion(request);
    }
}