package subsistema.pdf.lib.basic;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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
        private Style style = new Style();

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
}
