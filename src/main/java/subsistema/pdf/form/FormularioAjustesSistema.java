package subsistema.pdf.form;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.pdfbox.pdmodel.font.PDFont;
import subsistema.pdf.dto.AjustesSistemaDTO;
import subsistema.pdf.dto.SucursalDTO;
import subsistema.pdf.dto.UsuarioDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.lib.text.MultipleParagraph;
import subsistema.pdf.utils.Data;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.factories.Model;

public class FormularioAjustesSistema {
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
			.addLeading(1f)
			.build();

	private final Style fuenteNormalNegrita = Style.builder()
			.addTextFont(fuenteBasicaNegrita)
			.addFontSize(10.5f)
			.addTextColor(Color.BLACK)
			.addLeading(0.8f)
			.build();

	public FormularioAjustesSistema(String ruta,
									AjustesSistemaDTO ajustes,
									UsuarioDTO usuarioEditor,
									SucursalDTO sucursal,
									Date fechaExportacion) throws IOException {
		PDDocument doc = new PDDocument();
		PDPage page = new PDPage(new PDRectangle(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight()));

		doc.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);

		GeneradorFormularioFactory.setDimensiones(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight());
		GeneradorFormularioFactory.setMarginStartX(50f);

		GeneradorFormularioFactory.crearMargen(contentStream);
		GeneradorFormularioFactory.crearCabecera(contentStream,doc);
		GeneradorFormularioFactory.crearInfo(contentStream,sucursal);
		GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion);
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor);
		GeneradorFormularioFactory.crearTitulo(contentStream,"AJUSTES DEL SISTEMA");

		crearSubTituloAjustesSistema(contentStream);
		crearDatosAjustesSistema(contentStream,ajustes);

		contentStream.close();

		doc.save(new File(ruta));
		doc.close();
	}

	private void crearSubTituloAjustesSistema(
			PDPageContentStream contentStream) throws IOException {


		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + relativePositionX)
				.addStartY(maxY - 180 - 30 - relativePositionY)
				.addWidth(180f)
				.addTextContent("DATOS DEL SISTEMA")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteSubtitulo)
				.build()
				.draw(contentStream);
	}

	private void crearDatosAjustesSistema(
			PDPageContentStream contentStream,
			AjustesSistemaDTO ajustes) throws IOException {

		float width1 = 200f;
		float width2 = 300f - 10f;
		float initY = 235;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("CAMBIO USD: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("CAMBIO UFV: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c13 = EasyComponentsFactory.getSimpleCellFromText("AMORTIZACION DIAS: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("SEGURO DESGRAVAMEN: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("DIAS PARA ARCHIVAR: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c16 = EasyComponentsFactory.getSimpleCellFromText("C.I.P.: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c17 = EasyComponentsFactory.getSimpleCellFromText("INTERES ANUAL: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c18 = EasyComponentsFactory.getSimpleCellFromText("CORREO DE NOTIFICACIONES: ", fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c19 = EasyComponentsFactory.getSimpleCellFromText("DIAS MAXIMO PARA DESCUENTOS: ", fuenteNormalNegrita, width1, 0f, 5f, margin);

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCambioUSD().toString(), fuenteNormal, width2, 0f, 5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCambioUFV().toString(), fuenteNormal, width2, 0f, 5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getAmortizacionDias().toString(), fuenteNormal, width2, 0f, 5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getSeguroDesgravamen().toString(), fuenteNormal, width2, 0f, 5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getDiasParaArchivar().toString(), fuenteNormal, width2, 0f, 5f, margin);
		Cell c26 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCIP().toString(), fuenteNormal, width2, 0f, 5f, margin);
		Cell c27 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getInteresAnual().toString(), fuenteNormal, width2, 0f, 5f, margin);
		Cell c28 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCorreoNotificaciones(), fuenteNormal, width2, 0f, 5f, margin);
		Cell c29 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getDiasMaximoParaDescuentos().toString(), fuenteNormal, width2, 0f, 5f, margin);

		float auxY = maxY - initY;

		SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
				marginStartX + thickness, auxY,
				new Cell[]{c11, c21},
				new Cell[]{c12, c22},
				new Cell[]{c13, c23},
				new Cell[]{c14, c24},
				new Cell[]{c15, c25},
				new Cell[]{c16, c26},
				new Cell[]{c17, c27},
				new Cell[]{c18, c28},
				new Cell[]{c19, c29}
		);
		EasyComponentsFactory.getBoxStroke(10f, 5f, Color.BLACK, table).draw(contentStream);
	}
}
