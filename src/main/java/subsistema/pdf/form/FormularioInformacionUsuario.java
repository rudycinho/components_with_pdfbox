package subsistema.pdf.form;

import subsistema.pdf.dto.SucursalDTOPDF;
import subsistema.pdf.dto.UsuarioDTOPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.lib.text.simple.MultipleParagraph;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.*;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioInformacionUsuario {

    private final float maxY         = Models.MODEL_1.getMaxY();
    private final float maxX         = Models.MODEL_1.getMaxX();
    private final float thickness    = Models.MODEL_1.getThickness();
    private final float marginStartX = Models.MODEL_1.getMarginStartX();
    private final float marginEndX   = Models.MODEL_1.getMarginEndX();
    private final FontEnum fontEnum  = Models.MODEL_1.getFont();

    private Style fuenteSubtitulo;
    private Style fuenteNormalNegrita;
    private Style fuenteNormalEspaciada;

    public FormularioInformacionUsuario(String ruta,
                                        UsuarioDTOPDF usuarioEditado,
                                        UsuarioDTOPDF usuarioEditor,
                                        SucursalDTOPDF sucursal,
                                        Date fechaExportacion) throws IOException {

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(maxX,maxY));

        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        FontGroup fontGroup = FontGroup.getFontGroup(fontEnum,doc);

        fuenteSubtitulo       = Style.getStyle(fontGroup, StyleEnum.SUBTITLE_BOLD);
        fuenteNormalNegrita   = Style.getStyle(fontGroup, StyleEnum.DATA_BOLD);
        fuenteNormalEspaciada = Style.getStyle(fontGroup, StyleEnum.DATA_NORMAL_SPACED);

        GeneradorFormularioFactory.crearMargen(contentStream, Models.MODEL_1);
        GeneradorFormularioFactory.crearCabecera(contentStream,doc, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.HEADER));
        GeneradorFormularioFactory.crearInfo(contentStream,sucursal, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.FOOTER));
        GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.OWNER));
        GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.OWNER_BOLD),Style.getStyle(fontGroup,StyleEnum.OWNER));
        GeneradorFormularioFactory.crearTitulo(contentStream,"DATOS DE REGISTRO\nDE USUARIO DEL SISTEMA", Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.TITLE));

        crearSubTituloUsuario(contentStream);
        crearFechaRegistroUsuario(contentStream, usuarioEditado);
        crearDatosUsuario(contentStream,usuarioEditado);

        crearObservacionesUsuario(contentStream, usuarioEditado);

        contentStream.close();

        doc.save(new File(ruta));
        doc.close();
    }

    private void crearSubTituloUsuario(
            PDPageContentStream contentStream) throws IOException {

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + 5f)
                .addStartY(maxY - 180 - 30 - 5f)
                .addWidth(180f)
                .addTextContent("DATOS DEL USUARIO")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }

    private void crearFechaRegistroUsuario(
            PDPageContentStream contentStream,
            UsuarioDTOPDF usuarioEditado) throws IOException {

        String fechaCreacion = usuarioEditado.getFechaRegistro();
        float width1 = 140f;
        float width2 = 80f;

        MultipleParagraph.builder()
                .addStartX(maxX - (marginEndX + thickness + width1 + width2))
                .addStartY(maxY - 250)
                .addWidth(width1)
                .addTextContent("FECHA DE REGISTRO: ")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX(maxX - (marginEndX + thickness + width2))
                .addStartY(maxY - 250)
                .addWidth(width2)
                .addTextContent(fechaCreacion)
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalEspaciada)
                .build()
                .draw(contentStream);
    }

    private void crearDatosUsuario(
            PDPageContentStream contentStream,
            UsuarioDTOPDF usuarioEditado) throws IOException {

        float width1 = 150f;
        float width2 = 350f - 10f;
        float initY = 280;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("NOMBRE Y APELLIDOS: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("N DE CI: "           , fuenteNormalNegrita, width1, 0f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("N TELEFONO: "        , fuenteNormalNegrita, width1, 0f, 5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("CORREO ELECTRONICO: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("CARGO: "             , fuenteNormalNegrita, width1, 0f, 5f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("AREA DE TRABAJO: "   , fuenteNormalNegrita, width1, 0f, 5f, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("SUCURSAL: "          , fuenteNormalNegrita, width1, 0f, 5f, margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText("NOMBRE DE USUARIO: " , fuenteNormalNegrita, width1, 0f, 5f, margin);
        Cell c19 = EasyComponentsFactory.getSimpleCellFromText("ESTADO: "            , fuenteNormalNegrita, width1, 0f, 5f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getNombreCompleto()   , fuenteNormalEspaciada, width2, 0f, 5f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getCarnetIdentidad()  , fuenteNormalEspaciada, width2, 0f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getTelefono()         , fuenteNormalEspaciada, width2, 0f, 5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getCorreoElectronico(), fuenteNormalEspaciada, width2, 0f, 5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getNombreCargo()      , fuenteNormalEspaciada, width2, 0f, 5f, margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getNombreArea()       , fuenteNormalEspaciada, width2, 0f, 5f, margin);
        Cell c27 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getNombreSucursal()   , fuenteNormalEspaciada, width2, 0f, 5f, margin);
        Cell c28 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getNombreUsuario()    , fuenteNormalEspaciada, width2, 0f, 5f, margin);
        Cell c29 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getEstado()           , fuenteNormalEspaciada, width2, 0f, 5f, margin);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{c11, c21},
                new Cell[]{c12, c22},
                new Cell[]{c13, c23},
                new Cell[]{c14, c24},
                new Cell[]{c15, c25},
                new Cell[]{c16, c26},
                new Cell[]{c17, c27},
                new Cell[]{c18, c28},
                new Cell[]{c19, c29}
        );
        EasyComponentsFactory.getBoxStroke(10f, 5f, Color.BLACK, table).draw(contentStream);
    }

    private void crearObservacionesUsuario(
            PDPageContentStream contentStream,
            UsuarioDTOPDF usuarioEditado) throws IOException {

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + 2 * 5f)
                .addStartY(maxY - 500 - 2 * 5f)
                .addWidth(130f)
                .addTextContent("OBSERVACIONES")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + (2 * 5f))
                .addStartY(maxY - 520 - (2 * 5f))
                .addWidth(500f)
                .addTextContent(usuarioEditado.getObservaciones())
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalEspaciada)
                .build()
                .draw(contentStream);

    }

}