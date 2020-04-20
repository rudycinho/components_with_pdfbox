package utils.factories;

import dto.*;
import dto.PlanDePagosDTO;
import dto.EstadoAfiliacionDTO;
import dto.complex.DefinicionCreditoDTO;
import lib.basic.Alignment;
import lib.basic.Style;
import lib.shapes.Line;
import lib.tables.Cell;
import lib.tables.Column;
import lib.tables.RecursiveTable;
import lib.tables.SimpleTable;
import lib.text.MultipleParagraph;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import lib.shapes.Rectangle;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import utils.Fecha;
import utils.factories.EasyComponentsFactory;

import java.awt.Color;
import java.io.IOException;
import java.util.*;

public class GeneradorFormularioFactory {
    private static float maxY = 0;
    private static float maxX = 0;

    private static float thickness         = 15f;

    private static float marginStartX      = 50f;
    private static float marginEndX        = 25f;

    private static float relativePositionX = 5f;
    private static float relativePositionY = 5f;


    private static PDFont fuenteBasica        = PDType1Font.HELVETICA;
    private static PDFont fuenteBasicaNegrita = PDType1Font.HELVETICA_BOLD;

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

    public static void setMarginStartX(float x){
        marginStartX = x;
    }

    // METODOS GENERICOS

    public static void crearMargen(PDPageContentStream contentStream) throws IOException {
        crearMargen(contentStream,marginStartX, 40, maxX - (marginStartX + marginEndX), maxY - 65);
    }

    public static void crearCabecera(
            PDPageContentStream contentStream,
            PDDocument document) throws IOException {

        Style fuenteCabecera = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .build();

        String informacion = String.format(
                "%s: %s\n%s: %s  -  %s: %s  -  %s: %s",
                "DIRECCION"         , sucursalDTO.getDireccion(),
                "TELEFONOS"         , sucursalDTO.getTelefono(),
                "FAX"               , sucursalDTO.getFax(),
                "CORREO ELECTRONICO", "covipol.afiliaciones@covipol.gob.bo"
        );

        float distance = 35f;

        MultipleParagraph.builder()
                .addStartX(marginStartX + distance)
                .addStartY(35f)
                .addWidth(maxX - marginStartX - marginEndX - (2*distance))
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
                .addTextFont(fuenteBasica)
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
                .addTextFont(fuenteBasica)
                .addTextColor(Color.BLACK)
                .addLeading(1.1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addFontSize(10.5f)
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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

    public static void crearTablaDetallesReafiliado(
            PDPageContentStream contentStream,
            List<PDPageContentStream> contentStreams,
            PDDocument doc,
            List<EstadoAfiliacionDTO> estados) throws IOException {

        List<List<String>> listOfList = new ArrayList<>();

        int i=0;
        for(EstadoAfiliacionDTO estado : estados) {
            List<String> auxList = new ArrayList<>();
            auxList.add(String.format("%d", ++i));
            auxList.add(estado.getCausa());
            auxList.add(estado.getObservaciones());
            listOfList.add(auxList);
        }

        List<Float>  widths = Arrays.asList(new Float[]{30f,230f,230f});
        List<String> header = Arrays.asList(new String[]{"N*","Causa Reafiliacion","Observaciones"});

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
                .addLeading(0.8f)
                .build();

        Column headerColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteNormalNegrita,Alignment.LEFT);

        Column anotherHeaderColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteNormalNegrita,Alignment.LEFT);

        List<Column> bodyColumns = new LinkedList<>();

        for(List<String> auxList : listOfList)
            bodyColumns.add(EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(auxList, widths, fuenteNormal,Alignment.LEFT));

        List<RecursiveTable> tables = EasyComponentsFactory.getHeaderTable(
                marginStartX+thickness,maxY-40,maxY-40,headerColumn,anotherHeaderColumn,bodyColumns,45);

        Iterator<RecursiveTable> it = tables.iterator();
        it.next().draw(contentStream);


        while(it.hasNext()){
            PDPage page = new PDPage(new PDRectangle(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight()));
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            it.next().draw(content);
            contentStreams.add(content);
        }

        contentStreams.add(0,contentStream);
    }

    // METODOS DESAFILIADO

