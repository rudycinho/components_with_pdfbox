package subsistema.pdf.form;

import org.apache.pdfbox.pdmodel.font.PDFont;
import subsistema.pdf.dto.DictamenDTOPDF;
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
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.Model;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioPrecalificadorPrestamos {

	private final float maxY         = Model.MODEL_2.getMaxY();
	private final float maxX         = Model.MODEL_2.getMaxX();
	private final float thickness    = Model.MODEL_2.getThickness();
	private final float marginStartX = Model.MODEL_2.getMarginStartX();
	private final float marginEndX   = Model.MODEL_2.getMarginEndX();
	private final float relativePositionX = 5f;
	private final float relativePositionY = 5f;

	private final PDFont fuenteBasica        = Model.MODEL_2.getFuenteBasica();
	private final PDFont fuenteBasicaNegrita = Model.MODEL_2.getFuenteBasicaNegrita();

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

	public FormularioPrecalificadorPrestamos(
			String ruta,
			DictamenDTOPDF dictamen,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal,
			Date fechaExportacion) throws IOException {

		PDDocument doc = new PDDocument();
		PDPage page = new PDPage(new PDRectangle(maxX,maxY));

		doc.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);

		GeneradorFormularioFactory.crearMargen(contentStream, Model.MODEL_2);
		GeneradorFormularioFactory.crearCabecera(contentStream,doc, Model.MODEL_2);
		GeneradorFormularioFactory.crearInfo(contentStream,sucursal, Model.MODEL_2);
		GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion, Model.MODEL_2);
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor, Model.MODEL_2);
		GeneradorFormularioFactory.crearTitulo(contentStream,"PRECALIFICADOR DE PRESTAMOS", Model.MODEL_2);

		crearPlanPagosSeccionDictamen(contentStream, dictamen);
		crearPrecalicacionSeccionDictamen(contentStream, dictamen);
		crearDatosSeccionDictamen(contentStream, dictamen);

		contentStream.close();

		doc.save(new File(ruta));
		doc.close();
	}

	private void crearPlanPagosSeccionDictamen(
			PDPageContentStream contentStream,
			DictamenDTOPDF dictamen) throws IOException {

		String montoAFinanciar = String.format("%s Bs", dictamen.getMontoSolicitadoBS());
		String amortizacion = String.format("Cada %s dias", dictamen.getCantidadDiasDeAmortizacion());
		String plazo = String.format("%s cuotas(s)", dictamen.getPlazoCuotas());
		String tazaInteres = String.format("%s %%", dictamen.getTasaInteresFija());
		String maximoComprometido = String.format("%s %%", dictamen.getMaximoComprometido());

		String cuotaParcial = String.format("%s Bs", dictamen.getCuotaParcial());
		String segDesgravamen = String.format("%s Bs", dictamen.getSeguroDesgravamen());
		String cip = String.format("%s Bs", dictamen.getCip());
		String totalCuota = String.format("%s Bs", dictamen.getCuotaTotal());

		Style fuenteTitulo = Style.builder()
				.addTextFont(fuenteBasicaNegrita)
				.addFontSize(10.5f)
				.addTextColor(Color.BLACK)
				.addLeading(1f)
				.build();

		Style fuenteNormal = Style.builder()
				.addTextFont(fuenteBasica)
				.addFontSize(9.5f)
				.addTextColor(Color.BLACK)
				.addLeading(1f)
				.build();

		Style fuenteNormalNegrita = Style.builder()
				.addTextFont(fuenteBasicaNegrita)
				.addFontSize(9.5f)
				.addTextColor(Color.BLACK)
				.addLeading(0.8f)
				.build();

		float width1 = 120f;
		float width2 = 90f;
		float width3 = 120f;
		float width4 = 70f;
		float initY = 325f;
		boolean margin = false;

		Cell cTitle = EasyComponentsFactory.getSimpleCellFromText("PLAN DE PAGOS", fuenteTitulo, width1 + width2 + width3 + width4, 0f, 2.5f, Alignment.CENTER, margin);
		Cell cLine = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2 + width3 + width4, 2f, 1f, margin);

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Monto a financiar", fuenteNormalNegrita, width1, 1f, 5f, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("Amortizacion", fuenteNormalNegrita, width1, 1f, 5f, margin);
		Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Plazo", fuenteNormalNegrita, width1, 1f, 5f, margin);
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Taza de interes", fuenteNormalNegrita, width1, 1f, 5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Maximo comprometido", fuenteNormalNegrita, width1, 1f, 5f, margin);

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(montoAFinanciar, fuenteNormal, width2, 1f, 5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(amortizacion, fuenteNormal, width2, 1f, 5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(plazo, fuenteNormal, width2, 1f, 5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(tazaInteres, fuenteNormal, width2, 1f, 5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(maximoComprometido, fuenteNormal, width2, 1f, 5f, margin);

		Cell c31 = EasyComponentsFactory.getSimpleCellFromText("Cuota parcial", fuenteNormalNegrita, width3, 1f, 5f, margin);
		Cell c32 = EasyComponentsFactory.getSimpleCellFromText("Seguro de Desgravamen", fuenteNormalNegrita, width3, 1f, 5f, margin);
		Cell c33 = EasyComponentsFactory.getSimpleCellFromText("C.I.P.", fuenteNormalNegrita, width3, 1f, 5f, margin);
		Cell c34 = EasyComponentsFactory.getSimpleCellFromText("Total cuota a cancelar", fuenteNormalNegrita, width3, 1f, 5f, margin);

		Cell c41 = EasyComponentsFactory.getSimpleCellFromText(cuotaParcial, fuenteNormal, width4, 1f, 5f, margin);
		Cell c42 = EasyComponentsFactory.getSimpleCellFromText(segDesgravamen, fuenteNormal, width4, 1f, 5f, margin);
		Cell c43 = EasyComponentsFactory.getSimpleCellFromText(cip, fuenteNormal, width4, 1f, 5f, margin);
		Cell c44 = EasyComponentsFactory.getSimpleCellFromText(totalCuota, fuenteNormal, width4, 1f, 5f, margin);

		float auxY = maxY - initY;

		SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
				marginStartX + thickness, auxY,
				new Cell[]{cTitle},
				new Cell[]{cLine},
				new Cell[]{c11, c21, c31, c41},
				new Cell[]{c12, c22, c32, c42},
				new Cell[]{c13, c23, c33, c43},
				new Cell[]{c14, c24, c34, c44},
				new Cell[]{c15, c25}
		);
		Cell box = EasyComponentsFactory.getBoxStroke(30f, 15f, new Color(107, 250, 194), table);
		box.setHasMargin(true);
		box.setHasFilling(true);
		box.setColorMargin(new Color(107, 250, 194));
		box.setColorFilling(new Color(107, 250, 194));
		box.draw(contentStream);
	}

	private void crearPrecalicacionSeccionDictamen(
			PDPageContentStream contentStream,
			DictamenDTOPDF dictamen) throws IOException {

		String liquidoPagable = dictamen.getSueldoLiquidoPagable();
		String dictamenLiteral = dictamen.getDictamenLiteral();
		String porcentajeDestinoPago = dictamen.getPorcentajeDestinadoAPago();
		String cuotaPrestamo = dictamen.getCuotaTotal();
		String cuotaMensual = dictamen.getCuotaParcial();

		Style fuenteTitulo = Style.builder()
				.addTextFont(fuenteBasicaNegrita)
				.addFontSize(10.5f)
				.addTextColor(Color.BLACK)
				.addLeading(1f)
				.build();

		Style fuenteNormal = Style.builder()
				.addTextFont(fuenteBasica)
				.addFontSize(9.5f)
				.addTextColor(Color.BLACK)
				.addLeading(1.2f)
				.build();

		Style fuenteNormalRoja = Style.builder()
				.addTextFont(fuenteBasicaNegrita)
				.addFontSize(9.5f)
				.addTextColor(Color.RED)
				.addLeading(1.2f)
				.build();

		Style fuenteNormalNegrita = Style.builder()
				.addTextFont(fuenteBasicaNegrita)
				.addFontSize(9.5f)
				.addTextColor(Color.BLACK)
				.addLeading(1.2f)
				.build();

		float width1 = 80f;
		float width2 = 90f;
		float initY = 200f;
		boolean margin = false;

		Cell cTitle = EasyComponentsFactory.getSimpleCellFromText("Precalificacion en Bolivianos", fuenteTitulo, width1 + width2, 0f, 2.5f, Alignment.CENTER, margin);
		Cell cLine1 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 1f, margin);
		Cell cLine2 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 0.5f, margin);
		Cell cLine3 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 0.5f, margin);
		Cell cLine4 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 0.5f, margin);

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Detalle", fuenteNormalNegrita, width1, 1f, 7.5f, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("Liquido Pagable", fuenteNormalNegrita, width1, 1f, 7.5f, margin);
		Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Cuota de prestamo", fuenteNormalNegrita, width1, 1f, 7.5f, margin);
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Dictamen", fuenteNormalNegrita, width1, 1f, 7.5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("%Destinado Pago", fuenteNormalNegrita, width1, 1f, 7.5f, margin);
		Cell c16 = EasyComponentsFactory.getSimpleCellFromText("Cuota Mensual", fuenteNormalNegrita, width1 + width2, 1f, 7.5f, Alignment.CENTER, margin);
		Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Bs", fuenteNormalNegrita, width1 + width2, 1f, 7.5f, Alignment.CENTER, margin);
		Cell c18 = EasyComponentsFactory.getSimpleCellFromText(cuotaMensual, fuenteNormalNegrita, width1 + width2, 1f, 7.5f, Alignment.CENTER, margin);
		c18.setHasFilling(true);
		c18.setColorFilling(new Color(150, 150, 150));

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText("Monto Bs", fuenteNormal, width2, 1f, 7.5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(liquidoPagable, fuenteNormal, width2, 1f, 7.5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(cuotaPrestamo, fuenteNormal, width2, 1f, 7.5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(dictamenLiteral, fuenteNormalRoja, width2, 1f, 7.5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(porcentajeDestinoPago, fuenteNormal, width2, 1f, 7.5f, margin);

		float auxY = maxY - initY;

		SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
				maxX - marginEndX - 225, auxY,
				new Cell[]{cTitle},
				new Cell[]{cLine1},
				new Cell[]{c11, c21},
				new Cell[]{cLine2},
				new Cell[]{c12, c22},
				new Cell[]{c13, c23},
				new Cell[]{c14, c24},
				new Cell[]{c15, c25},
				new Cell[]{cLine3},
				new Cell[]{c16},
				new Cell[]{cLine4},

				new Cell[]{c17},
				new Cell[]{c18}
		);
		Cell box = EasyComponentsFactory.getBoxStroke(15f, 10f, new Color(107, 250, 194), table);
		box.setHasMargin(true);
		box.setHasFilling(true);
		box.setColorMargin(new Color(107, 250, 194));
		box.setColorFilling(new Color(107, 250, 194));
		box.draw(contentStream);

	}

	private void crearDatosSeccionDictamen(PDPageContentStream contentStream, DictamenDTOPDF dictamen) throws IOException {
		String solicitante = dictamen.getNombreCliente();
		String ci = dictamen.getCi();

		String montoUFV = dictamen.getMontoSolicitadoUFV();
		String montoSus = dictamen.getMontoSolicitadoUS();
		String mondoBs = dictamen.getMontoSolicitadoBS();
		String cambioUFV = dictamen.getCambioUFV();
		String cambioSus = dictamen.getCambioUS();

		Style fuenteNormal = Style.builder()
				.addTextFont(fuenteBasica)
				.addFontSize(9.5f)
				.addTextColor(Color.BLACK)
				.addLeading(1f)
				.build();

		Style fuenteNormalNegrita = Style.builder()
				.addTextFont(fuenteBasicaNegrita)
				.addFontSize(9.5f)
				.addTextColor(Color.BLACK)
				.addLeading(0.8f)
				.build();

		float width1 = 120f;
		float width2 = 60f;
		float width3 = 150f;
		float width4 = 70f;
		float initY = 200f;
		boolean margin = false;

		float auxY = maxY - initY;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Solicitante", fuenteNormalNegrita, width1, 1f, 5f, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("CI", fuenteNormalNegrita, width1, 1f, 5f, margin);
		Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en UFV", fuenteNormalNegrita, width1, 1f, 5f, margin);
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en SUS", fuenteNormalNegrita, width1, 1f, 5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en BS", fuenteNormalNegrita, width1, 1f, 5f, margin);

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(solicitante, fuenteNormal, width2 + width3 + width4, 1f, 5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(ci, fuenteNormal, width2 + width3 + width4, 1f, 5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(montoUFV, fuenteNormal, width2, 1f, 5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(montoSus, fuenteNormal, width2, 1f, 5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(mondoBs, fuenteNormal, width2, 1f, 5f, margin);

		Cell c33 = EasyComponentsFactory.getSimpleCellFromText("Tipo de cambio venta UFV", fuenteNormalNegrita, width3, 1f, 5f, margin);
		Cell c34 = EasyComponentsFactory.getSimpleCellFromText("Tipo de cambio venta SUS", fuenteNormalNegrita, width3, 1f, 5f, margin);

		Cell c43 = EasyComponentsFactory.getSimpleCellFromText(cambioUFV, fuenteNormal, width4, 1f, 5f, margin);
		Cell c44 = EasyComponentsFactory.getSimpleCellFromText(cambioSus, fuenteNormal, width4, 1f, 5f, margin);

		SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
				marginStartX + thickness, auxY,
				new Cell[]{c11, c21},
				new Cell[]{c12, c22},
				new Cell[]{c13, c23, c33, c43},
				new Cell[]{c14, c24, c34, c44},
				new Cell[]{c15, c25}
		);
		Cell box = EasyComponentsFactory.getBoxStroke(30f, 7.5f, new Color(107, 250, 194), table);
		box.setHasMargin(false);
		box.setHasFilling(false);
		box.draw(contentStream);
	}



}
