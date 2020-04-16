package utils;

import dto.*;
import lib.basic.Alignment;
import lib.basic.Style;
import lib.shapes.Line;
import lib.tables.Cell;
import lib.tables.SimpleTable;
import lib.text.MultipleParagraph;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import lib.shapes.Rectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.Color;
import java.io.IOException;
import java.util.Date;

public class GeneradorFormularioFactory {
    private static float maxY = 0;
    private static float maxX = 0;

    private static float thickness         = 15f;
    private static int thickness2          = 10;

    private static float marginStartX      = 50f;
    private static float marginEndX        = 25f;

    private static float relativePositionX = 5f;
    private static float relativePositionY = 5f;


    private static final String imagenPolicia =
            "/home/rudy/polbol.png";
    private static final String imagenCovipol =
            "/home/rudy/covipol.png";
   // private static final String flechaNorte =
    //        "./src/main/resources/north_arrow.png";

    // METODOS BASICOS
    public static void setDimensiones(float width, float height) {
        maxX = width;
        maxY = height;
    }

    // METODOS GENERICOS

    public static void crearMargen(PDPageContentStream contentStream) throws IOException {
        crearMargen(contentStream,marginStartX, 40, maxX - (marginStartX + marginEndX), maxY - 65);
    }

    public static void crearCabecera(
            PDPageContentStream contentStream,
            PDDocument document) throws IOException {

        Style fuenteCabecera = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(18)
                .addLeading(1f)
                .addTextColor(Color.BLACK)
                .build();

        dibujarImagen(
                contentStream,document, imagenPolicia,
                marginStartX+thickness+8,maxY-75-80,66,80);

        dibujarImagen(
                contentStream,document, imagenCovipol,
                maxX - (marginEndX+thickness+80),maxY-75-80,80,80);

        MultipleParagraph.builder()
                .addTextContent("CONSEJO NACIONAL DE VIVIENDA POLICIAL")
                .addAlignment(Alignment.CENTER)
                .addStartX(115)
                .addStartY(maxY-50)
                .addWidth(maxX-2*100)
                .addStyle(fuenteCabecera)
                .build()
                .draw(contentStream);

        Rectangle.builder()
                .addStartX(marginStartX+thickness)
                .addStartY(maxY-40)
                .addWidth(maxX - (marginStartX + marginEndX) - 2*thickness)
                .addHeight(-120)
                .addThickness(2)
                .addColor(Color.BLACK)
                .build()
                .draw(contentStream);

        new Line(marginStartX+thickness, maxY-75,maxX-(marginEndX+thickness),maxY-75)
                .setColor(Color.BLACK)
                .setThickness(1)
                .draw(contentStream);

        new Line(marginStartX+thickness+80, maxY-75,marginStartX+thickness+80,maxY-75-80)
                .setColor(Color.BLACK)
                .setThickness(1)
                .draw(contentStream);

        new Line(maxX - (marginEndX+thickness+80), maxY-75,maxX - (marginEndX+thickness+80),maxY-75-80)
                .setColor(Color.BLACK)
                .setThickness(1)
                .draw(contentStream);
    }

    public static void crearInfo(
            PDPageContentStream contentStream,
            SucursalDTO sucursalDTO) throws IOException {

        Style fuenteDiminuta = Style.builder()
                .addFontSize(8)
                .addTextFont(PDType1Font.HELVETICA)
                .build();

        String informacion = String.format(
                "%s: %s\n%s: %s  -  %s: %s  -  %s: %s",
                "DIRECCION"         , sucursalDTO.getDireccion(),
                "TELEFONOS"         , sucursalDTO.getTelefono(),
                "FAX"               , sucursalDTO.getFax(),
                "CORREO ELECTRONICO", "covipol.afiliaciones@covipol.gob.bo"
        );

        MultipleParagraph.builder()
                .addStartX(100f)
                .addStartY(35f)
                .addWidth(430f)
                .addAlignment(Alignment.CENTER)
                .addTextContent(informacion)
                .addStyle(fuenteDiminuta)
                .build()
                .draw(contentStream);
    }

