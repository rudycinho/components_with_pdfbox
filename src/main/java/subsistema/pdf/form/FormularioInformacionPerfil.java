package subsistema.pdf.form;

import org.apache.pdfbox.pdmodel.font.PDFont;
import subsistema.pdf.dto.PerfilDTOPDF;
import subsistema.pdf.dto.PermisoDTOPDF;
import subsistema.pdf.dto.SucursalDTOPDF;
import subsistema.pdf.dto.UsuarioDTOPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
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

public class FormularioInformacionPerfil {

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

    public FormularioInformacionPerfil(String ruta,
                                       PerfilDTOPDF perfil,
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
        GeneradorFormularioFactory.crearTitulo(contentStream,"INFORMACION DE PERMISOS\nPOR PERFIL", Model.MODEL_1);

        crearSubTitulosPerfil(contentStream);
        crearDatosPerfil(contentStream,perfil);
        crearSubTitulosPermisos(contentStream);

        crearPermisosTablaPerfil(
                contentStream,
                contentStreams,
                doc,
                perfil);

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

    private void crearSubTitulosPerfil(
            PDPageContentStream contentStream) throws IOException {

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + relativePositionX)
                .addStartY(maxY - 180 - 30 - relativePositionY)
                .addWidth(180f)
                .addTextContent("DATOS DEL PERFIL")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }

    private void crearDatosPerfil(
            PDPageContentStream contentStream,
            PerfilDTOPDF perfil) throws IOException {

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
        EasyComponentsFactory.getBoxStroke(10f, 5f, Color.BLACK, table).draw(contentStream);
    }

    private void crearSubTitulosPermisos(
            PDPageContentStream contentStream) throws IOException {

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + relativePositionX)
                .addStartY(maxY - 320 - relativePositionY)
                .addWidth(180f)
                .addTextContent("PERMISOS ASOCIADOS")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }

    private void crearPermisosTablaPerfil(
            PDPageContentStream contentStream,
            List<PDPageContentStream> contentStreams,
            PDDocument doc,
            PerfilDTOPDF perfil) throws IOException {

        List<List<String>> listOfList = new ArrayList<>();

        int i = 0;
        for (PermisoDTOPDF permiso : perfil.getPermisos()) {
            List<String> auxList = new ArrayList<>();
            auxList.add(String.format("%d", ++i));
            auxList.add(permiso.getNombre());
            auxList.add(permiso.getDescripcion());
            listOfList.add(auxList);
        }

        List<Float> widths  = Arrays.asList(30f, 170f, 300f);
        List<String> header = Arrays.asList("N*", "Nombre Permiso", "Descripcion Permiso");

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Column headerColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteNormalNegrita, Alignment.LEFT);

        Column anotherHeaderColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteNormalNegrita, Alignment.LEFT);

        List<Column> bodyColumns = new LinkedList<>();

        for (List<String> auxList : listOfList)
            bodyColumns.add(EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(auxList, widths, fuenteNormal, Alignment.LEFT));

        List<RecursiveTable> tables = EasyComponentsFactory.getHeaderTable(
                marginStartX + thickness, maxY - 345, maxY - 40, headerColumn, anotherHeaderColumn, bodyColumns, 45);

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
