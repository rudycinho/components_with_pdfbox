package subsistema.pdf.utils.settings;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Data {
    public static FontEnum FONT_COURIER  = FontEnum.COURIER;
    public static FontEnum FONT_TIMES    = FontEnum.TIMES;
    public static FontEnum FONT_HELVETICA= FontEnum.HELVETICA;
    public static FontEnum FONT_ARIAL    = FontEnum.ARIAL;

    public static float MAX_LETTER_X = PDRectangle.LETTER.getWidth();
    public static float MAX_LETTER_Y = PDRectangle.LETTER.getHeight();

    public static float MAX_OFFICE_X = PDRectangle.LEGAL.getWidth();
    public static float MAX_OFFICE_Y = PDRectangle.LEGAL.getHeight() - PDRectangle.LEGAL.getHeight()/14;

    public static float THICKNESS_MODEL_WIDE   = 15f;
    public static float THICKNESS_MODEL_NARROW = 15f;

    public static float MARGIN_START_X_MODEL_WIDE   = 50f;
    public static float MARGIN_START_X_MODEL_NARROW = 25f;

    public static float MARGIN_END_X_MODEL_WIDE = 25f;
}
