package com.example.idcarduploadapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

public class IdCardListAdapter extends RecyclerView.Adapter {

    private final LinkedList<String> mIdCardList;
    private final LayoutInflater mInflater;

    public IdCardListAdapter(LinkedList<String> mIdCardList, LayoutInflater mInflater) {
        this.mIdCardList = mIdCardList;
        this.mInflater = mInflater;
    }

    class IdCardListViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout frontIdCardRelativeLayout;
        RelativeLayout backIdCardRelativeLayout;


        public IdCardListViewHolder(@NonNull View itemView) {
            super(itemView);
            frontIdCardRelativeLayout.findViewById(R.id.rel_Registry_Id_Card_Front);
            backIdCardRelativeLayout.findViewById(R.id.rel_Registry_Id_Card_Back);
            frontIdCardRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            backIdCardRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_id_card_list,parent,false);
        return new IdCardListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}



