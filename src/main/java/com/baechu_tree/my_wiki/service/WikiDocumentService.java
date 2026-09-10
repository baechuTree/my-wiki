package com.baechu_tree.my_wiki.service;

import com.baechu_tree.my_wiki.domain.WikiDocument;

import java.util.List;
import java.util.Optional;

public interface WikiDocumentService {

    /**
     * 문서를 저장하는 메서드.
     * @param document
     * @return 저장된 문서의 id를 찾는 데 성공하면 id값 반환, 실패하면 -1 반환
     */
    int save(WikiDocument document);

    Optional<WikiDocument> findById(int id);
    Optional<WikiDocument> findByTitle(String title);
    List<WikiDocument> findAll();

    /**
     * 문서를 수정하는 메서드.
     * @param document
     * @return 수정된 문서의 id를 찾는 데 성공하면 id값 반환, 실패하면 -1 반환
     */
    int update(WikiDocument document);

    void deleteById(int id);
    void deleteByTitle(String title);
}
