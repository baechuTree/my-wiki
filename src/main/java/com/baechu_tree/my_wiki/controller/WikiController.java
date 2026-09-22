package com.baechu_tree.my_wiki.controller;

import com.baechu_tree.my_wiki.constants.WikiPaths;
import com.baechu_tree.my_wiki.domain.WikiDocument;
import com.baechu_tree.my_wiki.dto.WikiCreateRequest;
import com.baechu_tree.my_wiki.dto.WikiUpdateRequest;
import com.baechu_tree.my_wiki.service.WikiDocumentService;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<DocumentInfo> documentInfos = new ArrayList<>();

        List<WikiDocument> allDocuments = documentService.findAll();
        for (WikiDocument document : allDocuments) {
            documentInfos.add(new DocumentInfo(document));
        }

        model.addAttribute("documentInfos", documentInfos);
        model.addAttribute("updatePagePath", WikiPaths.PATH_DOCUMENT_UPDATE_PAGE);
        model.addAttribute("deletePath", WikiPaths.PATH_DOCUMENT_DELETE);

        return "document_list";
    }

    @GetMapping(WikiPaths.PATH_DOCUMENT_DETAIL_PAGE)
    public String DocumentDetailPage(Model model, @PathVariable String documentTitle) {
        Optional<WikiDocument> documentOptional = documentService.findByTitle(documentTitle);

        if (documentOptional.isEmpty()) return "temp_error"; // TODO: 제대로 된 에러 처리 필요!

        WikiDocument document = documentOptional.get();

        model.addAttribute("document", document);
        model.addAttribute("updatePagePath", WikiPaths.PATH_DOCUMENT_UPDATE_PAGE);
        model.addAttribute("deletePath", WikiPaths.PATH_DOCUMENT_DELETE);

        return "document_detail";
    }

    @GetMapping(WikiPaths.PATH_DOCUMENT_SAVE_PAGE)
    public String DocumentSavePage(Model model) {
        model.addAttribute("savePath", WikiPaths.PATH_DOCUMENT_SAVE);

        return "document_save";
    }

    @PostMapping(WikiPaths.PATH_DOCUMENT_SAVE)
    public String DocumentSave(Model model, @RequestBody WikiCreateRequest documentToSave) {
        WikiDocument document = new WikiDocument(
                null,
                documentToSave.getDocumentTitle(),
                documentToSave.getContent(),
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

        model.addAttribute("originalDocument", document);
        model.addAttribute("updatePath", WikiPaths.PATH_DOCUMENT_UPDATE);

        return "document_update";
    }

    @PostMapping(WikiPaths.PATH_DOCUMENT_UPDATE)
    public String DocumentUpdate(Model model, @RequestBody WikiUpdateRequest documentToUpdate) {
        WikiDocument document = new WikiDocument(
                documentToUpdate.getDocumentId(),
                documentToUpdate.getDocumentTitle(),
                documentToUpdate.getContent(),
                null,
                null
        );
        int updatedDocumentId = documentService.update(document);

        if (updatedDocumentId == -1) return "temp_error"; // TODO: 제대로 된 에러 처리 필요!
        else return "redirect:/";
    }

    @PostMapping(WikiPaths.PATH_DOCUMENT_DELETE)
    public String DocumentDelete(Model model, @RequestParam String documentTitle) {
        // TODO: 타이틀값을 받아 문서를 지울 경우, 해커가 요청 정보를 쉽게 만들 수 있어 보안에 취약할 수 있음. 개선 필요
        documentService.deleteByTitle(documentTitle);
        return "redirect:/";
    }

    private String GetPathOfSpecificDocumentDetail(String documentTitle) {
        return WikiPaths.GetPathOfSpecificDocumentDetailPage(documentTitle);
    }

    @Getter
    class DocumentInfo {

        private final WikiDocument document;
        private final String path;

        public DocumentInfo(WikiDocument document) {
            this.document = document;
            this.path = GetPathOfSpecificDocumentDetail(document.getDocumentTitle());
        }
    }
}
