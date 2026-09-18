package com.baechu_tree.my_wiki.controller;

import com.baechu_tree.my_wiki.constants.WikiPaths;
import com.baechu_tree.my_wiki.domain.WikiDocument;
import com.baechu_tree.my_wiki.service.WikiDocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class WikiController {

    private final WikiDocumentService documentService;

    public WikiController(WikiDocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping(WikiPaths.PATH_DOCUMENT_LIST_PAGE)
    public String DocumentListPage(Model model) {
        List<TitleAndRoute> titlesAndPaths = new ArrayList<>();

        List<WikiDocument> allDocuments = documentService.findAll();
        for (WikiDocument document : allDocuments) {
            titlesAndPaths.add(new TitleAndRoute(document.getDocumentTitle()));
        }

        model.addAttribute("titlesAndPaths", titlesAndPaths);

        return "document_list";
    }

    @GetMapping(WikiPaths.PATH_DOCUMENT_DETAIL_PAGE)
    public String DocumentDetailPage(Model model, @PathVariable String documentTitle) {
        Optional<WikiDocument> documentOptional = documentService.findByTitle(documentTitle);

        if (documentOptional.isEmpty()) return "temp_error"; // TODO: 제대로 된 에러 처리 필요!

        WikiDocument document = documentOptional.get();

        model.addAttribute("documentTitle", document.getDocumentTitle());
        model.addAttribute("documentArticle", document.getContent());

        return "document_detail";
    }

    @GetMapping(WikiPaths.PATH_DOCUMENT_SAVE_PAGE)
    public String DocumentSavePage(Model model) {
        return "document_save";
    }

    @PostMapping(WikiPaths.PATH_DOCUMENT_SAVE)
    public String DocumentSave(Model model, @RequestParam("document_title") String documentTitle, @RequestParam("content") String content) {
        // TODO: 문서 세이브 로직 완성 필요!
        WikiDocument document = new WikiDocument(
                null,
                documentTitle,
                content,
                null,
                null
        );
        int savedDocumentId = documentService.save(document);

        if (savedDocumentId == -1) return "temp_error"; // TODO: 제대로 된 에러 처리 필요!
        else return "redirect:/";
    }

    @GetMapping(WikiPaths.PATH_DOCUMENT_UPDATE_PAGE)
    public String DocumentUpdatePage(Model model, @PathVariable String documentTitle) {

        Optional<WikiDocument> documentOptional = documentService.findByTitle(documentTitle);

        if (documentOptional.isEmpty()) return "temp_error"; // TODO: 제대로 된 에러 처리 필요!

        WikiDocument document = documentOptional.get();

        model.addAttribute("originalDocumentArticle", document.getContent());

        return "document_update";
    }

    @PostMapping(WikiPaths.PATH_DOCUMENT_UPDATE)
    public String DocumentUpdate(Model model) {
        // TODO: 문서 업데이트 로직 완성 필요!
        return null;
    }

    @PostMapping(WikiPaths.PATH_DOCUMENT_DELETE)
    public String DocumentDelete(Model model) {
        // TODO: 문서 삭제 로직 완성 필요!
        return null;
    }

    private String GetPathOfSpecificDocumentDetail(String documentTitle) {
        return WikiPaths.GetPathOfSpecificDocumentDetailPage(documentTitle);
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
