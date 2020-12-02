package com.andreev.scanner.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andreev.scanner.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    protected TextView typeTV;
    protected TextView markTV;
    protected TextView diameterTV;
    protected TextView packingTV;
    protected TextView partyTV;
    protected TextView meltingTV;
    protected TextView massTV;
    protected TextView statusTV;


    public ItemViewHolder(View itemView){
        super(itemView);
        typeTV = itemView.findViewById(R.id.type);
        markTV = itemView.findViewById(R.id.mark);
        diameterTV = itemView.findViewById(R.id.diameter);
        packingTV = itemView.findViewById(R.id.packing);
        partyTV = itemView.findViewById(R.id.party);
        meltingTV = itemView.findViewById(R.id.melting);
        massTV = itemView.findViewById(R.id.mass);
        statusTV = itemView.findViewById(R.id.status);
    }
}