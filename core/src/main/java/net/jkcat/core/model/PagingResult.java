package net.jkcat.core.model;

public class PagingResult {

    public boolean isEmpty;
    public boolean isFirstPage;

    public PagingResult(boolean isEmpty, boolean isFirstPage) {
        this.isEmpty = isEmpty;
        this.isFirstPage = isFirstPage;
    }

}
