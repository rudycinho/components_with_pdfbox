package subsistema.pdf.form;

import org.apache.pdfbox.pdmodel.font.PDFont;
import subsistema.pdf.dto.AfiliadoDTO;
import subsistema.pdf.dto.SucursalDTO;
import subsistema.pdf.dto.UsuarioDTO;
import subsistema.pdf.dto.EstadoAfiliacionDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.shapes.Line;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.Column;
import subsistema.pdf.lib.tables.RecursiveTable;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.lib.text.MultipleParagraph;
import subsistema.pdf.utils.Data;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class FormularioAfiliacion {

	private final float maxY         = Data.MAX_LETTER_Y;
	private final float maxX         = Data.MAX_LETTER_X;
	private final float thickness    = Data.THICKNESS_MODEL_1;
	private final float marginStartX = Data.MARGIN_START_X_MODEL_1;
	private final float marginEndX   = Data.MARGIN_END_X_MODEL_1;
	private final float relativePositionX = 5f;
	private final float relativePositionY = 5f;

	private final PDFont fuenteBasica        = Data.BASIC_FONT;
	private final PDFont fuenteBasicaNegrita = Data.BASIC_BOLD_FONT;

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

	public FormularioAfiliacion(String ruta,
                                AfiliadoDTO afiliado,
                                List<EstadoAfiliacionDTO> estados,
								UsuarioDTO usuarioEditor,
                                SucursalDTO sucursal,
                                Date fechaExportacion) throws IOException {

		PDDocument doc = new PDDocument();
		PDPage page1 = new PDPage(new PDRectangle(maxX,maxY));

		doc.addPage(page1);
		PDPageContentStream contentStream1 = new PDPageContentStream(doc, page1);

		GeneradorFormularioFactory.setDimensiones(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight());
		GeneradorFormularioFactory.setMarginStartX(50f);

		GeneradorFormularioFactory.crearMargen(contentStream1);
		GeneradorFormularioFactory.crearCabecera(contentStream1,doc);
		GeneradorFormularioFactory.crearInfo(contentStream1,sucursal);
		GeneradorFormularioFactory.crearFechaExportacion(contentStream1,fechaExportacion);
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream1,usuarioEditor);
		GeneradorFormularioFactory.crearTitulo(contentStream1,"FORMULARIO A-01\nSOLICITUD DE AFILIACION");

		crearSubTitulosAfiliado(contentStream1);
		crearFechaRegistroAfiliado(contentStream1, afiliado);
		crearDatosAfiliado(contentStream1, afiliado);
		crearObservacionesAfiliado(contentStream1, afiliado);

		crearFirmaAfiliado(contentStream1);
		crearCodigoAfiliado(contentStream1);
		crearNotaAfiliado(contentStream1);
		crearCampoRecepcionadoAfiliado(contentStream1);
		crearLineaSeparacionAfiliado(contentStream1);

		contentStream1.close();

		if(!estados.isEmpty()){
			List<PDPageContentStream> contentStreams = new LinkedList<>();

			PDPage page2 = new PDPage(new PDRectangle(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight()));
			doc.addPage(page2);
			PDPageContentStream contentStream2 = new PDPageContentStream(doc, page2);

			crearSubTitulosHistorialReafiliaciones(contentStream2);
			crearTablaDetallesReafiliado(
					contentStream2,
					contentStreams,
					doc,
					estados);

			for(PDPageContentStream contentStream : contentStreams){
				GeneradorFormularioFactory.crearMargen(contentStream);
				GeneradorFormularioFactory.crearInfo(contentStream,sucursal);
			}

			for(PDPageContentStream e : contentStreams)
				e.close();
		}

		doc.save(new File(ruta));
		doc.close();
	}

	private void crearSubTitulosAfiliado(
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

	private void crearFechaRegistroAfiliado(
			PDPageContentStream contentStream,
			AfiliadoDTO afiliado) throws IOException {

		String fechaCreacion = afiliado.getFechaSolicitud();
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

	private void crearDatosAfiliado(
			PDPageContentStream contentStream,
			AfiliadoDTO afiliado) throws IOException {

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
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("FECHA DE NACIMIENTO ", fuenteNormalNegrita, width3, 0f, 2.5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("N* TELEFONICO : ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c16 = EasyComponentsFactory.getSimpleCellFromText("N* DE CELULAR: ", fuenteNormalNegrita, width3, 0f, 2.5f, margin);
		Cell c17 = EasyComponentsFactory.getSimpleCellFromText("CORREO ELECTRONICO: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c18 = EasyComponentsFactory.getSimpleCellFromText("UNIDAD POLICIAL: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);
		Cell c19 = EasyComponentsFactory.getSimpleCellFromText("DEPARTAMENTO: ", fuenteNormalNegrita, width1, 0f, 2.5f, margin);

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getGrado(), fuenteNormal, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getNombreCompleto(), fuenteNormal, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getCarnetIdentidad(), fuenteNormal, width2, 0f, 2.5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getFechaNacimiento(), fuenteNormal, width4, 0f, 2.5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getTelefono(), fuenteNormal, width2, 0f, 2.5f, margin);
		Cell c26 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getCelular(), fuenteNormal, width4, 0f, 2.5f, margin);
		Cell c27 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getCorreoElectronico(), fuenteNormal, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c28 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getUnidadPolicial(), fuenteNormal, width2 + width3 + width4, 0f, 2.5f, margin);
		Cell c29 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getDepartamento(), fuenteNormal, width2 + width3 + width4, 0f, 2.5f, margin);

		float auxY = maxY - initY - 2.5f;

		SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
				marginStartX + thickness, auxY,
				new Cell[]{c11, c21},
				new Cell[]{c12, c22},
				new Cell[]{c13, c23, c14, c24},
				new Cell[]{c15, c25, c16, c26},
				new Cell[]{c17, c27},
				new Cell[]{c18, c28},
				new Cell[]{c19, c29}
		);
		EasyComponentsFactory.getBoxStroke(10f, 5f, Color.BLACK, table).draw(contentStream);
	}

	private void crearObservacionesAfiliado(
			PDPageContentStream contentStream,
			AfiliadoDTO afiliado) throws IOException {

		Style fuenteNormal = Style.builder()
				.addTextFont(fuenteBasica)
				.addFontSize(10.5f)
				.addTextColor(Color.BLACK)
				.addLeading(0.9f)
				.build();

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + 2 * relativePositionX)
				.addStartY(maxY - 595- 2 * relativePositionY)
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
				.addTextContent(afiliado.getObservaciones())
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteNormal)
				.build()
				.draw(contentStream);
	}

	private void crearFirmaAfiliado(
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

	private void crearCodigoAfiliado(
			PDPageContentStream contentStream) throws IOException {
		Style fuenteNormal = Style.builder()
				.addTextFont(fuenteBasica)
				.addFontSize(10.5f)
				.addTextColor(Color.BLACK)
				.addLeading(1f)
				.build();

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

	private void crearNotaAfiliado(
			PDPageContentStream contentStream) throws IOException {

		float width1 = 490f;
		float initY = 545f;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText(
				"NOTA: La Afiliacion a COVIPOL es VOLUNTARIA para sub oficiales, sargentos, cabos, policias y administrativos.\n" +
						"El descuento por afiliacion corresponde al 1% del TOTAL GANADO",
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

	private void crearCampoRecepcionadoAfiliado(
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

	private void crearLineaSeparacionAfiliado(
			PDPageContentStream contentStream) throws IOException {

		float initY = 600;
		float auxY = maxY - initY;

		new Line(marginStartX, auxY, maxX - marginEndX, auxY).setThickness(2).draw(contentStream);
	}

	private void crearSubTitulosHistorialReafiliaciones(
			PDPageContentStream contentStream) throws IOException {

		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + relativePositionX)
				.addStartY(maxY - 40 - relativePositionY)
				.addWidth(250f)
				.addTextContent("HISTORIAL REAFILIACIONES")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteSubtitulo)
				.build()
				.draw(contentStream);
	}

	private void crearTablaDetallesReafiliado(
			PDPageContentStream contentStream,
			List<PDPageContentStream> contentStreams,
			PDDocument doc,
			List<EstadoAfiliacionDTO> estados) throws IOException {

		List<List<String>> listOfList = new ArrayList<>();

		int i = 0;
		for (EstadoAfiliacionDTO estado : estados) {
			List<String> auxList = new ArrayList<>();
			auxList.add(String.format("%d", ++i));
			auxList.add(estado.getCausa());
			auxList.add(estado.getObservaciones());
			auxList.add(estado.getFechaSolicitud());
			listOfList.add(auxList);
		}

		List<Float> widths  = Arrays.asList(30f, 195f, 195f,85f);
		List<String> header = Arrays.asList("N*", "Causa Reafiliacion", "Observaciones","Fecha Solicitud");

		Style fuenteNormal = Style.builder()
				.addTextFont(fuenteBasica)
				.addFontSize(10.5f)
				.addTextColor(Color.BLACK)
				.addLeading(1f)
				.build();

		Style fuenteNormalNegrita = Style.builder()
				.addTextFont(fuenteBasicaNegrita)
				.addFontSize(10.5f)
				.addTextColor(Color.BLACK)
				.addLeading(0.95f)
				.build();

		Column headerColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
				header, widths, fuenteNormalNegrita, Alignment.LEFT);

		Column anotherHeaderColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
				header, widths, fuenteNormalNegrita, Alignment.LEFT);

		List<Column> bodyColumns = new LinkedList<>();

		for (List<String> auxList : listOfList)
			bodyColumns.add(EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(auxList, widths, fuenteNormal, Alignment.LEFT));

		List<RecursiveTable> tables = EasyComponentsFactory.getHeaderTable(
				marginStartX + thickness, maxY - 65, maxY - 40, headerColumn, anotherHeaderColumn, bodyColumns, 45);

		Iterator<RecursiveTable> it = tables.iterator();
		it.next().draw(contentStream);


		while (it.hasNext()) {
			PDPage page = new PDPage(new PDRectangle(PDRectangle.LETTER.getWidth(), PDRectangle.LETTER.getHeight()));
			doc.addPage(page);
			PDPageContentStream content = new PDPageContentStream(doc, page);
			it.next().draw(content);
			contentStreams.add(content);
		}

		contentStreams.add(0, contentStream);
	}

}
