package com.baechu_tree.my_wiki.service;

import com.baechu_tree.my_wiki.domain.WikiDocument;
import com.baechu_tree.my_wiki.repository.WikiDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WikiDocumentServiceImpl implements WikiDocumentService {

    // TODO: repository를 사용하는 각 메서드에 제약조건 체크 및 오류 대응 기능 추가해야 함!

    private final WikiDocumentRepository repository;

    @Override
    public int save(WikiDocument document) {
        WikiDocument savedDocument = repository.save(document);
        Integer documentId = savedDocument.getDocumentId();
        if (documentId != null) return savedDocument.getDocumentId();
        else return -1;
    }

    @Override
    public Optional<WikiDocument> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Optional<WikiDocument> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Override
    public List<WikiDocument> findAll() {
        return repository.findAll();
    }

    @Override
    public int update(WikiDocument document) {
        WikiDocument updatedDocument = repository.update(document);
        Integer documentId = updatedDocument.getDocumentId();
        if (documentId != null) return documentId;
        else return -1;
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByTitle(String title) {
        repository.deleteByTitle(title);
    }
}
