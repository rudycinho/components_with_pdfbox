package subsistema.pdf.form;

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
import subsistema.pdf.utils.settings.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FormularioPrecalificadorPrestamos {

	private final float maxY         = Models.MODEL_2.getMaxY();
	private final float maxX         = Models.MODEL_2.getMaxX();
	private final float thickness    = Models.MODEL_2.getThickness();
	private final float marginStartX = Models.MODEL_2.getMarginStartX();
	private final float marginEndX   = Models.MODEL_2.getMarginEndX();
	private final FontEnum fontEnum  = Models.MODEL_2.getFont();

	private Style fuenteTituloTabla;
	private Style fuenteMinimalEspaciado;
	private Style fuenteMinimalExtraEspaciado;
	private Style fuenteMinimalNegrita;
	private Style fuenteMinimalNegritaExtraEspaciado;
	private Style fuenteMinimalNegritaExtraEspaciadoRoja;

	public FormularioPrecalificadorPrestamos(
			String ruta,
			DictamenDTOPDF dictamen,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal,
			Date fechaExportacion) throws IOException {

		PDDocument doc = new PDDocument();

		FontGroup fontGroup = FontGroup.getFontGroup(fontEnum,doc);

		fuenteTituloTabla                     = Style.getStyle(fontGroup, StyleEnum.TABLE_BOLD_SPACED);
		fuenteMinimalEspaciado                = Style.getStyle(fontGroup, StyleEnum.TABLE_MINIMAL_NORMAL_SPACED);
		fuenteMinimalExtraEspaciado           = Style.getStyle(fontGroup, StyleEnum.TABLE_MINIMAL_NORMAL_EXTRA_SPACED);
		fuenteMinimalNegrita                  = Style.getStyle(fontGroup, StyleEnum.TABLE_MINIMAL_BOLD);
		fuenteMinimalNegritaExtraEspaciado    = Style.getStyle(fontGroup, StyleEnum.TABLE_MINIMAL_BOLD_EXTRA_SPACED);
		fuenteMinimalNegritaExtraEspaciadoRoja= Style.getStyle(fontGroup, StyleEnum.TABLE_MINIMAL_BOLD_EXTRA_SPACED_RED);

		PDPage page = new PDPage(new PDRectangle(maxX,maxY));

		doc.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);

		GeneradorFormularioFactory.crearMargen(contentStream, Models.MODEL_2);
		GeneradorFormularioFactory.crearCabecera(contentStream,doc, Models.MODEL_2,  Style.getStyle(fontGroup,StyleEnum.HEADER));
		GeneradorFormularioFactory.crearInfo(contentStream,sucursal, Models.MODEL_2,  Style.getStyle(fontGroup,StyleEnum.FOOTER));
		GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion, Models.MODEL_2,  Style.getStyle(fontGroup,StyleEnum.OWNER));
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor, Models.MODEL_2,  Style.getStyle(fontGroup,StyleEnum.OWNER_BOLD), Style.getStyle(fontGroup,StyleEnum.OWNER));
		GeneradorFormularioFactory.crearTitulo(contentStream,"PRECALIFICADOR DE PRESTAMOS", Models.MODEL_2,  Style.getStyle(fontGroup,StyleEnum.TITLE));

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

		String montoAFinanciar    = String.format("%s Bs", dictamen.getMontoSolicitadoBS());
		String amortizacion       = String.format("Cada %s dias", dictamen.getCantidadDiasDeAmortizacion());
		String plazo              = String.format("%s cuotas(s)", dictamen.getPlazoCuotas());
		String tazaInteres        = String.format("%s %%", dictamen.getTasaInteresFija());
		String maximoComprometido = String.format("%s %%", dictamen.getMaximoComprometido());

		String cuotaParcial       = String.format("%s Bs", dictamen.getCuotaParcial());
		String segDesgravamen     = String.format("%s Bs", dictamen.getSeguroDesgravamen());
		String cip                = String.format("%s Bs", dictamen.getCip());
		String totalCuota         = String.format("%s Bs", dictamen.getCuotaTotal());

		float width1 = 120f;
		float width2 = 90f;
		float width3 = 120f;
		float width4 = 70f;
		float initY = 325f;
		boolean margin = false;

		Cell cTitle = EasyComponentsFactory.getSimpleCellFromText("PLAN DE PAGOS", fuenteTituloTabla, width1 + width2 + width3 + width4, 0f, 2.5f, Alignment.CENTER, margin);
		Cell cLine = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2 + width3 + width4, 2f, 1f, margin);

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Monto a financiar"  , fuenteMinimalNegrita, width1, 1f, 5f, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("Amortizacion"       , fuenteMinimalNegrita, width1, 1f, 5f, margin);
		Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Plazo"              , fuenteMinimalNegrita, width1, 1f, 5f, margin);
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Taza de interes"    , fuenteMinimalNegrita, width1, 1f, 5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Maximo comprometido", fuenteMinimalNegrita, width1, 1f, 5f, margin);

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(montoAFinanciar   , fuenteMinimalEspaciado, width2, 1f, 5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(amortizacion      , fuenteMinimalEspaciado, width2, 1f, 5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(plazo             , fuenteMinimalEspaciado, width2, 1f, 5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(tazaInteres       , fuenteMinimalEspaciado, width2, 1f, 5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(maximoComprometido, fuenteMinimalEspaciado, width2, 1f, 5f, margin);

		Cell c31 = EasyComponentsFactory.getSimpleCellFromText("Cuota parcial"         , fuenteMinimalNegrita, width3, 1f, 5f, margin);
		Cell c32 = EasyComponentsFactory.getSimpleCellFromText("Seguro de Desgravamen" , fuenteMinimalNegrita, width3, 1f, 5f, margin);
		Cell c33 = EasyComponentsFactory.getSimpleCellFromText("C.I.P."                , fuenteMinimalNegrita, width3, 1f, 5f, margin);
		Cell c34 = EasyComponentsFactory.getSimpleCellFromText("Total cuota a cancelar", fuenteMinimalNegrita, width3, 1f, 5f, margin);

		Cell c41 = EasyComponentsFactory.getSimpleCellFromText(cuotaParcial  , fuenteMinimalEspaciado, width4, 1f, 5f, margin);
		Cell c42 = EasyComponentsFactory.getSimpleCellFromText(segDesgravamen, fuenteMinimalEspaciado, width4, 1f, 5f, margin);
		Cell c43 = EasyComponentsFactory.getSimpleCellFromText(cip           , fuenteMinimalEspaciado, width4, 1f, 5f, margin);
		Cell c44 = EasyComponentsFactory.getSimpleCellFromText(totalCuota    , fuenteMinimalEspaciado, width4, 1f, 5f, margin);

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

		float width1 = 80f;
		float width2 = 90f;
		float initY = 200f;
		boolean margin = false;

		Cell cTitle = EasyComponentsFactory.getSimpleCellFromText("Precalificacion en Bolivianos", fuenteTituloTabla, width1 + width2, 0f, 2.5f, Alignment.CENTER, margin);
		Cell cLine1 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 1f, margin);
		Cell cLine2 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 0.5f, margin);
		Cell cLine3 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 0.5f, margin);
		Cell cLine4 = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 0.5f, margin);

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Detalle"          , fuenteMinimalNegritaExtraEspaciado, width1, 1f, 7.5f, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("Liquido Pagable"  , fuenteMinimalNegritaExtraEspaciado, width1, 1f, 7.5f, margin);
		Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Cuota de prestamo", fuenteMinimalNegritaExtraEspaciado, width1, 1f, 7.5f, margin);
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Dictamen"         , fuenteMinimalNegritaExtraEspaciado, width1, 1f, 7.5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("%Destinado Pago"  , fuenteMinimalNegritaExtraEspaciado, width1, 1f, 7.5f, margin);
		Cell c16 = EasyComponentsFactory.getSimpleCellFromText("Cuota Mensual"    , fuenteMinimalNegritaExtraEspaciado, width1 + width2, 1f, 7.5f, Alignment.CENTER, margin);
		Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Bs"               , fuenteMinimalNegritaExtraEspaciado, width1 + width2, 1f, 7.5f, Alignment.CENTER, margin);
		Cell c18 = EasyComponentsFactory.getSimpleCellFromText(cuotaMensual             , fuenteMinimalNegritaExtraEspaciado, width1 + width2, 1f, 7.5f, Alignment.CENTER, margin);
		c18.setHasFilling(true);
		c18.setColorFilling(new Color(150, 150, 150));

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText("Monto Bs"     , fuenteMinimalExtraEspaciado    , width2, 1f, 7.5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(liquidoPagable       , fuenteMinimalExtraEspaciado    , width2, 1f, 7.5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(cuotaPrestamo        , fuenteMinimalExtraEspaciado    , width2, 1f, 7.5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(dictamenLiteral      , fuenteMinimalNegritaExtraEspaciadoRoja, width2, 1f, 7.5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(porcentajeDestinoPago, fuenteMinimalExtraEspaciado    , width2, 1f, 7.5f, margin);

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

		float width1 = 120f;
		float width2 = 60f;
		float width3 = 150f;
		float width4 = 70f;
		float initY = 200f;
		boolean margin = false;

		float auxY = maxY - initY;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Solicitante"            , fuenteMinimalNegrita, width1, 1f, 5f, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("CI"                     , fuenteMinimalNegrita, width1, 1f, 5f, margin);
		Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en UFV", fuenteMinimalNegrita, width1, 1f, 5f, margin);
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en SUS", fuenteMinimalNegrita, width1, 1f, 5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en BS" , fuenteMinimalNegrita, width1, 1f, 5f, margin);

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(solicitante, fuenteMinimalEspaciado, width2 + width3 + width4, 1f, 5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(ci         , fuenteMinimalEspaciado, width2 + width3 + width4, 1f, 5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(montoUFV   , fuenteMinimalEspaciado, width2, 1f, 5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(montoSus   , fuenteMinimalEspaciado, width2, 1f, 5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(mondoBs    , fuenteMinimalEspaciado, width2, 1f, 5f, margin);

		Cell c33 = EasyComponentsFactory.getSimpleCellFromText("Tipo de cambio venta UFV", fuenteMinimalNegrita, width3, 1f, 5f, margin);
		Cell c34 = EasyComponentsFactory.getSimpleCellFromText("Tipo de cambio venta SUS", fuenteMinimalNegrita, width3, 1f, 5f, margin);

		Cell c43 = EasyComponentsFactory.getSimpleCellFromText(cambioUFV, fuenteMinimalEspaciado, width4, 1f, 5f, margin);
		Cell c44 = EasyComponentsFactory.getSimpleCellFromText(cambioSus, fuenteMinimalEspaciado, width4, 1f, 5f, margin);

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
