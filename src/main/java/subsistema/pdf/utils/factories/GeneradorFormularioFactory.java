package subsistema.pdf.utils.factories;

import subsistema.pdf.dto.*;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.shapes.Line;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.Column;
import subsistema.pdf.lib.text.simple.MultipleParagraph;
import subsistema.pdf.lib.shapes.Rectangle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import subsistema.pdf.utils.Fecha;
import subsistema.pdf.utils.settings.Model;

import java.awt.Color;
import java.io.IOException;
import java.util.*;

public class GeneradorFormularioFactory {
    private static final String imagenPolicia =
            "/home/rudy/polbol.png";
    private static final String imagenCovipol =
            "/home/rudy/covipol.png";
    private static final String imagenFecha =
            "/home/rudy/empresasim/sip/backendsip/src/main/resources/north_arrow.png";

    public static void crearMargen(
            PDPageContentStream contentStream,
            Model model) throws IOException {
        float marginStartX = model.getMarginStartX();
        float marginEndX   = model.getMarginEndX();
        float maxX         = model.getMaxX();
        float maxY         = model.getMaxY();
        EasyComponentsFactory.createMargin(contentStream, marginStartX, 40, maxX - (marginStartX + marginEndX), maxY - 65);
    }

    public static void crearCabecera(
            PDPageContentStream contentStream,
            PDDocument document,
            Model model, Style fuenteCabecera) throws IOException {

        float marginStartX = model.getMarginStartX();
        float marginEndX   = model.getMarginEndX();
        float maxX         = model.getMaxX();
        float maxY         = model.getMaxY();
        float thickness    = model.getThickness();

        EasyComponentsFactory.drawImage(
                contentStream, document, imagenPolicia,
                marginStartX + thickness + 8, maxY - 75 - 80, 66, 80);

        EasyComponentsFactory.drawImage(
                contentStream, document, imagenCovipol,
                maxX - (marginEndX + thickness + 80), maxY - 75 - 80, 80, 80);

        MultipleParagraph.builder()
                .addTextContent("CONSEJO NACIONAL DE VIVIENDA POLICIAL")
                .addAlignment(Alignment.CENTER)
                .addStartX(115)
                .addStartY(maxY - 50)
                .addWidth(maxX - 2 * 100)
                .addStyle(fuenteCabecera)
                .build()
                .draw(contentStream);

        Rectangle.builder()
                .addStartX(marginStartX + thickness)
                .addStartY(maxY - 40)
                .addWidth(maxX - (marginStartX + marginEndX) - 2 * thickness)
                .addHeight(-120)
                .addThickness(2)
                .addColor(Color.BLACK)
                .build()
                .draw(contentStream);

        new Line(marginStartX + thickness, maxY - 75, maxX - (marginEndX + thickness), maxY - 75)
                .setColor(Color.BLACK)
                .setThickness(1)
                .draw(contentStream);

        new Line(marginStartX + thickness + 80, maxY - 75, marginStartX + thickness + 80, maxY - 75 - 80)
                .setColor(Color.BLACK)
                .setThickness(1)
                .draw(contentStream);

        new Line(maxX - (marginEndX + thickness + 80), maxY - 75, maxX - (marginEndX + thickness + 80), maxY - 75 - 80)
                .setColor(Color.BLACK)
                .setThickness(1)
                .draw(contentStream);
    }

    public static void crearInfo(
            PDPageContentStream contentStream,
            SucursalDTOPDF sucursalDTO,
            Model model, Style footer) throws IOException {

        float marginStartX = model.getMarginStartX();
        float marginEndX   = model.getMarginEndX();
        float maxX         = model.getMaxX();

        String informacion = String.format(
                "%s: %s\n%s: %s  -  %s: %s  -  %s: %s",
                "DIRECCION", sucursalDTO.getDireccion(),
                "TELEFONOS", sucursalDTO.getTelefono(),
                "FAX", sucursalDTO.getFax(),
                "CORREO ELECTRONICO", "covipol.afiliaciones@covipol.gob.bo"
        );

        float distance = 15f;

        MultipleParagraph.builder()
                .addStartX(marginStartX + distance)
                .addStartY(35f)
                .addWidth(maxX - marginStartX - marginEndX - (2 * distance))
                .addAlignment(Alignment.CENTER)
                .addTextContent(informacion)
                .addStyle(footer)
                .build()
                .draw(contentStream);
    }

