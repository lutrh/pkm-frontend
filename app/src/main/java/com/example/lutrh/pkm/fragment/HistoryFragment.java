package com.example.lutrh.pkm.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lutrh.pkm.R;
import com.example.lutrh.pkm.adapter.HistoryAdapter;
import com.example.lutrh.pkm.helper.DatabaseHelper;
import com.example.lutrh.pkm.model.History;

import java.util.List;

public class HistoryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private DatabaseHelper db;
    private RecyclerView recyclerHistory;
    private RecyclerView.Adapter adapter;
    private List<History> mHistory;
    private TextView textHistory, textDeskripsi;
    private RelativeLayout relativeNoFound;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerHistory = (RecyclerView) view.findViewById(R.id.recycle_history);
        recyclerHistory.setHasFixedSize(true);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        textHistory = (TextView) view.findViewById(R.id.text_history);
        textDeskripsi = (TextView) view.findViewById(R.id.text_deskripsi);
        relativeNoFound = (RelativeLayout) view.findViewById(R.id.relative_no_found);

        mHistory = db.getAllHistory();
        adapter = new HistoryAdapter(mHistory, getActivity());
        recyclerHistory.setAdapter(adapter);

        if (mHistory.size() == 0) {
            textHistory.setVisibility(View.GONE);
            textDeskripsi.setText("Oops, you don't have any history yet");
            relativeNoFound.setVisibility(View.VISIBLE);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
