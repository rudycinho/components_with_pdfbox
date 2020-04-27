package subsistema.pdf.utils.settings;

public class Model {
    private float maxY;
    private float maxX;
    private float thickness;
    private float marginStartX;
    private float marginEndX;
    private FontEnum font;


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

    public FontEnum getFont(){
        return font;
    }

    private Model(){}

    public static Model getModel1(){
        Model model = new Model();
        model.maxY         = Data.MAX_LETTER_Y;
        model.maxX         = Data.MAX_LETTER_X;
        model.thickness    = Data.THICKNESS_MODEL_WIDE;
        model.marginStartX = Data.MARGIN_START_X_MODEL_WIDE;
        model.marginEndX   = Data.MARGIN_END_X_MODEL_WIDE;
        model.font         = Data.FONT_COURIER;
        return model;
    }
    public static Model getModel2(){
        Model model = new Model();
        model.maxY         = Data.MAX_LETTER_X;
        model.maxX         = Data.MAX_LETTER_Y;
        model.thickness    = Data.THICKNESS_MODEL_WIDE;
        model.marginStartX = Data.MARGIN_START_X_MODEL_NARROW;
        model.marginEndX   = Data.MARGIN_END_X_MODEL_WIDE;
        model.font         = Data.FONT_COURIER;
        return model;
    }
    public static Model getModel3(){
        Model model = new Model();
        model.maxY         = Data.MAX_OFFICE_Y;
        model.maxX         = Data.MAX_OFFICE_X;
        model.thickness    = Data.THICKNESS_MODEL_WIDE;
        model.marginStartX = Data.MARGIN_START_X_MODEL_WIDE;
        model.marginEndX   = Data.MARGIN_END_X_MODEL_WIDE;
        model.font         = Data.FONT_COURIER;
        return model;
    }

}
