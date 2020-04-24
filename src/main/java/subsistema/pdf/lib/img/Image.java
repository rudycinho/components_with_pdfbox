package subsistema.pdf.lib.img;

import subsistema.pdf.lib.basic.Component;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;

public class Image implements Component {
    private float startX = 0f;
    private float startY = 0f;
    private float width  = 0f;
    private float height = 0f;

    private float dX = 0;
    private float dY = 0;

    private PDImageXObject pdImage = null;

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
        return height*-1;
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
        contentStream.drawImage(pdImage,startX,startY-height,width,height);
    }

    public static Image.ImageBuilder builder(){
        return new Image.ImageBuilder();
    }

    public static class ImageBuilder{
        private final Image image = new Image();

        public ImageBuilder addStartX(float startX){
            image.startX = startX;
            return this;
        }

        public ImageBuilder addStartY(float startY){
            image.startY = startY;
            return this;
        }

        public ImageBuilder addWidth(float width){
            image.width = width;
            return this;
        }

        public ImageBuilder addHeight(float height){
            //image.startY = image.startY - height;
            image.height = height;
            return this;
        }

        public ImageBuilder addImage(PDImageXObject pdImage){
            image.pdImage = pdImage;
            return this;
        }

        public Image build(){
            return image;
        }
    }
}
