package code.exercise.moj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WordlistTrieFactoryTest {

    @Mock
    WordlistReader reader;

    @InjectMocks
    WordlistTrieFactory testee;

    @Test
    public void createTrieFromReader() throws WordlistReadingException {
        when(reader.hasMoreWords()).thenReturn(true, true, true, true, true, false);
        when(reader.getNextWord()).thenReturn("ABCDE", "EDCBA", "abDCe", "DEcba", "word");
        WordTrie trie = testee.generateNewTrie();
        verify(reader, times(5)).getNextWord();
        assertThat(trie.getAnagramsOf("acbed")).hasSize(4);
        assertThat(trie.getAnagramsOf("drow")).hasSize(1);
        assertThat(trie.getAnagramsOf("abcde")).hasSize(3);
        assertThat(trie.getAnagramsOf("quack")).hasSize(0);
    }

    @Test(expected = WordlistReadingException.class)
    public void throwIfExceptionGettingNextWord() throws WordlistReadingException {
        when(reader.hasMoreWords()).thenReturn(true);
        when(reader.getNextWord()).thenThrow(new WordlistReadingException());
        testee.generateNewTrie();
    }

    @Test(expected = WordlistReadingException.class)
    public void throwIfHasMoreWordsException() throws WordlistReadingException {
        when(reader.hasMoreWords()).thenThrow(new WordlistReadingException());
        testee.generateNewTrie();
    }
}