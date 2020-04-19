package form;

import dto.DictamenDTO;
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

public class FormularioPrecalificadorPrestamos {

	public FormularioPrecalificadorPrestamos(
			String ruta,
			DictamenDTO dictamen,
			UsuarioDTO usuarioEditor,
			SucursalDTO sucursal,
			Date fechaExportacion) throws IOException {

		PDDocument doc = new PDDocument();
		PDPage page = new PDPage(new PDRectangle(PDRectangle.LETTER.getHeight(),PDRectangle.LETTER.getWidth()));

		doc.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);

		GeneradorFormularioFactory.setDimensiones(PDRectangle.LETTER.getHeight(),PDRectangle.LETTER.getWidth());
		GeneradorFormularioFactory.setMarginStartX(25);

		GeneradorFormularioFactory.crearMargen(contentStream);
		GeneradorFormularioFactory.crearCabecera(contentStream,doc);
		GeneradorFormularioFactory.crearInfo(contentStream,sucursal);
		GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion);
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor);
		GeneradorFormularioFactory.crearTitulo(contentStream,"PRECALIFICADOR DE PRESTAMOS");

		GeneradorFormularioFactory.crearPlanPagosSeccionDictamen(contentStream, dictamen);
		GeneradorFormularioFactory.crearPrecalicacionSeccionDictamen(contentStream, dictamen);
		GeneradorFormularioFactory.crearDatosSeccionDictamen(contentStream, dictamen);

		contentStream.close();

		doc.save(new File(ruta));
		doc.close();
	}

}
