import com.lowagie.text.DocumentException;
import lib.basic.Alignment;
import lib.basic.Style;
import lib.img.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import lib.shapes.Cross;
import lib.tables.Cell;
import lib.tables.Column;
import lib.text.MultipleParagraph;
import services.GeneradorFormulariosService;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static GeneradorFormulariosService service = new GeneradorFormulariosService();

    public static void main(String ...args) throws IOException, DocumentException {
        //"\u21B5"
        /*service.FormularioInformacionUsuario();
        service.FormularioInformacionSucursal();
        service.FormularioAjustesSistema();
        service.FormularioAfiliacion();
        service.FormularioDesafiliacion();
        service.FormularioInformacionPerfil();
        service.FormularioPrecalificadorPrestamos();
        service.FormularioPlanPagos();
        service.FormularioRequisitosSolicitudCredito();
        service.FormularioDefinicionCredito();
        */
        service.FormularioSolicitudCreditoVivienda();

    }

    public static void prueba() throws IOException {

        int maxY = (int) PDRectangle.LETTER.getHeight();
        int maxX = (int) PDRectangle.LETTER.getWidth();

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(maxX, maxY));

        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        String text1 = "Lorem ipsum dolor sit amet,\n consectetur\n adipiscing\n elit, \nsed do\n eiusmod\n tempor\n incididunt" +
                " ut labore et dolore magna aliqua.\n Ut enim ad minim veniam,\n quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute \nirure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua.\n Ut enim ad minim veniam, \nquis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. ";

        String text2 = "Lorem ipsum dolor sit amet, consectetur\n adipiscing elit, \nsed do eiusmod\n tempor\n incididunt" +
                " ut labore et dolore magna aliqua.Ut enim ad minim veniam,\n quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute \nirure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua.Ut enim ad minim veniam, \nquis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. ";

        String text3 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.\n Ut enim ad minim veniam,\n quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute \nirure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua.\n Ut enim ad minim veniam, \nquis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. ";

        String text4 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. ";

        String st = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Ut enim ad minim veniam, ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. ";

        //PDImageXObject pdImage = PDImageXObject.createFromFile("C:/PdfBox_Examples/logo.png",doc);

        //String st = "Lorem ipsum dolor sit amet";

        Style style1 = Style.builder()
                .addFontSize(13f)
                .addTextColor(Color.BLUE)
                .addTextFont(PDType1Font.COURIER_BOLD)
                .addLeading(1.4f)
                .build();

        Style style2 = Style.builder()
                .addFontSize(13f)
                .addTextColor(Color.BLUE)
                .addTextFont(PDType1Font.HELVETICA)
                .addLeading(1.1f)
                .build();

        /*TextLine textLine = TextLine.builder()
                .addTextContent(st)
                .addAlignment(Alignment.LEFT)
                .addStartX(20)
                .addStartY(90)
                .addWidth(500f)
                .addStyle(style1)
                .build();

        float x0 = textLine.getStartX();
        float y0 = textLine.getStartY();
        float xf = textLine.getWidth();
        float yf = textLine.getHeight();
        */

        /*Paragraph paragraph = Paragraph.builder()
                .addTextContent(st)
                .addAlignment(Alignment.LEFT)
                .addStartX(20)
                .addStartY(90)
                .addWidth(400f)
                .addStyle(style1)
                .build();

        float x0 = paragraph.getStartX();
        float y0 = paragraph.getStartY();
        float xf = paragraph.getWidth();
        float yf = paragraph.getHeight();

        Rectangle rectangle = new Rectangle(x0,y0,xf,yf);
        rectangle.draw(contentStream);
*/
        //Grid grid = new Grid(maxX,maxY);
        //grid.draw(contentStream);

        MultipleParagraph component1 = MultipleParagraph.builder()
                .addTextContent(text1)
                .addAlignment(Alignment.LEFT)
                .addStartX(120f)
                .addStartY(490f)
                .addWidth(200f)
                .addStyle(style1)
                .build();

        MultipleParagraph component2 = MultipleParagraph.builder()
                .addTextContent(text2)
                .addAlignment(Alignment.LEFT)
                .addStartX(20f)
                .addStartY(290f)
                .addWidth(200f)
                .addStyle(style1)
                .build();

        MultipleParagraph component3 = MultipleParagraph.builder()
                .addTextContent(text3)
                .addAlignment(Alignment.LEFT)
                .addStartX(320f)
                .addStartY(-190f)
                .addWidth(200f)
                .addStyle(style2)
                .build();

        /*

        Cell cell1 = Cell.builder()
            .addRelativeDistanceX(10)
            .addRelativeDistanceY(10)
            .addHasMargin(true)
            .addHasFilling(false)
            .addColorMargin(Color.BLACK)
            .addColorFilling(Color.WHITE)
            .addComponent(component1)
            .build();

        Cell cell2 = Cell.builder()
                .addRelativeDistanceX(10)
                .addRelativeDistanceY(10)
                .addHasMargin(true)
                .addHasFilling(false)
                .addColorMargin(Color.BLACK)
                .addColorFilling(Color.WHITE)
                .addComponent(component2)
                .build();

        Cell cell3 = Cell.builder()
                .addRelativeDistanceX(10)
                .addRelativeDistanceY(10)
                .addHasMargin(true)
                .addHasFilling(false)
                .addColorMargin(Color.BLACK)
                .addColorFilling(Color.WHITE)
                .addComponent(component3)
                .build();

        Column column = Column.builder()
                .addStartX(20f)
                .addStartY(800f)
                .addCell(cell1)
                .addCell(cell2)
                .addCell(cell3)
                .build();

        //column.draw(contentStream);

        column.setDX(30);
        column.setDY(30);
        column.rebuild();

        column.draw(contentStream);*/
