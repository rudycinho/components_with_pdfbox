package form;

import dto.SucursalDTO;
import dto.UsuarioDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import utils.GeneradorFormularioFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioInformacionUsuario {

    public FormularioInformacionUsuario(String ruta,
                                        UsuarioDTO usuarioEditado,
                                        UsuarioDTO usuarioEditor,
                                        SucursalDTO sucursal,
                                        Date fechaExportacion) throws IOException {

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight()));

        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        GeneradorFormularioFactory.setDimensiones(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight());

        GeneradorFormularioFactory.crearMargen(contentStream);
        GeneradorFormularioFactory.crearCabecera(contentStream,doc);
        GeneradorFormularioFactory.crearInfo(contentStream,sucursal);
        GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion);
        GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor);
        GeneradorFormularioFactory.crearTitulo(contentStream,"DATOS DE REGISTRO\nDE USUARIO DEL SISTEMA");

        GeneradorFormularioFactory.crearSubTituloUsuario(contentStream);
        GeneradorFormularioFactory.crearFechaRegistroUsuario(contentStream, usuarioEditado);
        GeneradorFormularioFactory.crearDatosUsuario(contentStream,usuarioEditado);

        GeneradorFormularioFactory.crearObservacionesUsuario(contentStream, usuarioEditado);

        contentStream.close();

        doc.save(new File("/home/rudy/example.pdf"));
    }

}