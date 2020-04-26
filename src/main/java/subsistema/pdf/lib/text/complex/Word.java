package subsistema.pdf.lib.text.complex;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Component;
import subsistema.pdf.lib.basic.Style;

import java.awt.*;
import java.io.IOException;

public class Word implements Component {
    private float startX = 0f;
    private float startY = 0f;
    private float width  = 0f;
    private float height = 0f;

    private String    textContent = "null";
    private Style     style       = Style.builder().build();
    private Alignment alignment   = Alignment.CENTER;

    private float dX = 0;
    private float dY = 0;

    private Word(){ }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height * style.getLeading();
    }

    @Override
    public void setDX(float dX) {
        this.dX = dX;
    }

    @Override
    public void setDY(float dY) {
        this.dY = dY;
    }

    @Override
    public void rebuild() {
        startX = startX + dX;
        startY = startY - dY;
        dX = 0;
        dY = 0;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public String getTextContent() {
        return textContent;
    }

    public Style getStyle() {
        return style;
    }

    public static WordBuilder builder(){
        return new WordBuilder();
    }

    private void buildWidth() throws IOException {
        width = style.getTextFont().getStringWidth(textContent) / 1000 * style.getFontSize();
    }

    public void draw(PDPageContentStream contentStream) throws IOException {
        contentStream.setNonStrokingColor(style.getTextColor());
        contentStream.setFont(style.getTextFont(), style.getFontSize());

        contentStream.beginText();
        contentStream.newLineAtOffset(startX+dX, startY + height - height/4);

        contentStream.showText(textContent);
        contentStream.endText();

        /*contentStream.setStrokingColor(Color.ORANGE);
        contentStream.addRect(startX,startY,width,height);
        contentStream.stroke();

        contentStream.setStrokingColor(Color.BLACK);
        contentStream.addRect(startX,startY,1,-1);
        contentStream.stroke();*/
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public static class WordBuilder{
        private Word word = new Word();

        public WordBuilder addStartX(float startX) {
            word.startX = startX;
            return this;
        }

        public WordBuilder addStartY(float startY) {
            word.startY = startY;
            return this;
        }

        public WordBuilder addTextContent(String textContent) throws IOException {
            word.textContent =textContent;
            word.buildWidth();
            return this;
        }

        public WordBuilder addStyle(Style style) throws IOException {
            word.style = style;
            word.buildWidth();
            return this;
        }

        public Word build(){
            word.height = word.getStyle().getFontSize() * -1;
            return word;
        }
    }

}
