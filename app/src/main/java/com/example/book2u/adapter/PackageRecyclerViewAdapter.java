package com.example.book2u.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book2u.Package;
import com.example.book2u.PackageDetailActivity;
import com.example.book2u.R;

import java.util.List;

public class PackageRecyclerViewAdapter extends RecyclerView.Adapter<PackageRecyclerViewAdapter.PackageViewHolder> {

    public List<Package> packageList;
    private Context context;

    public PackageRecyclerViewAdapter(Context context, List<Package> packageList) {
        this.context = context;
        this.packageList = packageList;
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View package_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row,null);

        PackageViewHolder packageVH = new PackageViewHolder(package_row);

        return packageVH;
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {

        holder.tvPackageName.setText(packageList.get(position).getName());
        holder.imgViewPackageImage.setImageResource(packageList.get(position).getImage());

    }

    @Override
    public int getItemCount() {

        return packageList.size();
    }

    public class PackageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvPackageName;
        public ImageView imgViewPackageImage;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPackageName= itemView.findViewById(R.id.tv_package_name);
            imgViewPackageImage = itemView.findViewById(R.id.img_package);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(),"Package Name: " + packageList.get(getAdapterPosition()).getName(),Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(view.getContext(), PackageDetailActivity.class);
            intent.putExtra("packageName",packageList.get(getAdapterPosition()).getName());
            //  intent.putExtra("packageImg",packageList.get(getAdapterPosition()),getImage());

            view.getContext().startActivity(intent);
        }
    }
}

