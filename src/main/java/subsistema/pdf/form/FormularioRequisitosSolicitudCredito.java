package subsistema.pdf.form;

import com.lowagie.text.DocumentException;
import subsistema.pdf.dto.SucursalDTOPDF;
import subsistema.pdf.dto.UsuarioDTOPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.Model;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioRequisitosSolicitudCredito {



    public FormularioRequisitosSolicitudCredito(
            String ruta,
            String porcion,
            UsuarioDTOPDF usuarioEditor,
            SucursalDTOPDF sucursal,
            Date fechaExportacion) throws IOException, DocumentException {

        new ArchivoRequisitosPDFConDatosHTML(ruta,porcion);

        File file      = new File(ruta);
        PDDocument doc = PDDocument.load(file);
        int totalPages = doc.getNumberOfPages();
        PDPage page;
        PDPageContentStream contentStream;

        for(int i=0;i<totalPages;i++) {
            page = doc.getPage(i);
            contentStream = new PDPageContentStream(doc, page, true, true);
            GeneradorFormularioFactory.crearInfo(contentStream, sucursal, Model.MODEL_1);
            GeneradorFormularioFactory.crearMargen(contentStream, Model.MODEL_1);
            if(i==0) {
                GeneradorFormularioFactory.crearCabecera(contentStream, doc, Model.MODEL_1);
                GeneradorFormularioFactory.crearFechaExportacion(contentStream, fechaExportacion, Model.MODEL_1);
                GeneradorFormularioFactory.crearUsuarioExportador(contentStream, usuarioEditor, Model.MODEL_1);
                GeneradorFormularioFactory.crearTitulo(contentStream, "REQUISITOS PARA\nSOLITICITUD DE CREDITO", Model.MODEL_1);
            }
            contentStream.close();
        }

        doc.save(new File(ruta));
        doc.close();
    }

}
