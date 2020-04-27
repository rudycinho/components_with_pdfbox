package subsistema.pdf.form;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.dto.*;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.shapes.Line;
import subsistema.pdf.lib.tables.Column;
import subsistema.pdf.lib.tables.RecursiveTable;
import subsistema.pdf.lib.text.complex.ParagraphMultipleStyle;
import subsistema.pdf.lib.text.complex.Text;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class FormularioCarpetaComentarios {

    private final float maxY         = Models.MODEL_1.getMaxY();
    private final float maxX         = Models.MODEL_1.getMaxX();
    private final float thickness    = Models.MODEL_1.getThickness();
    private final float marginStartX = Models.MODEL_1.getMarginStartX();
    private final float marginEndX   = Models.MODEL_1.getMarginEndX();
    private final FontEnum fontEnum  = Models.MODEL_1.getFont();

    private Style fuenteNormalSemiEspaciada;
    private Style fuenteComentario;
    private Style fuenteComentarioNegrita;
    private Style fuenteInfoEspaciado;
    private Style fuenteInfoNegritaEspaciado;

    public FormularioCarpetaComentarios(
            String ruta,
            ComentariosDTOPDF comentarios,
            UsuarioDTOPDF usuarioEditor,
            SucursalDTOPDF sucursal,
            Date fechaExportacion) throws IOException {

        PDDocument doc = new PDDocument();

        FontGroup fontGroup = FontGroup.getFontGroup(fontEnum,doc);

        fuenteNormalSemiEspaciada  = Style.getStyle(fontGroup, StyleEnum.DATA_SEMI_SPACED);
        fuenteComentario           = Style.getStyle(fontGroup, StyleEnum.MINIMAL_NORMAL);
        fuenteComentarioNegrita    = Style.getStyle(fontGroup, StyleEnum.MINIMAL_BOLD);
        fuenteInfoEspaciado        = Style.getStyle(fontGroup, StyleEnum.MINIMAL_NORMAL_SPACED);
        fuenteInfoNegritaEspaciado = Style.getStyle(fontGroup, StyleEnum.MINIMAL_BOLD_SPACED);

        PDPage page = new PDPage(new PDRectangle(maxX,maxY));

        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        List<PDPageContentStream> contentStreams = new LinkedList<>();

        GeneradorFormularioFactory.crearCabecera(contentStream,doc, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.HEADER));
        GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.OWNER));
        GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.OWNER_BOLD),Style.getStyle(fontGroup,StyleEnum.OWNER));
        GeneradorFormularioFactory.crearTitulo(contentStream,"COMENTARIOS\nDE CARPETA DE CREDITO", Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.TITLE));

        crearInfoAfiliado(contentStream,comentarios);
        crearLineaSeparacionDesafiliado(contentStream);

        crearTablaComentarios(
                contentStream,
                contentStreams,
                doc,
                comentarios);

        for(PDPageContentStream contentStream1 : contentStreams){
            GeneradorFormularioFactory.crearMargen(contentStream1, Models.MODEL_1);
            GeneradorFormularioFactory.crearInfo(contentStream1,sucursal, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.FOOTER));
        }

        for(PDPageContentStream e : contentStreams)
            e.close();

        contentStream.close();

        doc.save(new File(ruta));
        doc.close();
    }

    private void crearInfoAfiliado(
            PDPageContentStream contentStream,
            ComentariosDTOPDF comentarios) throws IOException {

        String prestamo        = comentarios.getPrestamoCodigo();
        String modalidad       = comentarios.getPrestamoModalidad();
        String garantia        = comentarios.getPrestamoTipoGarantia();
        String fechaInicio     = comentarios.getFechaInicioPrestamo();
        String grado           = comentarios.getGradoAfiliado();
        String nombreAfiliado  = comentarios.getNombreAfiliado();
        String carnetIdentidad = comentarios.getCarnetAfiliado();

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

    private void crearTablaComentarios(
            PDPageContentStream contentStream,
            List<PDPageContentStream> contentStreams,
            PDDocument doc,
            ComentariosDTOPDF comentarios) throws IOException {

        List<Column> bodyColumns = new LinkedList<>();
        Column column;

        for (ComentarioDTOPDF comentario : comentarios.getComentarios()) {
            column = EasyComponentsFactory.getSimpleColumn(
                    EasyComponentsFactory.getSimpleCell(0f, 10f, false,
                            EasyComponentsFactory.getSimpleTable(0, 0,
                                    EasyComponentsFactory.getSimpleColumn(
                                            EasyComponentsFactory.getSimpleCellFromText("Comentario realizado por: ",fuenteComentarioNegrita,160f,5f,2.5f,Alignment.CENTER,false),
                                            EasyComponentsFactory.getSimpleCell(5f,2.5f,false,false,Color.BLACK,Color.WHITE,
                                                    ParagraphMultipleStyle.builder()
                                                            .addStartX(marginStartX + thickness)
                                                            .addStartY(0f)
                                                            .addWidth(340f-20f)
                                                            .addText(new Text(comentario.getNombreUsuario(),fuenteComentario))
                                                            .addText(new Text(
                                                                    String.format("( %s - %s )",
                                                                        comentario.getNombreArea(),
                                                                        comentario.getNombreCargo()
                                                                    ),
                                                                    fuenteComentarioNegrita))
                                                            .build()
                                                    )
                                    ),
                                    EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
                                            Arrays.asList(
                                                    "Fecha :",
                                                    comentario.getFechaComentario(),
                                                    comentario.getHoraComentario(),
                                                    ""
                                            ),
                                            Arrays.asList(60f,80f,80f,280f),
                                            Arrays.asList(
                                                    fuenteComentarioNegrita,
                                                    fuenteComentario,
                                                    fuenteComentario,
                                                    fuenteComentario
                                            ),  Alignment.LEFT,
                                            5f, 2.5f,
                                            false,false,Color.BLACK,Color.WHITE),
                                    EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
                                            Arrays.asList(
                                                    comentario.getContenidoComentario()
                                            ),
                                            Arrays.asList(500f),
                                            Arrays.asList(fuenteNormalSemiEspaciada),  Alignment.LEFT,
                                            5f, 5f,
                                            false,false,Color.BLACK,Color.WHITE),
                                    EasyComponentsFactory.getSimpleColumn(
                                            EasyComponentsFactory.getSimpleCellRectangle(500f,0f,0f,false)
                                    )
                            )
                    )
            );
            bodyColumns.add(column);
        }

        List<RecursiveTable> tables = EasyComponentsFactory.getLargeTable(
                marginStartX + thickness, maxY - 255, maxY - 40,  bodyColumns, 45);

        Iterator<RecursiveTable> it = tables.iterator();
        it.next().draw(contentStream);


        while (it.hasNext()) {
            PDPage page = new PDPage(new PDRectangle(PDRectangle.LETTER.getWidth(), PDRectangle.LETTER.getHeight()));
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            it.next().draw(content);
            contentStreams.add(content);
        }
        contentStreams.add(0, contentStream);
    }

    private void crearLineaSeparacionDesafiliado(
            PDPageContentStream contentStream) throws IOException {

        float initY = 245f;
        float auxY = maxY - initY;

        new Line(marginStartX, auxY, maxX - marginEndX, auxY).setColor(Color.GRAY).setThickness(1).draw(contentStream);
    }
}
