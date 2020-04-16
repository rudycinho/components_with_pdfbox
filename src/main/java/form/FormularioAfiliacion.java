package form;

import dto.AfiliadoDTO;
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

public class FormularioAfiliacion {

	public FormularioAfiliacion(String ruta,
								AfiliadoDTO afiliado,
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
		GeneradorFormularioFactory.crearTitulo(contentStream,"FORMULARIO A-01\nSOLICITUD DE AFILIACION");

		GeneradorFormularioFactory.crearSubTitulosAfiliado(contentStream);
		GeneradorFormularioFactory.crearFechaRegistroAfiliado(contentStream, afiliado);
		GeneradorFormularioFactory.crearDatosAfiliado(contentStream, afiliado);
		GeneradorFormularioFactory.crearObservacionesAfiliado(contentStream, afiliado);

		GeneradorFormularioFactory.crearFirmaAfiliado(contentStream);
		GeneradorFormularioFactory.crearCodigoAfiliado(contentStream);
		GeneradorFormularioFactory.crearNotaAfiliado(contentStream);
		GeneradorFormularioFactory.crearCampoRecepcionadoAfiliado(contentStream);
		GeneradorFormularioFactory.crearLineaSeparacionAfiliado(contentStream);

		contentStream.close();

		doc.save(new File("/home/rudy/example.pdf"));
	}

}
