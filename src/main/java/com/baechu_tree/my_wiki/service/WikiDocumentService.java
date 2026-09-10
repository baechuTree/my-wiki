package com.baechu_tree.my_wiki.service;

import com.baechu_tree.my_wiki.domain.WikiDocument;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface WikiDocumentService {

    int save(WikiDocument document);

    Optional<WikiDocument> findById(int id);
    Optional<WikiDocument> findByTitle(String title);
    List<WikiDocument> findAll();

    int update(WikiDocument document);

    void deleteById(int id);
    void deleteByTitle(String title);
}
