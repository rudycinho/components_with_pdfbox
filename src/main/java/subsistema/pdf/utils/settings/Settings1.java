package subsistema.pdf.utils.settings;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class Settings1 {
    private float maxY;
    private float maxX;

    private float thickness;

    private float marginStartX;
    private float marginEndX;

    private PDFont fuenteBasica;
    private PDFont fuenteBasicaNegrita;

    private Settings1(){}

    public static Settings1 MODEL_1(){
        Settings1 settings = new Settings1();

        settings.maxY         = Data1.MAX_LETTER_Y;
        settings.maxX         = Data1.MAX_LETTER_X;
        settings.thickness    = Data1.THICKNESS_MODEL_1;
        settings.marginStartX = Data1.MARGIN_START_X_MODEL_1;
        settings.marginEndX   = Data1.MARGIN_END_X_MODEL_1;

        settings.fuenteBasica        = Data1.BASIC_FONT;
        settings.fuenteBasicaNegrita = Data1.BASIC_BOLD_FONT;

        return settings;
    }
    public static Settings1 MODEL_2(){
        Settings1 settings = new Settings1();
        settings.maxY         = Data1.MAX_LETTER_X;
        settings.maxX         = Data1.MAX_LETTER_Y;
        settings.thickness    = Data1.THICKNESS_MODEL_1;
        settings.marginStartX = Data1.MARGIN_START_X_MODEL_2;
        settings.marginEndX   = Data1.MARGIN_END_X_MODEL_1;

        settings.fuenteBasica        = Data1.BASIC_FONT;
        settings.fuenteBasicaNegrita = Data1.BASIC_BOLD_FONT;

        return settings;
    }
    public static Settings1 MODEL_3(){
        Settings1 settings = new Settings1();
        settings.maxY         = Data1.MAX_OFFICE_Y;
        settings.maxX         = Data1.MAX_OFFICE_X;
        settings.thickness    = Data1.THICKNESS_MODEL_1;
        settings.marginStartX = Data1.MARGIN_START_X_MODEL_1;
        settings.marginEndX   = Data1.MARGIN_END_X_MODEL_1;

        settings.fuenteBasica        = Data1.BASIC_FONT;
        settings.fuenteBasicaNegrita = Data1.BASIC_BOLD_FONT;
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
