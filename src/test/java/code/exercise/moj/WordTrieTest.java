package code.exercise.moj;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static code.exercise.moj.MojAssertions.assertThat;

public class WordTrieTest {

    private WordTrie testee;

    @Before
    public void setup() {
        testee = new WordTrie();
    }

    @Test
    public void storesNodeByChar() {
        addWords("ABC", "ACD", "ACDE");

        assertThat(testee.root).hasOnlyChildNodesFor('A');
        assertThat(testee.root.getChild('A').get()).hasOnlyChildNodesFor('B', 'C');
        assertThat(testee.root).hasWordStoredAt("ABC", 'A', 'B', 'C');
        assertThat(testee.root.getChild('A').get().getChild('C').get()).hasOnlyChildNodesFor('D');
        assertThat(testee.root.getChild('A').get().getChild('C').get().getChild('D').get()).hasOnlyChildNodesFor('E');
        assertThat(testee.root).hasWordStoredAt("ACD", 'A', 'C', 'D');
        assertThat(testee.root).hasWordStoredAt("ACDE", 'A', 'C', 'D', 'E');
    }

    private WordTrie.Node getChildFor(WordTrie.Node node, Character c) {
        return node.getChild(c).orElseThrow(() -> new RuntimeException("cannot find child for " + c));
    }

    @Test
    public void storeWordsInSortedOrder() {
        addWords("ABC", "ACB", "BAC", "ABCD");

        assertThat(testee.root).hasOnlyChildNodesFor('A');
        assertThat(testee.root.getChild('A').get()).hasOnlyChildNodesFor('B');
        assertThat(testee.root.getChild('A').get().getChild('B').get()).hasOnlyChildNodesFor('C');
        assertThat(testee.root.getChild('A').get().getChild('B').get().getChild('C').get()).hasOnlyChildNodesFor('D');
        assertThat(testee.root).hasWordStoredAt("ABC", 'A', 'B', 'C')
                .hasWordStoredAt("ACB", 'A', 'B', 'C')
                .hasWordStoredAt("BAC", 'A', 'B', 'C')
                .hasWordStoredAt("ABCD", 'A', 'B', 'C', 'D');
    }

    @Test
    public void storeWordsCaseInsensitive() {
        addWords("AbC", "AcB", "BaC");

        assertThat(testee.root).hasOnlyChildNodesFor('A');
        assertThat(testee.root.getChild('A').get()).hasOnlyChildNodesFor('B');
        assertThat(testee.root.getChild('A').get().getChild('B').get()).hasOnlyChildNodesFor('C');
        assertThat(testee.root).hasWordStoredAt("ABC", 'A', 'B', 'C')
                .hasWordStoredAt("ACB", 'A', 'B', 'C')
                .hasWordStoredAt("BAC", 'A', 'B', 'C');
    }

    @Test
    public void onlyStoreUniqueWords() {
        addWords("AbC", "ABc", "aBC");

        assertThat(testee.root.getChild('A').get().getChild('B').get().getChild('C').get().words).hasSize(1);
    }

    @Test
    public void getStoredWordsThatAreAnagrams() {
        Set<String> anagrams = anagramsOf("ABCDEF", 3);
        addWords(anagrams);

        assertThat(testee.getAnagramsOf("ABCDEF")).containsOnlyElementsOf(anagrams);
    }

    @Test
    public void anagramCheckIsCaseInsensitive() {
        Set<String> anagrams = anagramsOf("ABCDEF", 3);
        addWords(anagrams);

        assertThat(testee.getAnagramsOf("AbCdEf")).containsOnlyElementsOf(anagrams);
    }

    @Test
    public void emptySetIfNoAnagrams() {
        addWords(anagramsOf("ABCDEF", 3));

        assertThat(testee.getAnagramsOf("ABCDE")).hasSize(0);
        assertThat(testee.getAnagramsOf("ABCDEE")).hasSize(0);
        assertThat(testee.getAnagramsOf("ABCDEFG")).hasSize(0);
    }

    @Test
    public void originalWordNotIncludedInAnagramResults() {
        addWords("ABC", "BCA", "CAB", "ACB", "CBA", "BAC");

        assertThat(testee.getAnagramsOf("AbC")).hasSize(5);
        assertThat(testee.getAnagramsOf("CBa")).hasSize(5);
        assertThat(testee.getAnagramsOf("bCA")).hasSize(5);
    }

    @Test
    public void countWordsInTrie() {
        addWords("1", "2", "3", "4");
        assertThat(testee.countWords()).isEqualTo(4);
        addWords("5", "6", "7", "8");
        assertThat(testee.countWords()).isEqualTo(8);

        testee = new WordTrie();
        addWords("1", "2", "3", "4", "5", "6", "7", "8");
        assertThat(testee.countWords()).isEqualTo(8);
    }

    @Test
    public void countDuplicateWordsAddedToTrie() {
        addWords("1", "2", "1", "2");
        assertThat(testee.countWords()).isEqualTo(2);
        assertThat(testee.countDuplicateWordsAdded()).isEqualTo(2);
        addWords("5", "6", "7", "1");
        assertThat(testee.countWords()).isEqualTo(5);
        assertThat(testee.countDuplicateWordsAdded()).isEqualTo(3);

        testee = new WordTrie();
        addWords("1", "2", "3", "4", "5", "6", "7", "8", "1", "2", "3", "4", "5", "6", "7", "8");
        assertThat(testee.countWords()).isEqualTo(8);
        assertThat(testee.countDuplicateWordsAdded()).isEqualTo(8);
    }

    private Set<String> anagramsOf(String word, int numberOfAnagrams) {
        Set<String> anagrams = new HashSet<>();
        List<Character> letters = new ArrayList<>();
        word.chars().forEach(c -> letters.add((char) c));

        while (anagrams.size() < numberOfAnagrams) {
            StringBuilder sb = new StringBuilder();
            Collections.shuffle(letters);
            letters.forEach(sb::append);
            anagrams.add(sb.toString());
        }
        return anagrams;
    }

    private void addWords(String... words) {
        addWords(Arrays.asList(words));
    }

    private void addWords(Collection<String> words) {
        for (String word : words) {
            testee.addWord(word);
        }
    }
}