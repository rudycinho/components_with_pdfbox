package lib.text;

import lib.basic.Alignment;
import lib.basic.Component;
import lib.basic.Style;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MultipleParagraph implements Component {
    private float startX = 0f;
    private float startY = 0f;
    private float width  = 0f;
    private float height = 0f;

    private String    textContent = "null";
    private Style style       = Style.builder().build();
    private Alignment alignment   = Alignment.LEFT;

    private List<Paragraph> paragraphs = new LinkedList<>();
    private float dX = 0;
    private float dY = 0;

    public MultipleParagraph(){ }

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
        return height;
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

    @Override
    public void setDX(float dX) {
        this.dX = dX;
    }

    @Override
    public void setDY(float dY) {
        this.dY = dY;
    }

    public static MultipleParagraph.MultipleParagraphBuilder builder(){
        return new MultipleParagraph.MultipleParagraphBuilder();
    }

    public void rebuild(){
        startX = startX + dX;
        startY = startY - dY;

        for(Paragraph paragraph : paragraphs){
            paragraph.setDX(dX);
            paragraph.setDY(dY);
            paragraph.rebuild();
        }
        dX = 0;
        dY = 0;
    }


    public void draw(PDPageContentStream contentStream) throws IOException {
        for(Paragraph paragraph : paragraphs)
            paragraph.draw(contentStream);
        contentStream.setStrokingColor(Color.ORANGE);
        contentStream.addRect(startX,startY,width,height);
        contentStream.stroke();
    }

    public static class MultipleParagraphBuilder{
        private final MultipleParagraph multipleParagraph = new MultipleParagraph();

        public MultipleParagraph.MultipleParagraphBuilder addStartX(float startX){
            multipleParagraph.startX=startX;
            return this;
        }

        public MultipleParagraph.MultipleParagraphBuilder addStartY(float startY){
            multipleParagraph.startY=startY;
            return this;
        }

        public MultipleParagraph.MultipleParagraphBuilder addWidth(float width){
            multipleParagraph.width=width;
            return this;
        }

        public MultipleParagraph.MultipleParagraphBuilder addHeight(float height){
            multipleParagraph.height=height;
            return this;
        }

        public MultipleParagraph.MultipleParagraphBuilder addTextContent(String textContent){
            multipleParagraph.textContent=textContent;
            return this;
        }

        public MultipleParagraph.MultipleParagraphBuilder addStyle(Style style){
            multipleParagraph.style=style;
            return this;
        }

        public MultipleParagraph.MultipleParagraphBuilder addAlignment(Alignment alignment){
            multipleParagraph.alignment=alignment;
            return this;
        }

        public MultipleParagraph build() throws IOException {
            float auxY  = multipleParagraph.startY;
            float initY = multipleParagraph.startY;

            List<String> texts = Arrays.asList(multipleParagraph.textContent.split("\n"));
            Paragraph paragraph;

            for(String text : texts){
                paragraph = Paragraph.builder()
                        .addTextContent(text)
                        .addAlignment(multipleParagraph.alignment)
                        .addStartX(multipleParagraph.startX)
                        .addStartY(auxY)
                        .addWidth(multipleParagraph.width)
                        .addStyle(multipleParagraph.style)
                        .build();

                auxY += paragraph.getHeight();

                multipleParagraph.paragraphs.add(paragraph);
            }
            multipleParagraph.height = auxY - initY + multipleParagraph.style.getSeparation();
            return multipleParagraph;
        }

    }
}
