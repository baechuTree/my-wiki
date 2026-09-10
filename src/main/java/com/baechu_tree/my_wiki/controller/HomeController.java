package com.baechu_tree.my_wiki.controller;

import com.baechu_tree.my_wiki.constants.WikiPaths;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String Home(Model model) {
        model.addAttribute("documentListPagePath", WikiPaths.PATH_DOCUMENT_LIST_PAGE);
        model.addAttribute("documentSavePagePath", );
        return "index";
    }
}
