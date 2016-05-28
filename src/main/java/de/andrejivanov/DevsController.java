package de.andrejivanov;

import de.andrejivanov.models.MemberKnowledge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DevsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevsController.class);
    private DevService devService;

    @Autowired
    public DevsController(final DevService devService) {
        this.devService = devService;
    }

    @RequestMapping(value = "/knowledge", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<MemberKnowledge> knowledge() {
        LOGGER.debug("knowledge");
        return devService.knowledgeList();
    }

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("knowledgeList", devService.knowledgeList());
        return "knowledge";
    }

}
