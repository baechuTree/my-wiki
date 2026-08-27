package com.baechu_tree.my_wiki.repository;

import com.baechu_tree.my_wiki.constants.LocalConstantsForTest;
import com.baechu_tree.my_wiki.domain.WikiDocument;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Optional;

public class WikiDocumentRepositoryTest {

    // 테스트에 사용할 document_title 이름
    String title1 = "test1";
    String title2 = "test2";

    // Jdbc 테스트를 위한 DataSource 직접 생성
    DataSource dataSource = new DriverManagerDataSource(
            LocalConstantsForTest.DB_DATASOURCE_URL_FOR_TEST,
            LocalConstantsForTest.DB_DATASOURCE_USERNAME_FOR_TEST,
            LocalConstantsForTest.DB_DATASOURCE_PASSWORD_FOR_TEST
    );

    WikiDocumentRepository repository = new JdbcWikiDocumentRepository(dataSource);

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
            throw new IllegalStateException("Repository의 save 메서드에서 오류 발생");
        }
        Optional<WikiDocument> foundDocument = repository.findById(savedDocument.getDocumentId());

        // then
        if (foundDocument.isEmpty()) throw new IllegalStateException("findById로 저장한 문서 찾기 실패");
        WikiDocument doc = foundDocument.get();

        Assertions.assertThat(doc.getDocumentId()).isNotNull();
        Assertions.assertThat(doc.getDocumentTitle()).isEqualTo(document.getDocumentTitle());
        Assertions.assertThat(doc.getContent()).isEqualTo(document.getContent());
        Assertions.assertThat(doc.getCreatedAt()).isNotNull();
        Assertions.assertThat(doc.getUpdatedAt()).isNotNull();
    }
}
