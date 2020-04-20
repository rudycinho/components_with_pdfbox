package form.complex;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import dto.SucursalDTO;
import dto.UsuarioDTO;
import dto.complex.SolicitudCreditoViviendaDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import utils.factories.GeneradorFormularioFactory;

public class FormularioSolicitudCreditoVivienda {
	public FormularioSolicitudCreditoVivienda(
			String ruta,
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
		
        UsuarioDTO usuarioEditorDTO = UsuarioDTO.getFake();
		SucursalDTO   sucursalDTO   = SucursalDTO.getFake();
		
		SolicitudCreditoViviendaDTO solicitudDTO = SolicitudCreditoViviendaDTO.getFake();
		
		GeneradorFormularioFactory.setDimensiones(width,height);

		/*
		GeneradorFormularioFactory.crearMargen(contentStream1);
		GeneradorFormularioFactory.crearCabecera(contentStream1,doc);
		GeneradorFormularioFactory.crearInfo(contentStream1,sucursalDTO);
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream1,usuarioEditorDTO);
		GeneradorFormularioFactory.crearFechaExportacion(contentStream1,fechaExportacion);
		
		GeneradorFormularioFactory.crearTitulo(contentStream1,"FORMULARIO Nro. B-01\nSOLICITUD DE CREDITO PARA VIVIENDA");
		
		GeneradorFormularioFactory.crearCuadroCarpeta(solicitudDTO,contentStream1);
		
		GeneradorFormularioFactory.crearTipoCredito(solicitudDTO, contentStream1);
		GeneradorFormularioFactory.crearTipoGarantia(solicitudDTO, contentStream1);
		GeneradorFormularioFactory.crearMontoPrestamoSolicitado(solicitudDTO, contentStream1);
		GeneradorFormularioFactory.crearDatosSolicitado(solicitudDTO, contentStream1);
		
		GeneradorFormularioFactory.crearMargen(contentStream2);
		GeneradorFormularioFactory.crearDatosGarantesPersonales(solicitudDTO, contentStream2);
		GeneradorFormularioFactory.crearCroquis(contentStream2,doc);
		GeneradorFormularioFactory.crearInfo(contentStream2,sucursalDTO);
		*/
		contentStream1.close();
		contentStream2.close();

        doc.save(new File(ruta));
	}
}
