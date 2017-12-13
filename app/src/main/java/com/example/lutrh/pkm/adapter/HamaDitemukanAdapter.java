package com.example.lutrh.pkm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lutrh.pkm.R;
import com.example.lutrh.pkm.helper.DatabaseHelper;
import com.example.lutrh.pkm.layout.DetailHamaActivity;
import com.example.lutrh.pkm.model.Hama;

import java.util.List;

/**
 * Created by lutrh on 10/13/17.
 */

public class HamaDitemukanAdapter extends RecyclerView.Adapter<HamaDitemukanAdapter.ViewHolder> {

    private List<Hama> mHama;
    private Context mContext;
    private String ditemukan;

    public HamaDitemukanAdapter(List<Hama> mHama, Context mContext, String ditemukan) {
        this.mHama = mHama;
        this.mContext = mContext;
        this.ditemukan = ditemukan;
    }

    @Override
    public HamaDitemukanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HamaDitemukanAdapter.ViewHolder holder, int position) {
        final Hama hama = mHama.get(position);
        holder.textNama.setText(hama.getNama().substring(0, 1).toUpperCase() + hama.getNama().substring(1));
        holder.textNamaLatin.setText(hama.getNamaLatin());
        String tempat = "";
        switch (hama.getDitemukan()) {
            case "leaves":
                tempat = "daun";
                break;
            case "stem":
                tempat = "batang";
                break;
            case "water":
                tempat = "air";
                break;
        }
        holder.textDitemukan.setText("Terdapat di " + tempat);

        switch (hama.getNama()) {
            case "wereng":
                holder.imageHama.setImageResource(R.drawable.ig_wereng);
                break;
            case "belalang":
                holder.imageHama.setImageResource(R.drawable.ig_belalang);
                break;
            case "tikus sawah":
                holder.imageHama.setImageResource(R.drawable.ig_tikus);
                break;
            case "walang sangit":
                holder.imageHama.setImageResource(R.drawable.ig_walang_sangit);
                break;
        }

        switch (hama.getDitemukan()) {
            case "leaves":
                holder.imageDitemukan.setImageResource(R.drawable.ic_leafs);
                break;
            case "log":
                holder.imageDitemukan.setImageResource(R.drawable.ic_log);
                break;
            case "water":
                holder.imageDitemukan.setImageResource(R.drawable.ic_water);
                break;
        }

        holder.itemHama.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailHamaActivity.class);
                intent.putExtra("nama_hama", hama.getNama());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mHama.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textNama, textNamaLatin, textDitemukan;
        public ImageView imageHama, imageDitemukan;
        public LinearLayout itemHama;

        public ViewHolder(View itemView) {
            super(itemView);

            textNama = (TextView) itemView.findViewById(R.id.text_nama_hama);
            textNamaLatin = (TextView) itemView.findViewById(R.id.text_nama_latin);
            textDitemukan = (TextView) itemView.findViewById(R.id.text_ditemukan);
            imageHama = (ImageView) itemView.findViewById(R.id.image_hama);
            imageDitemukan = (ImageView) itemView.findViewById(R.id.image_ditemukan);
            itemHama = (LinearLayout) itemView.findViewById(R.id.item_history);
        }
    }
}
