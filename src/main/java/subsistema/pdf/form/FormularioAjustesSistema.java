package subsistema.pdf.form;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import subsistema.pdf.dto.AjustesSistemaDTOPDF;
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

public class FormularioAjustesSistema {

	private final float maxY         = Models.MODEL_1.getMaxY();
	private final float maxX         = Models.MODEL_1.getMaxX();
	private final float thickness    = Models.MODEL_1.getThickness();
	private final float marginStartX = Models.MODEL_1.getMarginStartX();
	private final float marginEndX   = Models.MODEL_1.getMarginEndX();
	private final FontEnum fontEnum  = Models.MODEL_1.getFont();

	private Style fuenteSubtitulo;
	private Style fuenteNormalNegrita;
	private Style fuenteNormalEspaciada;

	public FormularioAjustesSistema(String ruta,
									AjustesSistemaDTOPDF ajustes,
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
		GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion, Models.MODEL_1,Style.getStyle(fontGroup,StyleEnum.OWNER));
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor, Models.MODEL_1,Style.getStyle(fontGroup,StyleEnum.OWNER_BOLD),Style.getStyle(fontGroup,StyleEnum.OWNER));
		GeneradorFormularioFactory.crearTitulo(contentStream,"AJUSTES DEL SISTEMA", Models.MODEL_1, Style.getStyle(fontGroup,StyleEnum.TITLE));

		crearSubTituloAjustesSistema(contentStream);
		crearDatosAjustesSistema(contentStream,ajustes);

		contentStream.close();

		doc.save(new File(ruta));
		doc.close();
	}

	private void crearSubTituloAjustesSistema(
			PDPageContentStream contentStream) throws IOException {


		MultipleParagraph.builder()
				.addStartX(marginStartX + thickness + 5f)
				.addStartY(maxY - 180 - 30 - 5f)
				.addWidth(180f)
				.addTextContent("DATOS DEL SISTEMA")
				.addAlignment(Alignment.LEFT)
				.addStyle(fuenteSubtitulo)
				.build()
				.draw(contentStream);
	}

	private void crearDatosAjustesSistema(
			PDPageContentStream contentStream,
			AjustesSistemaDTOPDF ajustes) throws IOException {

		float width1 = 200f;
		float width2 = 300f - 10f;
		float initY = 235;
		boolean margin = false;

		Cell c11 = EasyComponentsFactory.getSimpleCellFromText("CAMBIO USD: "                 , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c12 = EasyComponentsFactory.getSimpleCellFromText("CAMBIO UFV: "                 , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c13 = EasyComponentsFactory.getSimpleCellFromText("AMORTIZACION DIAS: "          , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c14 = EasyComponentsFactory.getSimpleCellFromText("SEGURO DESGRAVAMEN: "         , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c15 = EasyComponentsFactory.getSimpleCellFromText("DIAS PARA ARCHIVAR: "         , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c16 = EasyComponentsFactory.getSimpleCellFromText("C.I.P.: "                     , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c17 = EasyComponentsFactory.getSimpleCellFromText("INTERES ANUAL: "              , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c18 = EasyComponentsFactory.getSimpleCellFromText("CORREO DE NOTIFICACIONES: "   , fuenteNormalNegrita, width1, 0f, 5f, margin);
		Cell c19 = EasyComponentsFactory.getSimpleCellFromText("DIAS MAXIMO PARA DESCUENTOS: ", fuenteNormalNegrita, width1, 0f, 5f, margin);

		Cell c21 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCambioUSD()               , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c22 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCambioUFV()               , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c23 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getAmortizacionDias()        , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c24 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getSeguroDesgravamen()       , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c25 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getDiasParaArchivar()        , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c26 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCIP()                     , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c27 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getInteresAnual()            , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c28 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCorreoNotificaciones()    , fuenteNormalEspaciada, width2, 0f, 5f, margin);
		Cell c29 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getDiasMaximoParaDescuentos(), fuenteNormalEspaciada, width2, 0f, 5f, margin);

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
