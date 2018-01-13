package code.exercise.moj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnagramService {

    private static Logger LOGGER = LoggerFactory.getLogger(AnagramService.class);

    private WordTrie trie;
    private WordlistTrieFactory trieFactory;

    @Autowired
    public AnagramService(WordlistTrieFactory trieFactory) {
        this.trieFactory = trieFactory;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("generating new wordlist trie...");
        try {
            this.trie = trieFactory.generateNewTrie();
        } catch (WordlistReadingException e) {
            LOGGER.info("Cannot read wordlist: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOGGER.info("finished generating new wordlist.");
        LOGGER.info(String.format("Added %d words from list, %d were duplicates, trie contains %d words.", trie.countWordsAdded(), trie.countDuplicateWordsAdded(), trie.countWords()));
    }

    public Map<String, Collection<String>> getAnagrams(String... words) {
        Map<String, Collection<String>> anagrams = new HashMap<>();
        for (String word : words) {
            anagrams.put(word, trie.getAnagramsOf(word));
        }
        return anagrams;
    }
}
