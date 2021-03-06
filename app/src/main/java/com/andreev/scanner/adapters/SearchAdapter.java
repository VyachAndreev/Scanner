package com.andreev.scanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andreev.scanner.R;
import com.andreev.scanner.classes.GetPositionView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<ItemViewHolder> {


    protected final ItemViewHolder.IListener mIListener;
    protected List<GetPositionView> data;

    public SearchAdapter(List <GetPositionView> data, ItemViewHolder.IListener listener){
        this.data = data;
        mIListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View layout = inflater.inflate(R.layout.item_searched, parent, false);

        return new ItemViewHolder(layout, mIListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        GetPositionView getPositionView = data.get(position);
        holder.markTV.setText("Марка: " + getPositionView.getMark());
        holder.diameterTV.setText("Диаметр: " +  getPositionView.getDiameter());
        holder.packingTV.setText("Упаковка: " + getPositionView.getPacking());
        holder.partyTV.setText("Партия: " + getPositionView.getPart());
        holder.meltingTV.setText("Плавка: " + getPositionView.getPlav());
        holder.massTV.setText("Вес: " + getPositionView.getMass().toString());
        String a;
        if (getPositionView.getStatus() != null) {
            switch (getPositionView.getStatus()) {
                case "Arriving":
                    a = "Прибывает";
                    break;
                case "In_stock":
                    a = "На складе";
                    break;
                case "Departured":
                    a = "Отгружен";
                    break;
                default:
                    a = "Местоположение неизвестно";
                    break;
            }
            holder.statusTV.setText("Статус: " + a);
        } else {
            holder.statusTV.setText("");
        }
        if (getPositionView.getType() != null) {
            switch (getPositionView.getType()) {
                case "POSITION":
                    a = "Позиция";
                    break;
                case "PACKAGE":
                    a = "Поддон";
                    break;
                default:
                    a = "Статуc неизвестен";
                    break;

            }
            holder.typeTV.setText(a);
        } else {
            holder.typeTV.setText("Позиция");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
