package com.baechu_tree.my_wiki.service;

import com.baechu_tree.my_wiki.domain.WikiDocument;
import com.baechu_tree.my_wiki.repository.WikiDocumentRepository;

import java.util.List;
import java.util.Optional;

public class WikiDocumentServiceImpl implements WikiDocumentService {

    private final WikiDocumentRepository repository;

    public WikiDocumentServiceImpl(WikiDocumentRepository repository) {
        this.repository = repository;
    }

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
