package subsistema.pdf.utils.factories;

import subsistema.pdf.dto.*;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.shapes.Line;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.Column;
import subsistema.pdf.lib.tables.RecursiveTable;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.lib.text.MultipleParagraph;
import subsistema.pdf.lib.shapes.Rectangle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import subsistema.pdf.utils.Data;
import subsistema.pdf.utils.Fecha;

import java.awt.Color;
import java.io.IOException;
import java.util.*;

public class GeneradorFormularioFactory {
    private static float maxY = 0;
    private static float maxX = 0;

    private static float thickness = 15f;

    private static float marginStartX = 50f;
    private static float marginEndX = 25f;

    private static float relativePositionX = 5f;
    private static float relativePositionY = 5f;


    private static PDFont fuenteBasica = Data.BASIC_FONT;
    private static PDFont fuenteBasicaNegrita = Data.BASIC_BOLD_FONT;
    private static PDFont fuenteBasicaNegritaInclinada = Data.BASIC_BOLD_OBLIQUE_FONT;
    private static PDFont fuenteBasicaInclinada = Data.BASIC_OBLIQUE_FONT;

    private static final String imagenPolicia =
            "/home/rudy/polbol.png";
    private static final String imagenCovipol =
            "/home/rudy/covipol.png";

    // private static final String flechaNorte =
    //        "./src/main/resources/north_arrow.png";


    // METODOS BASICOS
    public static void setDimensiones(float width, float height) {
        maxX = width;
        maxY = height;
    }

    public static void setMarginStartX(float x) {
        marginStartX = x;
    }

    // METODOS GENERICOS

    public static void crearMargen(PDPageContentStream contentStream) throws IOException {
        crearMargen(contentStream, marginStartX, 40, maxX - (marginStartX + marginEndX), maxY - 65);
    }

