package com.andreev.scanner.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andreev.scanner.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public interface IListener {
        void onItemClicked(int position);
    }

    protected TextView typeTV;
    protected TextView markTV;
    protected TextView diameterTV;
    protected TextView packingTV;
    protected TextView partyTV;
    protected TextView meltingTV;
    protected TextView massTV;
    protected TextView statusTV;
    protected IListener listener;


    public ItemViewHolder(View itemView, final IListener listener){
        super(itemView);
        this.listener = listener;
        typeTV = itemView.findViewById(R.id.type);
        markTV = itemView.findViewById(R.id.mark);
        diameterTV = itemView.findViewById(R.id.diameter);
        packingTV = itemView.findViewById(R.id.packing);
        partyTV = itemView.findViewById(R.id.party);
        meltingTV = itemView.findViewById(R.id.melting);
        massTV = itemView.findViewById(R.id.mass);
        statusTV = itemView.findViewById(R.id.status);

        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(getAdapterPosition());
            }
        };

        itemView.setOnClickListener(clickListener);
    }
}