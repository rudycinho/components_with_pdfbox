package lib.shapes;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.Color;
import java.io.IOException;

public class Line {

    private float startX;
    private float startY;
    private float endX;
    private float endY;

    private int thickness;
    private Color color;

    public Line setThickness(int thickness) {
        this.thickness = thickness;
        return this;
    }

    public Line setColor(Color color) {
        this.color = color;
        return this;
    }

    public Line(float startX, float startY, float endX, float endY){
        this.startX = startX;
        this.startY = startY;
        this.endX   = endX;
        this.endY   = endY;

        this.thickness = 1;
        this.color     = Color.BLACK;
    }

    public void draw(PDPageContentStream contentStream) throws IOException {
        float auxStartY = startY;
        float auxEndY   = endY;

        contentStream.setStrokingColor(color);

        for(int i=0; i<thickness;i++){
            contentStream.setStrokingColor(color);
            contentStream.moveTo(startX, auxStartY);
            contentStream.lineTo(endX  , auxEndY);
            contentStream.stroke();
            auxStartY += 1;
            auxEndY += 1;
        }
    }
}
