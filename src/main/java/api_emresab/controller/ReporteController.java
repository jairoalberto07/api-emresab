package api_emresab.controller;

import api_emresab.service.ReporteService;
import api_emresab.service.ReporteService;
import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
@Tag(name = "Reportes", description = "Generación de documentos y exportación de datos")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/inventario/pdf")
    @Operation(summary = "Descargar reporte de inventario", description = "Genera y retorna un archivo PDF con el estado actual del inventario de polímeros.")
    public ResponseEntity<byte[]> descargarReportePdf() {
        try {
            byte[] pdfBytes = reporteService.generarReporteInventarioPdf();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // Esto le dice al navegador que descargue el archivo en lugar de solo mostrar el texto
            headers.setContentDispositionFormData("attachment", "Reporte_Inventario_EMRESAB.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (DocumentException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}