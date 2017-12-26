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
import com.example.lutrh.pkm.model.History;

import java.util.List;

/**
 * Created by lutrh on 10/13/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> mHistory;
    private Context mContext;

    public HistoryAdapter(List<History> mHistory, Context mContext) {
        this.mHistory = mHistory;
        this.mContext = mContext;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        final History history = mHistory.get(position);
        final DatabaseHelper db = new DatabaseHelper(mContext);

        String tempat = "";
        switch (db.getHama(history.getHama()).getDitemukan()) {
            case "leaves": tempat = "daun"; break;
            case "log": tempat = "batang"; break;
            case "water": tempat = "air"; break;
        }

        holder.textNama.setText(db.getHama(history.getHama()).getNama().substring(0, 1).toUpperCase() + db.getHama(history.getHama()).getNama().substring(1));
        holder.textNamaLatin.setText(db.getHama(history.getHama()).getNamaLatin());
        holder.textDitemukan.setText("Ditemukan di " + tempat);

        switch (db.getHama(history.getHama()).getNama()) {
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

        switch (db.getHama(history.getHama()).getDitemukan()) {
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

        holder.itemHistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailHamaActivity.class);
                intent.putExtra("nama_hama", db.getHama(history.getHama()).getNama());
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
        return mHistory.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textNama, textNamaLatin, textDitemukan;
        public ImageView imageHama, imageDitemukan;
        public LinearLayout itemHistory;

        public ViewHolder(View itemView) {
            super(itemView);

            textNama = (TextView) itemView.findViewById(R.id.text_nama_hama);
            textNamaLatin = (TextView) itemView.findViewById(R.id.text_nama_latin);
            textDitemukan = (TextView) itemView.findViewById(R.id.text_ditemukan);
            imageHama = (ImageView) itemView.findViewById(R.id.image_hama);
            imageDitemukan = (ImageView) itemView.findViewById(R.id.image_ditemukan);
            itemHistory = (LinearLayout) itemView.findViewById(R.id.item_history);
        }
    }
}
