package com.baechu_tree.my_wiki.service;

import com.baechu_tree.my_wiki.domain.WikiDocument;

import java.util.List;
import java.util.Optional;

public class WikiDocumentServiceImpl implements WikiDocumentService {

    @Override
    public int save(WikiDocument document) {
        return 0;
    }

    @Override
    public Optional<WikiDocument> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<WikiDocument> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public List<WikiDocument> findAll() {
        return List.of();
    }

    @Override
    public int update(WikiDocument document) {
        return 0;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteByTitle(String title) {

    }
}
