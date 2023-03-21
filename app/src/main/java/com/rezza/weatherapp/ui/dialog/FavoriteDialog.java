package com.rezza.weatherapp.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rezza.weatherapp.R;
import com.rezza.weatherapp.database.table.FavoriteDB;
import com.rezza.weatherapp.ui.adapter.FavoriteAdapter;
import com.rezza.weatherapp.ui.master.MyDialog;

import java.util.ArrayList;

public class FavoriteDialog extends MyDialog {

    RecyclerView rc_data;
    TextView tv_notFound;

    ArrayList<String> favorite;
    FavoriteAdapter adapter;

    public FavoriteDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_favorite;
    }

    @Override
    protected void initLayout(View view) {
        view.findViewById(R.id.iv_close).setOnClickListener(view1 -> dismiss());

        rc_data = view.findViewById(R.id.rc_data);
        rc_data.setLayoutManager(new LinearLayoutManager(mActivity));

        tv_notFound = view.findViewById(R.id.tv_notFound);
        tv_notFound.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }


    @Override
    public void show() {
        super.show();

        favorite = new ArrayList<>();
        adapter = new FavoriteAdapter(favorite);
        rc_data.setAdapter(adapter);
        loadData();

        adapter.setOnSelectedListener(new FavoriteAdapter.OnSelectedListener() {
            @Override
            public void onSelected(String city) {
                if (onSelectedListener != null){
                    onSelectedListener.onSelect(city);
                    dismiss();
                }
            }

            @Override
            public void onDeleted(String city, int qty) {
                if (qty == 0){
                    loadData();
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData(){
        favorite.clear();
        FavoriteDB db = new FavoriteDB();
        ArrayList<FavoriteDB> list = db.getALl(mActivity);
        for (FavoriteDB favoriteDB : list){
            favorite.add(favoriteDB.name);
        }

        adapter.notifyDataSetChanged();
        if (favorite.isEmpty()){
            tv_notFound.setVisibility(View.VISIBLE);
        }
        else{
            tv_notFound.setVisibility(View.GONE);
        }
    }

    private OnSelectedListener onSelectedListener;
    public void setOnSelectedListener(OnSelectedListener onSelectedListener){
        this.onSelectedListener = onSelectedListener;
    }
    public interface OnSelectedListener{
        void onSelect(String city);
    }
}
