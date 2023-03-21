package com.rezza.weatherapp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rezza.weatherapp.R;
import com.rezza.weatherapp.database.table.FavoriteDB;
import com.rezza.weatherapp.libs.Utility;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    private Context context;
    ArrayList<String> listCity;

    public FavoriteAdapter(ArrayList<String> listCity){
        this.listCity = listCity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpter_favorite,parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String data = listCity.get(position);
        holder.tv_name.setText(data);
        holder.rv_select.setOnClickListener(view -> {
            if (onSelectedListener != null){
                onSelectedListener.onSelected(data);
            }
        });


        holder.rv_favorite.setOnClickListener(view -> {
            FavoriteDB db = new FavoriteDB();
            db.delete(context, data);
            listCity.remove(position);
            notifyDataSetChanged();
            if (onSelectedListener != null){
                onSelectedListener.onDeleted(data, listCity.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCity.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        RelativeLayout rv_select,rv_favorite;
        ImageView iv_favorite;

        public ViewHolder(@NonNull View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            rv_select = view.findViewById(R.id.rv_select);
            rv_favorite = view.findViewById(R.id.rv_favorite);
            iv_favorite = view.findViewById(R.id.iv_favorite);
        }
    }

    private OnSelectedListener onSelectedListener;
    public void setOnSelectedListener(OnSelectedListener onSelectedListener){
        this.onSelectedListener = onSelectedListener;
    }
    public interface OnSelectedListener{
        void onSelected(String city);
        void onDeleted(String city, int qty);
    }
}
