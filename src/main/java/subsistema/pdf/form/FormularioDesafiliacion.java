package subsistema.pdf.form;

import org.apache.pdfbox.pdmodel.font.PDFont;
import subsistema.pdf.dto.DesafiliadoDTO;
import subsistema.pdf.dto.SucursalDTO;
import subsistema.pdf.dto.UsuarioDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.shapes.Line;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.lib.text.MultipleParagraph;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.Model;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioDesafiliacion {

	private final float maxY         = Model.MODEL_1.getMaxY();
	private final float maxX         = Model.MODEL_1.getMaxX();
	private final float thickness    = Model.MODEL_1.getThickness();
	private final float marginStartX = Model.MODEL_1.getMarginStartX();
	private final float marginEndX   = Model.MODEL_1.getMarginEndX();
	private final float relativePositionX = 5f;
	private final float relativePositionY = 5f;

	private final PDFont fuenteBasica        = Model.MODEL_1.getFuenteBasica();
	private final PDFont fuenteBasicaNegrita = Model.MODEL_1.getFuenteBasicaNegrita();

	private final Style fuenteSubtitulo = Style.builder()
			.addTextFont(fuenteBasicaNegrita)
			.addFontSize(14f)
			.addTextColor(Color.BLACK)
			.addLeading(0.8f)
			.build();

	private final Style fuenteNormal = Style.builder()
			.addTextFont(fuenteBasica)
			.addFontSize(10.5f)
			.addTextColor(Color.BLACK)
			.addLeading(0.8f)
			.build();

	private final Style fuenteNormalNegrita = Style.builder()
			.addTextFont(fuenteBasicaNegrita)
			.addFontSize(10.5f)
			.addTextColor(Color.BLACK)
			.addLeading(0.8f)
			.build();

	private final Style fuenteDiminuta = Style.builder()
			.addFontSize(8)
			.addTextFont(fuenteBasica)
			.build();

	public FormularioDesafiliacion(String ruta,
								   DesafiliadoDTO desafiliado,
								   UsuarioDTO usuarioEditor,
								   SucursalDTO sucursal,
								   Date fechaExportacion) throws IOException {

		PDDocument doc = new PDDocument();
		PDPage page = new PDPage(new PDRectangle(maxX,maxY));

		doc.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);

		GeneradorFormularioFactory.crearMargen(contentStream, Model.MODEL_1);
		GeneradorFormularioFactory.crearCabecera(contentStream,doc, Model.MODEL_1);
		GeneradorFormularioFactory.crearInfo(contentStream,sucursal, Model.MODEL_1);
		GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion, Model.MODEL_1);
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor, Model.MODEL_1);
		GeneradorFormularioFactory.crearTitulo(contentStream,"FORMULARIO C-01\nSOLICITUD DE DESAFILIACION", Model.MODEL_1);

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
				.addStartX(marginStartX + thickness + relativePositionX)
				.addStartY(maxY - 190 - relativePositionY)
				.addWidth(180f)
				.addTextContent("DATOS DEL SOLICITANTE")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteSubtitulo)
				.build()
				.draw(contentStream);
	}

	private void crearFechaRegistroDesafiliado(
			PDPageContentStream contentStream,
			DesafiliadoDTO desafiliado) throws IOException {

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
			DesafiliadoDTO desafiliado) throws IOException {

		Style fuenteNormal = Style.builder()
				.addTextFont(fuenteBasica)
				.addFontSize(10.5f)
				.addTextColor(Color.BLACK)
				.addLeading(0.9f)
				.build();

		float width1 = 150f;
		float width2 = 130f;
		float width3 = 130f;
		float width4 = 80f;
		float initY = 220;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("GRADO: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("NOMBRES Y APELLIDOS: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c13 = EasyComponentsFactory.getSimpleCellFromText("N* DE CI: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("UNIDAD POLICIAL: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("DEPARTAMENTO: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getGrado(), fuenteNormal, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getNombreCompleto(), fuenteNormal, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getCarnetIdentidad(), fuenteNormal, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getUnidadPolicial(), fuenteNormal, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getDepartamento(), fuenteNormal, width2 + width3 + width4, 0f, 2.5f, margin);

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
			DesafiliadoDTO desafiliado) throws IOException {

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + 2 * relativePositionX)
				.addStartY(maxY - 335)
				.addWidth(300f)
				.addTextContent("CAUSA DE DESAFILIACION")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalNegrita)
				.build()
				.draw(contentStream);

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + (2 * relativePositionX))
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
			DesafiliadoDTO desafiliado) throws IOException {

		Style fuenteNormal = Style.builder()
				.addTextFont(fuenteBasica)
				.addFontSize(10.5f)
				.addTextColor(Color.BLACK)
				.addLeading(.9f)
				.build();

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + 2 * relativePositionX)
				.addStartY(maxY - 595 - 2 * relativePositionY)
				.addWidth(135f)
				.addTextContent("OBSERVACIONES")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormalNegrita)
				.build()
				.draw(contentStream);

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + (2 * relativePositionX))
				.addStartY(maxY - 610 - (2 * relativePositionY))
				.addWidth(500f)
				.addTextContent(desafiliado.getObservaciones())
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormal)
				.build()
				.draw(contentStream);
	}

	private void crearFirmaDesafiliado(
			PDPageContentStream contentStream) throws IOException {

		float width1 = 160f;
		float initY = 510;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("........................", fuenteNormal, width1, 0f, 2.5f, Alignment.CENTER, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("FIRMA DEL SOLICITANTE", fuenteNormalNegrita, width1, 0f, 2.5f, Alignment.CENTER, margin);

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
		Cell c21 = EasyComponentsFactory.getSimpleCellFromText("", fuenteNormal, width2, 0f, 2.5f, margin);

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
		float initY = 720f;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("RECEPCIONADO: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c21 = EasyComponentsFactory.getSimpleCellFromText("", fuenteNormal, width2, 0f, 2.5f, margin);

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
