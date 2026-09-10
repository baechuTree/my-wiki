package com.baechu_tree.my_wiki;

import com.baechu_tree.my_wiki.repository.JdbcWikiDocumentRepository;
import com.baechu_tree.my_wiki.repository.WikiDocumentRepository;
import com.baechu_tree.my_wiki.service.WikiDocumentService;
import com.baechu_tree.my_wiki.service.WikiDocumentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    // Bean에 필요한 데이터 또는 객체 추가

    private DataSource dataSource;

    public AppConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Bean 등록

    @Bean
    WikiDocumentRepository wikiDocumentRepository() {
        return new JdbcWikiDocumentRepository(dataSource);
    }

    @Bean
    WikiDocumentService wikiDocumentService() {
        return new WikiDocumentServiceImpl(wikiDocumentRepository());
    }
}
