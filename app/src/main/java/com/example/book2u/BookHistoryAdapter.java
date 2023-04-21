package com.example.book2u;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookHistoryAdapter extends RecyclerView.Adapter<BookHistoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<BookCarWash> list;

    public BookHistoryAdapter(Context context, ArrayList<BookCarWash> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.datalist,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BookCarWash bookCarWash = list.get(position);
        holder.carModel.setText(bookCarWash.getCarModel());
        holder.carPlate.setText(bookCarWash.getCarPlate());
        holder.carType.setText(bookCarWash.getCarType());
        holder.nickname.setText(bookCarWash.getNickname());
        holder.services.setText(bookCarWash.getServices());
        holder.time.setText(bookCarWash.getTimeSlot());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nickname,services,carPlate,carType,carModel,time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            carModel = itemView.findViewById(R.id.tv_Carmodel);
            carPlate = itemView.findViewById(R.id.tv_PlateNum);
            carType = itemView.findViewById(R.id.tv_Cartype);
            nickname = itemView.findViewById(R.id.tv_nickname);
            services = itemView.findViewById(R.id.tv_Services);
            time = itemView.findViewById(R.id.tv_timeBook);
        }
    }
}