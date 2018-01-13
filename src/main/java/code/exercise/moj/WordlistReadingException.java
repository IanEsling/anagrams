package code.exercise.moj;

import java.io.IOException;

public class WordlistReadingException extends Throwable {

    public WordlistReadingException(){}

    public WordlistReadingException(IOException e) {
        super(e);
    }
}
