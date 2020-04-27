package subsistema.pdf.form;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import subsistema.pdf.dto.SucursalDTOPDF;
import subsistema.pdf.dto.UsuarioDTOPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.lib.text.simple.MultipleParagraph;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.*;

public class FormularioInformacionSucursal {

	private final float maxY         = Models.MODEL_1.getMaxY();
	private final float maxX         = Models.MODEL_1.getMaxX();
	private final float thickness    = Models.MODEL_1.getThickness();
	private final float marginStartX = Models.MODEL_1.getMarginStartX();
	private final float marginEndX   = Models.MODEL_1.getMarginEndX();
	private final FontEnum fontEnum  = Models.MODEL_1.getFont();

	private Style fuenteSubtitulo;
	private Style fuenteNormalNegrita;
	private Style fuenteNormalEspaciada;

	public FormularioInformacionSucursal(String ruta,
										 UsuarioDTOPDF usuarioEditor,
										 SucursalDTOPDF sucursal,
										 Date fechaExportacion) throws IOException {
		PDDocument doc = new PDDocument();

		FontGroup fontGroup = FontGroup.getFontGroup(fontEnum,doc);

		fuenteSubtitulo       = Style.getStyle(fontGroup, StyleEnum.SUBTITLE_BOLD);
		fuenteNormalNegrita   = Style.getStyle(fontGroup, StyleEnum.DATA_BOLD);
		fuenteNormalEspaciada = Style.getStyle(fontGroup, StyleEnum.DATA_NORMAL_SPACED);

		PDPage page = new PDPage(new PDRectangle(maxX,maxY));

		doc.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);

		GeneradorFormularioFactory.crearMargen(contentStream, Models.MODEL_1);
		GeneradorFormularioFactory.crearCabecera(contentStream,doc, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.HEADER));
		GeneradorFormularioFactory.crearInfo(contentStream,sucursal, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.FOOTER));
		GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.OWNER));
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.OWNER_BOLD),Style.getStyle(fontGroup,StyleEnum.OWNER));
		GeneradorFormularioFactory.crearTitulo(contentStream,"INFORMACIÓN DE LA SUCURSAL", Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.TITLE));

		crearSubTitulosSucursal(contentStream);
		crearFechaRegistroSucursal(contentStream, sucursal);
		crearDatosSucursal(contentStream,sucursal);
		crearHorarioAtencionSucursal(contentStream, sucursal);
		crearObservacionesSucursal(contentStream, sucursal);

		contentStream.close();

		doc.save(new File(ruta));
		doc.close();
	}


	private void crearSubTitulosSucursal(
			PDPageContentStream contentStream) throws IOException {

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + 5f)
				.addStartY(maxY - 180 - 30 - 5f)
				.addWidth(180f)
				.addTextContent("DATOS DE LA SUCURSAL")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteSubtitulo)
				.build()
				.draw(contentStream);
	}

	private void crearFechaRegistroSucursal(
			PDPageContentStream contentStream,
			SucursalDTOPDF sucursal) throws IOException {

		String fechaCreacion = sucursal.getFechaRegistro();
		float width1 = 140f;
		float width2 = 80f;

		MultipleParagraph.builder()
				.addStartX(maxX - (marginEndX + thickness + width1 + width2))
				.addStartY(maxY - 250)
				.addWidth(width1)
				.addTextContent("FECHA DE REGISTRO: ")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalNegrita)
				.build()
				.draw(contentStream);

		MultipleParagraph.builder()
				.addStartX(maxX - (marginEndX + thickness + width2))
				.addStartY(maxY - 250)
				.addWidth(width2)
				.addTextContent(fechaCreacion)
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalEspaciada)
				.build()
				.draw(contentStream);
	}

	private void crearDatosSucursal(
			PDPageContentStream contentStream,
			SucursalDTOPDF sucursal) throws IOException {

		float width1 = 125f;
		float width2 = 375f - 10f;
		float initY = 280;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("NOMBRE: "      , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("DEPARTAMENTO: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c13 = EasyComponentsFactory.getSimpleCellFromText("ZONA: "        , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("FAX: "         , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("DIRECCION: "   , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c16 = EasyComponentsFactory.getSimpleCellFromText("TELEFONO: "    , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c17 = EasyComponentsFactory.getSimpleCellFromText("ESTADO: "      , fuenteNormalNegrita, width1, 0f, 5f, margin);

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getNombre()      , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getDepartamento(), fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getZona()        , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getFax()         , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getDireccion()   , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c26 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getTelefono()    , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c27 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getEstado()      , fuenteNormalEspaciada, width2, 0f, 5f, margin);

		float auxY = maxY - initY;

		SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
				marginStartX + thickness, auxY,
				new Cell[]{c11, c21},
				new Cell[]{c12, c22},
				new Cell[]{c13, c23},
				new Cell[]{c14, c24},
				new Cell[]{c15, c25},
				new Cell[]{c16, c26},
				new Cell[]{c17, c27}
		);
		EasyComponentsFactory.getBoxStroke(10f, 5f, Color.BLACK, table).draw(contentStream);
	}

	private void crearHorarioAtencionSucursal(
			PDPageContentStream contentStream,
			SucursalDTOPDF sucursal) throws IOException {

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + 2 * 5f)
				.addStartY(maxY - 450 - 2 * 5f)
				.addWidth(130f)
				.addTextContent("HORARIO DE ATENCION")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalNegrita)
				.build()
				.draw(contentStream);

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + (2 * 5f))
				.addStartY(maxY - 470 - (2 * 5f))
				.addWidth(500f)
				.addTextContent(sucursal.getHorarioAtencion())
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalEspaciada)
				.build()
				.draw(contentStream);
	}

	private void crearObservacionesSucursal(
			PDPageContentStream contentStream,
			SucursalDTOPDF sucursal) throws IOException {

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + 2 * 5f)
				.addStartY(maxY - 500 - 2 * 5f)
				.addWidth(130f)
				.addTextContent("OBSERVACIONES")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalNegrita)
				.build()
				.draw(contentStream);

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + (2 * 5f))
				.addStartY(maxY - 520 - (2 * 5f))
				.addWidth(500f)
				.addTextContent(sucursal.getObservaciones())
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalEspaciada)
				.build()
				.draw(contentStream);
	}
}
