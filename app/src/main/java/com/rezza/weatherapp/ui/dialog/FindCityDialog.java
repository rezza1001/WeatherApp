package com.rezza.weatherapp.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rezza.weatherapp.R;
import com.rezza.weatherapp.libs.Utility;
import com.rezza.weatherapp.ui.adapter.CityAdapter;
import com.rezza.weatherapp.ui.master.MyDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class FindCityDialog extends MyDialog {

    protected EditText et_search;
    protected ImageView iv_clear;
    private RecyclerView rc_data;

    ArrayList<String> filterCity;
    String[] listCity;

    private CityAdapter adapter;

    public FindCityDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_find_city;
    }

    @Override
    protected void initLayout(View view) {
        view.findViewById(R.id.rv_back).setOnClickListener(view1 -> dismiss());
        et_search = view.findViewById(R.id.et_search);
        iv_clear = view.findViewById(R.id.iv_clear);
        iv_clear.setVisibility(View.INVISIBLE);
        rc_data = view.findViewById(R.id.rc_data);
        rc_data.setLayoutManager(new LinearLayoutManager(mActivity));

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.isEmpty() && iv_clear.getVisibility() == View.VISIBLE){
                    iv_clear.setVisibility(View.INVISIBLE);
                }
                else if (!text.isEmpty()  && iv_clear.getVisibility() == View.INVISIBLE){
                    iv_clear.setVisibility(View.VISIBLE);
                }
                find(text);
            }
        });

        iv_clear.setOnClickListener(view1 -> et_search.setText(null));
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    @Override
    public void show() {
        super.show();

        filterCity = new ArrayList<>();
        adapter = new CityAdapter(filterCity);
        rc_data.setAdapter(adapter);
        loadData();

        find("");
        adapter.setOnSelectedListener(new CityAdapter.OnSelectedListener() {
            @Override
            public void onSelected(String city) {
                if (onSelectedListener != null){
                    onSelectedListener.onSelect(city);
                }
                dismiss();
            }

            @Override
            public void onFavorite(String city) {

            }
        });
    }

    private void loadData(){
        String allCity = Utility.readFileFromAsset(mActivity,"city.txt");
        listCity = allCity.split(",");

    }

    @SuppressLint("NotifyDataSetChanged")
    private void find(String text){
        adapter.setKey(text);
        filterCity.clear();

        if (text.isEmpty()){
            filterCity.addAll(Arrays.asList(listCity));
        }
        else {
            for (String s: listCity){
                if (s.toLowerCase().contains(text.toLowerCase())){
                    filterCity.add(s);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private OnSelectedListener onSelectedListener;
    public void setOnSelectedListener(OnSelectedListener onSelectedListener){
        this.onSelectedListener = onSelectedListener;
    }
    public interface OnSelectedListener{
        void onSelect(String city);
    }
}
