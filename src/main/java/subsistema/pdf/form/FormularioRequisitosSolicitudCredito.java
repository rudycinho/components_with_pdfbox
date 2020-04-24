package subsistema.pdf.form;

import com.lowagie.text.DocumentException;
import subsistema.pdf.dto.SucursalDTO;
import subsistema.pdf.dto.UsuarioDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioRequisitosSolicitudCredito {
    public FormularioRequisitosSolicitudCredito(
            String ruta,
            String porcion,
            UsuarioDTO usuarioEditor,
            SucursalDTO sucursal,
            Date fechaExportacion) throws IOException, DocumentException {

        new ArchivoRequisitosPDFConDatosHTML(ruta,porcion);

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
            }
            contentStream.close();
        }

        doc.save(new File(ruta));
        doc.close();
    }

}
