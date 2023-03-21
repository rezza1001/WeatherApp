package com.rezza.weatherapp.ui.adapter;

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

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>{

    private Context context;
    private String key;
    ArrayList<String> listCity;

    public CityAdapter(ArrayList<String> listCity){
        this.listCity = listCity;
    }

    public void setKey(String key){
        this.key = key;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpter_find_city,parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String data = listCity.get(position);
        int start   = data.toUpperCase().indexOf(key.toUpperCase());
        int end     = start+ (key.length());
        holder.tv_name.setText(Utility.BoldText(context, data,start,end,"#F79E46"));
        holder.rv_select.setOnClickListener(view -> {
            if (onSelectedListener != null){
                onSelectedListener.onSelected(data);
            }
        });

        FavoriteDB db = new FavoriteDB();
        db.getData(context,data);
        boolean favorite = !db.name.isEmpty();
        if (favorite){
            holder.iv_favorite.setColorFilter(Color.parseColor("#FEBA00"));
        }
        else {
            holder.iv_favorite.setColorFilter(Color.parseColor("#D5D5D5"));
        }

        holder.rv_favorite.setOnClickListener(view -> {
            if (favorite){
                db.delete(context, data);
            }
            else {
                db.name = data;
                db.insert(context);
            }
            notifyItemChanged(position);
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
        void onFavorite(String city);
    }
}
