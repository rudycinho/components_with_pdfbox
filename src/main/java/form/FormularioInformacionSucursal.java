package form;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import dto.SucursalDTO;
import dto.UsuarioDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import utils.GeneradorFormularioFactory;

public class FormularioInformacionSucursal {

	public FormularioInformacionSucursal(String ruta,
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
		GeneradorFormularioFactory.crearTitulo(contentStream,"INFORMACIÓN DE LA SUCURSAL");

		GeneradorFormularioFactory.crearSubTitulosSucursal(contentStream);
		GeneradorFormularioFactory.crearFechaRegistroSucursal(contentStream, sucursal);
		GeneradorFormularioFactory.crearDatosSucursal(contentStream,sucursal);
		GeneradorFormularioFactory.crearHorarioAtencionSucursal(contentStream, sucursal);
		GeneradorFormularioFactory.crearObservacionesSucursal(contentStream, sucursal);

		contentStream.close();

		doc.save(new File(ruta));
		doc.close();
	}


}
