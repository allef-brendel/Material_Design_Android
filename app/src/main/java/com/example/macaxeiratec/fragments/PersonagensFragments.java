package com.example.macaxeiratec.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.macaxeiratec.R;
import com.example.macaxeiratec.adapters.Personagens_Adapter;
import com.example.macaxeiratec.domain.Personagens;
import com.example.macaxeiratec.interfaces.MainActivity;
import com.example.macaxeiratec.interfaces.RecycleViewOnClickListinerHack;

import java.util.List;

public class PersonagensFragments extends Fragment implements RecycleViewOnClickListinerHack {

    private RecyclerView mRecyclerView;
    private List<Personagens> mList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personagens,container, false);

        mRecyclerView = view.findViewById(R.id.rv_fragment);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                Personagens_Adapter adapter = (Personagens_Adapter) mRecyclerView.getAdapter();

                if(mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1){
                    List<Personagens> listAux = ((MainActivity) getActivity()).getSetPersonagensList(10);

                    for(int i = 0; i < listAux.size(); i ++){
                        adapter.addListItem(listAux.get(i), mList.size());
                    }
                }
            }
        });
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mList = ((MainActivity)getActivity()).getSetPersonagensList(10);
        Personagens_Adapter adapter = new Personagens_Adapter(getActivity(),mList);
        adapter.setRecycleViewOnClickListinerHack(this);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClickListiner(View view, int position) {
        Toast.makeText(getActivity(), "Position: "+position, Toast.LENGTH_SHORT).show();

        Personagens_Adapter adapter = (Personagens_Adapter) mRecyclerView.getAdapter();
        adapter.removeListItem(position);
    }
}
