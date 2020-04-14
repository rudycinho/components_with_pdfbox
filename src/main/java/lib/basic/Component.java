package lib.basic;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public interface Component {
    public float getStartX();
    public float getStartY();
    public float getWidth();
    public float getHeight();

    public void setDX(float dX);
    public void setDY(float dY);

    public void rebuild();

    public void draw(PDPageContentStream contentStream) throws IOException;
}
