package com.baechu_tree.my_wiki.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity @Table(name = "wiki_documents")
public class WikiDocument {

    @Id @GeneratedValue @Column(name = "document_id") @Nullable
    private Integer documentId;
    @Column(name = "document_title", nullable = false, length = 255, unique = true)
    private String documentTitle;
    @Lob @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;
    @Column(name = "created_at", nullable = false) @Nullable
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false) @Nullable
    private LocalDateTime updatedAt;

    public WikiDocument(Integer documentId, String documentTitle, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.documentId = documentId;
        this.documentTitle = documentTitle;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
