package lib.tables;

import lib.basic.Component;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Column implements Component {
    private float startX=0;
    private float startY=0;
    private float width =0;
    private float height=0;

    private List<Cell> cells = new LinkedList<>();

    private float dX=0;
    private float dY=0;

    public Column(){ }

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
        for(Cell cell : cells){
            cell.setDX(dX);
            cell.setDY(dY);
            cell.rebuild();
        }
        dX = 0;
        dY = 0;
    }

    public static Column.ColumnBuilder builder(){
        return new Column.ColumnBuilder();
    }

    public void draw(PDPageContentStream contentStream) throws IOException {
        for(Cell cell : cells){
            cell.draw(contentStream);
        }
    }

    public static class ColumnBuilder{
        private Column column = new Column();

        public ColumnBuilder addStartX(float startX){
            column.startX = startX;
            return this;
        }

        public ColumnBuilder addStartY(float startY){
            column.startY = startY;
            return this;
        }

        public ColumnBuilder addWidth(float width){
            column.width = width;
            return this;
        }

        public ColumnBuilder addHeight(float height){
            column.height = height;
            return this;
        }

        public ColumnBuilder addCell(Cell cell){
            float dx = column.startX - cell.getStartX();
            float dy = column.startY - cell.getStartY();

            cell.setDX(dx);
            cell.setDY(-dy);

            cell.rebuild();
            column.cells.add(cell);
            column.width += cell.getWidth();
            return this;
        }

        public Column build(){
            float auxX = 0;
            float auxY = 0;

            for(Cell cell : column.cells){
                cell.setDX(auxX);
                auxX += cell.getWidth();
                if(Math.abs(auxY) < Math.abs(cell.getHeight())) {
                     auxY = cell.getHeight();
                }
                cell.rebuild();
            }

            column.height= auxY;

            for(Cell cell : column.cells){
                cell.setHeight(column.height) ;
                cell.rebuild();
            }

            return column;
        }
    }
}
