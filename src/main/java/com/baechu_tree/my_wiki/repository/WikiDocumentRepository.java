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

    /**
     * 문서를 수정하는 메서드. id 및 title(제목)은 수정이 불가함
     * @param document 이 객체가 가진 documentId 값을 사용해 수정할 문서를 찾고, 이 객체가 가진 나머지 값으로 문서 정보가 수정됨
     * @return 수정이 끝난 문서 객체
     */
    WikiDocument update(WikiDocument document);

    /**
     * 문서의 제목을 수정하는 메서드
     * @param documentId 문서의 id값. 이 값을 이용해 수정할 문서를 찾음
     * @param newTitle 문서의 새 제목. 해당 문서의 제목이 이 제목으로 수정될 것임
     * @return 수정이 끝난 문서 객체
     */
    WikiDocument updateTitle(int documentId, String newTitle);

    // delete
    void deleteById(int documentId);
    void deleteByTitle(String documentTitle);
}
