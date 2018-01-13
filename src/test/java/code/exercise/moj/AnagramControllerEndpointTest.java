package code.exercise.moj;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AnagramController.class)
public class AnagramControllerEndpointTest {

    @MockBean AnagramService anagramService;

    @Autowired private MockMvc mockMvc;
    private String resultJson;
    private Map<String, Collection<String>> result;

    @Before
    public void setUp() throws JsonProcessingException {
        result = ImmutableMap.of("someWord", ImmutableList.of("drowemos","rowdmeso"), "anotherWord", ImmutableList.of());
        resultJson = new ObjectMapper().writeValueAsString(result);
    }

    @Test
    public void testIndex() throws Exception {
        when(anagramService.getAnagrams("someWord", "anotherWord")).thenReturn(result);
        this.mockMvc.perform(get("/someWord,anotherWord"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultJson));
    }
}
