package subsistema.pdf.utils.factories;

import org.apache.pdfbox.pdmodel.font.PDFont;
import subsistema.pdf.utils.Data;

public class Settings {
    private float maxY;
    private float maxX;

    private float thickness;

    private float marginStartX;
    private float marginEndX;

    private PDFont fuenteBasica;
    private PDFont fuenteBasicaNegrita;

    private Settings(){}

    public static Settings MODEL_1(){
        Settings settings = new Settings();

        settings.maxY         = Data.MAX_LETTER_Y;
        settings.maxX         = Data.MAX_LETTER_X;
        settings.thickness    = Data.THICKNESS_MODEL_1;
        settings.marginStartX = Data.MARGIN_START_X_MODEL_1;
        settings.marginEndX   = Data.MARGIN_END_X_MODEL_1;

        settings.fuenteBasica        = Data.BASIC_FONT;
        settings.fuenteBasicaNegrita = Data.BASIC_BOLD_FONT;

        return settings;
    }
    public static Settings MODEL_2(){
        Settings settings = new Settings();
        settings.maxY         = Data.MAX_LETTER_X;
        settings.maxX         = Data.MAX_LETTER_Y;
        settings.thickness    = Data.THICKNESS_MODEL_1;
        settings.marginStartX = Data.MARGIN_START_X_MODEL_2;
        settings.marginEndX   = Data.MARGIN_END_X_MODEL_1;

        settings.fuenteBasica        = Data.BASIC_FONT;
        settings.fuenteBasicaNegrita = Data.BASIC_BOLD_FONT;

        return settings;
    }
    public static Settings MODEL_3(){
        Settings settings = new Settings();

        return settings;
    }



    public float getMaxY() {
        return maxY;
    }

    public float getMaxX() {
        return maxX;
    }

    public float getThickness() {
        return thickness;
    }

    public float getMarginStartX() {
        return marginStartX;
    }

    public float getMarginEndX() {
        return marginEndX;
    }

    public PDFont getFuenteBasica() {
        return fuenteBasica;
    }

    public PDFont getFuenteBasicaNegrita() {
        return fuenteBasicaNegrita;
    }
}
