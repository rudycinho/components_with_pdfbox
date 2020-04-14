package lib.shapes;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.Color;
import java.io.IOException;

public class Grid {
    private int endX;
    private int endY;

    public Grid(int endX, int endY){
        this.endX = endX;
        this.endY = endY;
    }

    public void draw(PDPageContentStream contentStream) throws IOException {
        for ( int i = 0 ; i < endY; i+=5) {
            contentStream.setStrokingColor(new Color(250, 250, 30));
            contentStream.moveTo(0, endY-i);
            contentStream.lineTo(endX, endY-i);
            contentStream.stroke();
        }

        for ( int i = 0 ; i < endX; i+=5) {
            contentStream.setStrokingColor(new Color(250, 250, 30));
            contentStream.moveTo(i, 0);
            contentStream.lineTo(i, endY);
            contentStream.stroke();
        }

        for ( int i = 0 ; i < endY; i+=25) {
            contentStream.setStrokingColor(new Color(250, 200, 30));
            contentStream.moveTo(0, endY-i);
            contentStream.lineTo(endX, endY-i);
            contentStream.stroke();
        }

        for ( int i = 0 ; i < endX; i+=25) {
            contentStream.setStrokingColor(new Color(250, 200, 30));
            contentStream.moveTo(i, 0);
            contentStream.lineTo(i, endY);
            contentStream.stroke();
        }

        for ( int i = 0 ; i < endY; i+=100) {
            contentStream.setStrokingColor(new Color(250, 150, 30));
            contentStream.moveTo(0, endY-i);
            contentStream.lineTo(endX, endY-i);
            contentStream.stroke();
        }

        for ( int i = 0 ; i < endX; i+=100) {
            contentStream.setStrokingColor(new Color(250, 150, 30));
            contentStream.moveTo(i, 0);
            contentStream.lineTo(i, endY);
            contentStream.stroke();
        }
    }
}