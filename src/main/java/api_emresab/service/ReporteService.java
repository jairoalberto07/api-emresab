package api_emresab.service;

import api_emresab.model.Material;
import api_emresab.repository.MaterialRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReporteService {

    private final MaterialRepository materialRepository;

    public ReporteService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public byte[] generarReporteInventarioPdf() throws DocumentException {
        List<Material> inventario = materialRepository.findAll();

        Document documento = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(documento, out);

        documento.open();

        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
        Paragraph titulo = new Paragraph("Reporte de Inventario - ERS-EMRESAB S.A.S", fuenteTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new int[]{1, 2, 2, 3}); 

        Font fuenteEncabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        String[] encabezados = {"ID", "Polímero", "Peso (Kg)", "Origen"};
        
        for (String tituloColumna : encabezados) {
            PdfPCell celda = new PdfPCell(new Phrase(tituloColumna, fuenteEncabezado));
            celda.setBackgroundColor(new BaseColor(40, 167, 69)); 
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(8);
            tabla.addCell(celda);
        }

        Font fuenteDatos = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
        for (Material lote : inventario) {
            tabla.addCell(new PdfPCell(new Phrase(String.valueOf(lote.getId()), fuenteDatos)));
            tabla.addCell(new PdfPCell(new Phrase(lote.getTipoPolimero(), fuenteDatos)));
            tabla.addCell(new PdfPCell(new Phrase(String.valueOf(lote.getPesoKilos()), fuenteDatos)));
            tabla.addCell(new PdfPCell(new Phrase(lote.getOrigen(), fuenteDatos)));
        }

        documento.add(tabla);
        documento.close();

        return out.toByteArray();
    }
}