    public static void crearSubTitulosDesafiliado(
            PDPageContentStream contentStream) throws IOException {

        Style fuenteSubtitulo = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.9f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.9f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(0.9f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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
                .addTextFont(fuenteBasica)
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
                .addTextFont(fuenteBasica)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
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


    // METODOS PERFIL

    public static void crearSubTitulosPerfil(
            PDPageContentStream contentStream) throws IOException {

        Style fuenteSubtitulo = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(14f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        MultipleParagraph.builder()
                .addStartX(marginStartX + thickness + relativePositionX)
                .addStartY(maxY - 180 - 30 - relativePositionY)
                .addWidth(180f)
                .addTextContent("DATOS DEL SOLICITANTE")
                .addAlignment(Alignment.LEFT)
                .addStyle(fuenteSubtitulo)
                .build()
                .draw(contentStream);
    }

    public static void crearPermisosTablaPerfil(
            PDPageContentStream contentStream,
            List<PDPageContentStream> contentStreams,
            PDDocument doc,
            PerfilDTO perfil) throws IOException {

        List<List<String>> listOfList = new ArrayList<>();

        int i=0;
        for(PermisoDTO permiso : perfil.getPermisos()) {
            List<String> auxList = new ArrayList<>();
            auxList.add(String.format("%d", ++i));
            auxList.add(permiso.getNombre());
            auxList.add(permiso.getDescripcion());
            listOfList.add(auxList);
        }

        List<Float>  widths = Arrays.asList(new Float[]{30f,170f,300f});
        List<String> header = Arrays.asList(new String[]{"N*","Nombre Permiso","Descripcion Permiso"});

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
                .addLeading(0.8f)
                .build();

        Column headerColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteNormalNegrita,Alignment.LEFT);

        Column anotherHeaderColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteNormalNegrita,Alignment.LEFT);

        List<Column> bodyColumns = new LinkedList<>();

        for(List<String> auxList : listOfList)
            bodyColumns.add(EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(auxList, widths, fuenteNormal,Alignment.LEFT));

        List<RecursiveTable> tables = EasyComponentsFactory.getHeaderTable(
                marginStartX+thickness,maxY-240,maxY-40,headerColumn,anotherHeaderColumn,bodyColumns,45);

        Iterator<RecursiveTable> it = tables.iterator();
        it.next().draw(contentStream);


        while(it.hasNext()){
            PDPage page = new PDPage(new PDRectangle(PDRectangle.LETTER.getWidth(),PDRectangle.LETTER.getHeight()));
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            it.next().draw(content);
            contentStreams.add(content);
        }

        contentStreams.add(0,contentStream);

    }

    // METODOS PRECALIFICADOR


    public static void crearPlanPagosSeccionDictamen(
            PDPageContentStream contentStream,
            DictamenDTO dictamen) throws IOException {

        String montoAFinanciar    = String.format("%s Bs", dictamen.getMontoSolicitadoBS());
        String amortizacion       = String.format("Cada %s dias", dictamen.getCantidadDiasDeAmortización());
        String plazo              = String.format("%s cuotas(s)", dictamen.getPlazoCuotas());
        String tazaInteres        = String.format("%s %%", dictamen.getTasaInteresFija());
        String maximoComprometido = String.format("%s %%", dictamen.getMaximoComprometido());

        String cuotaParcial       = String.format("%s Bs", dictamen.getCuotaParcial());
        String segDesgravamen     = String.format("%s Bs", dictamen.getSeguroDesgravamen());
        String cip                = String.format("%s Bs", dictamen.getCip());
        String totalCuota         = String.format("%s Bs", dictamen.getCuotaTotal());

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
        float initY  = 325f;
        boolean margin = false;

        Cell cTitle = EasyComponentsFactory.getSimpleCellFromText("PLAN DE PAGOS"     , fuenteTitulo, width1+width2+width3+width4, 0f, 2.5f, Alignment.CENTER,margin);
        Cell cLine   = EasyComponentsFactory.getSimpleCellRectangle(width1+width2+width3+width4, 2f, 1f, margin);

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Monto a financiar"     , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("Amortizacion"          , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Plazo"                 , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Taza de interes"       , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Maximo comprometido"   , fuenteNormalNegrita, width1, 1f, 5f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(montoAFinanciar   , fuenteNormal, width2, 1f, 5f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(amortizacion      , fuenteNormal, width2, 1f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(plazo             , fuenteNormal, width2, 1f, 5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(tazaInteres       , fuenteNormal, width2, 1f, 5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(maximoComprometido, fuenteNormal, width2, 1f, 5f, margin);

        Cell c31 = EasyComponentsFactory.getSimpleCellFromText("Cuota parcial"         , fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c32 = EasyComponentsFactory.getSimpleCellFromText("Seguro de Desgravamen" , fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c33 = EasyComponentsFactory.getSimpleCellFromText("C.I.P."                , fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c34 = EasyComponentsFactory.getSimpleCellFromText("Total cuota a cancelar", fuenteNormalNegrita, width3, 1f, 5f, margin);

        Cell c41 = EasyComponentsFactory.getSimpleCellFromText(cuotaParcial  , fuenteNormal, width4, 1f, 5f, margin);
        Cell c42 = EasyComponentsFactory.getSimpleCellFromText(segDesgravamen, fuenteNormal, width4, 1f, 5f, margin);
        Cell c43 = EasyComponentsFactory.getSimpleCellFromText(cip           , fuenteNormal, width4, 1f, 5f, margin);
        Cell c44 = EasyComponentsFactory.getSimpleCellFromText(totalCuota    , fuenteNormal, width4, 1f, 5f, margin);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{cTitle},
                new Cell[]{cLine},
                new Cell[]{c11,c21,c31,c41},
                new Cell[]{c12,c22,c32,c42},
                new Cell[]{c13,c23,c33,c43},
                new Cell[]{c14,c24,c34,c44},
                new Cell[]{c15,c25}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(30f, 15f, new Color(107,250,194), table);
        box.setHasMargin(true);
        box.setHasFilling(true);
        box.setColorMargin(new Color(107,250,194));
        box.setColorFilling(new Color(107,250,194));
        box.draw(contentStream);
    }

    public static void crearPrecalicacionSeccionDictamen(
            PDPageContentStream contentStream,
            DictamenDTO dictamen) throws IOException {

        String liquidoPagable        =dictamen.getSueldoLiquidoPagable();
        String dictamenLiteral       =dictamen.getDictamenLiteral();
        String porcentajeDestinoPago =dictamen.getPorcentajeDestinadoAPago();
        String cuotaPrestamo         =dictamen.getCuotaTotal();
        String cuotaMensual          =dictamen.getCuotaParcial();

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
        float initY  = 200f;
        boolean margin = false;

        Cell cTitle = EasyComponentsFactory.getSimpleCellFromText("Precalificacion en Bolivianos"     , fuenteTitulo, width1+width2, 0f, 2.5f, Alignment.CENTER,margin);
        Cell cLine1 = EasyComponentsFactory.getSimpleCellRectangle(width1+width2, 2f, 1f, margin);
        Cell cLine2 = EasyComponentsFactory.getSimpleCellRectangle(width1+width2, 2f, 0.5f, margin);
        Cell cLine3 = EasyComponentsFactory.getSimpleCellRectangle(width1+width2, 2f, 0.5f, margin);
        Cell cLine4 = EasyComponentsFactory.getSimpleCellRectangle(width1+width2, 2f, 0.5f, margin);

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText( "Detalle"           , fuenteNormalNegrita, width1, 1f, 7.5f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText( "Liquido Pagable"   , fuenteNormalNegrita, width1, 1f, 7.5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText( "Cuota de prestamo" , fuenteNormalNegrita, width1, 1f, 7.5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText( "Dictamen"          , fuenteNormalNegrita, width1, 1f, 7.5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText( "%Destinado Pago"   , fuenteNormalNegrita, width1, 1f, 7.5f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText( "Cuota Mensual"     , fuenteNormalNegrita, width1+width2, 1f, 7.5f, Alignment.CENTER, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText( "Bs"                , fuenteNormalNegrita, width1+width2, 1f, 7.5f, Alignment.CENTER, margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText( cuotaMensual              , fuenteNormalNegrita, width1+width2, 1f, 7.5f, Alignment.CENTER, margin);
        c18.setHasFilling(true);
        c18.setColorFilling(new Color(150,150,150));

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText("Monto Bs"     , fuenteNormal, width2, 1f, 7.5f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(liquidoPagable       , fuenteNormal, width2, 1f, 7.5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(cuotaPrestamo        , fuenteNormal, width2, 1f, 7.5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(dictamenLiteral      , fuenteNormalRoja, width2, 1f, 7.5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(porcentajeDestinoPago, fuenteNormal, width2, 1f, 7.5f, margin);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                maxX - marginEndX - 225, auxY,
                new Cell[]{cTitle},
                new Cell[]{cLine1},
                new Cell[]{c11,c21},
                new Cell[]{cLine2},
                new Cell[]{c12,c22},
                new Cell[]{c13,c23},
                new Cell[]{c14,c24},
                new Cell[]{c15,c25},
                new Cell[]{cLine3},
                new Cell[]{c16},
                new Cell[]{cLine4},

                new Cell[]{c17},
                new Cell[]{c18}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(15f, 10f, new Color(107,250,194), table);
        box.setHasMargin(true);
        box.setHasFilling(true);
        box.setColorMargin(new Color(107,250,194));
        box.setColorFilling(new Color(107,250,194));
        box.draw(contentStream);

    }

    public static void crearDatosSeccionDictamen(PDPageContentStream contentStream, DictamenDTO dictamen) throws IOException {
        String solicitante = dictamen.getNombreCliente();
        String ci          =dictamen.getCi();

        String montoUFV  = dictamen.getMontoSolicitadoUFV();
        String montoSus  = dictamen.getMontoSolicitadoUS();
        String mondoBs   = dictamen.getMontoSolicitadoBS();
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
        float initY  = 200f;
        boolean margin = false;

        float auxY = maxY - initY;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Solicitante"             , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("CI"                      , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en UFV" , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en SUS" , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Monto Solicitado en BS"  , fuenteNormalNegrita, width1, 1f, 5f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(solicitante, fuenteNormal, width2+width3+width4, 1f, 5f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(ci         , fuenteNormal, width2+width3+width4, 1f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(montoUFV   , fuenteNormal, width2, 1f, 5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(montoSus   , fuenteNormal, width2, 1f, 5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(mondoBs    , fuenteNormal, width2, 1f, 5f, margin);

        Cell c33 = EasyComponentsFactory.getSimpleCellFromText("Tipo de cambio venta UFV" , fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c34 = EasyComponentsFactory.getSimpleCellFromText("Tipo de cambio venta SUS" , fuenteNormalNegrita, width3, 1f, 5f, margin);

        Cell c43 = EasyComponentsFactory.getSimpleCellFromText(cambioUFV , fuenteNormal, width4, 1f, 5f, margin);
        Cell c44 = EasyComponentsFactory.getSimpleCellFromText(cambioSus , fuenteNormal, width4, 1f, 5f, margin);

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{c11,c21},
                new Cell[]{c12,c22},
                new Cell[]{c13,c23,c33,c43},
                new Cell[]{c14,c24,c34,c44},
                new Cell[]{c15,c25}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(30f, 7.5f, new Color(107,250,194), table);
        box.setHasMargin(false);
        box.setHasFilling(false);
        box.draw(contentStream);
    }

    public static void crearDetallesTablePlanDePagos(
            PDPageContentStream contentStream,
            List<PDPageContentStream> contentStreams,
            PDDocument doc,
            PlanDePagosDTO planDePagos) throws IOException {


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

        MultipleParagraph multipleParagraph = EasyComponentsFactory.getSimpleMultipleParagraph(
                "DETALLES",
                marginStartX + thickness, maxY - 380,
                fuenteNormalNegrita,
                100,
                Alignment.LEFT);
        multipleParagraph.draw(contentStream);

        List<List<String>> listOfList = planDePagos.getContenido();
        List<Float> widths = Arrays.asList(new Float[]{40f, 80f, 90f, 70f, 50f, 70f, 90f, 80f, 60f, 80f});
        List<String> header = planDePagos.getContenidoCabecera();

        Column headerColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteNormalNegrita, Alignment.CENTER);

        Column anotherHeaderColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteNormalNegrita, Alignment.CENTER);

        headerColumn.getCells().forEach(s -> {
            s.setColorFilling(Color.ORANGE);
            s.setHasFilling(true);
        });

        anotherHeaderColumn.getCells().forEach(s -> {
            s.setColorFilling(Color.ORANGE);
            s.setHasFilling(true);
        });


        List<Column> bodyColumns = new LinkedList<>();

        for (List<String> auxList : listOfList)
            bodyColumns.add(EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(auxList, widths, fuenteNormal, Alignment.LEFT));

        List<RecursiveTable> tables = EasyComponentsFactory.getHeaderTable(
                marginStartX + thickness, maxY - 400, maxY - 40, headerColumn, anotherHeaderColumn, bodyColumns, 45);

        Iterator<RecursiveTable> it = tables.iterator();
        it.next().draw(contentStream);

        while (it.hasNext()) {
            PDPage page = new PDPage(new PDRectangle(maxX, maxY));
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            it.next().draw(content);
            contentStreams.add(content);
        }
        contentStreams.add(0, contentStream);
    }

    // METODO PLAN DE PAGOS
    public static void crearClienteSeccionPlanDePagos(
            PDPageContentStream contentStream,
            DictamenDTO dictamen) throws IOException {

        String usuario                = dictamen.getNombreCliente();
        String ci                     = dictamen.getCi();

        String totalCuotaMensual      = String.format("%s Bs", dictamen.getCuotaParcial());
        String interesFijo            = String.format("%s%% Anual",dictamen.getTasaInteresFija());
        String segDesgravamen         = String.format("%s%% Anual",dictamen.getSeguroDesgravamen());

        String cipMensual             = String.format("%s%% Mensual",dictamen.getCip());

        String amortizacionFrecuencia = "Cada 30 dias";
        String totalCuotas            = dictamen.getCuotaTotal();
        String tipoInteres            = "Taza fija";
        String tipoCambio             = dictamen.getCambioUS();
        String liquidoPagable         = dictamen.getSueldoLiquidoPagable();
        String moneda                 = "Bolivianos";

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 90f;
        float width2 = 60f;
        float width3 = 90f;
        float width4 = 60f;
        float width5 = 90f;
        float width6 = 70f;
        float initY  = 200f;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Nombre Cliente"      , fuenteNormalNegrita, width1+width2/2, 1f, 5f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("CI"                  , fuenteNormalNegrita, width1+width2/2, 1f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Tipo de Amortizacion", fuenteNormalNegrita, width1+width2/2, 1f, 5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Moneda"              , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Amortizacion cada"   , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("Interes fijo"        , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Total Cuota Mensual" , fuenteNormalNegrita, width1, 1f, 5f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(usuario                                      , fuenteNormal, width3+width4+width5+width6+width2/2, 1f, 5f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(ci                                           , fuenteNormal, width3+width4+width5+width6+width2/2, 1f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText("PRESTAMO AMORTIZABLE - METODO FRANCES", fuenteNormal, width3+width4+width5+width6+width2/2, 1f, 5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(moneda                                       , fuenteNormal, width2, 1f, 5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(amortizacionFrecuencia                       , fuenteNormal, width2, 1f, 5f, margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(interesFijo                                  , fuenteNormal, width2, 1f, 5f, margin);
        Cell c27 = EasyComponentsFactory.getSimpleCellFromText(totalCuotaMensual                            , fuenteNormal, width2, 1f, 5f, margin);

        Cell c34 = EasyComponentsFactory.getSimpleCellFromText("Tipo de cambio(Bs.)" , fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c35 = EasyComponentsFactory.getSimpleCellFromText("Plazo(cuotas)"       , fuenteNormalNegrita, width3, 1f, 5f, margin);
        Cell c36 = EasyComponentsFactory.getSimpleCellFromText("Seg. desgravamen"    , fuenteNormalNegrita, width3, 1f, 5f, margin);

        Cell c44 = EasyComponentsFactory.getSimpleCellFromText(tipoCambio    , fuenteNormal, width4, 1f, 5f, margin);
        Cell c45 = EasyComponentsFactory.getSimpleCellFromText(totalCuotas   , fuenteNormal, width4, 1f, 5f, margin);
        Cell c46 = EasyComponentsFactory.getSimpleCellFromText(segDesgravamen, fuenteNormal, width4, 1f, 5f, margin);

        Cell c54 = EasyComponentsFactory.getSimpleCellFromText("Liquido pagable(Bs)", fuenteNormalNegrita, width5, 1f, 5f, margin);
        Cell c55 = EasyComponentsFactory.getSimpleCellFromText("Tipo de Interes"    , fuenteNormalNegrita, width5, 1f, 5f, margin);
        Cell c56 = EasyComponentsFactory.getSimpleCellFromText("CIP"                , fuenteNormalNegrita, width5, 1f, 5f, margin);

        Cell c64 = EasyComponentsFactory.getSimpleCellFromText(liquidoPagable , fuenteNormal, width6, 1f, 5f, margin);
        Cell c65 = EasyComponentsFactory.getSimpleCellFromText(tipoInteres    , fuenteNormal, width6, 1f, 5f, margin);
        Cell c66 = EasyComponentsFactory.getSimpleCellFromText(cipMensual     , fuenteNormal, width6, 1f, 5f, margin);

        cambiarColor(new Color(77,175,134),c23,c24,c44,c64,c25,c45,c65,c26,c46,c66);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{c11,c21},
                new Cell[]{c12,c22},
                new Cell[]{c13,c23},
                new Cell[]{c14,c24,c34,c44,c54,c64},
                new Cell[]{c15,c25,c35,c45,c55,c65},
                new Cell[]{c16,c26,c36,c46,c56,c66},
                new Cell[]{c17,c27}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(10f, 5f, new Color(107,250,194), table);
        box.setHasMargin(true);
        box.setHasFilling(true);
        box.setColorMargin(new Color(107,250,194));
        box.setColorFilling(new Color(107,250,194));
        box.draw(contentStream);
    }

    public static void crearResumenDatosSeccionPlanDePagos(
            PDPageContentStream contentStream,
            DictamenDTO dictamen) throws IOException {

        String capitalAPagar = dictamen.getCuotaTotal();
        String interesAPagar = dictamen.getTasaInteresFija();
        String segDesgravamen= dictamen.getSeguroDesgravamen();
        String cip           = dictamen.getCip();
        String totalAPagar   = dictamen.getCuotaTotal();
        String fechaInicio   = "";
        String fechaFin      = "";

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        Style fuenteTitulo = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        float width1 = 100f;
        float width2 = 80f;
        float initY  = 200f;
        boolean margin = false;

        Cell cLine   = EasyComponentsFactory.getSimpleCellRectangle(width1+width2, 2f, 1f, margin);

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("RESUMEN DE DATOS" , fuenteTitulo, width1+width2, 1f, 5f,Alignment.CENTER, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("Capital a pagar"  , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Interes a pagar"  , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Seg. desgravamen" , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("CIP"              , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("Total a pagar"    , fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Fecha inicio pago", fuenteNormalNegrita, width1, 1f, 5f, margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText("Fecha fin pago"   , fuenteNormalNegrita, width1, 1f, 5f, margin);

        Cell c22 = EasyComponentsFactory.getSimpleCellFromText( capitalAPagar  , fuenteNormal, width2, 1f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText( interesAPagar  , fuenteNormal, width2, 1f, 5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText( segDesgravamen , fuenteNormal, width2, 1f, 5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText( cip            , fuenteNormal, width2, 1f, 5f, margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText( totalAPagar    , fuenteNormal, width2, 1f, 5f, margin);
        Cell c27 = EasyComponentsFactory.getSimpleCellFromText( fechaInicio    , fuenteNormal, width2, 1f, 5f, margin);
        Cell c28 = EasyComponentsFactory.getSimpleCellFromText( fechaFin       , fuenteNormal, width2, 1f, 5f, margin);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                maxX - marginEndX - marginStartX - thickness - width1 - width2, auxY,
                new Cell[]{c11},
                new Cell[]{cLine},
                new Cell[]{c12,c22},
                new Cell[]{c13,c23},
                new Cell[]{c14,c24},
                new Cell[]{c15,c25},
                new Cell[]{c16,c26},
                new Cell[]{c17,c27},
                new Cell[]{c18,c28}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(10f, 5f, new Color(107,250,194), table);
        box.setHasMargin(true);
        box.setHasFilling(true);
        box.setColorMargin(new Color(107,250,194));
        box.setColorFilling(new Color(107,250,194));
        box.draw(contentStream);
    }

    public static void enumerarPaginas(
            PDPageContentStream contentStream,
            int numeroPagina, int totalPaginas
    ) throws IOException {

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(1f)
                .build();

        Cell cell1 = EasyComponentsFactory.getSimpleCellFromText(
                "SISTEMA INTEGRAL DE PRESTAMOS",
                fuenteNormalNegrita,
                700,
                0,0,
                Alignment.CENTER,false);

        Cell cell2 = EasyComponentsFactory.getSimpleCellFromText(
                String.format("%d/%d", numeroPagina , totalPaginas),
                fuenteNormalNegrita,
                30,
                0,0,
                Alignment.CENTER,false);

        cambiarColor(new Color(211,211,211),cell1,cell2);

        Column.builder()
                .addStartX(30)
                .addStartY(maxY - 580)
                .addCell(cell1)
                .addCell(cell2)
                .build()
                .draw(contentStream);
    }




    private static void cambiarColor(Color color, Cell ...cells) {
        for(Cell cell : cells) {
            cell.setHasFilling(true);
            cell.setColorFilling(color);
        }
    }

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


    public static void crearDatosAfiliadoDefinicion(
            PDPageContentStream contentStream,
            DefinicionCreditoDTO definicion) throws IOException {

        String gradoNombreAfiliado = definicion.getGradoNombreAfiliado();
        String carnetIdentidad     = definicion.getCarnetIdentidad();
        String numeroCarpeta       = definicion.getNumeroCarpeta();
        String fechaCreacion       = new Fecha(definicion.getFechaCreacion()).getFormatoFecha();
        String modalidadCredito    = definicion.getModalidadCredito();
        String tipoDeGarantia      = definicion.getTipoDeGarantia();
        String origenTramite       = definicion.getOrigenTramite();

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(1.2f)
                .build();

        Style fuenteNormalNegrita = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 280f;
        float width2 = 220f;

        float initY  = 200f;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Afiliado"            , fuenteNormalNegrita, width1, 1f, 2f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText(gradoNombreAfiliado         , fuenteNormal       , width1, 1f, 2f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Numero de carpeta"   , fuenteNormalNegrita, width1, 1f, 2f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText(numeroCarpeta               , fuenteNormal       , width1, 1f, 2f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Modalidad de credito", fuenteNormalNegrita, width1, 1f, 2f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText(modalidadCredito            , fuenteNormal       , width1, 1f, 2f, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Origen Tramite"      , fuenteNormalNegrita, width1, 1f, 2f, margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText(origenTramite               , fuenteNormal       , width1, 1f, 2f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText("Carnet de Identidad", fuenteNormalNegrita, width2, 1f, 2f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(carnetIdentidad            , fuenteNormal,        width2, 1f, 2f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText("Fecha de Creacion"  , fuenteNormalNegrita, width2, 1f, 2f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(fechaCreacion              , fuenteNormal,        width2, 1f, 2f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText("Tipo de garantia"   , fuenteNormalNegrita, width2, 1f, 2f, margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(tipoDeGarantia             , fuenteNormal,        width2, 1f, 2f, margin);
        
        float auxY = maxY - initY;

        EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{c11,c21},
                new Cell[]{c12,c22},
                new Cell[]{c13,c23},
                new Cell[]{c14,c24},
                new Cell[]{c15,c25},
                new Cell[]{c16,c26},
                new Cell[]{c17}, 
                new Cell[]{c18}).draw(contentStream);
    }

    public static void crearDetallesDefinicion(PDPageContentStream contentStream, DefinicionCreditoDTO definicion) throws IOException {
        Style fuenteTitulo = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(10.5f)
                .addTextColor(Color.BLACK)
                .addLeading(1.2f)
                .build();

        Style fuenteSubtitulo = Style.builder()
                .addTextFont(fuenteBasicaNegrita)
                .addFontSize(9f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        Style fuenteNormal = Style.builder()
                .addTextFont(fuenteBasica)
                .addFontSize(8f)
                .addTextColor(Color.BLACK)
                .addLeading(0.8f)
                .build();

        float width1 = 200f;
        float width2 = 300f - 30f;

        float initY  = 340f;
        boolean margin = false;

        Cell c10_0 = EasyComponentsFactory.getSimpleCellFromText("Detalles del documento\n" , fuenteTitulo, width1, 1f, 2f, margin);

        Cell c11_0 = EasyComponentsFactory.getSimpleCellFromText("  1. Area de Prestamos"                        , fuenteSubtitulo, width1, 1f, 2f, margin);
        Cell c11_1 = EasyComponentsFactory.getSimpleCellFromText("     1. Formulario de Solicitud B-1"           , fuenteNormal, width1, 1f, 2f, margin);
        Cell c11_2 = EasyComponentsFactory.getSimpleCellFromText("     2. Carnet de identidad solicitante"       , fuenteNormal,width1, 1f, 2f, margin);
        Cell c11_3 = EasyComponentsFactory.getSimpleCellFromText("     3. Boletas de pago solicitante"           , fuenteNormal, width1, 1f, 2f, margin);
        Cell c11_4 = EasyComponentsFactory.getSimpleCellFromText("     4. Carnet de identidad garantes"          , fuenteNormal, width1, 1f, 2f, margin);
        Cell c11_5 = EasyComponentsFactory.getSimpleCellFromText("     5. Boleta de pago garantes"               , fuenteNormal, width1, 1f, 2f, margin);
        Cell c11_6 = EasyComponentsFactory.getSimpleCellFromText("     6. Documentos de respaldo(si corresponde)", fuenteNormal, width1, 1f, 2f, margin);
        Cell c11_7 = EasyComponentsFactory.getSimpleCellFromText("     7. Calificacion y Aceptacion del Credito" , fuenteNormal, width1, 1f, 2f, margin);

        Cell c12_0 = EasyComponentsFactory.getSimpleCellFromText("  2. Area tecnica"             , fuenteSubtitulo, width1, 1f, 2f, margin);
        Cell c12_1 = EasyComponentsFactory.getSimpleCellFromText("     1. Plano de lote"         , fuenteNormal, width1, 1f, 2f, margin);
        Cell c12_2 = EasyComponentsFactory.getSimpleCellFromText("     2. Plano de construccion" , fuenteNormal,width1, 1f, 2f, margin);
        Cell c12_3 = EasyComponentsFactory.getSimpleCellFromText("     3. Fotografias"           , fuenteNormal, width1, 1f, 2f, margin);
        Cell c12_4 = EasyComponentsFactory.getSimpleCellFromText("     4. Otros documentos"      , fuenteNormal, width1, 1f, 2f, margin);
        Cell c12_5 = EasyComponentsFactory.getSimpleCellFromText("     5. Formulario B-4"        , fuenteNormal, width1, 1f, 2f, margin);
        Cell c12_6 = EasyComponentsFactory.getSimpleCellFromText("     6. Formulario B-5"        , fuenteNormal, width1, 1f, 2f, margin);
        Cell c12_7 = EasyComponentsFactory.getSimpleCellFromText("     7. Formulario B-6"        , fuenteNormal, width1, 1f, 2f, margin);
        Cell c12_8 = EasyComponentsFactory.getSimpleCellFromText("     7. Catastro"              , fuenteNormal, width1, 1f, 2f, margin);

        Cell c13_0 = EasyComponentsFactory.getSimpleCellFromText("  3. Area Social"      , fuenteSubtitulo, width1, 1f, 2f, margin);
        Cell c13_1 = EasyComponentsFactory.getSimpleCellFromText("     1. Formulario B-3", fuenteNormal, width1, 1f, 2f, margin);

        Cell c14_0  = EasyComponentsFactory.getSimpleCellFromText("  4. Area Legal"                                      , fuenteSubtitulo, width1, 1f, 2f, margin);
        Cell c14_1  = EasyComponentsFactory.getSimpleCellFromText("     1. Certificado de no propiedad NAL. de DD.RR."   , fuenteNormal, width1, 1f, 2f, margin);
        Cell c14_2  = EasyComponentsFactory.getSimpleCellFromText("     2. Carnet de identidad propietarios."            , fuenteNormal,width1, 1f, 2f, margin);
        Cell c14_3  = EasyComponentsFactory.getSimpleCellFromText("     3. Informacion rapida de DD.RR."                 , fuenteNormal, width1, 1f, 2f, margin);
        Cell c14_4  = EasyComponentsFactory.getSimpleCellFromText("     4. Folio legal"                                  , fuenteNormal, width1, 1f, 2f, margin);
        Cell c14_5  = EasyComponentsFactory.getSimpleCellFromText("     5. Testimonio de propiedad y/o test aclaratorios", fuenteNormal, width1, 1f, 2f, margin);
        Cell c14_6  = EasyComponentsFactory.getSimpleCellFromText("     6. Ultimo pago de impuesto"                      , fuenteNormal, width1, 1f, 2f, margin);
        Cell c14_7  = EasyComponentsFactory.getSimpleCellFromText("     7. Otros documentos"                             , fuenteNormal, width1, 1f, 2f, margin);
        Cell c14_8  = EasyComponentsFactory.getSimpleCellFromText("     8. Contrato de prestamo"                         , fuenteNormal, width1, 1f, 2f, margin);
        Cell c14_9  = EasyComponentsFactory.getSimpleCellFromText("     9. Informe Legal"                                , fuenteNormal, width1, 1f, 2f, margin);
        Cell c14_10 = EasyComponentsFactory.getSimpleCellFromText("    10. Resolucion"                                   , fuenteNormal, width1, 1f, 2f, margin);

        Cell c15_0 = EasyComponentsFactory.getSimpleCellFromText("  5. Direccion Administracion Financiera"     , fuenteSubtitulo, width1, 1f, 2f, margin);
        Cell c15_1 = EasyComponentsFactory.getSimpleCellFromText("     1. Formulario de registro - SIGEP (C-31)", fuenteNormal, width1, 1f, 2f, margin);

        Cell c16_0 = EasyComponentsFactory.getSimpleCellFromText("  6. Direccion Ejecutiva"        , fuenteSubtitulo, width1, 1f, 2f, margin);
        Cell c16_1 = EasyComponentsFactory.getSimpleCellFromText("     1. Aprobacion de desembolso", fuenteNormal, width1, 1f, 2f, margin);


        Cell c21_1 = EasyComponentsFactory.getSimpleCellFromText( definicion.getFormularioSolicitud_B1()       , fuenteNormal, width2, 1f, 2f, margin);
        Cell c21_2 = EasyComponentsFactory.getSimpleCellFromText( definicion.getCarnetIdentidadSolicitante()   , fuenteNormal, width2, 1f, 2f, margin);
        Cell c21_3 = EasyComponentsFactory.getSimpleCellFromText( definicion.getBoletasPagoSolicitante()       , fuenteNormal, width2, 1f, 2f, margin);
        Cell c21_4 = EasyComponentsFactory.getSimpleCellFromText( definicion.getCarnetIdentidadGarantes()      , fuenteNormal, width2, 1f, 2f, margin);
        Cell c21_5 = EasyComponentsFactory.getSimpleCellFromText( definicion.getBoletaPagoGarantes()           , fuenteNormal, width2, 1f, 2f, margin);
        Cell c21_6 = EasyComponentsFactory.getSimpleCellFromText( definicion.getDocumentosRespaldo()           , fuenteNormal, width2, 1f, 2f, margin);
        Cell c21_7 = EasyComponentsFactory.getSimpleCellFromText( definicion.getCalificacionAceptacionCredito(), fuenteNormal, width2, 1f, 2f, margin);

        Cell c22_1 = EasyComponentsFactory.getSimpleCellFromText(definicion.getPlanoLote()        , fuenteNormal, width2, 1f, 2f, margin);
        Cell c22_2 = EasyComponentsFactory.getSimpleCellFromText(definicion.getPlanoConstruccion(), fuenteNormal, width2, 1f, 2f, margin);
        Cell c22_3 = EasyComponentsFactory.getSimpleCellFromText(definicion.getFotografias()      , fuenteNormal, width2, 1f, 2f, margin);
        Cell c22_4 = EasyComponentsFactory.getSimpleCellFromText(definicion.getOtrosDocumentos()  , fuenteNormal, width2, 1f, 2f, margin);
        Cell c22_5 = EasyComponentsFactory.getSimpleCellFromText(definicion.getFormulario_B4()    , fuenteNormal, width2, 1f, 2f, margin);
        Cell c22_6 = EasyComponentsFactory.getSimpleCellFromText(definicion.getFormulario_B5()    , fuenteNormal, width2, 1f, 2f, margin);
        Cell c22_7 = EasyComponentsFactory.getSimpleCellFromText(definicion.getFormulario_B6()    , fuenteNormal, width2, 1f, 2f, margin);
        Cell c22_8 = EasyComponentsFactory.getSimpleCellFromText(definicion.getCatastro()         , fuenteNormal, width2, 1f, 2f, margin);

        Cell c23_1 = EasyComponentsFactory.getSimpleCellFromText(definicion.getFormulario_B3(), fuenteNormal, width2, 1f, 2f, margin);

        Cell c24_1  = EasyComponentsFactory.getSimpleCellFromText( definicion.getCertificadoNoPropiedad()             , fuenteNormal, width2, 1f, 2f, margin);
        Cell c24_2  = EasyComponentsFactory.getSimpleCellFromText( definicion.getCarnetIdentidadPropietarios()        , fuenteNormal, width2, 1f, 2f, margin);
        Cell c24_3  = EasyComponentsFactory.getSimpleCellFromText( definicion.getInformacionRapida()                  , fuenteNormal, width2, 1f, 2f, margin);
        Cell c24_4  = EasyComponentsFactory.getSimpleCellFromText( definicion.getFolioLegal()                         , fuenteNormal, width2, 1f, 2f, margin);
        Cell c24_5  = EasyComponentsFactory.getSimpleCellFromText( definicion.getTestimonioPropiedadTestAclaratorios(), fuenteNormal, width2, 1f, 2f, margin);
        Cell c24_6  = EasyComponentsFactory.getSimpleCellFromText( definicion.getUltimoPagoImpuesto()                 , fuenteNormal, width2, 1f, 2f, margin);
        Cell c24_7  = EasyComponentsFactory.getSimpleCellFromText( definicion.getOtrosDocumentos2()                   , fuenteNormal, width2, 1f, 2f, margin);
        Cell c24_8  = EasyComponentsFactory.getSimpleCellFromText( definicion.getContratoPrestamo()                   , fuenteNormal, width2, 1f, 2f, margin);
        Cell c24_9  = EasyComponentsFactory.getSimpleCellFromText( definicion.getInformeLegal()                       , fuenteNormal, width2, 1f, 2f, margin);
        Cell c24_10 = EasyComponentsFactory.getSimpleCellFromText( definicion.getResolucionLegal()                    , fuenteNormal, width2, 1f, 2f, margin);

        Cell c25_1 = EasyComponentsFactory.getSimpleCellFromText(definicion.getFormularioRegistro_SIGEP_C3(), fuenteNormal, width2, 1f, 2f, margin);

        Cell c26_1 = EasyComponentsFactory.getSimpleCellFromText(definicion.getAprobacionDesembolso(), fuenteNormal, width2, 1f, 2f, margin);


        float auxY = maxY - initY;

        EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness +20f, auxY,
                new Cell[]{c10_0},
                new Cell[]{c11_0},new Cell[]{c11_1,c21_1},new Cell[]{c11_2,c21_2},new Cell[]{c11_3,c21_3},new Cell[]{c11_4,c21_4},new Cell[]{c11_5,c21_5},new Cell[]{c11_6,c21_6},new Cell[]{c11_7,c21_7},
                new Cell[]{c12_0},new Cell[]{c12_1,c22_1},new Cell[]{c12_2,c22_2},new Cell[]{c12_3,c22_3},new Cell[]{c12_4,c22_4},new Cell[]{c12_5,c22_5},new Cell[]{c12_6,c22_6},new Cell[]{c12_7,c22_7},new Cell[]{c12_8,c22_8},
                new Cell[]{c13_0},new Cell[]{c13_1,c23_1},
                new Cell[]{c14_0},new Cell[]{c14_1,c24_1},new Cell[]{c14_2,c24_2},new Cell[]{c14_3,c24_3},new Cell[]{c14_4,c24_4},new Cell[]{c14_5,c24_5},new Cell[]{c14_6,c24_6},new Cell[]{c14_7,c24_7},new Cell[]{c14_8,c24_8},new Cell[]{c14_9,c24_9},new Cell[]{c14_10,c24_10},
                new Cell[]{c15_0},new Cell[]{c15_1,c25_1},
                new Cell[]{c16_0},new Cell[]{c16_1,c26_1})
                .draw(contentStream);
    }
}