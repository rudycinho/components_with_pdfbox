package form;

import dto.AfiliadoDTO;
import dto.SucursalDTO;
import dto.UsuarioDTO;
import dto.EstadoAfiliacionDTO;
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

public class FormularioAfiliacion {

	public FormularioAfiliacion(String ruta,
                                AfiliadoDTO afiliado,
                                List<EstadoAfiliacionDTO> estados,
								UsuarioDTO usuarioEditor,
                                SucursalDTO sucursal,
                                Date fechaExportacion) throws IOException {
		PDDocument doc = new PDDocument();
		PDPage page1 = new PDPage(new PDRectangle(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight()));

		doc.addPage(page1);
		PDPageContentStream contentStream1 = new PDPageContentStream(doc, page1);

		GeneradorFormularioFactory.setDimensiones(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight());
		GeneradorFormularioFactory.setMarginStartX(50f);

		GeneradorFormularioFactory.crearMargen(contentStream1);
		GeneradorFormularioFactory.crearCabecera(contentStream1,doc);
		GeneradorFormularioFactory.crearInfo(contentStream1,sucursal);
		GeneradorFormularioFactory.crearFechaExportacion(contentStream1,fechaExportacion);
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream1,usuarioEditor);
		GeneradorFormularioFactory.crearTitulo(contentStream1,"FORMULARIO A-01\nSOLICITUD DE AFILIACION");

		GeneradorFormularioFactory.crearSubTitulosAfiliado(contentStream1);
		GeneradorFormularioFactory.crearFechaRegistroAfiliado(contentStream1, afiliado);
		GeneradorFormularioFactory.crearDatosAfiliado(contentStream1, afiliado);
		GeneradorFormularioFactory.crearObservacionesAfiliado(contentStream1, afiliado);

		GeneradorFormularioFactory.crearFirmaAfiliado(contentStream1);
		GeneradorFormularioFactory.crearCodigoAfiliado(contentStream1);
		GeneradorFormularioFactory.crearNotaAfiliado(contentStream1);
		GeneradorFormularioFactory.crearCampoRecepcionadoAfiliado(contentStream1);
		GeneradorFormularioFactory.crearLineaSeparacionAfiliado(contentStream1);

		contentStream1.close();

		if(!estados.isEmpty()){
			List<PDPageContentStream> contentStreams = new LinkedList<>();

			PDPage page2 = new PDPage(new PDRectangle(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight()));
			doc.addPage(page2);
			PDPageContentStream contentStream2 = new PDPageContentStream(doc, page2);
			GeneradorFormularioFactory.crearTablaDetallesReafiliado(
					contentStream2,
					contentStreams,
					doc,
					estados);

			for(PDPageContentStream contentStream : contentStreams){
				GeneradorFormularioFactory.crearMargen(contentStream);
				GeneradorFormularioFactory.crearInfo(contentStream,sucursal);
			}

			for(PDPageContentStream e : contentStreams)
				e.close();
		}

		doc.save(new File(ruta));
		doc.close();
	}

}
