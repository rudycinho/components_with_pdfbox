package lib.text;

import lib.basic.Alignment;
import lib.basic.Component;
import lib.basic.Style;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public class TextLine implements Component {
    private float startX = 0f;
    private float startY = 0f;
    private float width  = 0f;
    private float height = 0f;

    private String    textContent = "null";
    private Style style       = Style.builder().build();
    private Alignment alignment   = Alignment.CENTER;

    private float dX = 0;
    private float dY = 0;

    private TextLine(){ }

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

    public static TextLineBuilder builder(){
        return new TextLine.TextLineBuilder();
    }

    public void draw(PDPageContentStream contentStream) throws IOException {
        float textWidth = style.getTextFont().getStringWidth(textContent) / 1000 * style.getFontSize();

        float dX = 0;
        if(alignment == Alignment.CENTER)
            dX = (width - textWidth) / 2;
        if(alignment == Alignment.RIGHT)
            dX = (width - textWidth);


        contentStream.setNonStrokingColor(style.getTextColor());
        contentStream.setFont(style.getTextFont(), style.getFontSize());

        contentStream.beginText();
        contentStream.newLineAtOffset(startX+dX, startY + height - height/4);

        contentStream.showText(textContent);
        contentStream.endText();
    }

    public static class TextLineBuilder{
        private TextLine textLine = new TextLine();

        public TextLineBuilder addStartX(float startX) {
            textLine.startX = startX;
            return this;
        }

        public TextLineBuilder addStartY(float startY) {
            textLine.startY = startY;
            return this;
        }

        public TextLineBuilder addWidth(float width) {
            textLine.width = width;
            return this;
        }

        public TextLineBuilder addHeight(float height) {
            textLine.height = height;
            return this;
        }

        public TextLineBuilder addAlignment(Alignment alignment) {
            textLine.alignment = alignment;
            return this;
        }

        public TextLineBuilder addTextContent(String textContent) {
            textLine.textContent =textContent;
            return this;
        }

        public TextLineBuilder addStyle(Style style) {
            textLine.style = style;
            return this;
        }

        public TextLine build(){
            textLine.height = textLine.getStyle().getFontSize() * -1;
            return textLine;
        }
    }

}
