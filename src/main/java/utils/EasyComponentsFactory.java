package utils;

import lib.basic.Alignment;
import lib.basic.Component;
import lib.basic.Style;
import lib.tables.Cell;
import lib.tables.Column;
import lib.tables.SimpleTable;
import lib.text.MultipleParagraph;

import java.awt.*;
import java.io.IOException;

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
}

