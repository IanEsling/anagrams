package code.exercise.moj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WordlistTrieFactory {

    private WordlistReader reader;

    @Autowired
    public WordlistTrieFactory(WordlistReader reader){
        this.reader = reader;
    }

    public WordTrie generateNewTrie() throws WordlistReadingException {
        WordTrie trie = new WordTrie();
        while (reader.hasMoreWords()) {
            trie.addWord(reader.getNextWord());
        }
        return trie;
    }
}
