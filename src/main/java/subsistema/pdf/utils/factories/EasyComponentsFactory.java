package subsistema.pdf.utils.factories;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Component;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.shapes.Rectangle;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.Column;
import subsistema.pdf.lib.tables.RecursiveTable;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.lib.text.simple.MultipleParagraph;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

    public class EasyComponentsFactory {
        public static MultipleParagraph getSimpleMultipleParagraph(String text,
                                                                   Style style,
                                                                   float width) throws IOException {
            return MultipleParagraph.builder()
                    .addWidth( width )
                    .addTextContent( text )
                    .addAlignment(Alignment.LEFT)
                    .addStyle(style)
                    .build();
        }

        public static MultipleParagraph getSimpleMultipleParagraph(String text,
                                                                   Style style,
                                                                   float width,
                                                                   Alignment alignment) throws IOException {
            return MultipleParagraph.builder()
                    .addWidth( width )
                    .addTextContent( text )
                    .addAlignment(alignment)
                    .addStyle(style)
                    .build();
        }

        public static Cell getSimpleCell(float relativeDistanceX,
                                         float relativeDistanceY,
                                         boolean hasMargin,
                                         Component component){
            return Cell.builder()
                    .addRelativeDistanceX(relativeDistanceX)
                    .addRelativeDistanceY(relativeDistanceY)
                    .addHasMargin(hasMargin)
                    .addComponent(component)
                    .build();
        }

        public static Column getSimpleColumn(Cell ...cells){
            Column.ColumnBuilder col = Column.builder();
            for(Cell cell : cells)
                col.addCell(cell);
            return col.build();
        }

        public static SimpleTable getSimpleTable(float startX,
                                                 float startY,
                                                 Column ...columns){
            SimpleTable.SimpleTableBuilder table = SimpleTable.builder()
                    .addStartX(startX)
                    .addStartY(startY);
            for(Column column : columns)
                table.addColumn(column);
            return table.build();
        }

        public static Cell getSimpleCellFromText(String text,
                                                 Style style,
                                                 float width,
                                                 float relativeDistanceX,
                                                 float relativeDistanceY,
                                                 boolean hasMargin) throws IOException {
            return getSimpleCell(
                    relativeDistanceX,
                    relativeDistanceY,
                    hasMargin,
                    getSimpleMultipleParagraph(text,style,width - ( 2*relativeDistanceX) ) );
        }

        public static Cell getSimpleCellFromTextAndAlignment(String text,
                                                 Style style,
                                                 float width,
                                                 float relativeDistanceX,
                                                 float relativeDistanceY,
                                                 Alignment alignment,
                                                 boolean hasMargin) throws IOException {
            return getSimpleCell(
                    relativeDistanceX,
                    relativeDistanceY,
                    hasMargin,
                    getSimpleMultipleParagraph(text,style,width - ( 2*relativeDistanceX) , alignment) );
        }

        public static Cell getSimpleCellFromText(String text,
                                                 Style style,
                                                 float width,
                                                 float relativeDistanceX,
                                                 float relativeDistanceY,
                                                 Alignment alignment,
                                                 boolean hasMargin) throws IOException {
            return getSimpleCell(
                    relativeDistanceX,
                    relativeDistanceY,
                    hasMargin,
                    getSimpleMultipleParagraph(text,style,width - ( 2*relativeDistanceX),alignment ) );
        }

        public static SimpleTable getSimpleTableFromCell(
                float startX,
                float startY,
                Cell[] ...cellList){

            SimpleTable.SimpleTableBuilder table = SimpleTable.builder()
                    .addStartX(startX)
                    .addStartY(startY);
            for(Cell[] cells : cellList)
                table.addColumn(getSimpleColumn(cells));

            return table.build();
        }

        public static Cell getBoxStroke(
                float relativeDistanceX,
                float relativeDistanceY,
                Color marginColor,
                Component component){
            return Cell.builder()
                    .addComponent(component)
                    .addHasMargin(true)
                    .addHasFilling(false)
                    .addRelativeDistanceX(relativeDistanceX)
                    .addRelativeDistanceY(relativeDistanceY)
                    .addColorMargin(marginColor)
                    .build();
        }

        public static Cell getBoxFilling(
                float relativeDistanceX,
                float relativeDistanceY,
                Color fillingColor,
                Component component){
            return Cell.builder()
                    .addComponent(component)
                    .addHasMargin(false)
                    .addHasFilling(true)
                    .addRelativeDistanceX(relativeDistanceX)
                    .addRelativeDistanceY(relativeDistanceY)
                    .addColorFilling(fillingColor)
                    .build();
        }

        public static Cell getBoxStrokeFilling(
                float relativeDistanceX,
                float relativeDistanceY,
                Color marginColor,
                Color fillingColor,
                Component component){
            return Cell.builder()
                    .addComponent(component)
                    .addHasMargin(true)
                    .addHasFilling(true)
                    .addRelativeDistanceX(relativeDistanceX)
                    .addRelativeDistanceY(relativeDistanceY)
                    .addColorMargin(marginColor)
                    .addColorFilling(fillingColor)
                    .build();
        }


        public static Column getSimpleColumnFromTextAndWithsAndStyle(
                List<String> texts,
                List<Float> widths,
                Style style,
                Alignment alignment) throws IOException {

            List<Cell> cells = new LinkedList<>();
            int size = widths.size();
            for(int i=0; i < size; i++)
                cells.add(getSimpleCellFromText(texts.get(i),style,widths.get(i), 5,5,alignment,true));

            return Column.builder().addCells(cells).build();
        }

        public static Column getSimpleColumnFromTextAndWidthsAndStyleAndRelativePosition(
                List<String> texts,
                List<Float> widths,
                Style style,
                float relativePositionX,
                float relativePositionY,
                Alignment alignment, boolean hasMargin) throws IOException {

            List<Cell> cells = new LinkedList<>();
            int size = widths.size();
            for(int i=0; i < size; i++)
                cells.add(getSimpleCellFromText(texts.get(i),style,widths.get(i), relativePositionX,relativePositionY,alignment,hasMargin));

            return Column.builder().addCells(cells).build();
        }

        public static Column getSimpleColumnFromTextAndWidthsAndStylesAndRelativePosition(
                List<String> texts,
                List<Float> widths,
                List<Style> styles,
                float relativePositionX,
                float relativePositionY,
                Alignment alignment, boolean hasMargin) throws IOException {

            List<Cell> cells = new LinkedList<>();
            int size = widths.size();
            for(int i=0; i < size; i++)
                cells.add(getSimpleCellFromText(texts.get(i),styles.get(i),widths.get(i), relativePositionX,relativePositionY,alignment,hasMargin));

            return Column.builder().addCells(cells).build();
        }



        public static List<RecursiveTable> getHeaderTable(
                float startX,
                float startY,
                float anotherStartY,
                Column headerColumn,
                Column anotherHeaderColumn,
                List<Column> bodyColumns,
                int limitY) {
            List<RecursiveTable> recursiveTables = new LinkedList<>();

            RecursiveTable table = createRecursiveTable(startX,startY,headerColumn,bodyColumns,limitY);
            recursiveTables.add(table);
            while (table.hasExcessColumns()){
                table = createRecursiveTable(startX,anotherStartY,anotherHeaderColumn,table.getExcessColumns(),limitY);
                recursiveTables.add(table);
            }

            return recursiveTables;
        }

        public static List<RecursiveTable> getLargeTable(
                float startX,
                float startY,
                float anotherStartY,
                List<Column> bodyColumns,
                int limitY) {
            List<RecursiveTable> recursiveTables = new LinkedList<>();

            RecursiveTable table = createRecursiveTable(startX,startY,bodyColumns,limitY);
            recursiveTables.add(table);
            while (table.hasExcessColumns()){
                table = createRecursiveTable(startX,anotherStartY,table.getExcessColumns(),limitY);
                recursiveTables.add(table);
            }

            return recursiveTables;
        }

        private static RecursiveTable createRecursiveTable(
                float startX,
                float startY,
                Column headerColum,
                List<Column> bodyColumns,
                int limitY) {
            return RecursiveTable.builder()
                    .addStartX(startX)
                    .addStartY(startY)
                    .addColumn(headerColum)
                    .addColumns(bodyColumns)
                    .addLimit(limitY)
                    .build();
        }


        private static RecursiveTable createRecursiveTable(
                float startX,
                float startY,
                List<Column> bodyColumns,
                int limitY) {
            return RecursiveTable.builder()
                    .addStartX(startX)
                    .addStartY(startY)
                    .addColumns(bodyColumns)
                    .addLimit(limitY)
                    .build();
        }

        public static Cell getSimpleCellRectangle(
                float width,
                float relativePositionX,
                float relativePositionY,
                boolean margin) {

            subsistema.pdf.lib.shapes.Rectangle rectangle = Rectangle.builder()
                    .addColor(new Color(110,110,110))
                    .addStartX(relativePositionX)
                    .addStartY(relativePositionY)
                    .addWidth(width-2*relativePositionX)
                    .addHeight(relativePositionY)
                    .addThickness(1)
                    .build();

            return getSimpleCell(
                    relativePositionX,
                    relativePositionY,
                    margin,rectangle);
        }

        public static MultipleParagraph getSimpleMultipleParagraph(
                String text,
                float startX,
                float startY,
                Style style,
                float width,
                Alignment left) throws IOException {
            return MultipleParagraph.builder()
                    .addStartX(startX)
                    .addStartY(startY)
                    .addWidth( width )
                    .addTextContent( text )
                    .addAlignment(Alignment.LEFT)
                    .addStyle(style)
                    .build();
        }


        public static Column getSimpleColumnFromTextAndWithsAndStyleAndRelativePosition(
                List<String> texts,
                List<Float> widths,
                Style style,
                Alignment alignment,
                float relativePositionX,
                float relativePositionY,
                boolean hasMargin,
                boolean hasFilling,
                Color colorMargin,
                Color colorFilling) throws IOException {

            List<Cell> cells = new LinkedList<>();
            int size = widths.size();
            for(int i=0; i < size; i++)
                cells.add(getSimpleCellFromText(
                        texts.get(i),
                        style,
                        widths.get(i),
                        relativePositionX,relativePositionY,
                        alignment,
                        hasMargin,hasFilling,
                        colorMargin,colorFilling));

            return Column.builder().addCells(cells).build();
        }

        public static Column getSimpleColumnFromTextAndWithsAndStylesAndRelativePosition(
                List<String> texts,
                List<Float> widths,
                List<Style> styles,
                Alignment alignment,
                float relativePositionX,
                float relativePositionY,
                boolean hasMargin,
                boolean hasFilling,
                Color colorMargin,
                Color colorFilling) throws IOException {

            List<Cell> cells = new LinkedList<>();
            int size = widths.size();
            for(int i=0; i < size; i++)
                cells.add(getSimpleCellFromText(
                        texts.get(i),
                        styles.get(i),
                        widths.get(i),
                        relativePositionX,relativePositionY,
                        alignment,
                        hasMargin,hasFilling,
                        colorMargin,colorFilling));

            return Column.builder().addCells(cells).build();
        }

        public static Cell getSimpleCellFromText(String text,
                                                 Style style,
                                                 float width,
                                                 float relativeDistanceX,
                                                 float relativeDistanceY,
                                                 Alignment alignment,
                                                 boolean hasMargin,
                                                 boolean hasFilling,
                                                 Color colorMargin,
                                                 Color colorFilling) throws IOException {
            return getSimpleCell(
                    relativeDistanceX,
                    relativeDistanceY,
                    hasMargin,
                    hasFilling,
                    colorMargin,
                    colorFilling,
                    getSimpleMultipleParagraph(text,style,width - ( 2*relativeDistanceX),alignment ) );
        }

        public static Cell getSimpleCell(float relativeDistanceX,
                                         float relativeDistanceY,
                                         boolean hasMargin,
                                         boolean hasFilling,
                                         Color colorMargin,
                                         Color colorFilling,
                                         Component component){
            return Cell.builder()
                    .addRelativeDistanceX(relativeDistanceX)
                    .addRelativeDistanceY(relativeDistanceY)
                    .addHasMargin(hasMargin)
                    .addHasFilling(hasFilling)
                    .addColorMargin(colorMargin)
                    .addColorFilling(colorFilling)
                    .addComponent(component)
                    .build();
        }

        public static void cambiarColor(Color color, Cell... cells) {
            for (Cell cell : cells) {
                cell.setHasFilling(true);
                cell.setColorFilling(color);
            }
        }

        public static void createMargin(PDPageContentStream contentStream,
                                        float startX, float startY, float witdh, float heigth) throws IOException {
            Rectangle rectangle = Rectangle.builder()
                    .addColor(Color.BLACK)
                    .addThickness(2)
                    .addStartX(startX)
                    .addStartY(startY)
                    .addWidth(witdh)
                    .addHeight(heigth)
                    .build();

            rectangle.draw(contentStream);
        }

        public static void drawImage(
                PDPageContentStream pageContentStream,
                PDDocument document, String imagePath,
                float offsetX, float offsetY, int width,
                int height
        ) throws IOException {
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);
            pageContentStream.drawImage(pdImage, offsetX, offsetY, width, height);
        }

    }
