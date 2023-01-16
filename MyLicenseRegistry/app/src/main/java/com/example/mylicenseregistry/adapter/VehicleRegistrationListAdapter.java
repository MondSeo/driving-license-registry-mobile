package com.example.mylicenseregistry.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.mylicenseregistry.R;
import com.example.mylicenseregistry.datadto.VehicleRegistrationImage;
import com.example.mylicenseregistry.datadto.VehicleRegistrationImage;
import com.example.mylicenseregistry.datadto.VehicleRegistrationImage;
import com.example.mylicenseregistry.util.Util;

import java.util.ArrayList;

public class VehicleRegistrationListAdapter extends RecyclerView.Adapter<VehicleRegistrationListAdapter.ViewHolder>{
    private Context mContext;
    private VehicleRegistrationListAdapter.OnItemClickListener mListener = null;
    private ArrayList<VehicleRegistrationImage> mDataList;

    public VehicleRegistrationListAdapter(Context context, ArrayList<VehicleRegistrationImage> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView vehicleRegistrationFrontImage;
        ImageView vehicleRegistrationBackImage;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            vehicleRegistrationFrontImage = itemView.findViewById(R.id.img_VehicleRegistrationImage_Front_RecyclerViewItem);
            vehicleRegistrationBackImage = itemView.findViewById(R.id.img_VehicleRegistrationImage_Back_RecyclerViewItem);
        }
    }

    @NonNull
    @Override
    public VehicleRegistrationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_vehicle_registration_list, parent, false);
        VehicleRegistrationListAdapter.ViewHolder viewHolder = new VehicleRegistrationListAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleRegistrationListAdapter.ViewHolder holder, int position) {
        final VehicleRegistrationImage item = mDataList.get(position);
        String frontImage = item.getFrontVehicleRegistrationBitmap();
        Bitmap VehicleRegistrationFrontImage = Util.getBitmapFromByteArray(Util.base64Decode(frontImage));
        String backImage = item.getBackVehicleRegistrationBitmap();
        Bitmap VehicleRegistrationBackImage = Util.getBitmapFromByteArray(Util.base64Decode(backImage));

        if (Util.isValidContextForGlide(mContext)) {
            Glide.with(mContext).load(VehicleRegistrationFrontImage)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .transform(new CenterCrop(), new RoundedCorners(36))
                    .skipMemoryCache(true)
                    .into(holder.vehicleRegistrationFrontImage);

            Glide.with(mContext).load(VehicleRegistrationBackImage)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .transform(new CenterCrop(), new RoundedCorners(36))
                    .skipMemoryCache(true)
                    .into(holder.vehicleRegistrationBackImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position);

                }
            }
        });
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

//    public void addImageItem() {
//        mDataList.add()
//    }

    public void deleteItem(VehicleRegistrationImage info, int realPosition) {
        mDataList.remove(info);
        notifyItemRemoved(realPosition);
    }

    public ArrayList<VehicleRegistrationImage> getDataList() {
        return mDataList;
    }


    public void setOnItemClickListener(VehicleRegistrationListAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
