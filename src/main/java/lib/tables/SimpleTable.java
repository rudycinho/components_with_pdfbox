package lib.tables;

import lib.basic.Component;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SimpleTable implements Component {

    private float startX=0;
    private float startY=0;
    private float width =0;
    private float height=0;

    private List<Column> columns = new LinkedList<>();

    private float dX=0;
    private float dY=0;

    private SimpleTable(){ }

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

    public void setDX(float dX) {
        this.dX = dX;
    }

    public void setDY(float dY) {
        this.dY = dY;
    }

    @Override
    public void rebuild() {
        startX = startX + dX;
        startY = startY - dY;
        for(Column column : columns){
            column.setDX(dX);
            column.setDY(dY);
            column.rebuild();
        }
        dX = 0;
        dY = 0;
    }

    @Override
    public void draw(PDPageContentStream contentStream) throws IOException {
        for(Column column : columns)
            column.draw(contentStream);
    }

    public static SimpleTable.SimpleTableBuilder builder(){
        return new SimpleTable.SimpleTableBuilder();
    }


    public static class SimpleTableBuilder{
        private SimpleTable simpleTable = new SimpleTable();

        public SimpleTable.SimpleTableBuilder addStartX(float startX){
            simpleTable.startX = startX;
            return this;
        }

        public SimpleTable.SimpleTableBuilder addStartY(float startY){
            simpleTable.startY = startY;
            return this;
        }

        public SimpleTable.SimpleTableBuilder addWidth(float width){
            simpleTable.width = width;
            return this;
        }

        public SimpleTable.SimpleTableBuilder addHeight(float height){
            simpleTable.height = height;
            return this;
        }

        public SimpleTable.SimpleTableBuilder addColumn(Column column){
            float dx = simpleTable.startX - column.getStartX();
            float dy = simpleTable.startY - column.getStartY();

            column.setDX(dx);
            column.setDY(-dy);
            column.rebuild();

            simpleTable.columns.add(column);
            simpleTable.height -= column.getHeight();

            if(simpleTable.width < column.getWidth())
                simpleTable.width = column.getWidth();
            return this;
        }

        public SimpleTable build(){
            float auxY = 0;

            for(Column column : simpleTable.columns){
                column.setDY(auxY);
                auxY -= column.getHeight();
                column.rebuild();
            }
            simpleTable.height= -auxY;

            return simpleTable;
        }

    }
}
