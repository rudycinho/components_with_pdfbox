package subsistema.pdf.lib.text.complex;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Component;
import subsistema.pdf.lib.basic.Style;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ParagraphMultipleStyle implements Component {
    private float startX = 0f;
    private float startY = 0f;
    private float width  = 0f;
    private float height = 0f;

    private Style     style       = Style.builder().build();
    private Alignment alignment   = Alignment.LEFT;

    private List<WordLine> wordLines  = new LinkedList<>();
    private float dX = 0;
    private float dY = 0;

    private ParagraphMultipleStyle(){ }

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
        return height - style.getSeparation();
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

        for(WordLine word : wordLines){
            word.setDX(dX);
            word.setDY(dY);
            word.rebuild();
        }
        dX = 0;
        dY = 0;
    }

    public Style getStyle() {
        return style;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public static ParagraphMultipleStyle.ParagraphMultipleBuilder builder(){
        return new ParagraphMultipleStyle.ParagraphMultipleBuilder();
    }

    public void draw(PDPageContentStream contentStream) throws IOException {
        for(WordLine wordLine : wordLines)
            wordLine.draw(contentStream);

        //contentStream.setStrokingColor(Color.RED);
        //contentStream.addRect(startX,startY,width,height);
        //contentStream.stroke();
    }

    public static class ParagraphMultipleBuilder{
        private ParagraphMultipleStyle paragraph = new ParagraphMultipleStyle();
        private final List<Word> words = new LinkedList<>();

        public ParagraphMultipleBuilder addStartX(float startX){
            paragraph.startX = startX;
            return this;
        }

        public ParagraphMultipleBuilder addStartY(float startY){
            paragraph.startY = startY;
            return this;
        }

        public ParagraphMultipleBuilder addWidth(float width){
            paragraph.width = width;
            return this;
        }

        public ParagraphMultipleBuilder addHeight(float height){
            paragraph.height = height;
            return this;
        }

        public ParagraphMultipleBuilder addAlignment(Alignment alignment){
            paragraph.alignment = alignment;
            return this;
        }

        public ParagraphMultipleBuilder addWord(Word word){
            float dx = paragraph.startX - word.getStartX();
            float dy = paragraph.startY - word.getStartY();

            word.setDX(dx);
            word.setDY(-dy);
            word.rebuild();

            this.words.add(word);

            return this;
        }

        public ParagraphMultipleBuilder addWords(List<Word> words){
            for(Word word : words)
                addWord(word);
            return this;
        }

        public ParagraphMultipleBuilder addText(Text text){
            addWords(text.getWordList());
            return this;
        }

        public ParagraphMultipleStyle build() throws IOException {
            float auxStartX = paragraph.startX;
            float auxStartY = paragraph.startY;

            float auxWidth      = 0;
            List<Word> auxList  = new LinkedList<>();

            for(Word word : this.words){

                if(auxWidth + word.getWidth() > paragraph.width){
                    WordLine wordLine = WordLine.builder()
                            .addStartX(auxStartX)
                            .addStartY(auxStartY)
                            .addWords(auxList)
                            .build();
                    paragraph.wordLines.add(wordLine);
                    auxList = new LinkedList<>();
                    auxList.add(word);
                    auxWidth = word.getWidth();
                    auxStartY = auxStartY + wordLine.getHeight();
                }else {
                    auxWidth = auxWidth + word.getWidth();
                    auxList.add(word);
                }
            }
            if(!auxList.isEmpty()){
                WordLine wordLine = WordLine.builder()
                        .addStartX(auxStartX)
                        .addStartY(auxStartY)
                        .addWords(auxList)
                        .build();
                paragraph.wordLines.add(wordLine);
                auxStartY = auxStartY + wordLine.getHeight();
            }
            paragraph.height = auxStartY - paragraph.startY;

            return paragraph;
        }
    }
}
