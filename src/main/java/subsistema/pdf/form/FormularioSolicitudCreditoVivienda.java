package form;

import dto.SucursalDTO;
import dto.UsuarioDTO;
import dto.SolicitudCreditoViviendaDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import utils.factories.GeneradorFormularioFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioSolicitudCreditoVivienda {
	public FormularioSolicitudCreditoVivienda(
			String ruta,
			SolicitudCreditoViviendaDTO solicitud,
			UsuarioDTO usuarioEditor,
			SucursalDTO sucursal,
			Date fechaExportacion) throws IOException {


		float heigthAux= PDRectangle.LEGAL.getHeight();

		float width = PDRectangle.LEGAL.getWidth();
		float height= heigthAux - heigthAux/14;

		PDDocument doc = new PDDocument();

		PDPage page1 = new PDPage(new PDRectangle(width,height));
		PDPage page2 = new PDPage(new PDRectangle(width,height));

		doc.addPage(page1);
		doc.addPage(page2);

		PDPageContentStream contentStream1 = new PDPageContentStream(doc, page1);
		PDPageContentStream contentStream2 = new PDPageContentStream(doc, page2);

		GeneradorFormularioFactory.setDimensiones(width,height);
		GeneradorFormularioFactory.setMarginStartX(50f);

		GeneradorFormularioFactory.crearMargen(contentStream1);
		GeneradorFormularioFactory.crearCabecera(contentStream1, doc);
		GeneradorFormularioFactory.crearInfo(contentStream1, sucursal);
		GeneradorFormularioFactory.crearFechaExportacion(contentStream1, fechaExportacion);
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream1, usuarioEditor);
		GeneradorFormularioFactory.crearTitulo(contentStream1, "FORMULARIO Nro. B-01\nSOLICITUD DE CREDITO PARA VIVIENDA");

		GeneradorFormularioFactory.crearMargen(contentStream2);
		GeneradorFormularioFactory.crearInfo(contentStream2, sucursal);




		
		//GeneradorFormularioFactory.crearCuadroCarpeta(solicitud,contentStream1);
		
		GeneradorFormularioFactory.crearTipoCredito(solicitud, contentStream1);
		GeneradorFormularioFactory.crearTipoGarantia(solicitud, contentStream1);
		GeneradorFormularioFactory.crearMontoPrestamoSolicitado(solicitud, contentStream1);
		GeneradorFormularioFactory.crearDatosSolicitado(solicitud, contentStream1);
	/*
		GeneradorFormularioFactory.crearMargen(contentStream2);
		GeneradorFormularioFactory.crearDatosGarantesPersonales(solicitudDTO, contentStream2);
		GeneradorFormularioFactory.crearCroquis(contentStream2,doc);
		GeneradorFormularioFactory.crearInfo(contentStream2,sucursalDTO);
		*/
		contentStream1.close();
		contentStream2.close();

        doc.save(new File("/home/rudy/file.pdf"));
	}
}
