package form.complex;

import dto.SucursalDTO;
import dto.UsuarioDTO;
import dto.complex.DefinicionCreditoDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import utils.GeneradorFormularioFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioDefinicionCredito {

    public FormularioDefinicionCredito(
            String ruta,
            DefinicionCreditoDTO definicion,
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
        GeneradorFormularioFactory.crearTitulo(contentStream,"DOCUMENTOS INICIALES\nRECEPCIONADOS");


        GeneradorFormularioFactory.crearDatosAfiliadoDefinicion(contentStream, definicion);
        GeneradorFormularioFactory.crearDetallesDefinicion(contentStream, definicion);

        contentStream.close();

        doc.save(new File(ruta));
        doc.close();
    }
}
