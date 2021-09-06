package net.jkcat.core.vm;

import net.jkcat.core.model.PagingResult;

public interface IBaseModelListener<R> {

    void onLoadSuccess(R response, PagingResult... pageInfo);

    void onLoadFailure(String prompt, PagingResult... pageInfo);

}
