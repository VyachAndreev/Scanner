package com.andreev.scanner.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andreev.scanner.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public interface IListener {
        void onItemClicked(int position);
    }

    protected TextView textView;
    protected IListener listener;


    public ItemViewHolder(View itemView, final IListener listener){
        super(itemView);
        this.listener = listener;
        textView = itemView.findViewById(R.id.name);

        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(getAdapterPosition());
            }
        };

        itemView.setOnClickListener(clickListener);
    }
}