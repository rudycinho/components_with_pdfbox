package form;

import dto.PerfilDTO;
import dto.SucursalDTO;
import dto.UsuarioDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import utils.factories.GeneradorFormularioFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FormularioInformacionPerfil {

    public FormularioInformacionPerfil(String ruta,
                                       PerfilDTO perfil,
                                       UsuarioDTO usuarioEditor,
                                       SucursalDTO sucursal,
                                       Date fechaExportacion) throws IOException {

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight()));

        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        List<PDPageContentStream> contentStreams = new LinkedList<>();

        GeneradorFormularioFactory.setDimensiones(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight());
        GeneradorFormularioFactory.setMarginStartX(50f);

        GeneradorFormularioFactory.crearCabecera(contentStream,doc);
        GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion);
        GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor);
        GeneradorFormularioFactory.crearTitulo(contentStream,"INFORMACION DE PERMISOS\nPOR PERFIL");

        GeneradorFormularioFactory.crearSubTitulosPerfil(contentStream);
        GeneradorFormularioFactory.crearPermisosTablaPerfil(
                contentStream,
                contentStreams,
                doc,
                perfil);

        for(PDPageContentStream contentStream1 : contentStreams){
            GeneradorFormularioFactory.crearMargen(contentStream1);
            GeneradorFormularioFactory.crearInfo(contentStream1,sucursal);
        }

        for(PDPageContentStream e : contentStreams)
            e.close();

        contentStream.close();

        doc.save(new File(ruta));
        doc.close();
    }
}
