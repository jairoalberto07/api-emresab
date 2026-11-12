package api_emresab.service;

import api_emresab.dto.ResumenPolimeroDTO;
import api_emresab.model.Material;
import api_emresab.repository.MaterialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock
    private MaterialRepository repository; // Simulamos la base de datos

    @InjectMocks
    private MaterialService service; // Inyectamos el mock en nuestro servicio real

    @Test
    void guardar_NormalizaDatosYGuardaCorrectamente() {
        // 1. Arrange (Preparar datos)
        Material materialEntrada = new Material();
        materialEntrada.setTipoPolimero("  hdpe "); // Minúsculas y con espacios
        materialEntrada.setPesoKilos(150.0);
        materialEntrada.setOrigen("  Acopio Sincé Centro  "); // Con espacios extra

        Material materialGuardado = new Material();
        materialGuardado.setId(1L);
        materialGuardado.setTipoPolimero("HDPE");
        materialGuardado.setPesoKilos(150.0);
        materialGuardado.setOrigen("Acopio Sincé Centro");

        // Configuramos el mock para que responda cuando intentemos guardar
        when(repository.save(any(Material.class))).thenReturn(materialGuardado);

        // 2. Act (Ejecutar el método del servicio)
        Material resultado = service.guardar(materialEntrada);

        // 3. Assert (Verificar que hizo lo que esperábamos)
        assertEquals("HDPE", materialEntrada.getTipoPolimero(), "El tipo de polímero debe normalizarse a mayúsculas y sin espacios");
        assertEquals("Acopio Sincé Centro", materialEntrada.getOrigen(), "El origen no debe tener espacios extra a los lados");
        assertNotNull(resultado.getId(), "El material devuelto debe tener un ID asignado");
        verify(repository, times(1)).save(materialEntrada); // Verifica que el repositorio fue llamado exactamente 1 vez
    }

    @Test
    void obtenerResumenInventario_CalculaTotalesCorrectamente() {
        // 1. Arrange (Preparar datos: 2 lotes de HDPE, 1 lote de PP)
        Material m1 = new Material(); m1.setTipoPolimero("HDPE"); m1.setPesoKilos(100.0);
        Material m2 = new Material(); m2.setTipoPolimero("HDPE"); m2.setPesoKilos(50.0);
        Material m3 = new Material(); m3.setTipoPolimero("PP"); m3.setPesoKilos(200.0);

        when(repository.findAll()).thenReturn(Arrays.asList(m1, m2, m3));

        // 2. Act
        List<ResumenPolimeroDTO> resumen = service.obtenerResumenInventario();

        // 3. Assert
        assertEquals(2, resumen.size(), "Debe haber 2 grupos de polímeros (HDPE y PP)");

        // Verificar métricas de HDPE
        ResumenPolimeroDTO resumenHDPE = resumen.stream().filter(r -> r.getTipoPolimero().equals("HDPE")).findFirst().get();
        assertEquals(2, resumenHDPE.getCantidadLotes(), "Deben ser 2 lotes de HDPE");
        assertEquals(150.0, resumenHDPE.getTotalKilos(), "El total de HDPE debe ser 150 kilos");

        // Verificar métricas de PP
        ResumenPolimeroDTO resumenPP = resumen.stream().filter(r -> r.getTipoPolimero().equals("PP")).findFirst().get();
        assertEquals(1, resumenPP.getCantidadLotes(), "Debe ser 1 lote de PP");
        assertEquals(200.0, resumenPP.getTotalKilos(), "El total de PP debe ser 200 kilos");
    }
}