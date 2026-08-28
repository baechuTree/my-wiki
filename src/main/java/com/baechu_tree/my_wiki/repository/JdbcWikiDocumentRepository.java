package com.baechu_tree.my_wiki.repository;

import com.baechu_tree.my_wiki.domain.WikiDocument;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Jdbc로 DB CRUD를 하는 클래스
 * 코드에 대한 설명은 findById 메서드에 있음
 */
public class JdbcWikiDocumentRepository implements WikiDocumentRepository {

    private final DataSource dataSource;
    // DataSource: DB에 접근하기 위한 기본 정보를 가진 객체
    // 접근하는 DB에 대한 정보는, 앱이 실행될 때
    // Spring Boot가 application.properties에 적힌 DB 정보를 확인한 다음
    // DataSource를 Bean으로 만든 뒤 그 정보를 넣어준다

    public JdbcWikiDocumentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public WikiDocument save(WikiDocument document) {

        // 1
        String sql = "INSERT" +
                " INTO wiki_documents(document_title, content)" +
                " VALUES (?, ?)";

        try (
                // 2
                Connection connection = dataSource.getConnection();
                // 3
                PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ) {
            // 4
            pstmt.setString(1, document.getDocumentTitle());
            pstmt.setString(2, document.getContent());

            // 5
            pstmt.executeUpdate();
            try (
                    ResultSet rs = pstmt.getGeneratedKeys();
                    ) {
                if (rs.next()) {
                    // 6: 주석 처리 - 매개변수는 건들지 않고 7번에서 튜플을 직접 찾아 사용
//                    document.setDocumentId(rs.getInt(1));
                    
                    // 7. findById를 이용해 저장된 튜플을 다시 찾아 반환
                    Optional<WikiDocument> foundDocument = findById(rs.getInt(1));
                    if(foundDocument.isPresent()) return foundDocument.get();
                }
                
                // 7-1. findById로 저장된 튜플을 찾지 못했다면 매개변수 객체를 그대로 반환
                return document;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<WikiDocument> findById(int documentId) {

        // 1. sql문 작성
        // "?"는 나중에 값을 넣을 자리
        String sql =
                "SELECT * " +
                "FROM wiki_documents " +
                "WHERE document_id = ?";

        // sql문 오류가 나면 잡기 위한 try
        try (
                // 2. DB와의 연결 정보를 담은 객체(Connection) 획득
                Connection connection = dataSource.getConnection();
                // 3. DB에 전달할 sql 정보를 담은 객체(PreparedStatement) 생성
                PreparedStatement ps = connection.prepareStatement(sql)
                // try문의 소괄호: try-with-resource 문법
                // 변수를 만들어 객체(resource, 자원)를 할당하는 코드를 삽입할 수 있음
                // 여기서 선언된 객체는 try문이 끝나거나 catch로 넘어가면 자동으로 닫힘(close(), 즉 메모리에서 삭제됨)
                ) {
            // 4. 파라미터 설정
            ps.setInt(1, documentId);

            // 5. sql 실행 + 결과(ResultSet) 받아오기
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // 6. ResultSet을 Java Entity로 변환
                    WikiDocument document = new WikiDocument(
                            rs.getInt("document_id"),
                            rs.getString("document_title"),
                            rs.getString("content"),
                            rs.getObject(4, LocalDateTime.class),
                            rs.getObject(5, LocalDateTime.class)
                    );

                    // 7. Java 엔티티 반환
                    return Optional.of(document);
                }
                
                // 7-1. 결과가 비어있으면 비어있는 객체 반환
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<WikiDocument> findByTitle(String documentTitle) {
        return Optional.empty();
    }

    @Override
    public List<WikiDocument> findAll() {
        return List.of();
    }

    @Override
    public WikiDocument update(WikiDocument document) {
        return null;
    }

    @Override
    public WikiDocument updateTitle(String legacyTitle, String newTitle) {
        return null;
    }

    @Override
    public void deleteById(int documentId) {

    }

    @Override
    public void deleteByTitle(String documentTitle) {

    }
}
