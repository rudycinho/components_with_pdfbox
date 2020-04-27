package subsistema.pdf.lib.basic;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import subsistema.pdf.utils.settings.FontGroup;
import subsistema.pdf.utils.settings.StyleEnum;

import java.awt.Color;

public class Style {
    private Color  textColor  = Color.BLACK;
    private PDFont textFont   = PDType1Font.HELVETICA;
    private float  fontSize   = 9;
    private float  leading = 1;

    private Style(){ }

    public Color getTextColor() {
        return textColor;
    }

    public PDFont getTextFont() {
        return textFont;
    }

    public float getFontSize() {
        return fontSize;
    }

    public float getLeading() {
        return leading;
    }

    public float getSeparation() {
        return leading * fontSize / 3;
    }


    public static StyleBuilder builder(){
        return new Style.StyleBuilder();
    }

    public static class StyleBuilder{
        private final Style style = new Style();

        public StyleBuilder addTextColor(Color textColor){
            style.textColor =textColor;
            return this;
        }
        public StyleBuilder addTextFont(PDFont textFont){
            style.textFont =textFont;
            return this;
        }

        public StyleBuilder addFontSize(float fontSize){
            style.fontSize =fontSize;
            return this;
        }

        public StyleBuilder addLeading(float leading){
            style.leading = leading;
            return this;
        }

        public Style build(){
            return style;
        }
    }

    public static Style getStyle(FontGroup fontGroup, StyleEnum styleEnum){
        Style style = new Style();

        if(styleEnum == StyleEnum.DATA_NORMAL){
            style.fontSize = 10.5f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 0.8f;
        }else if(styleEnum == StyleEnum.INFO_NORMAL){
            style.fontSize = 10.5f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 0.8f;
        }else if(styleEnum == StyleEnum.SUBTITLE_BOLD){
            style.fontSize = 14.0f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.8f;
        }else if(styleEnum == StyleEnum.DATA_BOLD){
            style.fontSize = 10.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.8f;
        }else if(styleEnum == StyleEnum.INFO_BOLD){
            style.fontSize = 10.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.8f;
        }else if(styleEnum == StyleEnum.DATA_NORMAL_SPACED){
            style.fontSize = 10.5f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 1.0f;
        }else if(styleEnum == StyleEnum.SMALL_NORMAL){
            style.fontSize = 8f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 1.0f;
        }else if(styleEnum == StyleEnum.DATA_SEMI_SPACED){
            style.fontSize = 10.5f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 0.9f;
        }else if(styleEnum == StyleEnum.DATA_BOLD_SPACED){
            style.fontSize = 10.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 1.0f;
        }else if(styleEnum == StyleEnum.MINIMAL_NORMAL){
            style.fontSize = 9.5f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 0.85f;
        }else if(styleEnum == StyleEnum.MINIMAL_BOLD){
            style.fontSize = 9.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.85f;
        }else if(styleEnum == StyleEnum.MINIMAL_NORMAL_SPACED){
            style.fontSize = 9.5f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 1.1f;
        }else if(styleEnum == StyleEnum.MINIMAL_BOLD_SPACED){
            style.fontSize = 9.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 1.1f;
        }else if(styleEnum == StyleEnum.TABLE_BOLD_SPACED){
            style.fontSize = 10.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 1.0f;
        }else if(styleEnum == StyleEnum.TABLE_MINIMAL_NORMAL_SPACED){
            style.fontSize = 9.5f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 1.0f;
        }else if(styleEnum == StyleEnum.TABLE_MINIMAL_NORMAL_EXTRA_SPACED){
            style.fontSize = 9.5f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 1.2f;
        }else if(styleEnum == StyleEnum.TABLE_MINIMAL_BOLD_EXTRA_SPACED_RED){
            style.fontSize = 9.5f;
            style.textFont = fontGroup.getBold();
            style.textColor= Color.RED;
            style.leading  = 1.2f;
        }else if(styleEnum == StyleEnum.TABLE_MINIMAL_BOLD){
            style.fontSize = 9.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.8f;
        }else if(styleEnum == StyleEnum.TABLE_MINIMAL_BOLD_SPACED){
            style.fontSize = 9.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 1.0f;
        }else if(styleEnum == StyleEnum.TABLE_MINIMAL_BOLD_EXTRA_SPACED){
            style.fontSize = 9.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 1.2f;
        }else if(styleEnum == StyleEnum.TABLE_LESS_MINIMAL_NORMAL_SPACED){
            style.fontSize = 9f;
            style.textFont = fontGroup.getBold();
            style.leading  = 1.0f;
        }else if(styleEnum == StyleEnum.TABLE_LESS_MINIMAL_BOLD){
            style.fontSize = 9f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.8f;
        }else if(styleEnum == StyleEnum.CUSTOMISED_FORM_BOLD_SPACED){
            style.fontSize = 10.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.9f;
        }else if(styleEnum == StyleEnum.CUSTOMISED_FORM_LESS_BOLD_SPACED){
            style.fontSize = 9.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.9f;
        }else if(styleEnum == StyleEnum.CUSTOMISED_FORM_NARROW_BOLD){
            style.fontSize = 9f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.8f;
        }else if(styleEnum == StyleEnum.CUSTOMISED_FORM_NARROW_BOLD_GRAY){
            style.fontSize = 9f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.8f;
            style.textColor= new Color(60,60,60);
        }else if(styleEnum == StyleEnum.CUSTOMISED_FORM_NARROW_BOLD_SPACED_GRAY){
            style.fontSize = 9f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.9f;
            style.textColor= new Color(60,60,60);
        }else if(styleEnum == StyleEnum.CUSTOMISED_FORM_NARROW){
            style.fontSize = 9f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 0.8f;
        }else if(styleEnum == StyleEnum.CUSTOMISED_FORM_OBLIQUE){
            style.fontSize = 9f;
            style.textFont = fontGroup.getItalic();
            style.leading  = 0.8f;
        }else if(styleEnum == StyleEnum.CUSTOMISED_FORM_TINY){
            style.fontSize = 7f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 0.8f;
        }else if(styleEnum == StyleEnum.CUSTOMISED_FORM_TINY_SPACED){
            style.fontSize = 7f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 0.9f;
        }else if(styleEnum == StyleEnum.HEADER){
            style.fontSize = 18f;
            style.textFont = fontGroup.getBold();
            style.leading  = 1f;
        }else if(styleEnum == StyleEnum.FOOTER){
            style.fontSize = 8f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 1f;
        }else if(styleEnum == StyleEnum.OWNER){
            style.fontSize = 8.5f;
            style.textFont = fontGroup.getNormal();
            style.leading  = 1f;
        }else if(styleEnum == StyleEnum.OWNER_BOLD){
            style.fontSize = 8.5f;
            style.textFont = fontGroup.getBold();
            style.leading  = 1f;
        }else if(styleEnum == StyleEnum.TITLE){
            style.fontSize = 15f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.95f;
        }else if(styleEnum == StyleEnum.NUMBER){
            style.fontSize = 9f;
            style.textFont = fontGroup.getBold();
            style.leading  = 0.95f;
        }



        return style;
    }
}
