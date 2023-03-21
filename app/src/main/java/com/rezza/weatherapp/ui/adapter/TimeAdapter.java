package com.rezza.weatherapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rezza.weatherapp.R;
import com.rezza.weatherapp.libs.Utility;
import com.rezza.weatherapp.ui.model.TimeHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder>{

    private Context context;
    ArrayList<TimeHolder> listTime = new ArrayList<>();

    public TimeAdapter(ArrayList<TimeHolder> listTime){
        this.listTime = listTime;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpter_time,parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeHolder data = listTime.get(position);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, data.getTime());
        calendar.set(Calendar.MINUTE, 0);
        holder.tv_time.setText(Utility.getDateString("HH:mm", calendar.getTime()));
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String degree = decimalFormat.format(data.getTemperature()) +" "+context.getResources().getString(R.string.degree);
        holder.tv_degree.setText(degree);

        holder.tv_status.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return listTime.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_time,tv_status,tv_degree;

        public ViewHolder(@NonNull View view) {
            super(view);

            tv_time = view.findViewById(R.id.tv_time);
            tv_status = view.findViewById(R.id.tv_status);
            tv_degree = view.findViewById(R.id.tv_degree);
        }
    }
}
