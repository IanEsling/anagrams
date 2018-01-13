package code.exercise.moj;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class WordlistFromFileTest {

    private WordlistTrieFactory trieFactory;
    private FileWordlistReader reader;

    @Before
    public void setup() throws FileNotFoundException {
        reader = new FileWordlistReader("/wordlist.txt");
        reader.init();
        trieFactory = new WordlistTrieFactory(reader);
    }

    @Test
    public void readFromFile() throws WordlistReadingException {
        System.out.println("Starting reading from file: " + Instant.now());
        WordTrie trie = trieFactory.generateNewTrie();

        System.out.println("Finished reading from file: " + Instant.now());
        System.out.println("Word Count: " + reader.wordcount);
        int wordcount = 0;
        for (WordTrie.Node node : trie.root.nodes.values()) {
            wordcount = wordcount + countWords(node);
        }
        System.out.println("Wordcount: " + wordcount);
    }

    @Test
    public void getAnagramsFromFile() throws WordlistReadingException {
        WordTrie trie = trieFactory.generateNewTrie();

        assertThat(trie.getAnagramsOf("crepitus")).containsExactlyInAnyOrder("PIECRUST", "PICTURES", "CUPRITES");
        assertThat(trie.getAnagramsOf("paste")).containsExactlyInAnyOrder("TEPAS", "SPATE", "PATES", "SEPTA", "PEATS", "TAPES");
        assertThat(trie.getAnagramsOf("kinship")).containsExactlyInAnyOrder("PINKISH");
        assertThat(trie.getAnagramsOf("enlist")).containsExactlyInAnyOrder("TINSEL", "ELINTS", "LISTEN", "INLETS", "SILENT");
        assertThat(trie.getAnagramsOf("boaster")).containsExactlyInAnyOrder("SORBATE", "BOATERS", "BAROTSE", "REBATOS", "BORATES");
        assertThat(trie.getAnagramsOf("fresher")).containsExactlyInAnyOrder("REFRESH");
        assertThat(trie.getAnagramsOf("sinks")).containsExactlyInAnyOrder("SKINS");
        assertThat(trie.getAnagramsOf("knits")).containsExactlyInAnyOrder("SKINT", "TINKS", "STINK");
        assertThat(trie.getAnagramsOf("sort")).containsExactlyInAnyOrder("STOR", "ORTS", "ROTS", "TORS");
        assertThat(trie.getAnagramsOf("sdfwehrtgegfg")).isEmpty();
    }

    private int countWords(WordTrie.Node node) {
        int wordcount = node.words.size();
        for (WordTrie.Node n : node.nodes.values()) {
            wordcount = wordcount + countWords(n);
        }
        return wordcount;
    }
}
