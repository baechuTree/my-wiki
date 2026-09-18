package com.baechu_tree.my_wiki.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity @Table(name = "wiki_documents")
@Getter @Setter @AllArgsConstructor
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
}
