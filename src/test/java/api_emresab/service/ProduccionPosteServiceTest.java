package api_emresab.service;

import api_emresab.dto.ProduccionRequestDTO;
import api_emresab.model.Material;
import api_emresab.model.ProduccionPoste;
import api_emresab.repository.MaterialRepository;
import api_emresab.repository.ProduccionPosteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProduccionPosteServiceTest {

    @Mock
    private ProduccionPosteRepository produccionRepository;

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private ProduccionPosteService service;

    @Test
    public void registrarProduccion_LanzaErrorPorInventarioInsuficiente() {
        // Arrange: Intentamos gastar 50 Kg de HDPE
        ProduccionRequestDTO request = new ProduccionRequestDTO();
        request.setPolimeroBase("HDPE");
        request.setPesoConsumidoKg(50.0);
        request.setCantidadPostes(10);

        // Simulamos que en base de datos solo hay un lote de 20 Kg
        Material lote = new Material();
        lote.setPesoKilos(20.0);
        when(materialRepository.findByTipoPolimero("HDPE")).thenReturn(Collections.singletonList(lote));

        // Act & Assert: Verificamos que lance la excepción correcta
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.registrarProduccion(request);
        });

        assertTrue(exception.getMessage().contains("Inventario insuficiente"));
        verify(produccionRepository, never()).save(any()); // Confirma que no se guardó ningún poste
    }

    @Test
    public void registrarProduccion_DescuentaExitosamenteConFIFO() {
        // Arrange: Intentamos gastar 30 Kg de HDPE
        ProduccionRequestDTO request = new ProduccionRequestDTO();
        request.setPolimeroBase("HDPE");
        request.setPesoConsumidoKg(30.0);
        request.setCantidadPostes(5);

        // Simulamos dos lotes (Lote 1: 20 Kg, Lote 2: 50 Kg)
        Material lote1 = new Material(); lote1.setPesoKilos(20.0);
        Material lote2 = new Material(); lote2.setPesoKilos(50.0);

        when(materialRepository.findByTipoPolimero("HDPE")).thenReturn(Arrays.asList(lote1, lote2));
        when(produccionRepository.save(any(ProduccionPoste.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ProduccionPoste resultado = service.registrarProduccion(request);

        // Assert
        assertNotNull(resultado);
        assertEquals(5, resultado.getCantidadPostes());
        
        // Verifica que el lote1 se eliminó (porque se gastó completo, 20 Kg)
        verify(materialRepository, times(1)).delete(lote1);
        
        // Verifica que el lote2 se actualizó restándole los 10 Kg faltantes
        verify(materialRepository, times(1)).save(lote2);
        assertEquals(40.0, lote2.getPesoKilos(), "El lote 2 debió quedar con 40 Kg");
    }
}