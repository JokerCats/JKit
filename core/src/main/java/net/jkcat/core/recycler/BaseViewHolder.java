package net.jkcat.core.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.jkcat.core.item.BaseCustomViewModel;
import net.jkcat.core.item.ICustomView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public ICustomView view;

    public BaseViewHolder(ICustomView view) {
        super((View) view);
        this.view = view;
    }

    public void bind(@NonNull BaseCustomViewModel item) {
        bind(item, -1);
    }

    public void bind(@NonNull BaseCustomViewModel item, int position) {
        view.setData(item);
        view.setIndex(position);
    }

}
