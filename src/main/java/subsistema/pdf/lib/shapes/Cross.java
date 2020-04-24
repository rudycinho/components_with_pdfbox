package subsistema.pdf.lib.shapes;

import java.awt.Color;
import java.io.IOException;

import subsistema.pdf.lib.basic.Component;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class Cross implements Component {
    private float startX = 0f;
    private float startY = 0f;
    private float width  = 0f;
    private float height = 0f;

    private float dX = 0;
    private float dY = 0;

    private int thickness = 1;
    private Color color   = Color.BLACK;

    public Cross(){ }

    public Cross(Rectangle rectangle) {
        this.startX = rectangle.getStartX();
        this.startY = rectangle.getStartY();
        this.width  = rectangle.getWidth();
        this.height = rectangle.getHeight();
    }

    public Cross(float startX, float startY, float width, float height) {
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
        float f=startX;
        float g=startY;
        float h=startX+width;
        float i=startY+height;

        Line line1= new Line(f, g, h, i).setColor(color).setThickness(thickness);
        Line line2= new Line(f, i, h, g).setColor(color).setThickness(thickness);

        line1.draw(contentStream);
        line2.draw(contentStream);
    }

    public static Cross.CrossBuilder builder(){
        return new Cross.CrossBuilder();
    }

    public static class CrossBuilder{
        private Cross cross = new Cross();

        public Cross.CrossBuilder addRectangle(Rectangle rectangle){
            cross.startX = rectangle.getStartX();
            cross.startY = rectangle.getStartY();
            cross.width  = rectangle.getWidth();
            cross.height = rectangle.getHeight();
            return this;
        }

        public Cross.CrossBuilder addStartX(float startX){
            cross.startX = startX;
            return this;
        }

        public Cross.CrossBuilder addStartY(float startY){
            cross.startY = startY;
            return this;
        }

        public Cross.CrossBuilder addWidth(float width){
            cross.width = width;
            return this;
        }

        public Cross.CrossBuilder addHeight(float height){
            cross.height = height;
            return this;
        }

        public Cross.CrossBuilder addThickness(int thickness){
            cross.thickness = thickness;
            return this;
        }

        public Cross.CrossBuilder addColor(Color color){
            cross.color = color;
            return this;
        }

        public Cross build(){
            return cross;
        }
    }
}
