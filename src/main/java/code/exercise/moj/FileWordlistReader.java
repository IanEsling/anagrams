package code.exercise.moj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class FileWordlistReader implements WordlistReader {

    private BufferedReader bufferedReader;
    int wordcount = 0;
    private final String path;

    @Autowired
    public FileWordlistReader(@Value("${wordlist.path}") String path) throws FileNotFoundException {
        this.path = path;
    }

    @PostConstruct
    public void init() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
    }

    @Override
    public String getNextWord() throws WordlistReadingException {
        wordcount++;
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new WordlistReadingException(e);
        }
    }

    @Override
    public boolean hasMoreWords() throws WordlistReadingException {
        try {
            return bufferedReader.ready();
        } catch (IOException e) {
            throw new WordlistReadingException(e);
        }
    }
}
