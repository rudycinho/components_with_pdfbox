package lib.shapes;

import lib.basic.Component;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.Color;
import java.io.IOException;

public class Rectangle implements Component {

    private float startX = 0f;
    private float startY = 0f;
    private float width  = 0f;
    private float height = 0f;

    private float dX = 0;
    private float dY = 0;

    private int thickness = 1;
    private Color color   = Color.BLACK;

    public Rectangle(){ }

    public Rectangle(float startX, float startY, float width, float height) {
        this.startX = startX;
        this.startY = startY;
        this.width  = width;
        this.height = height;
    }

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

    public int getThickness() {
        return thickness;
    }

    public Color getColor() {
        return color;
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

    @Override
    public void draw(PDPageContentStream contentStream) throws IOException {
        float auxX = startX;
        float auxY = startY;
        float auxW = width;
        float auxH = height;

        contentStream.setStrokingColor(color);

        for(int i=0; i<thickness;i++){
            contentStream.setStrokingColor(color);
            contentStream.addRect(auxX,auxY,auxW,auxH);
            contentStream.stroke();
            auxX += 1;
            auxY += 1;
            auxW -= 2;
            auxH -= 2;
        }
    }

    public static Rectangle.RectangleBuilder builder(){
        return new Rectangle.RectangleBuilder();
    }

    public static class RectangleBuilder{
        private final Rectangle rectangle = new Rectangle();

        public RectangleBuilder addStartX(float startX){
            rectangle.startX = startX;
            return this;
        }

        public RectangleBuilder addStartY(float startY){
            rectangle.startY = startY;
            return this;
        }

        public RectangleBuilder addWidth(float width){
            rectangle.width = width;
            return this;
        }

        public RectangleBuilder addHeight(float height){
            rectangle.height = height;
            return this;
        }

        public RectangleBuilder addThickness(int thickness){
            rectangle.thickness = thickness;
            return this;
        }

        public RectangleBuilder addColor(Color color){
            rectangle.color = color;
            return this;
        }

        public Rectangle build(){
            return rectangle;
        }
    }
}
