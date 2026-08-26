package com.baechu_tree.my_wiki.repository;

import com.baechu_tree.my_wiki.domain.WikiDocument;

import java.util.List;
import java.util.Optional;

public interface WikiDocumentRepository {

    // create
    WikiDocument save(WikiDocument document);

    // read
    Optional<WikiDocument> findById(int documentId);
    Optional<WikiDocument> findByTitle(String documentTitle);
    List<WikiDocument> findAll();

    // update
    WikiDocument update(WikiDocument document);

    // delete
    void deleteById(int documentId);
    void deleteByTitle(String documentTitle);
}