    public static void crearFechaExportacion(
            PDPageContentStream contentStream,
            Date fechaExportacion) throws IOException {

        float auxWidth = 120f;
        Fecha fecha = new Fecha(fechaExportacion);

        Style fuenteNormal = Style.builder()
                .addFontSize(10.5f)
                .addTextFont(PDType1Font.HELVETICA)
                .addTextColor(Color.BLACK)
                .addLeading(1.1f)
                .build();

        MultipleParagraph.builder()
                .addStartX( maxX - (marginEndX+thickness+auxWidth) + relativePositionX)
                .addStartY( maxY - 160 - relativePositionY)
                .addWidth( auxWidth )
                .addTextContent(fecha.getFormatoHoraFecha())
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearUsuarioExportador(
            PDPageContentStream contentStream,
            UsuarioDTO usuarioEditor) throws IOException {

        Style fuenteNormal = Style.builder()
                .addFontSize(10.5f)
                .addTextFont(PDType1Font.HELVETICA)
                .addTextColor(Color.BLACK)
                .addLeading(1.1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addFontSize(10.5f)
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addTextColor(Color.BLACK)
                .addLeading(1.1f)
                .build();

        float width1 = 100f;
        float width2 = 240f;

        MultipleParagraph.builder()
                .addStartX( marginStartX + thickness + relativePositionX )
                .addStartY( maxY - 160 - relativePositionY)
                .addWidth( width1 )
                .addTextContent("Generado por: ")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness + width1 + relativePositionX)
                .addStartY( maxY - 160 - relativePositionY)
                .addWidth( width2 )
                .addTextContent(usuarioEditor.getNombreCompleto())
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearTitulo(
            PDPageContentStream contentStream,
            String titulo) throws IOException {

        Style fuenteTitulo = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(15)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX(100+80 + relativePositionX)
                .addStartY(maxY - 90 - relativePositionY)
                .addWidth(maxX-2*100-2*80)
                .addTextContent(titulo)
                .addAlignment(Alignment.CENTER)
                .addStyle(fuenteTitulo)
                .build()
                .draw(contentStream);
    }



    // METODOS USUARIO
    public static void crearSubTituloUsuario(
            PDPageContentStream contentStream) throws IOException {

        Style fuenteSubtitulo = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(14f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + relativePositionX)
                .addStartY(maxY - 180 - 30 - relativePositionY)
                .addWidth(180f)
                .addTextContent("DATOS DEL USUARIO")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }

    public static void crearFechaRegistroUsuario(
            PDPageContentStream contentStream,
            UsuarioDTO usuarioEditado) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        String fechaCreacion = new Fecha(usuarioEditado.getFechaRegistro()).getFormatoFecha();
        float width1 = 140f;
        float width2 = 80f;

        MultipleParagraph.builder()
                .addStartX( maxX - (marginEndX+thickness+width1+width2 ) )
                .addStartY( maxY-250 )
                .addWidth( width1 )
                .addTextContent( "FECHA DE REGISTRO: " )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);


        MultipleParagraph.builder()
                .addStartX( maxX - (marginEndX+thickness+width2 ) )
                .addStartY( maxY-250 )
                .addWidth( width2 )
                .addTextContent( fechaCreacion )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearDatosUsuario(
            PDPageContentStream contentStream,
            UsuarioDTO usuarioEditado) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 150f;
        float width2 = 350f - 10f;
        float initY = 280;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("NOMBRE Y APELLIDOS: ",fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("N DE CI: "           ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("N TELEFONO: "        ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("CORREO ELECTRONICO: ",fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("CARGO: "             ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("AREA DE TRABAJO: "   ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("SUCURSAL: "          ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText("NOMBRE DE USUARIO: " ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c19 = EasyComponentsFactory.getSimpleCellFromText("ESTADO: "            ,fuenteNormalNegrita,width1,0f,5f,margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getNombreCompleto()   ,fuenteNormal,width2,0f,5f,margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getCarnetIdentidad()  ,fuenteNormal,width2,0f,5f,margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getTelefono()         ,fuenteNormal,width2,0f,5f,margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getCorreoElectronico(),fuenteNormal,width2,0f,5f,margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getNombreCargo()      ,fuenteNormal,width2,0f,5f,margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getNombreArea()       ,fuenteNormal,width2,0f,5f,margin);
        Cell c27 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getNombreSucursal()   ,fuenteNormal,width2,0f,5f,margin);
        Cell c28 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getNombreUsuario()    ,fuenteNormal,width2,0f,5f,margin);
        Cell c29 = EasyComponentsFactory.getSimpleCellFromText(usuarioEditado.getEstado()           ,fuenteNormal,width2,0f,5f,margin);

        float auxY = maxY-initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX+thickness, auxY,
                new Cell[]{c11,c21},
                new Cell[]{c12,c22},
                new Cell[]{c13,c23},
                new Cell[]{c14,c24},
                new Cell[]{c15,c25},
                new Cell[]{c16,c26},
                new Cell[]{c17,c27},
                new Cell[]{c18,c28},
                new Cell[]{c19,c29}
        );
        EasyComponentsFactory.getBoxStroke(10f,5f,Color.BLACK,table).draw(contentStream);
    }

    public static void crearObservacionesUsuario(
            PDPageContentStream contentStream,
            UsuarioDTO usuarioEditado) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+2*relativePositionX )
                .addStartY( maxY-500-2*relativePositionY )
                .addWidth( 130f )
                .addTextContent( "OBSERVACIONES" )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+(2*relativePositionX) )
                .addStartY( maxY-520-(2*relativePositionY) )
                .addWidth( 500f )
                .addTextContent( usuarioEditado.getObservaciones() )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);

    }

    // METODOS SUCURSAL

    public static void crearSubTitulosSucursal(
            PDPageContentStream contentStream) throws IOException {

        Style fuenteSubtitulo = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(14f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + relativePositionX)
                .addStartY(maxY - 180 - 30 - relativePositionY)
                .addWidth(180f)
                .addTextContent("DATOS DE LA SUCURSAL")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }

    public static void crearFechaRegistroSucursal(
            PDPageContentStream contentStream,
            SucursalDTO sucursal) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        String fechaCreacion = new Fecha(sucursal.getFechaRegistro()).getFormatoFecha();
        float width1 = 140f;
        float width2 = 80f;

        MultipleParagraph.builder()
                .addStartX( maxX - (marginEndX+thickness+width1+width2 ) )
                .addStartY( maxY-250 )
                .addWidth( width1 )
                .addTextContent( "FECHA DE REGISTRO: " )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX( maxX - (marginEndX+thickness+width2 ) )
                .addStartY( maxY-250 )
                .addWidth( width2 )
                .addTextContent( fechaCreacion )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearDatosSucursal(
            PDPageContentStream contentStream,
            SucursalDTO sucursal) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 125f;
        float width2 = 375f - 10f;
        float initY = 280;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("NOMBRE: "      ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("DEPARTAMENTO: ",fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("ZONA: "        ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("FAX: "         ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("DIRECCION: "   ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("TELEFONO: "    ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("ESTADO: "      ,fuenteNormalNegrita,width1,0f,5f,margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getNombre()      ,fuenteNormal,width2,0f,5f,margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getDepartamento(),fuenteNormal,width2,0f,5f,margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getZona()        ,fuenteNormal,width2,0f,5f,margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getFax()         ,fuenteNormal,width2,0f,5f,margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getDireccion()   ,fuenteNormal,width2,0f,5f,margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getTelefono()    ,fuenteNormal,width2,0f,5f,margin);
        Cell c27 = EasyComponentsFactory.getSimpleCellFromText(sucursal.getEstado()      ,fuenteNormal,width2,0f,5f,margin);

        float auxY = maxY-initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX+thickness, auxY,
                new Cell[]{c11,c21},
                new Cell[]{c12,c22},
                new Cell[]{c13,c23},
                new Cell[]{c14,c24},
                new Cell[]{c15,c25},
                new Cell[]{c16,c26},
                new Cell[]{c17,c27}
        );
        EasyComponentsFactory.getBoxStroke(10f,5f,Color.BLACK,table).draw(contentStream);
    }

    public static void crearHorarioAtencionSucursal(
            PDPageContentStream contentStream,
            SucursalDTO sucursal) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+2*relativePositionX )
                .addStartY( maxY-450-2*relativePositionY )
                .addWidth( 130f )
                .addTextContent( "HORARIO DE ATENCION" )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+(2*relativePositionX) )
                .addStartY( maxY-470-(2*relativePositionY) )
                .addWidth( 500f )
                .addTextContent( sucursal.getHorarioAtencion() )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearObservacionesSucursal(
            PDPageContentStream contentStream,
            SucursalDTO sucursal) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+2*relativePositionX )
                .addStartY( maxY-500-2*relativePositionY )
                .addWidth( 130f )
                .addTextContent( "OBSERVACIONES" )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+(2*relativePositionX) )
                .addStartY( maxY-520-(2*relativePositionY) )
                .addWidth( 500f )
                .addTextContent( sucursal.getObservaciones() )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    // METODOS AJUSTES
    public static void crearSubTituloAjustesSistema(
            PDPageContentStream contentStream) throws IOException {
        Style fuenteSubtitulo = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(14f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

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

    public static void crearDatosAjustesSistema(
            PDPageContentStream contentStream,
            AjustesSistemaDTO ajustes) throws IOException {
        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 200f;
        float width2 = 300f - 10f;
        float initY = 280;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("CAMBIO USD: "                 ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("CAMBIO UFV: "                 ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("AMORTIZACION DIAS: "          ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("SEGURO DESGRAVAMEN: "         ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("DIAS PARA ARCHIVAR: "         ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("C.I.P.: "                     ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("INTERES ANUAL: "              ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText("CORREO DE NOTIFICACIONES: "   ,fuenteNormalNegrita,width1,0f,5f,margin);
        Cell c19 = EasyComponentsFactory.getSimpleCellFromText("DIAS MAXIMO PARA DESCUENTOS: ",fuenteNormalNegrita,width1,0f,5f,margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCambioUSD().toString()               ,fuenteNormal,width2,0f,5f,margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCambioUFV().toString()               ,fuenteNormal,width2,0f,5f,margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getAmortizacionDias().toString()        ,fuenteNormal,width2,0f,5f,margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getSeguroDesgravamen().toString()       ,fuenteNormal,width2,0f,5f,margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getDiasParaArchivar().toString()        ,fuenteNormal,width2,0f,5f,margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCIP().toString()                     ,fuenteNormal,width2,0f,5f,margin);
        Cell c27 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getInteresAnual().toString()            ,fuenteNormal,width2,0f,5f,margin);
        Cell c28 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getCorreoNotificaciones()               ,fuenteNormal,width2,0f,5f,margin);
        Cell c29 = EasyComponentsFactory.getSimpleCellFromText(ajustes.getDiasMaximoParaDescuentos().toString(),fuenteNormal,width2,0f,5f,margin);

        float auxY = maxY-initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX+thickness, auxY,
                new Cell[]{c11,c21},
                new Cell[]{c12,c22},
                new Cell[]{c13,c23},
                new Cell[]{c14,c24},
                new Cell[]{c15,c25},
                new Cell[]{c16,c26},
                new Cell[]{c17,c27},
                new Cell[]{c18,c28},
                new Cell[]{c19,c29}
        );
        EasyComponentsFactory.getBoxStroke(10f,5f,Color.BLACK,table).draw(contentStream);
    }

    // METODOS AFILIADO

    public static void crearSubTitulosAfiliado(
            PDPageContentStream contentStream) throws IOException {

        Style fuenteSubtitulo = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(14f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + relativePositionX)
                .addStartY(maxY - 170 - 30 - relativePositionY)
                .addWidth(180f)
                .addTextContent("DATOS DEL SOLICITANTE")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }

    public static void crearFechaRegistroAfiliado(
            PDPageContentStream contentStream,
            AfiliadoDTO afiliado) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        String fechaCreacion = new Fecha(afiliado.getFechaSolicitud()).getFormatoFecha();
        float width1 = 140f;
        float width2 = 80f;

        MultipleParagraph.builder()
                .addStartX( maxX - (marginEndX+thickness+width1+width2 ) )
                .addStartY( maxY-230 )
                .addWidth( width1 )
                .addTextContent( "FECHA DE REGISTRO: " )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX( maxX - (marginEndX+thickness+width2 ) )
                .addStartY( maxY-230 )
                .addWidth( width2 )
                .addTextContent( fechaCreacion )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearDatosAfiliado(
            PDPageContentStream contentStream,
            AfiliadoDTO afiliado) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 150f;
        float width2 = 130f;
        float width3 = 130f;
        float width4 = 80f;
        float initY = 280;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("GRADO: "              ,fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("NOMBRES Y APELLIDOS: ",fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("N* DE CI: "           ,fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("FECHA DE NACIMIENTO " ,fuenteNormalNegrita,width3,0f,2.5f,margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("N* TELEFONICO : "     ,fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("N* DE CELULAR: "      ,fuenteNormalNegrita,width3,0f,2.5f,margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("CORREO ELECTRONICO: " ,fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText("UNIDAD POLICIAL: "    ,fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c19 = EasyComponentsFactory.getSimpleCellFromText("DEPARTAMENTO: "       ,fuenteNormalNegrita,width1,0f,2.5f,margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getGrado()                                       ,fuenteNormal,width2+width3+width4,0f,2.5f,margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getNombreCompleto()                              ,fuenteNormal,width2+width3+width4,0f,2.5f,margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getCarnetIdentidad()                             ,fuenteNormal,width2,0f,2.5f,margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(new Fecha(afiliado.getFechaNacimiento()).getFormatoFecha(),fuenteNormal,width4,0f,2.5f,margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getTelefono()                                    ,fuenteNormal,width2,0f,2.5f,margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getCelular()                                     ,fuenteNormal,width4,0f,2.5f,margin);
        Cell c27 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getCorreoElectronico()                           ,fuenteNormal,width2+width3+width4,0f,2.5f,margin);
        Cell c28 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getUnidadPolicial()                              ,fuenteNormal,width2+width3+width4,0f,2.5f,margin);
        Cell c29 = EasyComponentsFactory.getSimpleCellFromText(afiliado.getDepartamento()                                ,fuenteNormal,width2+width3+width4,0f,2.5f,margin);

        float auxY = maxY-initY+30f;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX+thickness, auxY,
                new Cell[]{c11,c21},
                new Cell[]{c12,c22},
                new Cell[]{c13,c23,c14,c24},
                new Cell[]{c15,c25,c16,c26},
                new Cell[]{c17,c27},
                new Cell[]{c18,c28},
                new Cell[]{c19,c29}
        );
        EasyComponentsFactory.getBoxStroke(10f,5f,Color.BLACK,table).draw(contentStream);
    }

    public static void crearObservacionesAfiliado(
            PDPageContentStream contentStream,
            AfiliadoDTO afiliado) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+2*relativePositionX )
                .addStartY( maxY-580-2*relativePositionY )
                .addWidth( 130f )
                .addTextContent( "OBSERVACIONES" )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+(2*relativePositionX) )
                .addStartY( maxY-600-(2*relativePositionY) )
                .addWidth( 500f )
                .addTextContent( afiliado.getObservaciones() )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearFirmaAfiliado(
            PDPageContentStream contentStream) throws IOException {
        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 160f;
        float initY = 520;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText(".......................................................",fuenteNormalNegrita,width1,0f,2.5f,Alignment.CENTER,margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("FIRMA DEL SOLICITANTE"   ,fuenteNormalNegrita,width1,0f,2.5f,Alignment.CENTER,margin);

        float auxY = maxY-initY+30f;
        float left = marginStartX + (maxX - (marginStartX + marginEndX) - width1) / 2;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                left, auxY,
                new Cell[]{c11},new Cell[]{c12}
        );
        table.draw(contentStream);
    }

    public static void crearCodigoAfiliado(
            PDPageContentStream contentStream) throws IOException {
        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 75f;
        float width2 = 75f;
        float initY = 715;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("CODIGO: ",fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(""        ,fuenteNormal       ,width2,0f,2.5f,margin);

        float auxY = maxY-initY;

        EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX+thickness+350f, auxY,
                new Cell[]{c11,c21}
        ).draw(contentStream);

        float initPoint = marginStartX+thickness+350f+width1;

        new Line(initPoint,auxY,initPoint+45,auxY).setThickness(2).draw(contentStream);
        new Line(initPoint,auxY-25,initPoint+50,auxY-25).setThickness(2).draw(contentStream);
        new Line(initPoint,auxY,initPoint,auxY-25).setThickness(2).draw(contentStream);
        new Line(initPoint+50,auxY-5,initPoint+50,auxY-25).setThickness(2).draw(contentStream);
        new Line(initPoint+45,auxY,initPoint+50,auxY-5).setThickness(2).draw(contentStream);

    }

    public static void crearNotaAfiliado(
            PDPageContentStream contentStream) throws IOException {
        Style fuenteDiminuta = Style.builder()
                .addFontSize(8)
                .addTextFont(PDType1Font.HELVETICA)
                .build();

        float width1 = 490f;
        float initY = 540f;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText(
                "NOTA: La Afiliacion a COVIPOL es VOLUNTARIA para sub oficiales, sargentos, cabos, policias y administrativos.\n" +
                      "El descuento por afiliacion corresponde al 1% del TOTAL GANADO",
                fuenteDiminuta,
                width1,
                0f,
                2.5f,
                margin);

        float auxY = maxY-initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX+thickness, auxY,
                new Cell[]{c11}
        );
        EasyComponentsFactory.getBoxStroke(10f,2.5f,Color.BLACK,table).draw(contentStream);
    }

    public static void crearCampoRecepcionadoAfiliado(
            PDPageContentStream contentStream) throws IOException {
        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 100f;
        float width2 = 150f;
        float initY = 715f;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("RECEPCIONADO: ",fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(""              ,fuenteNormal       ,width2,0f,2.5f,margin);

        float auxY = maxY-initY;

        EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX+thickness+10f, auxY,
                new Cell[]{c11,c21}
        ).draw(contentStream);
    }

    public static void crearLineaSeparacionAfiliado(
            PDPageContentStream contentStream) throws IOException {

        float initY = 580;
        float auxY = maxY-initY;

        new Line(marginStartX,auxY,maxX-marginEndX,auxY).setThickness(2).draw(contentStream);
    }

    // METODOS DESAFILIADO

    public static void crearSubTitulosDesafiliado(
            PDPageContentStream contentStream) throws IOException {

        Style fuenteSubtitulo = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(14f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + relativePositionX)
                .addStartY(maxY - 170 - 30 - relativePositionY)
                .addWidth(180f)
                .addTextContent("DATOS DEL SOLICITANTE")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }

    public static void crearFechaRegistroDesafiliado(
            PDPageContentStream contentStream,
            DesafiliadoDTO desafiliado) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        String fechaCreacion = new Fecha(desafiliado.getFechaSolicitud()).getFormatoFecha();
        float width1 = 140f;
        float width2 = 80f;

        MultipleParagraph.builder()
                .addStartX( maxX - (marginEndX+thickness+width1+width2 ) )
                .addStartY( maxY-225 )
                .addWidth( width1 )
                .addTextContent( "FECHA DE REGISTRO: " )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX( maxX - (marginEndX+thickness+width2 ) )
                .addStartY( maxY-225 )
                .addWidth( width2 )
                .addTextContent( fechaCreacion )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearDatosDesafiliado(
            PDPageContentStream contentStream,
            DesafiliadoDTO desafiliado) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.9f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 150f;
        float width2 = 130f;
        float width3 = 130f;
        float width4 = 80f;
        float initY = 280;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("GRADO: "              ,fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("NOMBRES Y APELLIDOS: ",fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("N* DE CI: "           ,fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("UNIDAD POLICIAL: "    ,fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("DEPARTAMENTO: "       ,fuenteNormalNegrita,width1,0f,2.5f,margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getGrado()          ,fuenteNormal,width2+width3+width4,0f,2.5f,margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getNombreCompleto() ,fuenteNormal,width2+width3+width4,0f,2.5f,margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getCarnetIdentidad(),fuenteNormal,width2+width3+width4,0f,2.5f,margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getUnidadPolicial() ,fuenteNormal,width2+width3+width4,0f,2.5f,margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(desafiliado.getDepartamento()   ,fuenteNormal,width2+width3+width4,0f,2.5f,margin);

        float auxY = maxY-initY+40f;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX+thickness, auxY,
                new Cell[]{c11,c21},
                new Cell[]{c12,c22},
                new Cell[]{c13,c23},
                new Cell[]{c14,c24},
                new Cell[]{c15,c25}

        );
        EasyComponentsFactory.getBoxStroke(10f,5f,Color.BLACK,table).draw(contentStream);
    }

    public static void crearCausaDesafiliado(
            PDPageContentStream contentStream,
            DesafiliadoDTO desafiliado) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.9f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+2*relativePositionX )
                .addStartY( maxY-340-2*relativePositionY )
                .addWidth( 300f )
                .addTextContent( "CAUSA DE DESAFILIACION" )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+(2*relativePositionX) )
                .addStartY( maxY-355-(2*relativePositionY) )
                .addWidth( 500f )
                .addTextContent( desafiliado.getCausaDesafiliacion() )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);}

    public static void crearObservacionesDesafiliado(
            PDPageContentStream contentStream,
            DesafiliadoDTO desafiliado) throws IOException {

        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.9f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+2*relativePositionX )
                .addStartY( maxY-600-2*relativePositionY )
                .addWidth( 130f )
                .addTextContent( "OBSERVACIONES" )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormalNegrita)
                .build()
                .draw(contentStream);

        MultipleParagraph.builder()
                .addStartX( marginStartX+thickness+(2*relativePositionX) )
                .addStartY( maxY-615-(2*relativePositionY) )
                .addWidth( 500f )
                .addTextContent( desafiliado.getObservaciones() )
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteNormal)
                .build()
                .draw(contentStream);
    }

    public static void crearFirmaDesafiliado(
            PDPageContentStream contentStream) throws IOException {
        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 160f;
        float initY = 520;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText(".......................................................",fuenteNormalNegrita,width1,0f,2.5f,Alignment.CENTER,margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("FIRMA DEL SOLICITANTE"   ,fuenteNormalNegrita,width1,0f,2.5f,Alignment.CENTER,margin);

        float auxY = maxY-initY+10f;
        float left = marginStartX + (maxX - (marginStartX + marginEndX) - width1) / 2;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                left, auxY,
                new Cell[]{c11},new Cell[]{c12}
        );
        table.draw(contentStream);
    }

    public static void crearCodigoDesafiliado(
            PDPageContentStream contentStream) throws IOException {
        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 75f;
        float width2 = 75f;
        float initY = 715;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("CODIGO: ",fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(""        ,fuenteNormal       ,width2,0f,2.5f,margin);

        float auxY = maxY-initY;

        EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX+thickness+350f, auxY,
                new Cell[]{c11,c21}
        ).draw(contentStream);

        float initPoint = marginStartX+thickness+350f+width1;

        new Line(initPoint,auxY,initPoint+45,auxY).setThickness(2).draw(contentStream);
        new Line(initPoint,auxY-25,initPoint+50,auxY-25).setThickness(2).draw(contentStream);
        new Line(initPoint,auxY,initPoint,auxY-25).setThickness(2).draw(contentStream);
        new Line(initPoint+50,auxY-5,initPoint+50,auxY-25).setThickness(2).draw(contentStream);
        new Line(initPoint+45,auxY,initPoint+50,auxY-5).setThickness(2).draw(contentStream);

    }

    public static void crearNotaDesafiliado(
            PDPageContentStream contentStream) throws IOException {
        Style fuenteDiminuta = Style.builder()
                .addFontSize(8)
                .addTextFont(PDType1Font.HELVETICA)
                .build();

        float width1 = 490f;
        float initY = 550f;
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

        float auxY = maxY-initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX+thickness, auxY,
                new Cell[]{c11}
        );
        EasyComponentsFactory.getBoxStroke(10f,2.5f,Color.BLACK,table).draw(contentStream);
    }

    public static void crearCampoRecepcionadoDesafiliado(
            PDPageContentStream contentStream) throws IOException {
        Style fuenteNormal = Style.builder()
                .addTextFont(PDType1Font.HELVETICA)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(PDType1Font.HELVETICA_BOLD)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 100f;
        float width2 = 150f;
        float initY = 715f;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("RECEPCIONADO: ",fuenteNormalNegrita,width1,0f,2.5f,margin);
        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(""              ,fuenteNormal       ,width2,0f,2.5f,margin);

        float auxY = maxY-initY;

        EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX+thickness+10f, auxY,
                new Cell[]{c11,c21}
        ).draw(contentStream);
    }

    public static void crearLineaSeparacionDesafiliado(
            PDPageContentStream contentStream) throws IOException {

        float initY = 600f;
        float auxY = maxY-initY;

        new Line(marginStartX,auxY,maxX-marginEndX,auxY).setThickness(2).draw(contentStream);
    }


    /*
    // METODOS SOLICITUD CREDITO PARA VIVIENDA
    public static void crearCuadroCarpeta(SolicitudCreditoViviendaDTO solicitudDTO, PDPageContentStream contentStream) throws IOException {
        Style fuente = fuenteEstrechaNegrita.clone().setSeparationFactor(0.6f);
        Style fuenteMinimalNegrita = new Style()
                .setTextFont(PDType1Font.HELVETICA_BOLD)
                .setFontSize(9)
                .setTextColor(new Color(60,60,60))
                .setSeparationFactor(0.8f);

        float initX  = marginStartX+thickness;

        ColumnText columnText1 = new ColumnText(
                initX, maxY-190,
                Arrays.asList(new String[] {
                        "Carpeta Nro",
                        solicitudDTO.getNumeroCarpeta(),
                        "Registro Nro",
                        solicitudDTO.getNumeroRegistro()}),
                Arrays.asList(new Float[]  {70f,280f,100f,50f}) ,
                Arrays.asList(new Style[]  {
                        fuente,
                        fuenteMinimalNegrita,
                        fuente,
                        fuenteMinimalNegrita}))
                .setHasMargin(false)
                .setSeparationFactor(1.3f);

        columnText1.build();
        columnText1.draw(contentStream);

        Rectangle rectangle1 = new Rectangle(initX+70 , maxY-190, 75, columnText1.getHeight());
        Rectangle rectangle2 = new Rectangle(initX+450, maxY-190, 45, columnText1.getHeight());

        rectangle1.draw(contentStream);
        rectangle2.draw(contentStream);
    }

    private static final Style fuenteNormal = new Style()
			.setTextFont(PDType1Font.HELVETICA)
            .setFontSize(10.5f)
            .setTextColor(Color.BLACK)
            .setSeparationFactor(1.1f);

	private static final Style fuenteNormalNegrita = new Style()
            .setTextFont(PDType1Font.HELVETICA_BOLD)
            .setFontSize(10.5f)
            .setTextColor(Color.BLACK)
            .setSeparationFactor(1.1f);

	private static final Style fuenteSubtitulo = new Style()
            .setTextFont(PDType1Font.HELVETICA_BOLD)
            .setFontSize(14)
            .setTextColor(Color.BLACK)
            .setSeparationFactor(1f);

	private static final Style fuenteTitulo = new Style()
            .setTextFont(PDType1Font.HELVETICA_BOLD)
            .setFontSize(15)
            .setTextColor(Color.BLACK)
            .setSeparationFactor(0.8f);

	private static final Style fuenteCabecera = new Style()
            .setTextFont(PDType1Font.HELVETICA_BOLD)
            .setFontSize(18)
            .setTextColor(Color.BLACK)
            .setSeparationFactor(1f);

	private static final Style fuenteEstrecha = new Style()
			.setTextFont(PDType1Font.HELVETICA)
            .setFontSize(9)
            .setTextColor(Color.BLACK)
            .setSeparationFactor(1f);

	private static final Style fuenteEstrechaInclinada = new Style()
			.setTextFont(PDType1Font.HELVETICA_OBLIQUE)
            .setFontSize(9)
            .setTextColor(Color.BLACK)
            .setSeparationFactor(1f);

	private static final Style fuenteEstrechaNegrita = new Style()
            .setTextFont(PDType1Font.HELVETICA_BOLD)
            .setFontSize(9)
            .setTextColor(Color.BLACK)
            .setSeparationFactor(1f);

	private static final Style fuenteDiminuta = new Style()
			.setTextFont(PDType1Font.HELVETICA)
            .setFontSize(8)
            .setTextColor(Color.BLACK)
            .setSeparationFactor(1f);

*/

    private static void crearMargen(PDPageContentStream contentStream,
                                    float startX, float startY, float witdh, float heigth) throws IOException {
        Rectangle rectangle = Rectangle.builder()
                .addColor(Color.BLACK)
                .addThickness(2)
                .addStartX(startX)
                .addStartY(startY)
                .addWidth(witdh)
                .addHeight(heigth)
                .build();

        rectangle.draw(contentStream);
    }

    public static void dibujarImagen(
            PDPageContentStream pageContentStream,
            PDDocument document, String imagePath,
            float offsetX, float offsetY,int width,
            int heigth
    ) throws IOException{
        PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath,document);
        pageContentStream.drawImage(pdImage, offsetX, offsetY,width,heigth);
    }


}