package subsistema.pdf.lib.tables;

import subsistema.pdf.lib.basic.Component;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.Color;
import java.io.IOException;

public class Cell implements Component {
    private float startX=0;
    private float startY=0;
    private float width =0;
    private float height=0;

    private float relativeDistanceX=0;
    private float relativeDistanceY=0;

    private boolean hasMargin =false;
    private boolean hasFilling=false;

    private Color colorMargin =Color.BLACK;
    private Color colorFilling=Color.WHITE;

    private Component component;

    private float dX = 0;
    private float dY = 0;

    public void setHasMargin(boolean hasMargin) {
        this.hasMargin = hasMargin;
    }

    public void setHasFilling(boolean hasFilling) {
        this.hasFilling = hasFilling;
    }

    public void setColorMargin(Color colorMargin) {
        this.colorMargin = colorMargin;
    }

    public void setColorFilling(Color colorFilling) {
        this.colorFilling = colorFilling;
    }

    private Cell(){ }

    public void setHeight(float height) {
        this.height = height;
    }

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
        return height;
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
        component.setDX(dX);
        component.setDY(dY);
        component.rebuild();
        dX = 0;
        dY = 0;
    }

    public float getRelativeDistanceX() {
        return relativeDistanceX;
    }

    public float getRelativeDistanceY() {
        return relativeDistanceY;
    }

    public boolean isHasMargin() {
        return hasMargin;
    }

    public boolean isHasFilling() {
        return hasFilling;
    }

    public Color getColorMargin() {
        return colorMargin;
    }

    public Color getColorFilling() {
        return colorFilling;
    }

    public Component getComponent() {
        return component;
    }

    public static Cell.CellBuilder builder(){
        return new Cell.CellBuilder();
    }

    public void draw(PDPageContentStream contentStream) throws IOException {
        if(hasFilling) {
            contentStream.setNonStrokingColor(colorFilling);
            contentStream.addRect(startX, startY, width, height);
            contentStream.fill();
        }

        component.draw(contentStream);

        if(hasMargin) {
            contentStream.setStrokingColor(colorMargin);
            contentStream.addRect(startX, startY, width, height);
            contentStream.stroke();
        }
    }

    public static class CellBuilder{
        private final Cell cell = new Cell();

        public CellBuilder addRelativeDistanceX(float relativeDistanceX){
            cell.relativeDistanceX = relativeDistanceX;
            return this;
        }

        public CellBuilder addRelativeDistanceY(float relativeDistanceY){
            cell.relativeDistanceY = relativeDistanceY;
            return this;
        }

        public CellBuilder addHasMargin(boolean hasMargin){
            cell.hasMargin = hasMargin;
            return this;
        }

        public CellBuilder addHasFilling(boolean hasFilling){
            cell.hasFilling = hasFilling;
            return this;
        }

        public CellBuilder addColorMargin(Color colorMargin){
            cell.colorMargin = colorMargin;
            return this;
        }

        public CellBuilder addColorFilling(Color colorFilling){
            cell.colorFilling = colorFilling;
            return this;
        }

        public CellBuilder addComponent(Component component){
            cell.component = component;
            return this;
        }


        public Cell build(){
            if(cell.component == null)
                System.out.println("ERROR");

            float startX = cell.component.getStartX();
            float startY = cell.component.getStartY();
            float width  = cell.component.getWidth();
            float height = cell.component.getHeight();

            cell.startX = startX;
            cell.startY = startY;
            cell.width  = width  + 2 * cell.relativeDistanceX;
            cell.height = height - 2 * cell.relativeDistanceY;

            cell.component.setDX(cell.relativeDistanceX);
            cell.component.setDY(cell.relativeDistanceY);
            cell.component.rebuild();

            return cell;
        }

    }
}
