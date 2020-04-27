package subsistema.pdf.form;

import subsistema.pdf.dto.DesafiliadoDTOPDF;
import subsistema.pdf.dto.SucursalDTOPDF;
import subsistema.pdf.dto.UsuarioDTOPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.shapes.Line;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.lib.text.simple.MultipleParagraph;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.*;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioDesafiliacion {

	private final float maxY         = Models.MODEL_1.getMaxY();
	private final float maxX         = Models.MODEL_1.getMaxX();
	private final float thickness    = Models.MODEL_1.getThickness();
	private final float marginStartX = Models.MODEL_1.getMarginStartX();
	private final float marginEndX   = Models.MODEL_1.getMarginEndX();
	private final FontEnum fontEnum  = Models.MODEL_1.getFont();

	private Style fuenteSubtitulo;
	private Style fuenteNormalNegrita;
	private Style fuenteNormal;
	private Style fuenteNormalEspaciada;
	private Style fuenteDiminuta;
	private Style fuenteNormalSemiEspaciada;

	public FormularioDesafiliacion(String ruta,
								   DesafiliadoDTOPDF desafiliado,
								   UsuarioDTOPDF usuarioEditor,
								   SucursalDTOPDF sucursal,
								   Date fechaExportacion) throws IOException {

		PDDocument doc = new PDDocument();

		FontGroup fontGroup = FontGroup.getFontGroup(fontEnum,doc);

		fuenteSubtitulo                = Style.getStyle(fontGroup, StyleEnum.SUBTITLE_BOLD);
		fuenteNormal                   = Style.getStyle(fontGroup, StyleEnum.DATA_NORMAL);
		fuenteNormalNegrita            = Style.getStyle(fontGroup, StyleEnum.DATA_BOLD);
		fuenteNormalSemiEspaciada      = Style.getStyle(fontGroup, StyleEnum.DATA_SEMI_SPACED);
		fuenteNormalEspaciada          = Style.getStyle(fontGroup, StyleEnum.DATA_NORMAL_SPACED);
		fuenteDiminuta                 = Style.getStyle(fontGroup, StyleEnum.SMALL_NORMAL);

		PDPage page = new PDPage(new PDRectangle(maxX,maxY));

		doc.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);

		GeneradorFormularioFactory.crearMargen(contentStream, Models.MODEL_1);
		GeneradorFormularioFactory.crearCabecera(contentStream,doc, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.HEADER));
		GeneradorFormularioFactory.crearInfo(contentStream,sucursal, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.FOOTER));
		GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion, Models.MODEL_1,Style.getStyle(fontGroup,StyleEnum.OWNER));
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor, Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.OWNER_BOLD),Style.getStyle(fontGroup,StyleEnum.OWNER));
		GeneradorFormularioFactory.crearTitulo(contentStream,"FORMULARIO C-01\nSOLICITUD DE DESAFILIACION", Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.TITLE));

		crearSubTitulosDesafiliado(contentStream);
		crearFechaRegistroDesafiliado(contentStream, desafiliado);
		crearDatosDesafiliado(contentStream, desafiliado);
		crearCausaDesafiliado(contentStream, desafiliado);
		crearObservacionesDesafiliado(contentStream, desafiliado);

		crearFirmaDesafiliado(contentStream);
		crearCodigoDesafiliado(contentStream);
		crearNotaDesafiliado(contentStream);
		crearCampoRecepcionadoDesafiliado(contentStream);
		crearLineaSeparacionDesafiliado(contentStream);

		contentStream.close();

		doc.save(new File(ruta));
		doc.close();
	}

	private void crearSubTitulosDesafiliado(
			PDPageContentStream contentStream) throws IOException {

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + 5f)
				.addStartY(maxY - 190 - 5f)
				.addWidth(180f)
				.addTextContent("DATOS DEL SOLICITANTE")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteSubtitulo)
				.build()
				.draw(contentStream);
	}

	private void crearFechaRegistroDesafiliado(
			PDPageContentStream contentStream,
			DesafiliadoDTOPDF desafiliado) throws IOException {

		String fechaCreacion = desafiliado.getFechaSolicitud();
		float width1 = 140f;
		float width2 = 80f;

		MultipleParagraph.builder()
				.addStartX(maxX - (marginEndX + thickness + width1 + width2))
				.addStartY(maxY - 210)
				.addWidth(width1)
				.addTextContent("FECHA DE REGISTRO: ")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalNegrita)
				.build()
				.draw(contentStream);

		MultipleParagraph.builder()
				.addStartX(maxX - (marginEndX + thickness + width2))
				.addStartY(maxY - 210)
				.addWidth(width2)
				.addTextContent(fechaCreacion)
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormal)
				.build()
				.draw(contentStream);
	}

	private void crearDatosDesafiliado(
			PDPageContentStream contentStream,
			DesafiliadoDTOPDF desafiliado) throws IOException {

		float width1 = 150f;
		float width2 = 130f;
		float width3 = 130f;
		float width4 = 80f;
		float initY = 220;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("GRADO: "              , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("NOMBRES Y APELLIDOS: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c13 = EasyComponentsFactory.getSimpleCellFromText("N* DE CI: "           , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("UNIDAD POLICIAL: "    , fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("DEPARTAMENTO: "       , fuenteNormalNegrita, width1, 0f, 2.5f, margin);

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getGrado()          , fuenteNormalSemiEspaciada, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getNombreCompleto() , fuenteNormalSemiEspaciada, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getCarnetIdentidad(), fuenteNormalSemiEspaciada, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getUnidadPolicial() , fuenteNormalSemiEspaciada, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getDepartamento()   , fuenteNormalSemiEspaciada, width2 + width3 + width4, 0f, 2.5f, margin);

		float auxY = maxY - initY  - 2.5f;

		SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
				marginStartX + thickness, auxY,
				new Cell[]{c11, c21},
				new Cell[]{c12, c22},
				new Cell[]{c13, c23},
				new Cell[]{c14, c24},
				new Cell[]{c15, c25}

		);
		EasyComponentsFactory.getBoxStroke(10f, 5f, Color.BLACK, table).draw(contentStream);
	}

	private void crearCausaDesafiliado(
			PDPageContentStream contentStream,
			DesafiliadoDTOPDF desafiliado) throws IOException {

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + 2 * 5f)
				.addStartY(maxY - 335)
				.addWidth(300f)
				.addTextContent("CAUSA DE DESAFILIACION")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalNegrita)
				.build()
				.draw(contentStream);

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + (2 * 5f))
				.addStartY(maxY - 350)
				.addWidth(500f)
				.addTextContent(desafiliado.getCausaDesafiliacion())
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormal)
				.build()
				.draw(contentStream);
	}

	private void crearObservacionesDesafiliado(
			PDPageContentStream contentStream,
			DesafiliadoDTOPDF desafiliado) throws IOException {

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + 2 * 5f)
				.addStartY(maxY - 595 - 2 * 5f)
				.addWidth(135f)
				.addTextContent("OBSERVACIONES")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalNegrita)
				.build()
				.draw(contentStream);

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + (2 * 5f))
				.addStartY(maxY - 610 - (2 * 5f))
				.addWidth(500f)
				.addTextContent(desafiliado.getObservaciones())
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalSemiEspaciada)
				.build()
				.draw(contentStream);
	}

	private void crearFirmaDesafiliado(
			PDPageContentStream contentStream) throws IOException {

		float width1 = 160f;
		float initY = 510;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("........................", fuenteNormal, width1, 0f, 2.5f, Alignment.CENTER, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("FIRMA DEL SOLICITANTE"   , fuenteNormalNegrita, width1, 0f, 2.5f, Alignment.CENTER, margin);

		float auxY = maxY - initY;
		float left = marginStartX + (maxX - (marginStartX + marginEndX) - width1) / 2;

		SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
				left, auxY,
				new Cell[]{c11}, new Cell[]{c12}
		);
		table.draw(contentStream);
	}

	private void crearCodigoDesafiliado(
			PDPageContentStream contentStream) throws IOException {

		float width1 = 75f;
		float width2 = 75f;
		float initY = 720;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("CODIGO: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(""        , fuenteNormalEspaciada, width2, 0f, 2.5f, margin);

		float auxY = maxY - initY;

		EasyComponentsFactory.getSimpleTableFromCell(
				marginStartX + thickness + 350f, auxY,
				new Cell[]{c11, c21}
		).draw(contentStream);

		float initPoint = marginStartX + thickness + 350f + width1;

		new Line(initPoint, auxY, initPoint + 45, auxY).setThickness(2).draw(contentStream);
		new Line(initPoint, auxY - 25, initPoint + 50, auxY - 25).setThickness(2).draw(contentStream);
		new Line(initPoint, auxY, initPoint, auxY - 25).setThickness(2).draw(contentStream);
		new Line(initPoint + 50, auxY - 5, initPoint + 50, auxY - 25).setThickness(2).draw(contentStream);
		new Line(initPoint + 45, auxY, initPoint + 50, auxY - 5).setThickness(2).draw(contentStream);
	}

	private void crearNotaDesafiliado(
			PDPageContentStream contentStream) throws IOException {

		float width1 = 490f;
		float initY = 545f;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText(
				"NOTA: La Desafiliacion a COVIPOL es VOLUNTARIA para sub oficiales, sargentos, cabos, policias y administrativos.\n" +
						"Puede volver a Afiliarse en cualquier momento\n" +
						"LA DEVOLUCION DE APORTES se la realizara luego de su jubilacion",
				fuenteDiminuta,
				width1,
				0f,
				2.5f,
				margin);

		float auxY = maxY - initY;

		SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
				marginStartX + thickness, auxY,
				new Cell[]{c11}
		);
		EasyComponentsFactory.getBoxStroke(10f, 2.5f, Color.BLACK, table).draw(contentStream);
	}

	private void crearCampoRecepcionadoDesafiliado(
			PDPageContentStream contentStream) throws IOException {

		float width1 = 100f;
		float width2 = 150f;
		float initY  = 720f;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("RECEPCIONADO: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(""              , fuenteNormal, width2, 0f, 2.5f, margin);

		float auxY = maxY - initY;

		EasyComponentsFactory.getSimpleTableFromCell(
				marginStartX + thickness + 10f, auxY,
				new Cell[]{c11, c21}
		).draw(contentStream);
	}

	private void crearLineaSeparacionDesafiliado(
			PDPageContentStream contentStream) throws IOException {

		float initY = 600f;
		float auxY = maxY - initY;

		new Line(marginStartX, auxY, maxX - marginEndX, auxY).setThickness(2).draw(contentStream);
	}

}
