package subsistema.pdf.lib.text.complex;

import subsistema.pdf.lib.basic.Style;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Text {
    private List<Word> wordList;

    public Text(String content, Style style) throws IOException {
        String[] texts = content.split(" ");
        wordList = new LinkedList<>();

        for(String text : texts){
            wordList.add(Word.builder()
                    .addStartX(0f)
                    .addStartY(0f)
                    .addTextContent(text.concat(" "))
                    .addStyle(style)
                    .build());
        }
    }

    public List<Word> getWordList(){
        return wordList;
    }
}
