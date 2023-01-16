package com.example.mylicenseregistry.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.mylicenseregistry.R;
import com.example.mylicenseregistry.datadto.DrivingLicenseImage;
import com.example.mylicenseregistry.util.Util;

import java.util.ArrayList;

public class DrivingLicenseListAdapter extends RecyclerView.Adapter<DrivingLicenseListAdapter.ViewHolder> {
    private Context mContext;
    private OnItemClickListener mListener = null;
    private ArrayList<DrivingLicenseImage> mDataList;

    public DrivingLicenseListAdapter(Context context, ArrayList<DrivingLicenseImage> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView drivingLicenseFrontImage;
        ImageView drivingLicenseBackImage;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            drivingLicenseFrontImage = itemView.findViewById(R.id.img_DrivingLicenseImage_Front_RecyclerViewItem);
            drivingLicenseBackImage = itemView.findViewById(R.id.img_DrivingLicenseImage_Back_RecyclerViewItem);
        }
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public DrivingLicenseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_driving_license_list, parent, false);
        DrivingLicenseListAdapter.ViewHolder viewHolder = new DrivingLicenseListAdapter.ViewHolder(view);

        return viewHolder;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(DrivingLicenseListAdapter.ViewHolder holder, int position) {
        final DrivingLicenseImage item = mDataList.get(position);
        String frontImage = item.getFrontDrivingLicenseBitmap();
        Bitmap drivingLicenseFrontImage = Util.getBitmapFromByteArray(Util.base64Decode(frontImage));
        String backImage = item.getBackDrivingLicenseBitmap();
        Bitmap drivingLicenseBackImage = Util.getBitmapFromByteArray(Util.base64Decode(backImage));

        if (Util.isValidContextForGlide(mContext)) {
            Glide.with(mContext).load(drivingLicenseFrontImage)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .transform(new CenterCrop(), new RoundedCorners(36))
                    .skipMemoryCache(true)
                    .into(holder.drivingLicenseFrontImage);

            Glide.with(mContext).load(drivingLicenseBackImage)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .transform(new CenterCrop(), new RoundedCorners(36))
                    .skipMemoryCache(true)
                    .into(holder.drivingLicenseBackImage);
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

    public void deleteItem(DrivingLicenseImage info, int realPosition) {
        mDataList.remove(info);
        notifyItemRemoved(realPosition);
    }

    public ArrayList<DrivingLicenseImage> getDataList() {
        return mDataList;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}



