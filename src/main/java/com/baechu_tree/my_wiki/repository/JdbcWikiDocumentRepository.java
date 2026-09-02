package com.baechu_tree.my_wiki.repository;

import com.baechu_tree.my_wiki.domain.WikiDocument;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
                    // 6. 주석 처리 - 매개변수로 들어온 객체는 건들지 않고, 7번에서 튜플을 직접 찾아 사용
//                    document.setDocumentId(rs.getInt(1));

                    // 7. findById를 이용해 저장된 튜플을 다시 찾아 반환
                    Optional<WikiDocument> foundDocument = findById(rs.getInt(1));
                    if (foundDocument.isPresent()) return foundDocument.get();
                    // 7-1. findById가 저장된 문서를 찾지 못했다면, 예외 발생
                    else throw new SQLException("findById가 저장된 문서를 찾지 못함. 저장은 성공했을 수 있음");
                } else {
                    // 7-2. ResultSet이 id값을 받지 못했다면, 예외 발생
                    throw new SQLException("저장된 문서의 id값 조회 실패. 저장이 실패했을 수 있음");
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
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
                            rs.getObject("created_at", LocalDateTime.class),
                            rs.getObject("updated_at", LocalDateTime.class)
                    );

                    // 7. Java 엔티티 반환
                    return Optional.of(document);
                }

                // 7-1. 결과가 비어있으면 비어있는 객체 반환
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<WikiDocument> findByTitle(String documentTitle) {

        // 1
        String sql = "SELECT * " +
                "FROM wiki_documents " +
                "WHERE document_title = ?";

        try (
                // 2
                Connection connection = dataSource.getConnection();
                // 3
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            // 4
            pstmt.setString(1, documentTitle);

            try (
                    // 5
                    ResultSet rs = pstmt.executeQuery()
            ) {
                if (rs.next()) {
                    // 6
                    WikiDocument document = new WikiDocument(
                            rs.getInt("document_id"),
                            rs.getString("document_title"),
                            rs.getString("content"),
                            rs.getObject("created_at", LocalDateTime.class),
                            rs.getObject("updated_at", LocalDateTime.class)
                    );

                    // 7
                    return Optional.of(document);
                }

                // 7-1
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<WikiDocument> findAll() {

        String sql = "SELECT * " +
                "FROM wiki_documents";

        List<WikiDocument> documents = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                WikiDocument document = new WikiDocument(
                        rs.getInt("document_id"),
                        rs.getString("document_title"),
                        rs.getString("content"),
                        rs.getObject("created_by", LocalDateTime.class),
                        rs.getObject("updated_by", LocalDateTime.class)
                );

                documents.add(document);
            }

            return documents;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public WikiDocument update(WikiDocument document) {

        if (document.getDocumentId() == null) {
            throw new IllegalStateException("수정하려는 문서의 id값을 매개변수로 받지 못함");
        }
        
        // 업데이트하려는 문서 정보의 title과 DB에 저장된 문서의 title이 같은지 확인
        Optional<WikiDocument> originalDocument = findById(document.getDocumentId());
        if (originalDocument.isPresent()) {
            if (!originalDocument.get().getDocumentTitle().equals(document.getDocumentTitle())) {
                throw new IllegalStateException("수정하려는 문서의 title과 DB에 저장된 문서의 title이 같지 않음");
            }
        }

        String sql = "UPDATE wiki_documents " +
                "SET " +
                "content = ? " +
                "WHERE document_id = ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setString(1, document.getContent());
            pstmt.setInt(2, document.getDocumentId());

            int result = pstmt.executeUpdate();

            if (result > 1) {
                throw new IllegalStateException("2개 이상의 문서가 수정됨!");
            } else if (result < 1) {
                throw new IllegalStateException("문서가 하나도 수정되지 않음!");
            }

            Optional<WikiDocument> updatedDocument = findById(document.getDocumentId());
            if (updatedDocument.isPresent()) return updatedDocument.get();
            throw new IllegalStateException("findById가 수정된 문서를 찾지 못함. 수정은 성공했을 수 있음");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public WikiDocument updateTitle(int documentId, String newTitle) {

        String sql = "UPDATE wiki_documents " +
                "SET " +
                "document_title = ? " +
                "WHERE document_id = ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, documentId);

            int result = pstmt.executeUpdate();

            if (result > 1) {
                throw new IllegalStateException("2개 이상의 문서가 수정됨!");
            } else if (result < 1) {
                throw new IllegalStateException("문서가 하나도 수정되지 않음!");
            }

            Optional<WikiDocument> updatedDocument = findById(documentId);
            if (updatedDocument.isPresent()) return updatedDocument.get();
            throw new IllegalStateException("findById가 수정된 문서를 찾지 못함. 수정은 성공했을 수 있음");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteById(int documentId) {

        String sql = "DELETE FROM wiki_documents " +
                "WHERE document_id = ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setInt(1, documentId);

            int result = pstmt.executeUpdate();

            if (result > 1) throw new IllegalStateException("2개 이상의 문서가 삭제됨!");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteByTitle(String documentTitle) {

        String sql = "DELETE FROM wiki_documents " +
                "WHERE document_title = ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setString(1, documentTitle);

            int result = pstmt.executeUpdate();

            if (result > 1) throw new IllegalStateException("2개 이상의 문서가 삭제됨!");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
