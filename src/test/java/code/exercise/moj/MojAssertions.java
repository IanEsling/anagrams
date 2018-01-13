package code.exercise.moj;

import org.assertj.core.api.Assertions;

public class MojAssertions extends Assertions {

    public static NodeAssert assertThat(WordTrie.Node actual) {
        return new NodeAssert(actual);
    }
}
