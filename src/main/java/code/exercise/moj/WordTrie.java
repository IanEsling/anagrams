package code.exercise.moj;

import java.util.*;
import java.util.stream.Collectors;

public class WordTrie {

    private int wordsAdded = 0;
    private int dupeWords = 0;
    Node root;

    public WordTrie() {
        this.root = new Node();
    }

    public void addWord(String word) {
        addWord(new TrieWord(word));
        wordsAdded++;
    }

    public Set<String> getAnagramsOf(String word) {
        return getAnagramsOf(new TrieWord(word));
    }

    public int countDuplicateWordsAdded() {
        return dupeWords;
    }

    public int countWords() {
        return countWords(root);
    }

    public int countWordsAdded(){
        return wordsAdded;
    }

    private int countWords(WordTrie.Node node) {
        int wordcount = node.words.size();
        for (WordTrie.Node n : node.nodes.values()) {
            wordcount = wordcount + countWords(n);
        }
        return wordcount;
    }

    private Set<String> getAnagramsOf(TrieWord word) {
        Optional<Node> node = Optional.of(root);
        for (char c : word.getChars()) {
            if (node.isPresent()) {
                node = node.get().getChild(c);
            }
        }
        return node.orElse(new Node()).getWordsExcept(word);
    }

    private void addWord(TrieWord word) {
        getLastNodeInCharSequence(word.getChars()).addWord(word);
    }

    private Node getLastNodeInCharSequence(char[] chars) {
        Node node = root;
        for (char c : chars) {
            node = node.nodes.merge(c, new Node(), (n1, n2) -> n1);
        }
        return node;
    }

    class TrieWord {

        private final String word;
        private final char[] sortedUpperCaseWordChars;

        TrieWord(String word) {
            this.word = word.toUpperCase();
            this.sortedUpperCaseWordChars = word.toUpperCase().toCharArray();
            Arrays.sort(sortedUpperCaseWordChars);
        }

        char[] getChars(){
            return sortedUpperCaseWordChars;
        }

        String getWord() {
            return word;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TrieWord trieWord = (TrieWord) o;

            return word.equals(trieWord.word);

        }

        @Override
        public int hashCode() {
            return word.hashCode();
        }
    }

    class Node {
        final Set<TrieWord> words;
        final Map<Character, Node> nodes;

        Node() {
            this.nodes = new HashMap<>();
            this.words = new HashSet<>();
        }

        Optional<Node> getChild(Character c) {
            return Optional.ofNullable(nodes.get(c));
        }

        void addWord(TrieWord word) {
            if (words.contains(word)) {
                dupeWords++;
            } else {
                words.add(word);
            }
        }

        Set<String> getWordsExcept(TrieWord word) {
            return words.stream()
                    .filter(w -> !w.equals(word))
                    .map(TrieWord::getWord)
                    .collect(Collectors.toSet());
        }
    }
}