    public static void crearFechaExportacion(
            PDPageContentStream contentStream,
            Date fechaExportacion,
            Model model,
            Style fuenteNormal) throws IOException {

        float marginEndX   = model.getMarginEndX();
        float maxX         = model.getMaxX();
        float maxY         = model.getMaxY();
        float thickness    = model.getThickness();

        float auxWidth = 120f;
        Fecha fecha = new Fecha(fechaExportacion);

        MultipleParagraph.builder()
                .addStartX(maxX - (marginEndX + thickness + auxWidth) + 5)
                .addStartY(maxY - 160 - 5)
                .addWidth(auxWidth)
                .addTextContent(fecha.getFormatoHoraFecha())
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearUsuarioExportador(
            PDPageContentStream contentStream,
            UsuarioDTOPDF usuarioEditor,
            Model model,
            Style fuenteNegrita,
            Style fuente) throws IOException {

        float marginStartX = model.getMarginStartX();
        float maxY         = model.getMaxY();
        float thickness    = model.getThickness();

        float width1 = 100f;
        float width2 = 240f;

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + 5)
                .addStartY(maxY - 160 - 5)
                .addWidth(width1)
                .addTextContent("Generado por: ")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + width1 + 5)
                .addStartY(maxY - 160 - 5)
                .addWidth(width2)
                .addTextContent(usuarioEditor.getNombreCompleto())
                .addAlignment(Alignment.LEFT)
                .addStyle(fuente)
                .build()
                .draw(contentStream);
    }

    public static void crearTitulo(
            PDPageContentStream contentStream,
            String titulo,
            Model model, Style fuenteTitulo) throws IOException {

        float maxX         = model.getMaxX();
        float maxY         = model.getMaxY();

        MultipleParagraph.builder()
                .addStartX(100 + 80 + 5)
                .addStartY(maxY - 90 - 5)
                .addWidth(maxX - 2 * 100 - 2 * 80)
                .addTextContent(titulo)
                .addAlignment(Alignment.CENTER)
                .addStyle(fuenteTitulo)
                .build()
                .draw(contentStream);
    }

    public static void enumerarPaginas(
            PDPageContentStream contentStream,
            int numeroPagina,
            int totalPaginas,
            Model model,
            Style fuenteNumero) throws IOException {

        float maxY         = model.getMaxY();

        Cell cell1 = EasyComponentsFactory.getSimpleCellFromText(
                "SISTEMA INTEGRAL DE PRESTAMOS",
                fuenteNumero,
                700,
                0, 0,
                Alignment.CENTER, false);

        Cell cell2 = EasyComponentsFactory.getSimpleCellFromText(
                String.format("%d/%d", numeroPagina, totalPaginas),
                fuenteNumero,
                30,
                0, 0,
                Alignment.CENTER, false);

        EasyComponentsFactory.cambiarColor(new Color(211, 211, 211), cell1, cell2);

        Column.builder()
                .addStartX(30)
                .addStartY(maxY - 580)
                .addCell(cell1)
                .addCell(cell2)
                .build()
                .draw(contentStream);
    }

    public static void dibujarFlecha(
            PDPageContentStream contentStream,
            PDDocument document,
            Model model) throws IOException {


        float marginEndX = model.getMarginStartX();
        float maxX       = model.getMaxX();
        float maxY       = model.getMaxY();
        float thickness  = model.getThickness();

        EasyComponentsFactory.drawImage(
                contentStream, document, imagenFecha,
                maxX - (marginEndX+thickness+70),maxY-440-90,60,80);

    }


}