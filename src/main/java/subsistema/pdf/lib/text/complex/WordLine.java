package subsistema.pdf.lib.text.complex;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Component;
import subsistema.pdf.lib.tables.Cell;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class WordLine implements Component {
    private float startX = 0f;
    private float startY = 0f;
    private float width  = 0f;
    private float height = 0f;

    private Alignment alignment   = Alignment.LEFT;

    private List<Word> words  = new LinkedList<>();
    private float dX = 0;
    private float dY = 0;

    private WordLine(){ }

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
        return height;// - style.getSeparation();
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

        for(Word word : words){
            word.setDX(dX);
            word.setDY(dY);
            word.rebuild();
        }
        dX = 0;
        dY = 0;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public static WordLine.WordLineBuilder builder(){
        return new WordLine.WordLineBuilder();
    }

    public void draw(PDPageContentStream contentStream) throws IOException {
        for(Word word : words)
            word.draw(contentStream);

        //contentStream.setStrokingColor(Color.RED);
        //contentStream.addRect(startX,startY,width,height);
        //contentStream.stroke();
    }

    public static class WordLineBuilder{
        private WordLine wordLine = new WordLine();

        public WordLineBuilder addStartX(float startX){
            wordLine.startX = startX;
            return this;
        }

        public WordLineBuilder addStartY(float startY){
            wordLine.startY = startY;
            return this;
        }

        public WordLineBuilder addAlignment(Alignment alignment){
            wordLine.alignment = alignment;
            return this;
        }

        public WordLineBuilder addWord(Word word){
            float dx = wordLine.startX - word.getStartX();
            float dy = wordLine.startY - word.getStartY();

            word.setDX(dx);
            word.setDY(-dy);
            word.rebuild();

            wordLine.words.add(word);
            wordLine.width += word.getWidth();

            return this;
        }

        public WordLineBuilder addWords(List<Word> words){
            for( Word word : words)
                addWord(word);
            return this;
        }

        public WordLine build() throws IOException {
            float auxX = 0;
            float auxY = 0;

            for(Word word : wordLine.words){
                word.setDX(auxX);
                auxX += word.getWidth();
                if(Math.abs(auxY) < Math.abs(word.getHeight())) {
                    auxY = word.getHeight();
                }
                word.rebuild();
            }

            wordLine.height= auxY;

            for(Word word : wordLine.words){
                word.setHeight(wordLine.height) ;
                word.rebuild();
            }

            return wordLine;
        }
    }
}
