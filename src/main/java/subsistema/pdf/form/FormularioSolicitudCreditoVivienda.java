package subsistema.pdf.form;

import subsistema.pdf.dto.SucursalDTOPDF;
import subsistema.pdf.dto.UsuarioDTOPDF;
import subsistema.pdf.dto.SolicitudCreditoViviendaDTOPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.shapes.Cross;
import subsistema.pdf.lib.shapes.Rectangle;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.Column;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.utils.enums.TipoCreditoEnum;
import subsistema.pdf.utils.enums.TipoGarantiaEnum;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.*;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class FormularioSolicitudCreditoVivienda {

	private final float maxY         = Models.MODEL_3.getMaxY();
	private final float maxX         = Models.MODEL_3.getMaxX();
	private final float thickness    = Models.MODEL_3.getThickness();
	private final float marginStartX = Models.MODEL_3.getMarginStartX();
	private final float marginEndX   = Models.MODEL_3.getMarginEndX();
	private final FontEnum fontEnum  = Models.MODEL_3.getFont();

	private final Style fuenteMinimalEspaciada;
	private final Style fuenteNegrita;
	private final Style fuenteEstrechaNegrita;
	private final Style fuenteMinimal;
	private final Style fuenteMinimalNegrita;
	private final Style fuenteMinimalNegritaEspaciada;
	private final Style fuenteEstrecha;
	private final Style fuenteEstrechaInclinada;
	private Style fuenteNegritaMayor;

	public FormularioSolicitudCreditoVivienda(
			String ruta,
			SolicitudCreditoViviendaDTOPDF solicitud,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal,
			Date fechaExportacion) throws IOException {

		PDDocument doc = new PDDocument();

		FontGroup fontGroup    = FontGroup.getFontGroup(fontEnum,doc);
		fuenteNegrita                 = Style.getStyle(fontGroup, StyleEnum.CUSTOMISED_FORM_BOLD_SPACED);
		fuenteEstrechaNegrita         = Style.getStyle(fontGroup, StyleEnum.CUSTOMISED_FORM_NARROW_BOLD);
		fuenteMinimalNegrita          = Style.getStyle(fontGroup, StyleEnum.CUSTOMISED_FORM_NARROW_BOLD_GRAY);
		fuenteMinimalNegritaEspaciada = Style.getStyle(fontGroup, StyleEnum.CUSTOMISED_FORM_NARROW_BOLD_SPACED_GRAY);
		fuenteMinimal                 = Style.getStyle(fontGroup, StyleEnum.CUSTOMISED_FORM_TINY);
		fuenteMinimalEspaciada        = Style.getStyle(fontGroup, StyleEnum.CUSTOMISED_FORM_TINY_SPACED);
		fuenteEstrecha                = Style.getStyle(fontGroup, StyleEnum.CUSTOMISED_FORM_NARROW);
		fuenteEstrechaInclinada       = Style.getStyle(fontGroup, StyleEnum.CUSTOMISED_FORM_OBLIQUE);
		fuenteNegritaMayor            = Style.getStyle(fontGroup, StyleEnum.CUSTOMISED_FORM_LESS_BOLD_SPACED);

		PDPage page1 = new PDPage(new PDRectangle(maxX,maxY));
		PDPage page2 = new PDPage(new PDRectangle(maxX,maxY));

		doc.addPage(page1);
		doc.addPage(page2);

		PDPageContentStream contentStream1 = new PDPageContentStream(doc, page1);
		PDPageContentStream contentStream2 = new PDPageContentStream(doc, page2);

		GeneradorFormularioFactory.crearMargen(contentStream1, Models.MODEL_3);
		GeneradorFormularioFactory.crearCabecera(contentStream1, doc, Models.MODEL_3,Style.getStyle(fontGroup,StyleEnum.HEADER));
		GeneradorFormularioFactory.crearInfo(contentStream1, sucursal, Models.MODEL_3, Style.getStyle(fontGroup,StyleEnum.FOOTER));
		GeneradorFormularioFactory.crearFechaExportacion(contentStream1, fechaExportacion, Models.MODEL_3, Style.getStyle(fontGroup,StyleEnum.OWNER));
		GeneradorFormularioFactory.crearUsuarioExportador(contentStream1, usuarioEditor, Models.MODEL_3, Style.getStyle(fontGroup,StyleEnum.OWNER_BOLD),Style.getStyle(fontGroup,StyleEnum.OWNER));
		GeneradorFormularioFactory.crearTitulo(contentStream1, "FORMULARIO Nro. B-01\nSOLICITUD DE CREDITO PARA VIVIENDA", Models.MODEL_3, Style.getStyle(fontGroup,StyleEnum.TITLE));

		GeneradorFormularioFactory.crearMargen(contentStream2, Models.MODEL_3);
		GeneradorFormularioFactory.crearInfo(contentStream2, sucursal, Models.MODEL_3, Style.getStyle(fontGroup,StyleEnum.FOOTER));

		crearCuadroCarpeta(solicitud,contentStream1);
		
		crearTipoCredito(solicitud, contentStream1);
		crearTipoGarantia(solicitud, contentStream1);
		crearMontoPrestamoSolicitado(solicitud, contentStream1);
		crearDatosSolicitado(solicitud, contentStream1);

		GeneradorFormularioFactory.crearMargen(contentStream2, Models.MODEL_3);
		crearDatosGarantesPersonales(solicitud, contentStream2);
		crearCroquis(contentStream2,doc);
        crearNotas(contentStream2);
        crearNotaValidez(contentStream2);

		contentStream1.close();
		contentStream2.close();

        doc.save(new File(ruta));
        doc.close();
	}

	private void crearTipoCredito(
			SolicitudCreditoViviendaDTOPDF solicitudDTO,
			PDPageContentStream contentStream) throws IOException {

		float witdh = 500f;
		float initX = marginStartX + thickness;

		TipoCreditoEnum tipo = solicitudDTO.getTipoCreditoODestinoPrestamoSolicitado();

		Column columnTitulo = EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
				Arrays.asList("A. TIPO DE CREDITO O DESTINO DE PRESTAMO SOLICITADO: "),
				Arrays.asList(witdh), fuenteNegrita, 5f, 2.5f, Alignment.LEFT, true);

		Column column1 = EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
				Arrays.asList("CONSTRUCCION", "AMPLIACION", "ANTICRETICO"),
				Arrays.asList(witdh / 3, witdh / 3, witdh / 3), fuenteEstrechaNegrita, 35, 5f, Alignment.LEFT, false);

		Column column2 = EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
				Arrays.asList("COMPRA DE TERRENO VIVIENTA O DEPARTAMENTO", "REFACCION"),
				Arrays.asList(2 * witdh / 3, witdh / 3), fuenteEstrechaNegrita, 35, 5f, Alignment.LEFT, false);

		Cell celdaCuerpo = EasyComponentsFactory.getSimpleCell(0, 5, true,
				EasyComponentsFactory.getSimpleTable(0, 0, column1, column2)
		);

		Column cuerpo = EasyComponentsFactory.getSimpleColumn(celdaCuerpo);

		SimpleTable tabla = EasyComponentsFactory.getSimpleTable(initX, maxY - 220, columnTitulo, cuerpo);
		tabla.draw(contentStream);

		Rectangle rectangle1 = new Rectangle(column1.getStartX()+10          , column1.getStartY()-2.5f, 15, -10);
		Rectangle rectangle2 = new Rectangle(column1.getStartX()+witdh/3+10  , column1.getStartY()-2.5f, 15, -10);
		Rectangle rectangle3 = new Rectangle(column1.getStartX()+2*witdh/3+10, column1.getStartY()-2.5f, 15, -10);

		Rectangle rectangle4 = new Rectangle(column2.getStartX()+10          , column2.getStartY()-2.5f, 15, -10);
		Rectangle rectangle5 = new Rectangle(column2.getStartX()+2*witdh/3+10, column2.getStartY()-2.5f, 15, -10);

		rectangle1.draw(contentStream);
		rectangle2.draw(contentStream);
		rectangle3.draw(contentStream);
		rectangle4.draw(contentStream);
		rectangle5.draw(contentStream);

		Cross cross1 = Cross.builder().addRectangle(rectangle1).addColor(Color.GRAY).addThickness(2).build();
		Cross cross2 = Cross.builder().addRectangle(rectangle2).addColor(Color.GRAY).addThickness(2).build();
		Cross cross3 = Cross.builder().addRectangle(rectangle3).addColor(Color.GRAY).addThickness(2).build();
		Cross cross4 = Cross.builder().addRectangle(rectangle4).addColor(Color.GRAY).addThickness(2).build();
		Cross cross5 = Cross.builder().addRectangle(rectangle5).addColor(Color.GRAY).addThickness(2).build();

		if(tipo == TipoCreditoEnum.CONSTRUCCION)
			cross1.draw(contentStream);
		if(tipo == TipoCreditoEnum.AMPLIACION)
			cross2.draw(contentStream);
		if(tipo == TipoCreditoEnum.ANTICRETICO)
			cross3.draw(contentStream);
		if(tipo == TipoCreditoEnum.COMPRA_TERRENO_VIVIENDA_DEPARTAMENTO)
			cross4.draw(contentStream);
		if(tipo == TipoCreditoEnum.REFACCION)
			cross5.draw(contentStream);

		Rectangle borde = new Rectangle(tabla.getStartX(), tabla.getStartY(), tabla.getWidth(), tabla.getHeight());
		borde.draw(contentStream);
	}

	private void crearTipoGarantia(
			SolicitudCreditoViviendaDTOPDF solicitudDTO,
			PDPageContentStream contentStream) throws IOException {

		float witdh = 500f;
		float initX = marginStartX + thickness;

		TipoGarantiaEnum tipo = solicitudDTO.getTipoGarantia();

		Column columnTitulo = EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
				Arrays.asList("B. TIPO DE GARANTIA: "),
				Arrays.asList(witdh), fuenteNegrita, 5f, 2.5f, Alignment.LEFT, true);

		Column column1 = EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
				Arrays.asList("REAL (GRAVAMEN HIPOTECARIO)",
						"PERSONAL (GARANTIA DE HABERES Y\nPRESENTACION DE DOS GARANTES)"),
				Arrays.asList(witdh / 2, witdh / 2), fuenteEstrechaNegrita, 35, 5f, Alignment.LEFT, false);

		Cell celdaCuerpo = EasyComponentsFactory.getSimpleCell(0, 5, true,
				EasyComponentsFactory.getSimpleTable(0, 0, column1)
		);

		Column cuerpo = EasyComponentsFactory.getSimpleColumn(celdaCuerpo);

		SimpleTable tabla = EasyComponentsFactory.getSimpleTable(initX, maxY - 290, columnTitulo, cuerpo);
		tabla.draw(contentStream);

		Rectangle rectangle1 = new Rectangle(column1.getStartX()+10          , column1.getStartY()-2.5f, 15, -10);
		Rectangle rectangle2 = new Rectangle(column1.getStartX()+witdh/2+10  , column1.getStartY()-2.5f, 15, -10);

		rectangle1.draw(contentStream);
		rectangle2.draw(contentStream);

		Cross cross1 = Cross.builder().addRectangle(rectangle1).addColor(Color.GRAY).addThickness(2).build();
		Cross cross2 = Cross.builder().addRectangle(rectangle2).addColor(Color.GRAY).addThickness(2).build();

		if(tipo == TipoGarantiaEnum.REAL)
			cross1.draw(contentStream);
		if(tipo == TipoGarantiaEnum.PERSONAL)
			cross2.draw(contentStream);

		Rectangle borde = new Rectangle(tabla.getStartX(), tabla.getStartY(), tabla.getWidth(), tabla.getHeight());
		borde.draw(contentStream);
	}

	private void crearMontoPrestamoSolicitado(
			SolicitudCreditoViviendaDTOPDF solicitudDTO,
			PDPageContentStream contentStream) throws IOException {

		float witdh = 500f;
		float initX = marginStartX + thickness;

		Column columnTitulo = EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
				Arrays.asList("C. MONTO DE PRESTAMO SOLICITADO: "),
				Arrays.asList(witdh), fuenteNegrita, 5f, 2.5f, Alignment.LEFT, true);

		Column column1 = EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStylesAndRelativePosition(
				Arrays.asList(
						"",
						"BS.-",
						solicitudDTO.getMontoPrestamoSolicitadoBs(),
						"En UFV.-",
						solicitudDTO.getMontoPrestamoSolicitadoUFV(),
						"Tipo de cambio oficial: UFV.-",
						solicitudDTO.getTipoCambioOficialUFV()
				),
				Arrays.asList(25f,30f,80f,50f,70f,170f,65f),
				Arrays.asList(
						fuenteEstrecha,
						fuenteEstrecha,
						fuenteMinimalNegrita,
						fuenteEstrecha,
						fuenteMinimalNegrita,
						fuenteEstrechaInclinada,
						fuenteMinimalNegrita
				),
				2.5f, 5f, Alignment.LEFT, false);

		Cell celdaCuerpo = EasyComponentsFactory.getSimpleCell(5f, 2.5f, true,
				EasyComponentsFactory.getSimpleTable(0, 0, column1)
		);

		Column cuerpo = EasyComponentsFactory.getSimpleColumn(celdaCuerpo);

		SimpleTable tabla = EasyComponentsFactory.getSimpleTable(initX, maxY - 350, columnTitulo, cuerpo);
		tabla.draw(contentStream);

		Rectangle borde = new Rectangle(tabla.getStartX(), tabla.getStartY(), tabla.getWidth(), tabla.getHeight());
		borde.draw(contentStream);
	}

	private void crearDatosSolicitado(
			SolicitudCreditoViviendaDTOPDF solicitud,
			PDPageContentStream contentStream) throws IOException {

		float initX  = marginStartX+thickness;
		float witdh = 500f;

		Color colorGris      = new Color(60,60,60);
		Color colorGrisClaro = new Color(160,160,160);


		float anchoRectangulo = 20f;

		Rectangle rectangulo0101 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0102 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0201 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0202 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0301 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0302 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0303 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0304 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();


		Rectangle rectangulo0401 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0402 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0501 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0502 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0601 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();

		Rectangle rectangulo0602 = Rectangle.builder()
				.addStartX(0)
				.addStartY(0)
				.addHeight(-10f)
				.addWidth(anchoRectangulo)
				.addColor(Color.BLACK)
				.build();


		Column columnTitulo = EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
				Collections.singletonList("D. DATOS DEL SOLICITANTE: "),
				Collections.singletonList(witdh), fuenteNegrita, 5f, 2.5f, Alignment.LEFT, true);

		SimpleTable tabla = EasyComponentsFactory.getSimpleTable(initX, maxY - 395,
				columnTitulo,
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getApellidoPaternoSolicitante(),
														solicitud.getApellidoMaternoSolicitante(),
														solicitud.getNombreSolicitante(),
														solicitud.getGradoSolicitante()
												),
												Arrays.asList(120f,120f,180f,80f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"APELLIDO PATERNO",
														"APELLIDO MATERNO",
														"NOMBRES","GRADO"
												),
												Arrays.asList(120f,120f,180f,80f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getCedulaIdentidadSolicitante(),
														solicitud.getCategoriaPorcentajeSolicitante(),
														solicitud.getUnidadSolicitante(),
														solicitud.getLiquidoPagableBsSueldoSolicitante(),
														solicitud.getAntiguedadBsSolicitante(),
														solicitud.getPostGradoBsSolicitante(),
														solicitud.getBonoBsSegSolicitante(),
														solicitud.getAniosServicioSolicitante()
												),
												Arrays.asList(70f,60f,40f,80f,60f,65f,75f,50f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"N* DE CELULA\nIDENTIDAD",
														"CATEGORIA\nEN %",
														"N* de\nUNIDAD",
														"Liquido Pagable\nSUELDO (Bs.)",
														"ANTIGUEDAD\n(Bs.)",
														"POST GRADO\nEN (Bs.)",
														"BONO SEG.\nCIUDADANA (Bs.)",
														"AÑOS DE SERVICIO"
												),
												Arrays.asList(70f,60f,40f,80f,60f,65f,75f,50f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Collections.singletonList("Llena de acuerdo a los datos de la boleta de pago"),
												Collections.singletonList(witdh),
												fuenteMinimal,  Alignment.CENTER,
												5f, 1.5f,
												true,true,Color.BLACK,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumn(
												EasyComponentsFactory.getSimpleCell(
														0f, 0f,
														true,false,colorGrisClaro,colorGrisClaro,
														EasyComponentsFactory.getSimpleTable(0, 0,
																EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
																		Arrays.asList(
																				solicitud.getFechaNacimientoSolicitanteDia(),
																				solicitud.getFechaNacimientoSolicitanteMes(),
																				solicitud.getFechaNacimientoSolicitanteAnio()
																		),
																		Arrays.asList(40f,40f,40f),
																		fuenteMinimalNegrita,  Alignment.CENTER,
																		5f, 1.5f,
																		true,false,colorGrisClaro,colorGrisClaro),
																EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
																		Arrays.asList("Dia","Mes","Año"),
																		Arrays.asList(40f,40f,40f),
																		fuenteMinimal,  Alignment.CENTER,
																		5f, 1.5f,
																		true,false,colorGrisClaro,colorGrisClaro)
														)
												),
												EasyComponentsFactory.getSimpleCellFromText(
														solicitud.getCiudadLocalidadSolicitanteDomilicioActual(),
														fuenteMinimalNegrita,
														160f,
														5f, 5f,
														Alignment.CENTER,true,false,colorGrisClaro,colorGrisClaro
												),
												EasyComponentsFactory.getSimpleCellFromText(
														solicitud.getProvinciaSolicitante(),
														fuenteMinimalNegrita,
														110f,
														5f, 5f,
														Alignment.CENTER,true,false,colorGrisClaro,colorGrisClaro
												),
												EasyComponentsFactory.getSimpleCellFromText(
														solicitud.getDepartamentoSolicitante(),
														fuenteMinimalNegrita,
														110f,
														5f, 5f,
														Alignment.CENTER,true,false,colorGrisClaro,colorGrisClaro
												)
										),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"FECHA DE NACIMIENTO",
														"CIUDAD / LOCALIDAD",
														"PROVINCIA",
														"DEPARTAMENTO"
												),
												Arrays.asList(120f,160f,110f,110f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getEdadSolicitante(),
														solicitud.getEstadoCivilSolicitante(),
														solicitud.getDependientesSolicitante(),
														solicitud.getTotalGrupoFamiliarSolicitante()
												),
												Arrays.asList(100f,120f,140f,140f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"EDAD",
														"ESTADO CIVIL",
														"DEPENDIENTES",
														"TOTAL GRUPO FAMILIAR"
												),
												Arrays.asList(100f,120f,140f,140f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getCalleAvenidaSolicitante(),
														solicitud.getNumeroCasaSolicitante(),
														solicitud.getZonaBarrioSolicitante(),
														solicitud.getCiudadLocalidadSolicitante(),
														solicitud.getDepartamentoSolicitante()
												),
												Arrays.asList(110f,30f,120f,120f,120f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"CALLE / AVENIDA",
														"Nro",
														"ZONA / BARRIO",
														"CIUDAD / LOCALIDAD",
														"DEPARTAMENTO"
												),
												Arrays.asList(110f,30f,120f,120f,120f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Collections.singletonList("DATOS DEL DOMICILIO ACTUAL"),
												Collections.singletonList(witdh),
												fuenteMinimal,  Alignment.CENTER,
												5f, 1.5f,
												true,true,Color.BLACK,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getLugarInmuebleNumeroDomicilioDestinoActual(),
														solicitud.getNumeroCelularSolicitanteDomilicioActual(),
														solicitud.getNumeroTelefonoDomicilioSolicitanteDomilicioActual(),
														solicitud.getNumeroTelefonoReferenciaSolicitanteDomilicioActual()
												),
												Arrays.asList(120f,130f,120f,130f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"Nro TELEFONO DOM.",
														"Nro CELULAR",
														"TELEFONO LABORAL",
														"TELEFONO REFERENCIA"
												),
												Arrays.asList(120f,130f,120f,130f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getUnidadPolicialSolicitanteDomilicioActual(),
														solicitud.getCiudadLocalidadSolicitanteDomilicioActual(),
														solicitud.getDepartamentoSolicitanteDomilicioActual()
												),
												Arrays.asList(260f,120f,120f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"REPARTICION POLICIAL UNIDAD",
														"CIUDAD / LOCALIDAD",
														"DEPARTAMENTO"
												),
												Arrays.asList(260f,120f,120f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Collections.singletonList("DESTINO ACTUAL DEL SOLICITANTE"),
												Collections.singletonList(witdh),
												fuenteMinimal,  Alignment.CENTER,
												5f, 1.5f,
												true,true,Color.BLACK,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Collections.singletonList("SITUACION HABITACIONAL DEL SOLICITANTE: (Marque con una x)"),
												Collections.singletonList(500f),
												fuenteMinimal,  Alignment.LEFT,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumn(
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("SI",fuenteMinimalNegrita,45f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0101)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("NO",fuenteMinimalNegrita,45f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0102)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("SI",fuenteMinimalNegrita,45f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0201)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("NO",fuenteMinimalNegrita,45f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0202)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("PROPIA",fuenteMinimalNegrita,60f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0301)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("ALQUILADA",fuenteMinimalNegrita,75f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0302)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("ANTICRETICO",fuenteMinimalNegrita,85f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0303)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("CEDIDA",fuenteMinimalNegrita,60f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0304)
																)
														)
												)
										),

										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"TIENE TERRENO PROPIO",
														"TIENE VIVIENDA PROPIA",
														"HABITA EN VIVIENDA"
												),
												Arrays.asList(100f,100f,300f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Collections.singletonList("PRESTAMOS ANTERIORES: (Marque con una x)"),
												Collections.singletonList(witdh),
												fuenteMinimal,  Alignment.LEFT,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumn(
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("SI",fuenteMinimalNegrita,50f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0401)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("NO",fuenteMinimalNegrita,50f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0402)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("SI",fuenteMinimalNegrita,50f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0501)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("NO",fuenteMinimalNegrita,50f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0502)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("SI",fuenteMinimalNegrita,65f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0601)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f,0f,true,false,colorGrisClaro,Color.WHITE,
														EasyComponentsFactory.getSimpleTable(0,0,
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCellFromText("NO",fuenteMinimalNegrita,65f-anchoRectangulo,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE),
																		EasyComponentsFactory.getSimpleCell(2.5f,2.5f,false,false,colorGrisClaro,Color.WHITE,rectangulo0602)
																)
														)
												),
												EasyComponentsFactory.getSimpleCellFromText(
														solicitud.getNumeroCreditosAnteriores(),
														fuenteMinimalNegrita,140f,5f,2.5f,Alignment.CENTER,false,false,colorGrisClaro,Color.WHITE
												)
										)
										,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"TERRENO",
														"VIVIENDA PROPIA",
														"ANTICRETICO",
														"Nros CREDITOS ANTERIORES"
												),
												Arrays.asList(110f,110f,140f,140f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList("LUGAR DE UBICACION DEL BIEN INMUEBLE SUJETO DE COMPRA, ANTICRETICO O REFACCION: "),
												Arrays.asList(witdh),
												fuenteMinimal,  Alignment.LEFT,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getCalleAvenidaSolicitante(),
														solicitud.getLugarInmuebleNumeroDomicilioDestinoActual(),
														solicitud.getZonaBarrioSolicitante(),
														solicitud.getCiudadLocalidadSolicitante(),
														solicitud.getDepartamentoSolicitante()
												),
												Arrays.asList(170f,30f,100f,100f,100f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"CALLE / AVENIDA",
														"Nro",
														"ZONA / BARRIO",
														"CIUDAD / LOCALIDAD",
														"DEPARTAMENTO"
												),
												Arrays.asList(170f,30f,100f,100f,100f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
												Arrays.asList(
														"LUGAR Y FECHA DE LA SUBSCRIPCION DEL FORMULARIO: ",
														String.format(
																"%s - %s",
																solicitud.getCiudadLocalidadSolicitante(),
																solicitud.getFechaSuscripcionFormularioDestinoActual())
												),
												Arrays.asList(230f,270f),
												Arrays.asList(fuenteMinimal,fuenteMinimalNegrita),  Alignment.LEFT,
												5f, 2.5f,
												false,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(""),
												Arrays.asList(witdh),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(" "),
												Arrays.asList(200f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 25f,
												false,false,colorGrisClaro,colorGrisClaro),EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList("...................."),
												Arrays.asList(200f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												false,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList("FIRMA SOLICITANTE"),
												Arrays.asList(200f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 5f,
												false,false,colorGrisClaro,colorGrisClaro)
								)
						),
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList("OBSERVACIONES"),
												Arrays.asList(300f),
												fuenteMinimal,  Alignment.LEFT,
												5f, 2.5f,
												false,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(solicitud.getObservaciones()),
												Arrays.asList(300f),
												fuenteMinimalNegritaEspaciada,  Alignment.LEFT,
												5f, 2.5f,
												false,false,colorGrisClaro,colorGrisClaro)
								)
						)
				)

		);
		tabla.draw(contentStream);

		Rectangle borde = new Rectangle(tabla.getStartX(), tabla.getStartY(), tabla.getWidth(), tabla.getHeight());
		borde.draw(contentStream);
	}

	private void crearDatosGarantesPersonales(
			SolicitudCreditoViviendaDTOPDF solicitud,
			PDPageContentStream contentStream) throws IOException {

		float initX  = marginStartX+thickness;
		float witdh = 500f;

		String fechaGarante1 = solicitud.getFechaGarante1();
		String fechaGarante2 = solicitud.getFechaGarante2();

		Color colorGris      = new Color(60,60,60);
		Color colorGrisClaro = new Color(160,160,160);

		Column columnTitulo = EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
				Arrays.asList("E. DATOS DE LOS GARANTES PERSONALES "),
				Arrays.asList(witdh), fuenteNegrita, 5f, 2.5f, Alignment.LEFT, true);

		SimpleTable tabla = EasyComponentsFactory.getSimpleTable(initX, maxY - 40,
				columnTitulo,
				EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
						Arrays.asList("1) PRIMER GARANTE"),
						Arrays.asList(witdh), fuenteMinimalNegrita, 5f, 2.5f, Alignment.LEFT, true
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getApellidoPaternoGarante1(),
														solicitud.getApellidoMaternoGarante1(),
														solicitud.getNombreGarante1(),
														solicitud.getGradoGarante1()
												),
												Arrays.asList(120f,120f,180f,80f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"APELLIDO PATERNO",
														"APELLIDO MATERNO",
														"NOMBRES",
														"GRADO"
												),
												Arrays.asList(120f,120f,180f,80f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getCedulaIdentidadGarante1(),
														solicitud.getCategoriaPorcentajeGarante1(),
														solicitud.getUnidadGarante1(),
														solicitud.getSueldoGarante1(),
														solicitud.getAntiguedadGarante1(),
														solicitud.getPostGradoEnBsGarante1(),
														solicitud.getBonoBSCBsGarante1()
												),
												Arrays.asList(75f,65f,45f,85f,70f,75f,85f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"N* DE CELULA\nIDENTIDAD",
														"CATEGORIA\nEN %",
														"N* de\nUNIDAD",
														"SUELDO (Bs.)",
														"ANTIGUEDAD\n(Bs.)",
														"POST GRADO\nEN (Bs.)",
														"BONO B.S.C.EN\n (Bs.)"
												),
												Arrays.asList(75f,65f,45f,85f,70f,75f,85f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList("Llena de acuerdo a los datos de la boleta de pago"),
												Arrays.asList(witdh),
												fuenteMinimal,  Alignment.LEFT,
												5f, 1.5f,
												true,true,Color.BLACK,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getAniosServicioGarante1(),
														solicitud.getTelefonoCelularGarante1(),
														solicitud.getDestinoActualGarante1(),
														solicitud.getDepartamentoGarante1()
												),
												Arrays.asList(66f,132f,200f,102f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"AÑOS DE SERVICIO",
														"TELEFONO / CELULAR",
														"DESTINO ACTUAL (UNIDAD)",
														"DEPARTAMENTO"
												),
												Arrays.asList(66f,132f,200f,102f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
						Arrays.asList("2) PRIMER GARANTE"),
						Arrays.asList(witdh), fuenteMinimalNegrita, 5f, 2.5f, Alignment.LEFT, true
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getApellidoPaternoGarante2(),
														solicitud.getApellidoMaternoGarante2(),
														solicitud.getNombreGarante2(),
														solicitud.getGradoGarante2()
												),
												Arrays.asList(120f,120f,180f,80f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"APELLIDO PATERNO",
														"APELLIDO MATERNO",
														"NOMBRES",
														"GRADO"
												),
												Arrays.asList(120f,120f,180f,80f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getCedulaIdentidadGarante2(),
														solicitud.getCategoriaPorcentajeGarante2(),
														solicitud.getUnidadGarante2(),
														solicitud.getSueldoGarante2(),
														solicitud.getAntiguedadGarante2(),
														solicitud.getPostGradoEnBsGarante2(),
														solicitud.getBonoBSCBsGarante2()
												),
												Arrays.asList(75f,65f,45f,85f,70f,75f,85f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"N* DE CELULA\nIDENTIDAD",
														"CATEGORIA\nEN %",
														"N* de\nUNIDAD",
														"SUELDO (Bs.)",
														"ANTIGUEDAD\n(Bs.)",
														"POST GRADO\nEN (Bs.)",
														"BONO B.S.C.EN\n (Bs.)"
												),
												Arrays.asList(75f,65f,45f,85f,70f,75f,85f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList("Llena de acuerdo a los datos de la boleta de pago"),
												Arrays.asList(witdh),
												fuenteMinimal,  Alignment.LEFT,
												5f, 1.5f,
												true,true,Color.BLACK,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														solicitud.getAniosServicioGarante2(),
														solicitud.getTelefonoCelularGarante2(),
														solicitud.getDestinoActualGarante2(),
														solicitud.getDepartamentoGarante2()
												),
												Arrays.asList(66f,132f,200f,102f),
												fuenteMinimalNegrita,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro),
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"AÑOS DE SERVICIO",
														"TELEFONO / CELULAR",
														"DESTINO ACTUAL (UNIDAD)",
														"DEPARTAMENTO"
												),
												Arrays.asList(66f,132f,200f,102f),
												fuenteMinimal,  Alignment.CENTER,
												5f, 2.5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
												Arrays.asList(
														"LOS GARANTES SOLIDARIA Y MANCOMUNADAMENTE ANTE EL INCUMPLIMIENTO DE LA OBLIGACION DEL DEUDOR(AFILIADO)"
												),
												Arrays.asList(witdh),
												fuenteMinimal,  Alignment.LEFT,
												5f, 5f,
												true,false,colorGrisClaro,colorGrisClaro)
								)
						)
				),
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0f, 0f, true,false,colorGris,colorGris,
								EasyComponentsFactory.getSimpleTable(0, 0,
										EasyComponentsFactory.getSimpleColumn(
												EasyComponentsFactory.getSimpleCell(
														0f, 0f,
														true,false,colorGrisClaro,colorGrisClaro,
														EasyComponentsFactory.getSimpleTable(0, 0,
																EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
																		Arrays.asList(" "),
																		Arrays.asList(250f),
																		fuenteMinimalNegrita,  Alignment.CENTER,
																		5f, 25f,
																		false,false,colorGrisClaro,colorGrisClaro),EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
																		Arrays.asList("...................."),
																		Arrays.asList(250f),
																		fuenteMinimal,  Alignment.CENTER,
																		5f, 2.5f,
																		false,false,colorGrisClaro,colorGrisClaro
																),
																EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
																		Arrays.asList("FIRMA SOLICITANTE"),
																		Arrays.asList(250f),
																		fuenteMinimal,  Alignment.CENTER,
																		5f, 5f,
																		false,false,colorGrisClaro,colorGrisClaro
																),
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCell(0f, 0f, true,false,colorGrisClaro,colorGrisClaro,
																				EasyComponentsFactory.getSimpleTable(0, 5f,
																						EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
																								Arrays.asList(
																										"LUGAR Y FECHA:",
																										String.format("%s, %s",
																												solicitud.getLugarGarante1(),
																												fechaGarante1)
																								),
																								Arrays.asList(80f,170f),
																								Arrays.asList(
																										fuenteMinimal,
																										fuenteMinimalNegrita
																								)
																								,  Alignment.LEFT,
																								5f, 5f,
																								false,false,colorGrisClaro,colorGrisClaro
																						)
																				)
																		)
																)
														)
												),
												EasyComponentsFactory.getSimpleCell(
														0f, 0f,
														true,false,colorGrisClaro,colorGrisClaro,
														EasyComponentsFactory.getSimpleTable(0, 0,
																EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
																		Arrays.asList(" "),
																		Arrays.asList(250f),
																		fuenteMinimalNegrita,  Alignment.CENTER,
																		5f, 25f,
																		false,false,colorGrisClaro,colorGrisClaro),EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
																		Arrays.asList("...................."),
																		Arrays.asList(250f),
																		fuenteMinimal,  Alignment.CENTER,
																		5f, 2.5f,
																		false,false,colorGrisClaro,colorGrisClaro
																),
																EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
																		Arrays.asList("FIRMA SOLICITANTE"),
																		Arrays.asList(250f),
																		fuenteMinimal,  Alignment.CENTER,
																		5f, 5f,
																		false,false,colorGrisClaro,colorGrisClaro
																),
																EasyComponentsFactory.getSimpleColumn(
																		EasyComponentsFactory.getSimpleCell(0f, 0f, true,false,colorGrisClaro,colorGrisClaro,
																				EasyComponentsFactory.getSimpleTable(0, 5f,
																						EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
																								Arrays.asList(
																										"LUGAR Y FECHA:",
																										String.format("%s, %s",
																												solicitud.getLugarGarante1(),
																												fechaGarante1)
																								),
																								Arrays.asList(80f,170f),
																								Arrays.asList(
																										fuenteMinimal,
																										fuenteMinimalNegrita
																								)
																								,  Alignment.LEFT,
																								5f, 5f,
																								false,false,colorGrisClaro,colorGrisClaro
																						)
																				)
																		)
																)
														)
												)
										)
								)
						)
				)
		);
		tabla.draw(contentStream);

		Rectangle borde = new Rectangle(tabla.getStartX(), tabla.getStartY(), tabla.getWidth(), tabla.getHeight());
		borde.draw(contentStream);
	}

	private void crearCroquis(
			PDPageContentStream contentStream,
			PDDocument doc) throws IOException {

		float initX = marginStartX + thickness;
		float witdh = 500f;

		Column columnTitulo = EasyComponentsFactory.getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
				Arrays.asList("F. CROQUIS DE UBICACION DEL INMUEBLE SUJETO DE ANTICRESIS, COMPRA, REFACCION O " +
						"CONSTRUCCION"),
				Arrays.asList(witdh), fuenteNegrita, 5f, 2.5f, Alignment.LEFT, true);

		SimpleTable tabla = EasyComponentsFactory.getSimpleTable(initX, maxY - 410,
				columnTitulo,
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCell(0,0,false,
								Rectangle.builder().addStartX(0f).addStartY(0f).addWidth(500f).addHeight(-275).build()
						)
				)
		);

		tabla.draw(contentStream);

		GeneradorFormularioFactory.dibujarFlecha(contentStream,doc,Models.MODEL_3);
	}

	private void crearNotas(
			PDPageContentStream contentStream) throws IOException {
		float initX = marginStartX + thickness;

		SimpleTable tabla = EasyComponentsFactory.getSimpleTable(initX, maxY - 720,
				EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
						Arrays.asList("NOTA 1:","Elaborar el croquis con referencias a un punto especifico (Avenida calle principal, plazas, parques, iglesias, parada de buses y otros)"),
						Arrays.asList(80f,420f) ,
						Arrays.asList(fuenteMinimal,fuenteMinimalEspaciada),
						Alignment.LEFT,10f,5f,false,false,Color.WHITE,Color.WHITE
				),
				EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
						Arrays.asList("NOTA 2:","Cualquier tachadura, raspadura o borron, INVALIDA el presente formulario"),
						Arrays.asList(80f,420f) ,
						Arrays.asList(fuenteMinimal,fuenteMinimalEspaciada),
						Alignment.LEFT,10f,5f,false,false,Color.WHITE,Color.WHITE
				),
				EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
						Arrays.asList("NOTA 3:","Los datos consignados en la presenta solicitud son veraces y tienen el rango de declaracion jurada, asimismo se compromete" +
								"hacer el buen uso del objeto de credito"),
						Arrays.asList(80f,420f) ,
						Arrays.asList(fuenteMinimal,fuenteMinimalEspaciada),
						Alignment.LEFT,10f,5f,false,false,Color.WHITE,Color.WHITE
				),
				EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
						Arrays.asList("NOTA 4:","El solicitante debe cumplir con los requisitos exigidos de acuerdo a la modalidad del credito"),
						Arrays.asList(80f,420f) ,
						Arrays.asList(fuenteMinimal,fuenteMinimalEspaciada),
						Alignment.LEFT,10f,0f,false,false,Color.WHITE,Color.WHITE
				)
		);

		tabla.draw(contentStream);
	}

	private void crearNotaValidez(PDPageContentStream contentStream) throws IOException {
		float initX = marginStartX + thickness;
		float witdh = 500f;

		SimpleTable tabla = EasyComponentsFactory.getSimpleTable(initX, maxY - 800,
				EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
						Arrays.asList("EL PRESENTE FORMULARIO TIENE VALIDEZ DE TRES(3) MESES A PARTIR DE SU ADQUISICION"),
						Arrays.asList(witdh) ,
						Arrays.asList(fuenteNegritaMayor),
						Alignment.CENTER,10f,5f,false,false,Color.WHITE,Color.WHITE
				)
		);

		tabla.draw(contentStream);
	}

	private void crearCuadroCarpeta(
			SolicitudCreditoViviendaDTOPDF solicitud,
			PDPageContentStream contentStream) throws IOException {

		float initX  = marginStartX+thickness;

		SimpleTable tabla = EasyComponentsFactory.getSimpleTable(initX, maxY - 200,
				EasyComponentsFactory.getSimpleColumn(
						EasyComponentsFactory.getSimpleCellFromText("Carpeta Nro",fuenteNegritaMayor,80f,5f,2.5f,Alignment.LEFT,false,false,Color.BLACK,Color.WHITE),
						EasyComponentsFactory.getSimpleCellFromText(
								solicitud.getNumeroCarpeta(),
								fuenteEstrechaNegrita,75f,5f,2.5f,Alignment.LEFT,true,false,Color.BLACK,Color.WHITE),
						EasyComponentsFactory.getSimpleCellFromText("",fuenteEstrechaNegrita,200f,5f,2.5f,Alignment.LEFT,false,false,Color.BLACK,Color.WHITE),
						EasyComponentsFactory.getSimpleCellFromText("Registro Nro",fuenteNegritaMayor,80f,5f,2.5f,Alignment.LEFT,false,false,Color.BLACK,Color.WHITE),
						EasyComponentsFactory.getSimpleCellFromText(
								solicitud.getNumeroRegistro(),
								fuenteEstrechaNegrita,60f,5f,2.5f,Alignment.LEFT,true,false,Color.BLACK,Color.WHITE)
				)
		);
		tabla.draw(contentStream);
	}
}
