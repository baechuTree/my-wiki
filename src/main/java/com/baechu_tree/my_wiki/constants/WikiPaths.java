package com.baechu_tree.my_wiki.constants;

public final class WikiPaths {

    private WikiPaths() {}

    public static final String PATH_DOCUMENT_DETAIL_PAGE = "/wiki/doc/{documentTitle}";
    public static final String PATH_DOCUMENT_LIST_PAGE = "/wiki/list";
    public static final String PATH_DOCUMENT_SAVE_PAGE = "/wiki/save_page";
    public static final String PATH_DOCUMENT_SAVE = "/wiki/save";
    public static final String PATH_DOCUMENT_UPDATE_PAGE = "/wiki/update_page";
    public static final String PATH_DOCUMENT_UPDATE = "/wiki/update";
    public static final String PATH_DOCUMENT_DELETE = "/wiki/delete";

    public static String GetPathOfSpecificDocumentDetail(String title) {
        return "/wiki/doc/" + title;
    }
}
