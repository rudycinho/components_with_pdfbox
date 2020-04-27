package subsistema.pdf.utils.settings;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Data1 {
    public static PDFont BASIC_FONT              = PDType1Font.COURIER;
    public static PDFont BASIC_BOLD_FONT         = PDType1Font.COURIER_BOLD;
    public static PDFont BASIC_BOLD_OBLIQUE_FONT = PDType1Font.COURIER_BOLD_OBLIQUE;
    public static PDFont BASIC_OBLIQUE_FONT      = PDType1Font.COURIER_OBLIQUE;

    public static float MAX_LETTER_X = PDRectangle.LETTER.getWidth();
    public static float MAX_LETTER_Y = PDRectangle.LETTER.getHeight();

    public static float MAX_OFFICE_X = PDRectangle.LEGAL.getWidth();
    public static float MAX_OFFICE_Y = PDRectangle.LEGAL.getHeight() - PDRectangle.LEGAL.getHeight()/14;


    public static float THICKNESS_MODEL_1 = 15f;
    public static float THICKNESS_MODEL_2 = 15f;

    public static float MARGIN_START_X_MODEL_1 = 50f;
    public static float MARGIN_START_X_MODEL_2 = 25f;
    public static float MARGIN_END_X_MODEL_1 = 25f;
}
