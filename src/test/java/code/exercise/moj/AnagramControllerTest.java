package code.exercise.moj;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnagramControllerTest {

    Map<String, Collection<String>> anagramAnswers;

    @Mock AnagramService anagramService;

    @InjectMocks
    AnagramController testee;

    @Before
    public void setup(){
        anagramAnswers = new HashMap<>();
    }

    @Test
    public void returnsResultsFromAnagramService(){
        String query = "AnagramQuery";
        Collection<String> anagrams = new ArrayList<>();
        anagrams.add("Anagram1");
        anagramAnswers.put(query, anagrams);
        when(anagramService.getAnagrams(query)).thenReturn(anagramAnswers);
        assertThat(testee.getAnagrams(query)).containsAllEntriesOf(anagramAnswers);
    }

    @Test
    public void parseCommaSeparatedQueries(){
        String query = "query1,query2,query3";
        when(anagramService.getAnagrams(query)).thenReturn(new HashMap<>());
        testee.getAnagrams(query);
        verify(anagramService).getAnagrams("query1", "query2", "query3");
    }
}