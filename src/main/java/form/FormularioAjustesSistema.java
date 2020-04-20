package form;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import dto.AjustesSistemaDTO;
import dto.SucursalDTO;
import dto.UsuarioDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import utils.factories.GeneradorFormularioFactory;

public class FormularioAjustesSistema {

	public FormularioAjustesSistema(String ruta,
									AjustesSistemaDTO ajustes,
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
		GeneradorFormularioFactory.crearTitulo(contentStream,"AJUSTES DEL SISTEMA");

		GeneradorFormularioFactory.crearSubTituloAjustesSistema(contentStream);
		GeneradorFormularioFactory.crearDatosAjustesSistema(contentStream,ajustes);

		contentStream.close();

		doc.save(new File(ruta));
		doc.close();
	}

}
