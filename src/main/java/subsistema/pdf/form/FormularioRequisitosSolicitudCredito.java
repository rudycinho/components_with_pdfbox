package subsistema.pdf.form;

import com.lowagie.text.DocumentException;
import subsistema.pdf.dto.SucursalDTOPDF;
import subsistema.pdf.dto.UsuarioDTOPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.*;

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

        FontEnum fontEnum  = Models.MODEL_1.getFont();
        FontGroup fontGroup = FontGroup.getFontGroup(fontEnum,doc);

        int totalPages = doc.getNumberOfPages();
        PDPage page;
        PDPageContentStream contentStream;

        for(int i=0;i<totalPages;i++) {
            page = doc.getPage(i);
            contentStream = new PDPageContentStream(doc, page, true, true);
            GeneradorFormularioFactory.crearMargen(contentStream, Models.MODEL_1);
            if(i==0) {
                GeneradorFormularioFactory.crearCabecera(contentStream, doc, Models.MODEL_1,  Style.getStyle(fontGroup,StyleEnum.HEADER));
                GeneradorFormularioFactory.crearFechaExportacion(contentStream, fechaExportacion, Models.MODEL_1,  Style.getStyle(fontGroup,StyleEnum.OWNER));
                GeneradorFormularioFactory.crearUsuarioExportador(contentStream, usuarioEditor, Models.MODEL_1,  Style.getStyle(fontGroup,StyleEnum.OWNER_BOLD), Style.getStyle(fontGroup,StyleEnum.OWNER));
                GeneradorFormularioFactory.crearTitulo(contentStream, "REQUISITOS PARA\nSOLITICITUD DE CREDITO", Models.MODEL_1,  Style.getStyle(fontGroup,StyleEnum.TITLE));
            }
            contentStream.close();
        }

        doc.save(new File(ruta));
        doc.close();
    }

}
