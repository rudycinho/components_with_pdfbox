package subsistema.pdf.form;

import com.lowagie.text.DocumentException;
import subsistema.pdf.dto.SucursalDTOPDF;
import subsistema.pdf.dto.UsuarioDTOPDF;
import subsistema.pdf.dto.DefinicionCreditoDTOPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.text.simple.MultipleParagraph;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioDefinicionCredito {

    private final float maxY         = Models.MODEL_1.getMaxY();
    private final float maxX         = Models.MODEL_1.getMaxX();
    private final float thickness    = Models.MODEL_1.getThickness();
    private final float marginStartX = Models.MODEL_1.getMarginStartX();
    private final float marginEndX   = Models.MODEL_1.getMarginEndX();
    private final FontEnum fontEnum  = Models.MODEL_1.getFont();

    private Style fuenteSubtitulo;
    private Style fuenteInfoEspaciado;
    private Style fuenteInfoNegritaEspaciado;

    public FormularioDefinicionCredito(
            String ruta,
            DefinicionCreditoDTOPDF definicion,
            UsuarioDTOPDF usuarioEditor,
            SucursalDTOPDF sucursal,
            Date fechaExportacion) throws IOException, DocumentException {

        new ArchivoDefinicionCreditoPDFConDatosHTML(ruta,definicion.getDetallesHTML());

        File file      = new File(ruta);
        PDDocument doc = PDDocument.load(file);

        FontGroup fontGroup = FontGroup.getFontGroup(fontEnum,doc);
        fuenteSubtitulo                = Style.getStyle(fontGroup, StyleEnum.SUBTITLE_BOLD);
        fuenteInfoEspaciado        = Style.getStyle(fontGroup, StyleEnum.MINIMAL_NORMAL_SPACED);
        fuenteInfoNegritaEspaciado = Style.getStyle(fontGroup, StyleEnum.MINIMAL_BOLD_SPACED);

        int totalPages = doc.getNumberOfPages();
        PDPage page;
        PDPageContentStream contentStream;

        for(int i=0;i<totalPages;i++) {
            page = doc.getPage(i);
            contentStream = new PDPageContentStream(doc, page, true, true);
            GeneradorFormularioFactory.crearInfo(contentStream, sucursal, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.FOOTER));
            GeneradorFormularioFactory.crearMargen(contentStream, Models.MODEL_1);
            if(i==0) {
                GeneradorFormularioFactory.crearCabecera(contentStream, doc, Models.MODEL_1,Style.getStyle(fontGroup,StyleEnum.HEADER));
                GeneradorFormularioFactory.crearFechaExportacion(contentStream, fechaExportacion, Models.MODEL_1,Style.getStyle(fontGroup,StyleEnum.OWNER));
                GeneradorFormularioFactory.crearUsuarioExportador(contentStream, usuarioEditor, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.OWNER_BOLD),Style.getStyle(fontGroup,StyleEnum.OWNER));
                GeneradorFormularioFactory.crearTitulo(contentStream, "DOCUMENTOS INICIALES\nRECEPCIONADOS", Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.TITLE));

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
                .addStartX(marginStartX + thickness + 5f)
                .addStartY(maxY - 190 - 5f)
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
                .addStartX(marginStartX + thickness + 5f)
                .addStartY(maxY - 355 - 5f)
                .addWidth(180f)
                .addTextContent("DETALLES")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }


    private void crearDatosAfiliadoDefinicion(
            PDPageContentStream contentStream,
            DefinicionCreditoDTOPDF definicion) throws IOException {

        String gradoNombreAfiliado = definicion.getGradoNombreAfiliado();
        String carnetIdentidad     = definicion.getCarnetIdentidad();
        String numeroCarpeta       = definicion.getNumeroCarpeta();
        String fechaCreacion       = definicion.getFechaCreacion();
        String modalidadCredito    = definicion.getModalidadCredito();
        String tipoDeGarantia      = definicion.getTipoDeGarantia();
        String origenTramite       = definicion.getOrigenTramite();

        float width1 = 280f;
        float width2 = 220f;

        float initY = 215f;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Afiliado", fuenteInfoNegritaEspaciado, width1, 1f, 2f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText(gradoNombreAfiliado, fuenteInfoEspaciado, width1, 1f, 2f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Numero de carpeta", fuenteInfoNegritaEspaciado, width1, 1f, 2f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText(numeroCarpeta, fuenteInfoEspaciado, width1, 1f, 2f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Modalidad de credito", fuenteInfoNegritaEspaciado, width1, 1f, 2f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText(modalidadCredito, fuenteInfoEspaciado, width1, 1f, 2f, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Origen Tramite", fuenteInfoNegritaEspaciado, width1, 1f, 2f, margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText(origenTramite, fuenteInfoEspaciado, width1, 1f, 2f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText("Carnet de Identidad", fuenteInfoNegritaEspaciado, width2, 1f, 2f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(carnetIdentidad, fuenteInfoEspaciado, width2, 1f, 2f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText("Fecha de Creacion", fuenteInfoNegritaEspaciado, width2, 1f, 2f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(fechaCreacion, fuenteInfoEspaciado, width2, 1f, 2f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText("Tipo de garantia", fuenteInfoNegritaEspaciado, width2, 1f, 2f, margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(tipoDeGarantia, fuenteInfoEspaciado, width2, 1f, 2f, margin);

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
