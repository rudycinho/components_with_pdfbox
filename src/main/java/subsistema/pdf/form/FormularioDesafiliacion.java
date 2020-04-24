package form;

import dto.DesafiliadoDTO;
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

public class FormularioDesafiliacion {

	public FormularioDesafiliacion(String ruta,
								   DesafiliadoDTO desafiliado,
								   UsuarioDTO usuarioEditor,
								   SucursalDTO sucursal,
								   Date fechaExportacion) throws IOException {

		PDDocument doc = new PDDocument();
		PDPage page = new PDPage(new PDRectangle(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight()));

		doc.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);

		GeneradorFormularioFactory.setDimensiones(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight());
		GeneradorFormularioFactory.setMarginStartX(50f);

		GeneradorFormularioFactory.crearMargen(contentStream);
		GeneradorFormularioFactory.crearCabecera(contentStream,doc);
		GeneradorFormularioFactory.crearInfo(contentStream,sucursal);
		GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion);
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor);
		GeneradorFormularioFactory.crearTitulo(contentStream,"FORMULARIO C-01\nSOLICITUD DE DESAFILIACION");

		GeneradorFormularioFactory.crearSubTitulosDesafiliado(contentStream);
		GeneradorFormularioFactory.crearFechaRegistroDesafiliado(contentStream, desafiliado);
		GeneradorFormularioFactory.crearDatosDesafiliado(contentStream, desafiliado);
		GeneradorFormularioFactory.crearCausaDesafiliado(contentStream, desafiliado);
		GeneradorFormularioFactory.crearObservacionesDesafiliado(contentStream, desafiliado);

		GeneradorFormularioFactory.crearFirmaDesafiliado(contentStream);
		GeneradorFormularioFactory.crearCodigoDesafiliado(contentStream);
		GeneradorFormularioFactory.crearNotaDesafiliado(contentStream);
		GeneradorFormularioFactory.crearCampoRecepcionadoDesafiliado(contentStream);
		GeneradorFormularioFactory.crearLineaSeparacionDesafiliado(contentStream);

		contentStream.close();

		doc.save(new File(ruta));
		doc.close();
	}

}
