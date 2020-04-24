package lib.tables;

import lib.basic.Component;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RecursiveTable implements Component {
    private float startX=0;
    private float startY=0;
    private float width =0;
    private float height=0;

    private float limY;
    private List<Column> columns       = new LinkedList<>();
    private List<Column> excessColumns = new LinkedList<>();

    private float dX=0;
    private float dY=0;

    private RecursiveTable(){ }

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

    public List<Column> getExcessColumns() {
        return excessColumns;
    }

    public boolean hasExcessColumns() {
        return !excessColumns.isEmpty();
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

    public static RecursiveTable.RecursiveTableBuilder builder(){
        return new RecursiveTable.RecursiveTableBuilder();
    }


    public static class RecursiveTableBuilder{
        private RecursiveTable table = new RecursiveTable();

        public RecursiveTable.RecursiveTableBuilder addStartX(float startX){
            table.startX = startX;
            return this;
        }

        public RecursiveTable.RecursiveTableBuilder addStartY(float startY){
            table.startY = startY;
            return this;
        }

        public RecursiveTable.RecursiveTableBuilder addWidth(float width){
            table.width = width;
            return this;
        }

        public RecursiveTable.RecursiveTableBuilder addHeight(float height){
            table.height = height;
            return this;
        }

        public RecursiveTable.RecursiveTableBuilder addColumn(Column column){
            float dx = table.startX - column.getStartX();
            float dy = table.startY - column.getStartY();

            column.setDX(dx);
            column.setDY(-dy);
            column.rebuild();

            table.columns.add(column);
            table.height -= column.getHeight();

            if(table.width < column.getWidth())
                table.width = column.getWidth();
            return this;
        }

        public RecursiveTable.RecursiveTableBuilder addColumns(List<Column> columns){
            for(Column column : columns)
                addColumn(column);
            return this;
        }

        public RecursiveTable build(){
            boolean overflow = false;
            Column auxColumn;
            table.excessColumns = table.columns;
            table.columns       = new LinkedList<>();

            float auxHeight  = 0;

            while (!table.excessColumns.isEmpty() && !overflow){
                auxColumn = table.excessColumns.get(0);
                auxHeight += Math.abs(auxColumn.getHeight());

                if(table.limY > auxColumn.getStartY()-auxHeight)
                    overflow=true;
                else {
                    table.columns.add(auxColumn);
                    table.excessColumns.remove(0);
                }
            }

            float auxY = 0;
            for(Column column : table.columns){
                column.setDY(auxY);
                auxY -= column.getHeight();
                column.rebuild();
            }
            table.height= -auxY;

            return table;
        }

        public RecursiveTable.RecursiveTableBuilder addLimit(int limitY){
            table.limY = limitY;
            return this;
        }
    }
}
