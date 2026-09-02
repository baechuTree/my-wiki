package com.baechu_tree.my_wiki.repository;

import com.baechu_tree.my_wiki.constants.LocalConstantsForTest;
import com.baechu_tree.my_wiki.domain.WikiDocument;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class WikiDocumentRepositoryTest {

    // 테스트에 사용할 document_title 이름
    static String title1 = "test1";
    static String title2 = "test2";

    // Jdbc 테스트를 위한 DataSource 직접 생성
    static DataSource dataSource = new DriverManagerDataSource(
            LocalConstantsForTest.DB_DATASOURCE_URL_FOR_TEST,
            LocalConstantsForTest.DB_DATASOURCE_USERNAME_FOR_TEST,
            LocalConstantsForTest.DB_DATASOURCE_PASSWORD_FOR_TEST
    );

    static WikiDocumentRepository repository = new JdbcWikiDocumentRepository(dataSource);

    @BeforeAll
    static void BeforeAll() {
        repository.deleteByTitle(title1);
        repository.deleteByTitle(title2);
    }

    @AfterEach
    void AfterEach() {
        repository.deleteByTitle(title1);
        repository.deleteByTitle(title2);
    }

    @Test @DisplayName("WikiDocumentRepositoryTest:save - 위키 문서가 성공적으로 저장 후 반환되어야 한다")
    void save() {
        // given
        WikiDocument document = new WikiDocument(
                null,
                title1,
                "owo What's this?",
                null,
                null
        );

        // when
        WikiDocument savedDocument = repository.save(document);

        // then
        Assertions.assertThat(savedDocument).isNotNull();
        Assertions.assertThat(savedDocument.getDocumentId()).isNotNull();
        Assertions.assertThat(savedDocument.getDocumentTitle()).isEqualTo(document.getDocumentTitle());
        Assertions.assertThat(savedDocument.getContent()).isEqualTo(document.getContent());
        Assertions.assertThat(savedDocument.getCreatedAt()).isNotNull();
        Assertions.assertThat(savedDocument.getUpdatedAt()).isNotNull();
    }

    @Test @DisplayName("WikiDocumentRepositoryTest:save - 실패: 제목이 중복되는 문서는 저장되지 않아야 한다")
    void save_failure_duplicateTitle() {
        // given
        WikiDocument document1 = new WikiDocument(
                null,
                title1,
                "Kyahooo",
                null,
                null
        );
        WikiDocument document2 = new WikiDocument(
                null,
                title1,
                "owo What's this?",
                null,
                null
        );

        // when, then
        assertThrows(IllegalStateException.class, () -> {
            repository.save(document1);
            repository.save(document2);
        });
    }

    @Test
    void findById() {
        // given
        WikiDocument document = new WikiDocument(
                null,
                title1,
                "Kyahooo",
                null,
                null
        );

        // when
        WikiDocument savedDocument = repository.save(document);
        if (savedDocument.getDocumentId() == null) {
            throw new IllegalStateException("WikiDocumentRepository의 save 메서드에서 오류 발생");
        }
        Optional<WikiDocument> foundDocumentOptional = repository.findById(savedDocument.getDocumentId());

        // then
        if (foundDocumentOptional.isEmpty()) throw new IllegalStateException("findById로 저장된 문서 찾기 실패");
        WikiDocument foundDocument = foundDocumentOptional.get();

        Assertions.assertThat(foundDocument.getDocumentId()).isNotNull();
        Assertions.assertThat(foundDocument.getDocumentTitle()).isEqualTo(document.getDocumentTitle());
        Assertions.assertThat(foundDocument.getContent()).isEqualTo(document.getContent());
        Assertions.assertThat(foundDocument.getCreatedAt()).isNotNull();
        Assertions.assertThat(foundDocument.getUpdatedAt()).isNotNull();
    }

    @Test
    void findByTitle() {
        // given
        WikiDocument document = new WikiDocument(
                null,
                title1,
                "Kyahooo",
                null,
                null
        );

        // when
        WikiDocument savedDocument = repository.save(document);
        if (savedDocument.getDocumentId() == null) throw new IllegalStateException("WikiDocumentRepository의 save 메서드에서 오류 발생");
        Optional<WikiDocument> foundDocumentOptional = repository.findByTitle(title1);

        // then
        if (foundDocumentOptional.isEmpty()) throw new IllegalStateException("findByTitle로 저장된 문서 찾기 실패");
        WikiDocument foundDocument = foundDocumentOptional.get();

        Assertions.assertThat(foundDocument.getDocumentId()).isNotNull();
        Assertions.assertThat(foundDocument.getDocumentTitle()).isEqualTo(document.getDocumentTitle());
        Assertions.assertThat(foundDocument.getContent()).isEqualTo(document.getContent());
        Assertions.assertThat(foundDocument.getCreatedAt()).isNotNull();
        Assertions.assertThat(foundDocument.getUpdatedAt()).isNotNull();
    }

    @Test
    void findAll() {
        // given
        WikiDocument document1 = new WikiDocument(
                null,
                title1,
                "Kyahooo",
                null,
                null
        );
        WikiDocument document2 = new WikiDocument(
                null,
                title2,
                "owo What's this?",
                null,
                null
        );

        List<WikiDocument> documents = new ArrayList<>();
        documents.add(document1);
        documents.add(document2);

        // when
        WikiDocument savedDocument1 = repository.save(document1);
        WikiDocument savedDocument2 = repository.save(document2);
        if (savedDocument1.getDocumentId() == null) throw new IllegalStateException("WikiDocumentRepository의 save 메서드에서 오류 발생");
        if (savedDocument2.getDocumentId() == null) throw new IllegalStateException("WikiDocumentRepository의 save 메서드에서 오류 발생");
        List<WikiDocument> foundDocuments = repository.findAll();

        // then
        if (foundDocuments.isEmpty()) throw new IllegalStateException("저장된 문서 찾기 실패");

        for (int i = 0; i < foundDocuments.size(); i++) {
            Assertions.assertThat(foundDocuments.get(i).getDocumentId()).isNotNull();
            Assertions.assertThat(foundDocuments.get(i).getDocumentTitle()).isEqualTo(documents.get(i).getDocumentTitle());
            Assertions.assertThat(foundDocuments.get(i).getContent()).isEqualTo(documents.get(i).getContent());
            Assertions.assertThat(foundDocuments.get(i).getCreatedAt()).isNotNull();
            Assertions.assertThat(foundDocuments.get(i).getUpdatedAt()).isNotNull();
        }
    }
    
    @Test @DisplayName("WikiDocumentRepositoryTest:findAll: 저장된 튜플이 없는 경우 빈 리스트가 반환되어야 한다")
    void findAll_whenNothingFound() {
        List<WikiDocument> foundDocuments = repository.findAll();

        Assertions.assertThat(foundDocuments.size()).isEqualTo(0);
    }

    @Test
    void update() {
        // given
        WikiDocument documentBefore = new WikiDocument(
                null,
                title1,
                "Kyahooo",
                null,
                null
        );
        WikiDocument documentAfter = new WikiDocument(
                null,
                null,
                "I changed content!",
                null,
                null
        );

        // when
        WikiDocument savedDocument = repository.save(documentBefore);
        if (savedDocument.getDocumentId() == null) throw new IllegalStateException("WikiDocumentRepository의 save 메서드에서 오류 발생");

        documentAfter.setDocumentId(savedDocument.getDocumentId());
        WikiDocument updatedDocument = repository.update(documentAfter);

        // then
        Assertions.assertThat(updatedDocument.getDocumentId()).isNotNull();
        Assertions.assertThat(updatedDocument.getDocumentTitle()).isEqualTo(documentAfter.getDocumentTitle());
        Assertions.assertThat(updatedDocument.getContent()).isEqualTo(documentAfter.getContent());
        Assertions.assertThat(updatedDocument.getCreatedAt()).isNotNull();
        Assertions.assertThat(updatedDocument.getUpdatedAt()).isNotNull();
    }

    @Test @DisplayName("WikiDocumentRepositoryTest:update - 실패: 문서를 다른 제목으로 저장하려고 할 경우 실패해야 함")
    void update_failure_differentTitle() {
        // given
        WikiDocument documentBefore = new WikiDocument(
                null,
                title1,
                "Kyahooo",
                null,
                null
        );
        WikiDocument documentAfter = new WikiDocument(
                null,
                title2,
                "I want change title!",
                null,
                null
        );

        // when, then
        assertThrows(IllegalStateException.class, () -> {
            WikiDocument savedDocument = repository.save(documentBefore);
            documentAfter.setDocumentId(savedDocument.getDocumentId());

            repository.update(documentAfter);
        });
    }

    @Test
    void deleteById() {
        // given
        WikiDocument document = new WikiDocument(
                null,
                title1,
                "Kyahooo",
                null,
                null
        );

        // when
        WikiDocument savedDocument = repository.save(document);
        if (savedDocument.getDocumentId() == null) throw new IllegalStateException("WikiDocumentRepository의 save 메서드에서 오류 발생");

        repository.deleteById(savedDocument.getDocumentId());

        // then
        Assertions.assertThat(repository.findById(savedDocument.getDocumentId())).isEqualTo(Optional.empty());
    }

    @Test
    void deleteByTitle() {
        // given
        WikiDocument document = new WikiDocument(
                null,
                title1,
                "Kyahooo",
                null,
                null
        );

        // when
        WikiDocument savedDocument = repository.save(document);
        if (savedDocument.getDocumentId() == null) throw new IllegalStateException("WikiDocumentRepository의 save 메서드에서 오류 발생");

        repository.deleteByTitle(savedDocument.getDocumentTitle());

        // then
        Assertions.assertThat(repository.findById(savedDocument.getDocumentId())).isEqualTo(Optional.empty());
    }
}