    public static void crearCabecera(
            PDPageContentStream contentStream,
            PDDocument document) throws IOException {

        Style fuenteCabecera = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(18)
                .addLeading(1f)
                .addTextColor(Color.BLACK)
                .build();

        dibujarImagen(
                contentStream, document, imagenPolicia,
                marginStartX + thickness + 8, maxY - 75 - 80, 66, 80);

        dibujarImagen(
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
            SucursalDTO sucursalDTO) throws IOException {

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
            Date fechaExportacion) throws IOException {

        float auxWidth = 120f;
        Fecha fecha = new Fecha(fechaExportacion);

        Style fuenteNormal = Style.builder()
                .addFontSize(8.5f)
                .addTextFont(fuenteBasica)
                .addTextColor(Color.BLACK)
                .build();

        MultipleParagraph.builder()
                .addStartX(maxX - (marginEndX + thickness + auxWidth) + relativePositionX)
                .addStartY(maxY - 160 - relativePositionY)
                .addWidth(auxWidth)
                .addTextContent(fecha.getFormatoHoraFecha())
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearUsuarioExportador(
            PDPageContentStream contentStream,
            UsuarioDTO usuarioEditor) throws IOException {

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
                .addStartX(marginStartX + thickness + relativePositionX)
                .addStartY(maxY - 160 - relativePositionY)
                .addWidth(width1)
                .addTextContent("Generado por: ")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + width1 + relativePositionX)
                .addStartY(maxY - 160 - relativePositionY)
                .addWidth(width2)
                .addTextContent(usuarioEditor.getNombreCompleto())
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearTitulo(
            PDPageContentStream contentStream,
            String titulo) throws IOException {

        Style fuenteTitulo = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(15)
                .addTextColor(Color.BLACK)
                .addLeading(0.9f)
                .build();

        MultipleParagraph.builder()
                .addStartX(100 + 80 + relativePositionX)
                .addStartY(maxY - 90 - relativePositionY)
                .addWidth(maxX - 2 * 100 - 2 * 80)
                .addTextContent(titulo)
                .addAlignment(Alignment.CENTER)
                .addStyle(fuenteTitulo)
                .build()
                .draw(contentStream);
    }


    // METODOS PRECALIFICADOR

    public static void crearPlanPagosSeccionDictamen(
            PDPageContentStream contentStream,
            DictamenDTO dictamen) throws IOException {

        String montoAFinanciar = String.format("%s Bs", dictamen.getMontoSolicitadoBS());
        String amortizacion = String.format("Cada %s dias", dictamen.getCantidadDiasDeAmortizacion());
        String plazo = String.format("%s cuotas(s)", dictamen.getPlazoCuotas());
        String tazaInteres = String.format("%s %%", dictamen.getTasaInteresFija());
        String maximoComprometido = String.format("%s %%", dictamen.getMaximoComprometido());

        String cuotaParcial = String.format("%s Bs", dictamen.getCuotaParcial());
        String segDesgravamen = String.format("%s Bs", dictamen.getSeguroDesgravamen());
        String cip = String.format("%s Bs", dictamen.getCip());
        String totalCuota = String.format("%s Bs", dictamen.getCuotaTotal());

        Style fuenteTitulo = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(9.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 120f;
        float width2 = 90f;
        float width3 = 120f;
        float width4 = 70f;
        float initY = 325f;
        boolean margin = false;

        Cell cTitle = EasyComponentsFactory.getSimpleCellFromText("PLAN DE PAGOS", fuenteTitulo, width1 + width2 + width3 + width4, 0f, 2.5f, Alignment.CENTER, margin);
        Cell cLine = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2 + width3 + width4, 2f, 1f, margin);

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Monto a financiar", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("Amortizacion", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Plazo", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Taza de interes", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Maximo comprometido", fuenteNormalNegrita, width1, 1f, 5f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(montoAFinanciar, fuenteNormal, width2, 1f, 5f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(amortizacion, fuenteNormal, width2, 1f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(plazo, fuenteNormal, width2, 1f, 5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(tazaInteres, fuenteNormal, width2, 1f, 5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(maximoComprometido, fuenteNormal, width2, 1f, 5f, margin);

        Cell c31 = EasyComponentsFactory.getSimpleCellFromText("Cuota parcial", fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c32 = EasyComponentsFactory.getSimpleCellFromText("Seguro de Desgravamen", fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c33 = EasyComponentsFactory.getSimpleCellFromText("C.I.P.", fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c34 = EasyComponentsFactory.getSimpleCellFromText("Total cuota a cancelar", fuenteNormalNegrita, width3, 1f, 5f, margin);

        Cell c41 = EasyComponentsFactory.getSimpleCellFromText(cuotaParcial, fuenteNormal, width4, 1f, 5f, margin);
        Cell c42 = EasyComponentsFactory.getSimpleCellFromText(segDesgravamen, fuenteNormal, width4, 1f, 5f, margin);
        Cell c43 = EasyComponentsFactory.getSimpleCellFromText(cip, fuenteNormal, width4, 1f, 5f, margin);
        Cell c44 = EasyComponentsFactory.getSimpleCellFromText(totalCuota, fuenteNormal, width4, 1f, 5f, margin);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{cTitle},
                new Cell[]{cLine},
                new Cell[]{c11, c21, c31, c41},
                new Cell[]{c12, c22, c32, c42},
                new Cell[]{c13, c23, c33, c43},
                new Cell[]{c14, c24, c34, c44},
                new Cell[]{c15, c25}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(30f, 15f, new Color(107, 250, 194), table);
        box.setHasMargin(true);
        box.setHasFilling(true);
        box.setColorMargin(new Color(107, 250, 194));
        box.setColorFilling(new Color(107, 250, 194));
        box.draw(contentStream);
    }

    public static void crearPrecalicacionSeccionDictamen(
            PDPageContentStream contentStream,
            DictamenDTO dictamen) throws IOException {

        String liquidoPagable = dictamen.getSueldoLiquidoPagable();
        String dictamenLiteral = dictamen.getDictamenLiteral();
        String porcentajeDestinoPago = dictamen.getPorcentajeDestinadoAPago();
        String cuotaPrestamo = dictamen.getCuotaTotal();
        String cuotaMensual = dictamen.getCuotaParcial();

        Style fuenteTitulo = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(9.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1.2f)
                .build();

        Style fuenteNormalRoja = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9.5f)
                .addTextColor(Color.RED)
                .addLeading(1.2f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1.2f)
                .build();

        float width1 = 80f;
        float width2 = 90f;
        float initY = 200f;
        boolean margin = false;

        Cell cTitle = EasyComponentsFactory.getSimpleCellFromText("Precalificacion en Bolivianos", fuenteTitulo, width1 + width2, 0f, 2.5f, Alignment.CENTER, margin);
        Cell cLine1 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 1f, margin);
        Cell cLine2 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 0.5f, margin);
        Cell cLine3 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 0.5f, margin);
        Cell cLine4 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 0.5f, margin);

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Detalle", fuenteNormalNegrita, width1, 1f, 7.5f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("Liquido Pagable", fuenteNormalNegrita, width1, 1f, 7.5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Cuota de prestamo", fuenteNormalNegrita, width1, 1f, 7.5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Dictamen", fuenteNormalNegrita, width1, 1f, 7.5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("%Destinado Pago", fuenteNormalNegrita, width1, 1f, 7.5f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("Cuota Mensual", fuenteNormalNegrita, width1 + width2, 1f, 7.5f, Alignment.CENTER, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Bs", fuenteNormalNegrita, width1 + width2, 1f, 7.5f, Alignment.CENTER, margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText(cuotaMensual, fuenteNormalNegrita, width1 + width2, 1f, 7.5f, Alignment.CENTER, margin);
        c18.setHasFilling(true);
        c18.setColorFilling(new Color(150, 150, 150));

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText("Monto Bs", fuenteNormal, width2, 1f, 7.5f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(liquidoPagable, fuenteNormal, width2, 1f, 7.5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(cuotaPrestamo, fuenteNormal, width2, 1f, 7.5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(dictamenLiteral, fuenteNormalRoja, width2, 1f, 7.5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(porcentajeDestinoPago, fuenteNormal, width2, 1f, 7.5f, margin);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                maxX - marginEndX - 225, auxY,
                new Cell[]{cTitle},
                new Cell[]{cLine1},
                new Cell[]{c11, c21},
                new Cell[]{cLine2},
                new Cell[]{c12, c22},
                new Cell[]{c13, c23},
                new Cell[]{c14, c24},
                new Cell[]{c15, c25},
                new Cell[]{cLine3},
                new Cell[]{c16},
                new Cell[]{cLine4},

                new Cell[]{c17},
                new Cell[]{c18}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(15f, 10f, new Color(107, 250, 194), table);
        box.setHasMargin(true);
        box.setHasFilling(true);
        box.setColorMargin(new Color(107, 250, 194));
        box.setColorFilling(new Color(107, 250, 194));
        box.draw(contentStream);

    }

    public static void crearDatosSeccionDictamen(PDPageContentStream contentStream, DictamenDTO dictamen) throws IOException {
        String solicitante = dictamen.getNombreCliente();
        String ci = dictamen.getCi();

        String montoUFV = dictamen.getMontoSolicitadoUFV();
        String montoSus = dictamen.getMontoSolicitadoUS();
        String mondoBs = dictamen.getMontoSolicitadoBS();
        String cambioUFV = dictamen.getCambioUFV();
        String cambioSus = dictamen.getCambioUS();

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(9.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 120f;
        float width2 = 60f;
        float width3 = 150f;
        float width4 = 70f;
        float initY = 200f;
        boolean margin = false;

        float auxY = maxY - initY;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Solicitante", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("CI", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en UFV", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en SUS", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en BS", fuenteNormalNegrita, width1, 1f, 5f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(solicitante, fuenteNormal, width2 + width3 + width4, 1f, 5f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(ci, fuenteNormal, width2 + width3 + width4, 1f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(montoUFV, fuenteNormal, width2, 1f, 5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(montoSus, fuenteNormal, width2, 1f, 5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(mondoBs, fuenteNormal, width2, 1f, 5f, margin);

        Cell c33 = EasyComponentsFactory.getSimpleCellFromText("Tipo de cambio venta UFV", fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c34 = EasyComponentsFactory.getSimpleCellFromText("Tipo de cambio venta SUS", fuenteNormalNegrita, width3, 1f, 5f, margin);

        Cell c43 = EasyComponentsFactory.getSimpleCellFromText(cambioUFV, fuenteNormal, width4, 1f, 5f, margin);
        Cell c44 = EasyComponentsFactory.getSimpleCellFromText(cambioSus, fuenteNormal, width4, 1f, 5f, margin);

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{c11, c21},
                new Cell[]{c12, c22},
                new Cell[]{c13, c23, c33, c43},
                new Cell[]{c14, c24, c34, c44},
                new Cell[]{c15, c25}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(30f, 7.5f, new Color(107, 250, 194), table);
        box.setHasMargin(false);
        box.setHasFilling(false);
        box.draw(contentStream);
    }

    public static void crearDetallesTablePlanDePagos(
            PDPageContentStream contentStream,
            List<PDPageContentStream> contentStreams,
            PDDocument doc,
            PlanDePagosDTO planDePagos) throws IOException {


        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(9.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph multipleParagraph = EasyComponentsFactory.getSimpleMultipleParagraph(
                "DETALLES",
                marginStartX + thickness, maxY - 380,
                fuenteNormalNegrita,
                100,
                Alignment.LEFT);
        multipleParagraph.draw(contentStream);

        List<List<String>> listOfList = planDePagos.getContenido();
        List<Float> widths = Arrays.asList(new Float[]{40f, 80f, 90f, 70f, 50f, 70f, 90f, 80f, 60f, 80f});
        List<String> header = planDePagos.getContenidoCabecera();

        Column headerColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteNormalNegrita, Alignment.CENTER);

        Column anotherHeaderColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteNormalNegrita, Alignment.CENTER);

        headerColumn.getCells().forEach(s -> {
            s.setColorFilling(Color.ORANGE);
            s.setHasFilling(true);
        });

        anotherHeaderColumn.getCells().forEach(s -> {
            s.setColorFilling(Color.ORANGE);
            s.setHasFilling(true);
        });


        List<Column> bodyColumns = new LinkedList<>();

        for (List<String> auxList : listOfList)
            bodyColumns.add(EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(auxList, widths, fuenteNormal, Alignment.LEFT));

        List<RecursiveTable> tables = EasyComponentsFactory.getHeaderTable(
                marginStartX + thickness, maxY - 400, maxY - 40, headerColumn, anotherHeaderColumn, bodyColumns, 45);

        Iterator<RecursiveTable> it = tables.iterator();
        it.next().draw(contentStream);

        while (it.hasNext()) {
            PDPage page = new PDPage(new PDRectangle(maxX, maxY));
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            it.next().draw(content);
            contentStreams.add(content);
        }
        contentStreams.add(0, contentStream);
    }

    // METODO PLAN DE PAGOS
    public static void crearClienteSeccionPlanDePagos(
            PDPageContentStream contentStream,
            DictamenDTO dictamen) throws IOException {

        String usuario = dictamen.getNombreCliente();
        String ci = dictamen.getCi();

        String totalCuotaMensual = String.format("%s Bs", dictamen.getCuotaParcial());
        String interesFijo = String.format("%s%% Anual", dictamen.getTasaInteresFija());
        String segDesgravamen = String.format("%s%% Anual", dictamen.getSeguroDesgravamen());

        String cipMensual = String.format("%s%% Mensual", dictamen.getCip());

        String amortizacionFrecuencia = "Cada 30 dias";
        String totalCuotas = dictamen.getCuotaTotal();
        String tipoInteres = "Taza fija";
        String tipoCambio = dictamen.getCambioUS();
        String liquidoPagable = dictamen.getSueldoLiquidoPagable();
        String moneda = "Bolivianos";

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 90f;
        float width2 = 60f;
        float width3 = 90f;
        float width4 = 60f;
        float width5 = 90f;
        float width6 = 70f;
        float initY = 200f;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Nombre Cliente", fuenteNormalNegrita, width1 + width2 / 2, 1f, 5f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("CI", fuenteNormalNegrita, width1 + width2 / 2, 1f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Tipo de Amortizacion", fuenteNormalNegrita, width1 + width2 / 2, 1f, 5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Moneda", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Amortizacion cada", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("Interes fijo", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Total Cuota Mensual", fuenteNormalNegrita, width1, 1f, 5f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(usuario, fuenteNormal, width3 + width4 + width5 + width6 + width2 / 2, 1f, 5f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(ci, fuenteNormal, width3 + width4 + width5 + width6 + width2 / 2, 1f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText("PRESTAMO AMORTIZABLE - METODO FRANCES", fuenteNormal, width3 + width4 + width5 + width6 + width2 / 2, 1f, 5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(moneda, fuenteNormal, width2, 1f, 5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(amortizacionFrecuencia, fuenteNormal, width2, 1f, 5f, margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(interesFijo, fuenteNormal, width2, 1f, 5f, margin);
        Cell c27 = EasyComponentsFactory.getSimpleCellFromText(totalCuotaMensual, fuenteNormal, width2, 1f, 5f, margin);

        Cell c34 = EasyComponentsFactory.getSimpleCellFromText("Tipo de cambio(Bs.)", fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c35 = EasyComponentsFactory.getSimpleCellFromText("Plazo(cuotas)", fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c36 = EasyComponentsFactory.getSimpleCellFromText("Seg. desgravamen", fuenteNormalNegrita, width3, 1f, 5f, margin);

        Cell c44 = EasyComponentsFactory.getSimpleCellFromText(tipoCambio, fuenteNormal, width4, 1f, 5f, margin);
        Cell c45 = EasyComponentsFactory.getSimpleCellFromText(totalCuotas, fuenteNormal, width4, 1f, 5f, margin);
        Cell c46 = EasyComponentsFactory.getSimpleCellFromText(segDesgravamen, fuenteNormal, width4, 1f, 5f, margin);

        Cell c54 = EasyComponentsFactory.getSimpleCellFromText("Liquido pagable(Bs)", fuenteNormalNegrita, width5, 1f, 5f, margin);
        Cell c55 = EasyComponentsFactory.getSimpleCellFromText("Tipo de Interes", fuenteNormalNegrita, width5, 1f, 5f, margin);
        Cell c56 = EasyComponentsFactory.getSimpleCellFromText("CIP", fuenteNormalNegrita, width5, 1f, 5f, margin);

        Cell c64 = EasyComponentsFactory.getSimpleCellFromText(liquidoPagable, fuenteNormal, width6, 1f, 5f, margin);
        Cell c65 = EasyComponentsFactory.getSimpleCellFromText(tipoInteres, fuenteNormal, width6, 1f, 5f, margin);
        Cell c66 = EasyComponentsFactory.getSimpleCellFromText(cipMensual, fuenteNormal, width6, 1f, 5f, margin);

        cambiarColor(new Color(77, 175, 134), c23, c24, c44, c64, c25, c45, c65, c26, c46, c66);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{c11, c21},
                new Cell[]{c12, c22},
                new Cell[]{c13, c23},
                new Cell[]{c14, c24, c34, c44, c54, c64},
                new Cell[]{c15, c25, c35, c45, c55, c65},
                new Cell[]{c16, c26, c36, c46, c56, c66},
                new Cell[]{c17, c27}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(10f, 5f, new Color(107, 250, 194), table);
        box.setHasMargin(true);
        box.setHasFilling(true);
        box.setColorMargin(new Color(107, 250, 194));
        box.setColorFilling(new Color(107, 250, 194));
        box.draw(contentStream);
    }

    public static void crearResumenDatosSeccionPlanDePagos(
            PDPageContentStream contentStream,
            DictamenDTO dictamen) throws IOException {

        String capitalAPagar = dictamen.getCuotaTotal();
        String interesAPagar = dictamen.getTasaInteresFija();
        String segDesgravamen = dictamen.getSeguroDesgravamen();
        String cip = dictamen.getCip();
        String totalAPagar = dictamen.getCuotaTotal();
        String fechaInicio = "";
        String fechaFin = "";

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        Style fuenteTitulo = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        float width1 = 100f;
        float width2 = 80f;
        float initY = 200f;
        boolean margin = false;

        Cell cLine = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 1f, margin);

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("RESUMEN DE DATOS", fuenteTitulo, width1 + width2, 1f, 5f, Alignment.CENTER, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("Capital a pagar", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Interes a pagar", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Seg. desgravamen", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("CIP", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("Total a pagar", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Fecha inicio pago", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText("Fecha fin pago", fuenteNormalNegrita, width1, 1f, 5f, margin);

        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(capitalAPagar, fuenteNormal, width2, 1f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(interesAPagar, fuenteNormal, width2, 1f, 5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(segDesgravamen, fuenteNormal, width2, 1f, 5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(cip, fuenteNormal, width2, 1f, 5f, margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(totalAPagar, fuenteNormal, width2, 1f, 5f, margin);
        Cell c27 = EasyComponentsFactory.getSimpleCellFromText(fechaInicio, fuenteNormal, width2, 1f, 5f, margin);
        Cell c28 = EasyComponentsFactory.getSimpleCellFromText(fechaFin, fuenteNormal, width2, 1f, 5f, margin);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                maxX - marginEndX - marginStartX - thickness - width1 - width2, auxY,
                new Cell[]{c11},
                new Cell[]{cLine},
                new Cell[]{c12, c22},
                new Cell[]{c13, c23},
                new Cell[]{c14, c24},
                new Cell[]{c15, c25},
                new Cell[]{c16, c26},
                new Cell[]{c17, c27},
                new Cell[]{c18, c28}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(10f, 5f, new Color(107, 250, 194), table);
        box.setHasMargin(true);
        box.setHasFilling(true);
        box.setColorMargin(new Color(107, 250, 194));
        box.setColorFilling(new Color(107, 250, 194));
        box.draw(contentStream);
    }

    public static void enumerarPaginas(
            PDPageContentStream contentStream,
            int numeroPagina, int totalPaginas
    ) throws IOException {

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

        cambiarColor(new Color(211, 211, 211), cell1, cell2);

        Column.builder()
                .addStartX(30)
                .addStartY(maxY - 580)
                .addCell(cell1)
                .addCell(cell2)
                .build()
                .draw(contentStream);
    }




    private static void cambiarColor(Color color, Cell... cells) {
        for (Cell cell : cells) {
            cell.setHasFilling(true);
            cell.setColorFilling(color);
        }
    }

    private static void crearMargen(PDPageContentStream contentStream,
                                    float startX, float startY, float witdh, float heigth) throws IOException {
        Rectangle rectangle = Rectangle.builder()
                .addColor(Color.BLACK)
                .addThickness(2)
                .addStartX(startX)
                .addStartY(startY)
                .addWidth(witdh)
                .addHeight(heigth)
                .build();

        rectangle.draw(contentStream);
    }

    public static void dibujarImagen(
            PDPageContentStream pageContentStream,
            PDDocument document, String imagePath,
            float offsetX, float offsetY, int width,
            int heigth
    ) throws IOException {
        PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);
        pageContentStream.drawImage(pdImage, offsetX, offsetY, width, heigth);
    }


}