package form;

import dto.DictamenDTO;
import dto.SucursalDTO;
import dto.UsuarioDTO;
import dto.PlanDePagosDTO;
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

public class FormularioPlanPagos {
    public FormularioPlanPagos(
            String ruta,
            DictamenDTO dictamen,
            PlanDePagosDTO planDePagos,
            UsuarioDTO usuarioEditor,
            SucursalDTO sucursal,
            Date fechaExportacion) throws IOException {

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(PDRectangle.LETTER.getHeight(),PDRectangle.LETTER.getWidth()));

        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        List<PDPageContentStream> contentStreams = new LinkedList<>();

        GeneradorFormularioFactory.setDimensiones(PDRectangle.LETTER.getHeight(),PDRectangle.LETTER.getWidth());
        GeneradorFormularioFactory.setMarginStartX(25f);

        GeneradorFormularioFactory.crearCabecera(contentStream,doc);
        GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion);
        GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor);
        GeneradorFormularioFactory.crearTitulo(contentStream,"PLAN DE PAGOS");

        GeneradorFormularioFactory.crearClienteSeccionPlanDePagos(contentStream, dictamen);
        GeneradorFormularioFactory.crearResumenDatosSeccionPlanDePagos(contentStream, dictamen);
        GeneradorFormularioFactory.crearDetallesTablePlanDePagos(contentStream,contentStreams,doc,planDePagos);

        int index=1;
        int total=contentStreams.size();
        for(PDPageContentStream contentStream1 : contentStreams){
            GeneradorFormularioFactory.crearMargen(contentStream1);
            GeneradorFormularioFactory.enumerarPaginas(contentStream1,index,total);
            index+=1;
        }

        for(PDPageContentStream e : contentStreams)
            e.close();

        doc.save(new File(ruta));
        doc.close();
    }



}
