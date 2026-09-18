package com.baechu_tree.my_wiki.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WikiCreateRequest {

    private String documentTitle;
    private String content;
}
