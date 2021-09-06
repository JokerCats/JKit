package net.jkcat.core.vm;

import net.jkcat.core.model.BaseModel;
import net.jkcat.core.model.PagingResult;

public interface IBaseModelListener<T> {

    void onLoadSuccess(BaseModel<?, T> model, T data, PagingResult... pageInfo);

    void onLoadFailure(BaseModel<?, T> model, String prompt, PagingResult... pageInfo);

}
