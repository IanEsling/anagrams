package code.exercise.moj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/{query}")
public class AnagramController {

    private AnagramService anagramService;

    @Autowired
    public AnagramController(AnagramService anagramService) {
        this.anagramService = anagramService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Collection<String>> getAnagrams(@PathVariable("query") String query) {
        return anagramService.getAnagrams(query.split(","));
    }
}
