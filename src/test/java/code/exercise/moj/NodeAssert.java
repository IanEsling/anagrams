package code.exercise.moj;

import org.assertj.core.api.AbstractAssert;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class NodeAssert extends AbstractAssert<NodeAssert, WordTrie.Node> {

    public NodeAssert(WordTrie.Node actual) {
        super(actual, NodeAssert.class);
    }

    public NodeAssert hasOnlyChildNodesFor(char... keys) {
        assertThat(actual.nodes).hasSize(keys.length);
        for (char c : keys) {
            assertThat(actual.nodes.get(c)).isNotNull();
        }
        return this;
    }

    public NodeAssert hasWordStoredAt(String word, char... keys) {
        Optional<WordTrie.Node> node = Optional.of(actual);
        for (char c : keys) {
            node = node
                    .orElseThrow(() -> new RuntimeException("cannot find node for char " + c))
                    .getChild(c);
        }
        assertThat(node.orElseThrow(() -> new RuntimeException("cannot find node for word " + word))
                .words.stream().map(WordTrie.TrieWord::getWord).collect(Collectors.toList())
                .contains(word));
        return this;
    }
}
