package subsistema.pdf.form;

import subsistema.pdf.dto.DatosCarpetaAfiliadioDTOPDF;
import subsistema.pdf.dto.SucursalDTOPDF;
import subsistema.pdf.dto.UsuarioDTOPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.shapes.Line;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.lib.text.complex.ParagraphMultipleStyle;
import subsistema.pdf.lib.text.complex.Text;
import subsistema.pdf.lib.text.simple.MultipleParagraph;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.*;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioCarpetaDatosAfiliadio {

    private final float maxY         = Models.MODEL_1.getMaxY();
    private final float maxX         = Models.MODEL_1.getMaxX();
    private final float thickness    = Models.MODEL_1.getThickness();
    private final float marginStartX = Models.MODEL_1.getMarginStartX();
    private final float marginEndX   = Models.MODEL_1.getMarginEndX();
    private final FontEnum fontEnum  = Models.MODEL_1.getFont();

    private Style fuenteSubtitulo;
    private Style fuenteNormalNegrita;
    private Style fuenteNormalEspaciada;
    private Style fuenteNormalSemiEspaciada;

    private Style fuenteInfoEspaciado;
    private Style fuenteInfoNegritaEspaciado;


    public FormularioCarpetaDatosAfiliadio(
            String ruta,
            DatosCarpetaAfiliadioDTOPDF datos,
            UsuarioDTOPDF usuarioEditor,
            SucursalDTOPDF sucursal,
            Date fechaExportacion) throws IOException {

        PDDocument doc = new PDDocument();

        FontGroup fontGroup = FontGroup.getFontGroup(fontEnum,doc);

        fuenteSubtitulo                = Style.getStyle(fontGroup, StyleEnum.SUBTITLE_BOLD);
        fuenteNormalNegrita            = Style.getStyle(fontGroup, StyleEnum.DATA_BOLD);
        fuenteNormalSemiEspaciada      = Style.getStyle(fontGroup, StyleEnum.DATA_SEMI_SPACED);
        fuenteNormalEspaciada          = Style.getStyle(fontGroup, StyleEnum.DATA_NORMAL_SPACED);

        fuenteInfoEspaciado        = Style.getStyle(fontGroup, StyleEnum.MINIMAL_NORMAL_SPACED);
        fuenteInfoNegritaEspaciado = Style.getStyle(fontGroup, StyleEnum.MINIMAL_BOLD_SPACED);

        PDPage page = new PDPage(new PDRectangle(maxX,maxY));

        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        GeneradorFormularioFactory.crearMargen(contentStream, Model1.MODEL_1);
        GeneradorFormularioFactory.crearCabecera(contentStream,doc, Model1.MODEL_1);
        GeneradorFormularioFactory.crearInfo(contentStream,sucursal, Model1.MODEL_1);
        GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion, Model1.MODEL_1);
        GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor, Model1.MODEL_1);
        GeneradorFormularioFactory.crearTitulo(contentStream,"DATOS\nDE CARPETA DE PRESTAMO", Model1.MODEL_1);

        crearSubTitulosAfiliado(contentStream);
        crearInfoAfiliado(contentStream,datos);

        crearLineaSeparacionAfiliado(contentStream);

        crearDatosAfiliado(contentStream, datos);
        crearObservacionesAfiliado(contentStream, datos);

        contentStream.close();

        doc.save(new File(ruta));
        doc.close();
    }

    private void crearSubTitulosAfiliado(
            PDPageContentStream contentStream) throws IOException {

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + 5f)
                .addStartY(maxY - 265 - 5f)
                .addWidth(180f)
                .addTextContent("DATOS DEL AFILIADO")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }

    private void crearDatosAfiliado(
            PDPageContentStream contentStream,
            DatosCarpetaAfiliadioDTOPDF datos) throws IOException {

        float width1 = 200f;
        float width2 = 280f;
        float initY = 285;
        boolean margin = false;

        Cell c101 = EasyComponentsFactory.getSimpleCellFromText("GRADO: "                , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c102 = EasyComponentsFactory.getSimpleCellFromText("NOMBRES: "              , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c103 = EasyComponentsFactory.getSimpleCellFromText("APELLIDO PATERNO: "     , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c104 = EasyComponentsFactory.getSimpleCellFromText("APELLIDO MATERNO: "     , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c105 = EasyComponentsFactory.getSimpleCellFromText("CARNET DE IDENTIDAD: "  , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c106 = EasyComponentsFactory.getSimpleCellFromText("PROCEDENCIA DE CARNET: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c107 = EasyComponentsFactory.getSimpleCellFromText("FECHA DE NACIMIENTO: "  , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c108 = EasyComponentsFactory.getSimpleCellFromText("ESTADO POLICIAL: "      , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c109 = EasyComponentsFactory.getSimpleCellFromText("FECHA DE AFILIACION: "  , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c110 = EasyComponentsFactory.getSimpleCellFromText("TELEFONO: "             , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c111 = EasyComponentsFactory.getSimpleCellFromText("TELEFONO MOVIL: "       , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c112 = EasyComponentsFactory.getSimpleCellFromText("CORREO ELECTRONICO: "   , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c113 = EasyComponentsFactory.getSimpleCellFromText("UNIDAD POLICIAL: "      , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c114 = EasyComponentsFactory.getSimpleCellFromText("DEPARTAMENTO: "         , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c115 = EasyComponentsFactory.getSimpleCellFromText("TIPO DE AFILIACION: "   , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
        Cell c116 = EasyComponentsFactory.getSimpleCellFromText("ESTADO DE AFILIACION: " , fuenteNormalNegrita, width1, 0f, 2.5f, margin);

        Cell c201 = EasyComponentsFactory.getSimpleCellFromText(datos.getGrado()            , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c202 = EasyComponentsFactory.getSimpleCellFromText(datos.getNombres()          , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c203 = EasyComponentsFactory.getSimpleCellFromText(datos.getApellidoPaterno()  , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c204 = EasyComponentsFactory.getSimpleCellFromText(datos.getApellidoMaterno()  , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c205 = EasyComponentsFactory.getSimpleCellFromText(datos.getCarnetIdentidad()  , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c206 = EasyComponentsFactory.getSimpleCellFromText(datos.getProcedenciaCarnet(), fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c207 = EasyComponentsFactory.getSimpleCellFromText(datos.getFechaNacimiento()  , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c208 = EasyComponentsFactory.getSimpleCellFromText(datos.getEstadoPolicial()   , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c209 = EasyComponentsFactory.getSimpleCellFromText(datos.getEstadoAfiliacion() , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c210 = EasyComponentsFactory.getSimpleCellFromText(datos.getTelefono()         , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c211 = EasyComponentsFactory.getSimpleCellFromText(datos.getTelefonoMovil()    , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c212 = EasyComponentsFactory.getSimpleCellFromText(datos.getCorreoElectronico(), fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c213 = EasyComponentsFactory.getSimpleCellFromText(datos.getUnidadPolicial()   , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c214 = EasyComponentsFactory.getSimpleCellFromText(datos.getDepartamento()     , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c215 = EasyComponentsFactory.getSimpleCellFromText(datos.getTipoAfiliacion()   , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);
        Cell c216 = EasyComponentsFactory.getSimpleCellFromText(datos.getEstadoAfiliacion() , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);

        float auxY = maxY - initY  - 2.5f;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{c101, c201},
                new Cell[]{c102, c202},
                new Cell[]{c103, c203},
                new Cell[]{c104, c204},
                new Cell[]{c105, c205},
                new Cell[]{c106, c206},
                new Cell[]{c107, c207},
                new Cell[]{c108, c208},
                new Cell[]{c109, c209},
                new Cell[]{c110, c210},
                new Cell[]{c111, c211},
                new Cell[]{c112, c212},
                new Cell[]{c113, c213},
                new Cell[]{c114, c214},
                new Cell[]{c115, c215},
                new Cell[]{c116, c216}
        );
        EasyComponentsFactory.getBoxStroke(10f, 5f, Color.BLACK, table).draw(contentStream);
    }

    private void crearObservacionesAfiliado(
            PDPageContentStream contentStream,
            DatosCarpetaAfiliadioDTOPDF datos) throws IOException {

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + 2 * 5f)
                .addStartY(maxY - 545 - 2 * 5f)
                .addWidth(135f)
                .addTextContent("OBSERVACIONES")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + (2 * 5f))
                .addStartY(maxY - 560 - (2 * 5f))
                .addWidth(500f)
                .addTextContent(datos.getObservacion())
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalSemiEspaciada)
                .build()
                .draw(contentStream);
    }

    private void crearLineaSeparacionAfiliado(
            PDPageContentStream contentStream) throws IOException {

        float initY = 245f;
        float auxY = maxY - initY;

        new Line(marginStartX, auxY, maxX - marginEndX, auxY).setColor(Color.GRAY).setThickness(1).draw(contentStream);
    }

    private void crearInfoAfiliado(
            PDPageContentStream contentStream,
            DatosCarpetaAfiliadioDTOPDF datos) throws IOException {

        String prestamo        = datos.getPrestamoCodigo();
        String modalidad       = datos.getPrestamoModalidad();
        String garantia        = datos.getPrestamoTipoGarantia();
        String fechaInicio     = datos.getFechaInicio();
        String grado           = datos.getGrado();
        String nombreAfiliado  = datos.getNombres();
        String carnetIdentidad = datos.getCarnetIdentidad();

        float width = 500f;
        float initY = maxY-200;

        ParagraphMultipleStyle paragraph = ParagraphMultipleStyle.builder()
                .addStartX(marginStartX + thickness)
                .addStartY(initY)
                .addWidth(width)
                .addText(new Text("Prestamo",fuenteInfoEspaciado))
                .addText(new Text(prestamo + ",",fuenteInfoNegritaEspaciado))
                .addText(new Text("modalidad",fuenteInfoEspaciado))
                .addText(new Text(modalidad + ",",fuenteInfoNegritaEspaciado))
                .addText(new Text("con tipo de garantia",fuenteInfoEspaciado))
                .addText(new Text(garantia + ",",fuenteInfoNegritaEspaciado))
                .addText(new Text("fecha de inicio",fuenteInfoEspaciado))
                .addText(new Text(fechaInicio + ",",fuenteInfoNegritaEspaciado))
                .addText(new Text("en favor de",fuenteInfoEspaciado))
                .addText(new Text(grado,fuenteInfoNegritaEspaciado))
                .addText(new Text(nombreAfiliado + ",",fuenteInfoNegritaEspaciado))
                .addText(new Text("con numero de carnet de identidad",fuenteInfoEspaciado))
                .addText(new Text(carnetIdentidad + ".",fuenteInfoNegritaEspaciado))
                .build();

        paragraph.draw(contentStream);
    }
}
