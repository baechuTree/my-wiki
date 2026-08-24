package com.baechu_tree.my_wiki.controller;

import com.baechu_tree.my_wiki.constants.WikiPaths;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WikiController {

    @GetMapping(WikiPaths.PATH_DOCUMENT_LIST)
    public String DocumentList(Model model) {
        TitleAndRoute testTitleAndRoute1 = new TitleAndRoute("Spring");
        TitleAndRoute testTitleAndRoute2 = new TitleAndRoute("Java");
        TitleAndRoute testTitleAndRoute3 = new TitleAndRoute("React");

        List<TitleAndRoute> testTitlesAndPaths = new ArrayList<>();
        testTitlesAndPaths.add(testTitleAndRoute1);
        testTitlesAndPaths.add(testTitleAndRoute2);
        testTitlesAndPaths.add(testTitleAndRoute3);

        model.addAttribute("titlesAndPaths", testTitlesAndPaths);
        return "document_list";
    }

    @GetMapping(WikiPaths.PATH_DOCUMENT_DETAIL)
    public String DocumentDetail(Model model, @PathVariable String documentTitle) {
        model.addAttribute("documentTitle", documentTitle);
        return "document_detail";
    }

    private String GetPathOfSpecificDocumentDetail(String documentTitle) {
        return WikiPaths.GetPathOfSpecificDocumentDetail(documentTitle);
    }

    class TitleAndRoute {

        public String title;
        public String path;

        public TitleAndRoute(String title) {
            this.title = title;
            this.path = GetPathOfSpecificDocumentDetail(title);
        }
    }
}
