package code.exercise.moj;

public interface WordlistReader {
    String getNextWord() throws WordlistReadingException;

    boolean hasMoreWords() throws WordlistReadingException;
}
