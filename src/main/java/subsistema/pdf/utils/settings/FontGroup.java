package subsistema.pdf.utils.settings;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class FontGroup {
    private PDFont normal;
    private PDFont bold;
    private PDFont italic;
    private PDFont boldItalic;

    public PDFont getNormal() {
        return normal;
    }

    public PDFont getBold() {
        return bold;
    }

    public PDFont getItalic() {
        return italic;
    }

    public PDFont getBoldItalic() {
        return boldItalic;
    }

    public static FontGroup getFontGroup(
            FontEnum fontEnum,
            PDDocument doc){
        FontGroup fontGroup = new FontGroup();

        if(fontEnum==FontEnum.ARIAL){
            try {
                fontGroup.normal     = PDTrueTypeFont.loadTTF(doc, new File("/home/rudy/arial.ttf"));
                fontGroup.bold       = PDTrueTypeFont.loadTTF(doc, new File("/home/rudy/arial_bold.ttf"));
                fontGroup.italic     = PDTrueTypeFont.loadTTF(doc, new File("/home/rudy/arial_italic.ttf"));
                fontGroup.boldItalic = PDTrueTypeFont.loadTTF(doc, new File("/home/rudy/arial_bold_italic.ttf"));
            } catch (IOException e) {
                fontGroup.normal     = PDType1Font.HELVETICA;
                fontGroup.bold       = PDType1Font.HELVETICA_BOLD;
                fontGroup.italic     = PDType1Font.HELVETICA_OBLIQUE;
                fontGroup.boldItalic = PDType1Font.HELVETICA_BOLD_OBLIQUE;
            }
        }else if(fontEnum==FontEnum.COURIER){
            fontGroup.normal     = PDType1Font.COURIER;
            fontGroup.bold       = PDType1Font.COURIER_BOLD;
            fontGroup.italic     = PDType1Font.COURIER_OBLIQUE;
            fontGroup.boldItalic = PDType1Font.COURIER_BOLD_OBLIQUE;
        }else if(fontEnum==FontEnum.TIMES){
            fontGroup.normal     = PDType1Font.HELVETICA;
            fontGroup.bold       = PDType1Font.HELVETICA_BOLD;
            fontGroup.italic     = PDType1Font.HELVETICA_OBLIQUE;
            fontGroup.boldItalic = PDType1Font.HELVETICA_BOLD_OBLIQUE;
        }else{
            fontGroup.normal     = PDType1Font.HELVETICA;
            fontGroup.bold       = PDType1Font.HELVETICA_BOLD;
            fontGroup.italic     = PDType1Font.HELVETICA_OBLIQUE;
            fontGroup.boldItalic = PDType1Font.HELVETICA_BOLD_OBLIQUE;
        }
        return fontGroup;
    }
}
