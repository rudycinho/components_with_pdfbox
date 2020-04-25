package subsistema.pdf.form;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import subsistema.pdf.dto.*;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.Column;
import subsistema.pdf.lib.tables.RecursiveTable;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.lib.text.MultipleParagraph;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.Model;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class FormularioCarpetaComentarios {

    private final float maxY         = Model.MODEL_1.getMaxY();
    private final float maxX         = Model.MODEL_1.getMaxX();
    private final float thickness    = Model.MODEL_1.getThickness();
    private final float marginStartX = Model.MODEL_1.getMarginStartX();
    private final float marginEndX   = Model.MODEL_1.getMarginEndX();
    private final float relativePositionX = 5f;
    private final float relativePositionY = 5f;

    private final PDFont fuenteBasica        = Model.MODEL_1.getFuenteBasica();
    private final PDFont fuenteBasicaNegrita = Model.MODEL_1.getFuenteBasicaNegrita();

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

    private final Style fuenteNormalEspaciada = Style.builder()
            .addTextFont(fuenteBasica)
            .addFontSize(10.5f)
            .addTextColor(Color.BLACK)
            .addLeading(1f)
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


    public FormularioCarpetaComentarios(
            String ruta,
            ComentariosDTOPDF comentarios,
            UsuarioDTOPDF usuarioEditor,
            SucursalDTOPDF sucursal,
            Date fechaExportacion) throws IOException {

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(maxX,maxY));

        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        List<PDPageContentStream> contentStreams = new LinkedList<>();

        GeneradorFormularioFactory.crearCabecera(contentStream,doc, Model.MODEL_1);
        GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion, Model.MODEL_1);
        GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor, Model.MODEL_1);
        GeneradorFormularioFactory.crearTitulo(contentStream,"COMENTARIOS\nDE CARPETA DE CREDITO", Model.MODEL_1);

        crearInfoAfiliado(contentStream,comentarios);

        crearTablaObservaciones(
                contentStream,
                contentStreams,
                doc,
                comentarios);

        for(PDPageContentStream contentStream1 : contentStreams){
            GeneradorFormularioFactory.crearMargen(contentStream1, Model.MODEL_1);
            GeneradorFormularioFactory.crearInfo(contentStream1,sucursal, Model.MODEL_1);
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
        /*
        float width1 = 150f;
        float width2 = 350f - 10f;
        float initY = 235;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("NOMBRE: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("ROL: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("ESTADO: ", fuenteNormalNegrita, width1, 0f, 5f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(perfil.getNombre(), fuenteNormalEspaciada, width2, 0f, 5f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(perfil.getRol(), fuenteNormalEspaciada, width2, 0f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(perfil.getEstado(), fuenteNormalEspaciada, width2, 0f, 5f, margin);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{c11, c21},
                new Cell[]{c12, c22},
                new Cell[]{c13, c23}
        );
        EasyComponentsFactory.getBoxStroke(10f, 5f, Color.BLACK, table).draw(contentStream);*/
    }

    private void crearTablaObservaciones(
            PDPageContentStream contentStream,
            List<PDPageContentStream> contentStreams,
            PDDocument doc,
            ComentariosDTOPDF comentarios) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(9.5f)
                .addTextColor(Color.BLACK)
                .addLeading(.85f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9.5f)
                .addTextColor(Color.BLACK)
                .addLeading(.85f)
                .build();

        List<Column> bodyColumns = new LinkedList<>();
        Column column;

        for (ComentarioDTOPDF comentario : comentarios.getComentarios()) {
            column = EasyComponentsFactory.getSimpleColumn(
                    EasyComponentsFactory.getSimpleCell(0f, 7.5f, false,
                            EasyComponentsFactory.getSimpleTable(0, 0,
                                    EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
                                            Arrays.asList(
                                                    "Comentario realizado por: ",
                                                    comentario.getNombreUsuario(),
                                                    String.format("( %s - %s )",
                                                            comentario.getNombreArea(),
                                                            comentario.getNombreCargo()
                                                            )
                                            ),
                                            Arrays.asList(160f,140f,200f),
                                            Arrays.asList(
                                                    fuenteNormalNegrita,
                                                    fuenteNormal,
                                                    fuenteNormalNegrita),
                                            Alignment.LEFT,
                                            5f, 2.5f,
                                            false,false,Color.BLACK,Color.WHITE),
                                    EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
                                            Arrays.asList(
                                                    "Fecha :",
                                                    comentario.getFechaComentario(),
                                                    comentario.getHoraComentario(),
                                                    ""
                                            ),
                                            Arrays.asList(60f,80f,80f,280f),
                                            Arrays.asList(
                                                    fuenteNormalNegrita,
                                                    fuenteNormal,
                                                    fuenteNormal,
                                                    fuenteNormal
                                            ),  Alignment.LEFT,
                                            5f, 2.5f,
                                            false,false,Color.BLACK,Color.WHITE),
                                    EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
                                            Arrays.asList(
                                                    comentario.getContenidoComentario()
                                            ),
                                            Arrays.asList(500f),
                                            Arrays.asList(fuenteNormal),  Alignment.LEFT,
                                            5f, 5f,
                                            false,false,Color.BLACK,Color.WHITE)
                            )
                    )
            );
            bodyColumns.add(column);
        }

        List<RecursiveTable> tables = EasyComponentsFactory.getLargeTable(
                marginStartX + thickness, maxY - 245, maxY - 40,  bodyColumns, 45);

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
}
