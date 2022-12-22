package com.example.drivinglicenseupload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idcarduploadapp.R;

import java.util.LinkedList;

public class DrivingLicenseListAdapter extends RecyclerView.Adapter {

    private final LinkedList<RelativeLayout> mIdCardList;

    public DrivingLicenseListAdapter(Context context, LinkedList<RelativeLayout> mIdCardList, LayoutInflater mInflater) {
        mInflater = LayoutInflater.from(context);
        this.mIdCardList = mIdCardList;
    }

    class IdCardListViewHolder extends RecyclerView.ViewHolder {

        public IdCardListViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_driving_license_list,parent,false);
        return new IdCardListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mIdCardList.size();
    }
}



