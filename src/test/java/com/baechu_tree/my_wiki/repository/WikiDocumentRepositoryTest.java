package com.baechu_tree.my_wiki.repository;

import com.baechu_tree.my_wiki.constants.LocalConstantsForTest;
import com.baechu_tree.my_wiki.domain.WikiDocument;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class WikiDocumentRepositoryTest {

    DataSource dataSource = new DriverManagerDataSource(
            LocalConstantsForTest.DB_DATASOURCE_URL_FOR_TEST,
            LocalConstantsForTest.DB_DATASOURCE_USERNAME_FOR_TEST,
            LocalConstantsForTest.DB_DATASOURCE_PASSWORD_FOR_TEST
    );

    WikiDocumentRepository repository = new JdbcWikiPageRepository(dataSource);

    @Test @DisplayName("WikiDocumentRepositoryTest:save - 위키 문서가 성공적으로 저장 후 반환되어야 한다")
    void save() {
        // given
        WikiDocument document = new WikiDocument(
                null,
                "test",
                "owo What's this?",
                null,
                null
        );

        // when
        WikiDocument savedDocument = repository.save(document);

        // then
        Assertions.assertThat(savedDocument).isNotNull();
        Assertions.assertThat(savedDocument.getDocumentId()).isNotNull();
        Assertions.assertThat(savedDocument.getDocumentId()).isEqualTo(document.getDocumentId());
        Assertions.assertThat(savedDocument.getDocumentTitle()).isEqualTo(document.getDocumentTitle());
        Assertions.assertThat(savedDocument.getCreatedAt()).isNotNull();
        Assertions.assertThat(savedDocument.getUpdatedAt()).isNotNull();
    }
}
