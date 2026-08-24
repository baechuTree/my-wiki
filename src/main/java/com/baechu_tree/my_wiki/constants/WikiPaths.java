package com.baechu_tree.my_wiki.constants;

public final class WikiPaths {

    private WikiPaths() {}

    public static final String PATH_DOCUMENT_DETAIL = "/wiki/doc/{documentTitle}";
    public static final String PATH_DOCUMENT_LIST = "/wiki/list";

    public static String GetPathOfSpecificDocumentDetail(String title) {
        return "/wiki/doc/" + title;
    }
}
