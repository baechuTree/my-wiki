package com.baechu_tree.my_wiki.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WikiResponse {

    private int documentId;
    private String documentTitle;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