/*
        float x0 = column.getStartX();
        float y0 = column.getStartY();
        float xf = column.getWidth();
        float yf = column.getHeight();

        //System.out.println(x0);
        //System.out.println(y0);
        //System.out.println(xf);
        //System.out.println(yf);

        Rectangle rectangle = Rectangle.builder()
                .addStartX(column.getStartX())
                .addStartY(column.getStartY())
                .addWidth(column.getWidth())
                .addHeight(column.getHeight())
                .addColor(Color.GREEN)
                .addThickness(3)
                .build();
        rectangle.draw(contentStream);

        System.out.println(column.getHeight());

        Cross cross = Cross.builder()
                .addStartX(column.getStartX())
                .addStartY(column.getStartY())
                .addWidth(column.getWidth())
                .addHeight(column.getHeight())
                .addColor(Color.GREEN)
                .addThickness(3)
                .build();
        cross.draw(contentStream);


        Image image = Image.builder()
                .addStartX(200)
                .addStartY(200)
                .addWidth(120)
                .addHeight(160)
                .addImage( PDImageXObject.createFromFile("/home/rudy/Code/spring/libPDFBOX/src/main/resources/saitama.png",doc))
                .build();
        image.draw(contentStream);


        /*contentStream.setStrokingColor(Color.ORANGE);
        contentStream.setNonStrokingColor(Color.ORANGE);
        contentStream.addRect(
                column.getStartX(),
                column.getStartY(),
                column.getWidth(),
                column.getHeight());//.setColor(Color.ORANGE);
        //contentStream.stroke();
        contentStream.fill();
        //rectangle.draw(contentStream);*/

        Image image = Image.builder()
                .addStartX(200)
                .addStartY(200)
                .addWidth(120)
                .addHeight(160)
                .addImage( PDImageXObject.createFromFile("/home/rudy/Code/spring/libPDFBOX/src/main/resources/saitama.png",doc))
                .build();

        image.draw(contentStream);


        Cell cell1 = Cell.builder()
                .addRelativeDistanceX(10)
                .addRelativeDistanceY(10)
                .addHasMargin(true)
                .addHasFilling(false)
                .addComponent(image)
                .build();

        Cell cell2 = Cell.builder()
                .addRelativeDistanceX(10)
                .addRelativeDistanceY(10)
                .addHasMargin(true)
                .addHasFilling(false)
                .addComponent(component3)
                .build();

        Cell cell3 = Cell.builder()
                .addRelativeDistanceX(10)
                .addRelativeDistanceY(10)
                .addHasMargin(true)
                .addHasFilling(false)
                .addComponent(Cross.builder()
                        .addWidth(50f)
                        .addHeight(-60f)
                        .addColor(Color.GREEN)
                        .addThickness(3)
                        .build())
                .build();

        Column column = Column.builder()
                .addStartX(20f)
                .addStartY(600f)
                .addCell(cell1)
                .addCell(cell2)
                .addCell(cell3)
                .build();

        //image.draw(contentStream);

        column.draw(contentStream);

        contentStream.close();
        doc.save(new File("/home/rudy/example.pdf"));
    }
}
