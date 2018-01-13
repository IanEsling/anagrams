package code.exercise.moj;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnagramServiceTest {

    @Mock
    WordTrie trie;
    @Mock
    WordlistTrieFactory trieFactory;

    private AnagramService anagramService;

    @Before
    public void setup() throws WordlistReadingException {
        when(trieFactory.generateNewTrie()).thenReturn(trie);
        anagramService = new AnagramService(trieFactory);
        anagramService.init();
    }

    @Test
    public void anagramsFromTrie() {
        String word = "word";
        when(trie.getAnagramsOf(word)).thenReturn(ImmutableSet.of("anagram1", "anagram2"));
        assertThat(anagramService.getAnagrams(word)).containsAllEntriesOf(ImmutableMap.of(word, ImmutableSet.of("anagram1", "anagram2")));
    }

    @Test
    public void multipleAnagrams() {
        Set<String> word1Anagrams = ImmutableSet.of("anagram1", "anagram2");
        Set<String> word2Anagrams = ImmutableSet.of("anagram3");
        Set<String> word3Anagrams = Sets.newHashSet();
        when(trie.getAnagramsOf("word1")).thenReturn(word1Anagrams);
        when(trie.getAnagramsOf("word2")).thenReturn(word2Anagrams);
        when(trie.getAnagramsOf("word3")).thenReturn(word3Anagrams);
        Map<String, Collection<String>> anagrams = ImmutableMap.of("word1", word1Anagrams, "word2", word2Anagrams, "word3", word3Anagrams);

        assertThat(anagramService.getAnagrams("word1", "word2", "word3")).containsAllEntriesOf(anagrams);
    }

    @Test(expected = RuntimeException.class)
    public void throwsRuntimeExceptionIfWordlistReadingException() throws WordlistReadingException {
        when(trieFactory.generateNewTrie()).thenThrow(new WordlistReadingException());
        anagramService = new AnagramService(trieFactory);
        anagramService.init();
    }
}