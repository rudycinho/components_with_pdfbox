package subsistema.pdf.utils.factories;

import subsistema.pdf.dto.*;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.shapes.Line;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.Column;
import subsistema.pdf.lib.text.MultipleParagraph;
import subsistema.pdf.lib.shapes.Rectangle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import subsistema.pdf.utils.Fecha;
import subsistema.pdf.utils.settings.Settings;

import java.awt.Color;
import java.io.IOException;
import java.util.*;

public class GeneradorFormularioFactory {
    /*private static float maxY = 0;
    private static float maxX = 0;

    private static float thickness = 15f;

    private static float marginStartX = 50f;
    private static float marginEndX = 25f;

    private static float relativePositionX = 5f;
    private static float relativePositionY = 5f;


    private static PDFont fuenteBasica = Data.BASIC_FONT;
    private static PDFont fuenteBasicaNegrita = Data.BASIC_BOLD_FONT;
*/
    private static final String imagenPolicia =
            "/home/rudy/polbol.png";
    private static final String imagenCovipol =
            "/home/rudy/covipol.png";

    // private static final String flechaNorte =
    //        "./src/main/resources/north_arrow.png";

    // METODOS GENERICOS

    public static void crearMargen(PDPageContentStream contentStream, Settings model) throws IOException {
        float marginStartX = model.getMarginStartX();
        float marginEndX   = model.getMarginEndX();
        float maxX         = model.getMaxX();
        float maxY         = model.getMaxY();
        EasyComponentsFactory.createMargin(contentStream, marginStartX, 40, maxX - (marginStartX + marginEndX), maxY - 65);
    }

    public static void crearCabecera(
            PDPageContentStream contentStream,
            PDDocument document, Settings model) throws IOException {

        float marginStartX = model.getMarginStartX();
        float marginEndX   = model.getMarginEndX();
        float maxX         = model.getMaxX();
        float maxY         = model.getMaxY();
        float thickness    = model.getThickness();

        PDFont fuenteBasicaNegrita = model.getFuenteBasicaNegrita();

        Style fuenteCabecera = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(18)
                .addLeading(1f)
                .addTextColor(Color.BLACK)
                .build();

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
            SucursalDTO sucursalDTO, Settings model) throws IOException {

        PDFont fuenteBasica = model.getFuenteBasica();
        float marginStartX = model.getMarginStartX();
        float marginEndX   = model.getMarginEndX();
        float maxX         = model.getMaxX();

        Style fuenteDiminuta = Style.builder()
                .addFontSize(8)
                .addTextFont(fuenteBasica)
                .build();

        String informacion = String.format(
                "%s: %s\n%s: %s  -  %s: %s  -  %s: %s",
                "DIRECCION", sucursalDTO.getDireccion(),
                "TELEFONOS", sucursalDTO.getTelefono(),
                "FAX", sucursalDTO.getFax(),
                "CORREO ELECTRONICO", "covipol.afiliaciones@covipol.gob.bo"
        );

        float distance = 35f;

        MultipleParagraph.builder()
                .addStartX(marginStartX + distance)
                .addStartY(35f)
                .addWidth(maxX - marginStartX - marginEndX - (2 * distance))
                .addAlignment(Alignment.CENTER)
                .addTextContent(informacion)
                .addStyle(fuenteDiminuta)
                .build()
                .draw(contentStream);
    }

    public static void crearFechaExportacion(
            PDPageContentStream contentStream,
            Date fechaExportacion, Settings model) throws IOException {

        PDFont fuenteBasica = model.getFuenteBasica();
        float marginEndX   = model.getMarginEndX();
        float maxX         = model.getMaxX();
        float maxY         = model.getMaxY();
        float thickness    = model.getThickness();

        float auxWidth = 120f;
        Fecha fecha = new Fecha(fechaExportacion);

        Style fuenteNormal = Style.builder()
                .addFontSize(8.5f)
                .addTextFont(fuenteBasica)
                .addTextColor(Color.BLACK)
                .build();

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
            UsuarioDTO usuarioEditor, Settings model) throws IOException {

        PDFont fuenteBasica = model.getFuenteBasica();
        PDFont fuenteBasicaNegrita = model.getFuenteBasicaNegrita();
        float marginStartX = model.getMarginStartX();
        float maxY         = model.getMaxY();
        float thickness    = model.getThickness();

        Style fuenteNormal = Style.builder()
                .addFontSize(8.5f)
                .addTextFont(fuenteBasica)
                .addTextColor(Color.BLACK)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addFontSize(8.5f)
                .addTextFont(fuenteBasicaNegrita)
                .addTextColor(Color.BLACK)
                .build();

        float width1 = 100f;
        float width2 = 240f;

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + 5)
                .addStartY(maxY - 160 - 5)
                .addWidth(width1)
                .addTextContent("Generado por: ")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + width1 + 5)
                .addStartY(maxY - 160 - 5)
                .addWidth(width2)
                .addTextContent(usuarioEditor.getNombreCompleto())
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearTitulo(
            PDPageContentStream contentStream,
            String titulo, Settings model) throws IOException {

        PDFont fuenteBasicaNegrita = model.getFuenteBasicaNegrita();
        float maxX         = model.getMaxX();
        float maxY         = model.getMaxY();

        Style fuenteTitulo = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(15)
                .addTextColor(Color.BLACK)
                .addLeading(0.9f)
                .build();

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
            int numeroPagina, int totalPaginas,
            Settings model
    ) throws IOException {

        PDFont fuenteBasicaNegrita = model.getFuenteBasicaNegrita();
        float maxY         = model.getMaxY();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Cell cell1 = EasyComponentsFactory.getSimpleCellFromText(
                "SISTEMA INTEGRAL DE PRESTAMOS",
                fuenteNormalNegrita,
                700,
                0, 0,
                Alignment.CENTER, false);

        Cell cell2 = EasyComponentsFactory.getSimpleCellFromText(
                String.format("%d/%d", numeroPagina, totalPaginas),
                fuenteNormalNegrita,
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



}