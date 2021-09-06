package net.jkcat.jkit.java.list;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.jkcat.core.recycler.BaseViewHolder;

import java.util.List;

public class ActionListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<ActionListResult.ActionEntity.ActionBean> mItems;

    public void setData(List<ActionListResult.ActionEntity.ActionBean> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(new ActionListView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(mItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mItems != null && mItems.size() > 0) {
            return mItems.size();
        }
        return 0;
    }

}
