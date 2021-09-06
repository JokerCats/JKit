package net.jkcat.core.item;

public interface ICustomView<S extends BaseCustomViewModel> {

    void setData(S data);

    void setIndex(int position);

}
