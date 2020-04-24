package subsistema.pdf.form;

import com.lowagie.text.DocumentException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import subsistema.pdf.dto.SucursalDTO;
import subsistema.pdf.dto.UsuarioDTO;
import subsistema.pdf.dto.DefinicionCreditoDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.text.MultipleParagraph;
import subsistema.pdf.utils.Data;
import subsistema.pdf.utils.Fecha;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioDefinicionCredito {

    private final float maxY         = Data.MAX_LETTER_Y;
    private final float maxX         = Data.MAX_LETTER_X;
    private final float thickness    = Data.THICKNESS_MODEL_1;
    private final float marginStartX = Data.MARGIN_START_X_MODEL_1;
    private final float marginEndX   = Data.MARGIN_END_X_MODEL_1;
    private final float relativePositionX = 5f;
    private final float relativePositionY = 5f;

    private final PDFont fuenteBasica        = Data.BASIC_FONT;
    private final PDFont fuenteBasicaNegrita = Data.BASIC_BOLD_FONT;

    private final Style fuenteSubtitulo = Style.builder()
            .addTextFont(fuenteBasicaNegrita)
            .addFontSize(14f)
            .addTextColor(Color.BLACK)
            .addLeading(0.8f)
            .build();

    private final Style fuenteNormal = Style.builder()
            .addTextFont(fuenteBasica)
            .addFontSize(10.5f)
            .addTextColor(Color.BLACK)
            .addLeading(0.8f)
            .build();

    private final Style fuenteNormalNegrita = Style.builder()
            .addTextFont(fuenteBasicaNegrita)
            .addFontSize(10.5f)
            .addTextColor(Color.BLACK)
            .addLeading(0.8f)
            .build();

    private final Style fuenteDiminuta = Style.builder()
            .addFontSize(8)
            .addTextFont(fuenteBasica)
            .build();

    public FormularioDefinicionCredito(
            String ruta,
            DefinicionCreditoDTO definicion,
            UsuarioDTO usuarioEditor,
            SucursalDTO sucursal,
            Date fechaExportacion) throws IOException, DocumentException {

        new ArchivoDefinicionCreditoPDFConDatosHTML(ruta,definicion.getDetallesHTML());

        GeneradorFormularioFactory.setDimensiones(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight());
        GeneradorFormularioFactory.setMarginStartX(50f);

        File       file= new File(ruta);
        PDDocument doc = PDDocument.load(file);
        int totalPages = doc.getNumberOfPages();
        PDPage page;
        PDPageContentStream contentStream;

        for(int i=0;i<totalPages;i++) {
            page = doc.getPage(i);
            contentStream = new PDPageContentStream(doc, page, true, true);
            GeneradorFormularioFactory.crearInfo(contentStream, sucursal);
            GeneradorFormularioFactory.crearMargen(contentStream);
            if(i==0) {
                GeneradorFormularioFactory.crearCabecera(contentStream, doc);
                GeneradorFormularioFactory.crearFechaExportacion(contentStream, fechaExportacion);
                GeneradorFormularioFactory.crearUsuarioExportador(contentStream, usuarioEditor);
                GeneradorFormularioFactory.crearTitulo(contentStream, "REQUISITOS PARA\nSOLITICITUD DE CREDITO");

                crearSubTitulosDatos(contentStream);
                crearDatosAfiliadoDefinicion(contentStream, definicion);
                crearSubTitulosDetalles(contentStream);
            }
            contentStream.close();
        }

        doc.save(new File(ruta));
        doc.close();
    }

    private void crearSubTitulosDatos(
            PDPageContentStream contentStream) throws IOException {

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + relativePositionX)
                .addStartY(maxY - 190 - relativePositionY)
                .addWidth(180f)
                .addTextContent("DATOS")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }

    private void crearSubTitulosDetalles(
            PDPageContentStream contentStream) throws IOException {

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + relativePositionX)
                .addStartY(maxY - 345 - relativePositionY)
                .addWidth(180f)
                .addTextContent("DETALLES")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }


    private void crearDatosAfiliadoDefinicion(
            PDPageContentStream contentStream,
            DefinicionCreditoDTO definicion) throws IOException {

        String gradoNombreAfiliado = definicion.getGradoNombreAfiliado();
        String carnetIdentidad = definicion.getCarnetIdentidad();
        String numeroCarpeta = definicion.getNumeroCarpeta();
        String fechaCreacion = new Fecha(definicion.getFechaCreacion()).getFormatoFecha();
        String modalidadCredito = definicion.getModalidadCredito();
        String tipoDeGarantia = definicion.getTipoDeGarantia();
        String origenTramite = definicion.getOrigenTramite();

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(1.2f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 280f;
        float width2 = 220f;

        float initY = 215f;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Afiliado", fuenteNormalNegrita, width1, 1f, 2f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText(gradoNombreAfiliado, fuenteNormal, width1, 1f, 2f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Numero de carpeta", fuenteNormalNegrita, width1, 1f, 2f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText(numeroCarpeta, fuenteNormal, width1, 1f, 2f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Modalidad de credito", fuenteNormalNegrita, width1, 1f, 2f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText(modalidadCredito, fuenteNormal, width1, 1f, 2f, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Origen Tramite", fuenteNormalNegrita, width1, 1f, 2f, margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText(origenTramite, fuenteNormal, width1, 1f, 2f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText("Carnet de Identidad", fuenteNormalNegrita, width2, 1f, 2f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(carnetIdentidad, fuenteNormal, width2, 1f, 2f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText("Fecha de Creacion", fuenteNormalNegrita, width2, 1f, 2f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(fechaCreacion, fuenteNormal, width2, 1f, 2f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText("Tipo de garantia", fuenteNormalNegrita, width2, 1f, 2f, margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(tipoDeGarantia, fuenteNormal, width2, 1f, 2f, margin);

        float auxY = maxY - initY;

        EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{c11, c21},
                new Cell[]{c12, c22},
                new Cell[]{c13, c23},
                new Cell[]{c14, c24},
                new Cell[]{c15, c25},
                new Cell[]{c16, c26},
                new Cell[]{c17},
                new Cell[]{c18}).draw(contentStream);
    }

}
