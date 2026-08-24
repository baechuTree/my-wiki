package com.baechu_tree.my_wiki.controller;

import com.baechu_tree.my_wiki.constants.WikiPaths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * HomeController와 WikiController가 HTTP 요청을 올바른 템플릿으로 연결하는지 확인한다.
 *
 * @WebMvcTest 는 웹 계층(Controller, URL 매핑, Thymeleaf 렌더링)에만 집중하는 테스트다.
 * 따라서 JPA나 MySQL을 시작하지 않아 빠르고, DB 설정 없이도 실행할 수 있다.
 */
@WebMvcTest(controllers = {HomeController.class, WikiController.class})
class PageControllerTest {

    /**
     * MockMvc는 실제 서버를 8080 포트로 실행하지 않고도 HTTP 요청을 흉내 내는 도구다.
     */
    @Autowired
    private MockMvc mockMvc;

    @Test
    void homePage_returnsIndexTemplateAndLinkToDocumentList() throws Exception {
        // GET / 요청을 보낸다.
        mockMvc.perform(get("/"))
                // HTTP 응답 상태가 200 OK인지 확인한다.
                .andExpect(status().isOk())
                // HomeController가 templates/index.html에 해당하는 "index"를 반환하는지 확인한다.
                .andExpect(view().name("index"))
                // Controller가 목록 페이지 주소를 Model에 전달하는지 확인한다.
                .andExpect(model().attribute("documentListPath", WikiPaths.PATH_DOCUMENT_LIST))
                // 렌더링된 HTML에 목록 페이지 링크가 있는지 확인한다.
                .andExpect(content().string(containsString(WikiPaths.PATH_DOCUMENT_LIST)));
    }

    @Test
    void documentListPage_returnsListTemplateAndDocumentLinks() throws Exception {
        // URL 상수를 사용하므로 목록 페이지 주소를 바꾸면 테스트도 함께 따라간다.
        mockMvc.perform(get(WikiPaths.PATH_DOCUMENT_LIST))
                .andExpect(status().isOk())
                // WikiController가 templates/document_list.html을 반환하는지 확인한다.
                .andExpect(view().name("document_list"))
                // 목록을 만들기 위한 데이터가 Model에 담겼는지 확인한다.
                .andExpect(model().attributeExists("titlesAndPaths"))
                // 임시 문서 "Java"가 화면에 표시되는지 확인한다.
                .andExpect(content().string(containsString("Java")))
                // Java 문서로 이동하는 링크가 실제 HTML에 생성되는지 확인한다.
                .andExpect(content().string(containsString(
                        WikiPaths.GetPathOfSpecificDocumentDetail("Java")
                )));
    }

    @Test
    void documentDetailPage_putsPathVariableIntoModel() throws Exception {
        String documentTitle = "Java";

        // /wiki/doc/Java 요청을 만들 때도 WikiPaths의 메서드를 사용한다.
        mockMvc.perform(get(WikiPaths.GetPathOfSpecificDocumentDetail(documentTitle)))
                .andExpect(status().isOk())
                // WikiController가 templates/document_detail.html을 반환하는지 확인한다.
                .andExpect(view().name("document_detail"))
                // @PathVariable로 받은 제목이 Model의 documentTitle에 저장되는지 확인한다.
                .andExpect(model().attribute("documentTitle", documentTitle))
                // 해당 제목이 최종 HTML에도 보이는지 확인한다.
                .andExpect(content().string(containsString(documentTitle)));
    }
}
