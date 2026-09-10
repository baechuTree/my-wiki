package com.baechu_tree.my_wiki.controller;

import com.baechu_tree.my_wiki.constants.WikiPaths;
import com.baechu_tree.my_wiki.domain.WikiDocument;
import com.baechu_tree.my_wiki.service.WikiDocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WikiController {

    private final WikiDocumentService documentService;

    public WikiController(WikiDocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping(WikiPaths.PATH_DOCUMENT_LIST)
    public String DocumentList(Model model) {
        List<TitleAndRoute> titlesAndPaths = new ArrayList<>();

        List<WikiDocument> allDocuments = documentService.findAll();
        for (WikiDocument document : allDocuments) {
            titlesAndPaths.add(new TitleAndRoute(document.getDocumentTitle()));
        }

        model.addAttribute("titlesAndPaths", titlesAndPaths);
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
