package subsistema.pdf.lib.text;

import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Component;
import subsistema.pdf.lib.basic.Style;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Paragraph implements Component {
    private float startX = 0f;
    private float startY = 0f;
    private float width  = 0f;
    private float height = 0f;

    private String    textContent = "null";
    private Style style       = Style.builder().build();
    private Alignment alignment   = Alignment.LEFT;

    private List<TextLine> lines  = new LinkedList<>();
    private float dX = 0;
    private float dY = 0;

    private Paragraph(){ }

    @Override
    public float getStartX() {
        return startX;
    }

    @Override
    public float getStartY() {
        return startY;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height - style.getSeparation();
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

        for(TextLine line : lines){
            line.setDX(dX);
            line.setDY(dY);
            line.rebuild();
        }
        dX = 0;
        dY = 0;
    }

    public String getTextContent() {
        return textContent;
    }

    public Style getStyle() {
        return style;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public static Paragraph.ParagraphBuilder builder(){
        return new Paragraph.ParagraphBuilder();
    }

    public void draw(PDPageContentStream contentStream) throws IOException {
        for(TextLine line : lines)
            line.draw(contentStream);
    }

    public static class ParagraphBuilder{
        private Paragraph paragraph = new Paragraph();

        public ParagraphBuilder addStartX(float startX){
            paragraph.startX = startX;
            return this;
        }

        public ParagraphBuilder addStartY(float startY){
            paragraph.startY = startY;
            return this;
        }

        public ParagraphBuilder addWidth(float width){
            paragraph.width = width;
            return this;
        }

        public ParagraphBuilder addHeight(float height){
            paragraph.height = height;
            return this;
        }

        public ParagraphBuilder addTextContent(String textContent){
            paragraph.textContent = textContent;
            return this;
        }

        public ParagraphBuilder addStyle(Style style){
            paragraph.style = style;
            return this;
        }

        public ParagraphBuilder addAlignment(Alignment alignment){
            paragraph.alignment = alignment;
            return this;
        }

        public Paragraph build() throws IOException {
            float auxY = paragraph.startY;
            float initY = paragraph.startY;
            List<String> texts = paragraph.parseLines();
            TextLine line;

            for(String text : texts){
                line = TextLine.builder()
                        .addTextContent(text)
                        .addAlignment(paragraph.alignment)
                        .addStartX(paragraph.startX)
                        .addStartY(auxY)
                        .addWidth(paragraph.width)
                        .addStyle(paragraph.style)
                        .build();

                auxY += line.getHeight();

                paragraph.lines.add(line);
            }
            paragraph.height = auxY - initY;
            return paragraph;
        }

    }

    private List<String> parseLines() throws IOException {
        float fontSize = style.getFontSize();
        PDFont textFont = style.getTextFont();

        String text = textContent;
        List<String> lines = new ArrayList<>();
        int lastSpace = -1;

        while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0)
                spaceIndex = text.length();
            String subString = text.substring(0, spaceIndex);
            float size = fontSize * textFont.getStringWidth(subString) / 1000;
            if (size > width) {
                if (lastSpace < 0){
                    lastSpace = spaceIndex;
                }
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                lastSpace = -1;
            } else if (spaceIndex == text.length()) {
                lines.add(text);
                text = "";
            } else {
                lastSpace = spaceIndex;
            }
        }
        return lines;
    }
}
