package com.example.macaxeiratec.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.macaxeiratec.R;
import com.example.macaxeiratec.adapters.Personagens_Adapter;
import com.example.macaxeiratec.domain.Personagens;
import com.example.macaxeiratec.MainActivity;
import com.example.macaxeiratec.interfaces.RecycleViewOnClickListinerHack;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

public class PersonagensFragments extends Fragment implements RecycleViewOnClickListinerHack, View.OnClickListener {

    public RecyclerView mRecyclerView;
    public List<Personagens> mList;
    //private FloatingActionButton fab;
    public FloatingActionMenu fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personagens,container, false);

        mRecyclerView = view.findViewById(R.id.rv_fragment);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    fab.hideMenuButton(true);
                }
                else{
                    fab.showMenuButton(true);
                }

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                //GridLayoutManager llm = (GridLayoutManager) mRecyclerView.getLayoutManager();

               /* StaggeredGridLayoutManager llm = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
                int[] aux = llm.findLastCompletelyVisibleItemPositions(null);
                int max = -1;
                for(int i = 0; i < aux.length;i++){
                    max = aux[i] > max ? aux[i] : max;
                }*/

                Personagens_Adapter adapter = (Personagens_Adapter) mRecyclerView.getAdapter();


                if(mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1){
                //if(mList.size() == max + 1){
                    List<Personagens> listAux = ((MainActivity) getActivity()).getSetPersonagensList(11);

                    for(int i = 0; i < listAux.size(); i ++){
                        adapter.addListItem(listAux.get(i), mList.size());
                    }
                }
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(),mRecyclerView,this));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setReverseLayout(false);
        mRecyclerView.setLayoutManager(llm);

       /* GridLayoutManager llm = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(llm);*/

       /*StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
       llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
       mRecyclerView.setLayoutManager(llm);*/

        mList = ((MainActivity)getActivity()).getSetPersonagensList(11);
        Personagens_Adapter adapter = new Personagens_Adapter(getActivity(),mList);
      //  adapter.setRecycleViewOnClickListinerHack(this);
        mRecyclerView.setAdapter(adapter);

        fab = getActivity().findViewById(R.id.fab);
        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                Toast.makeText(getActivity(), "Abriu? " + (opened ? "true" : "false"), Toast.LENGTH_SHORT).show();
            }
        });
        fab.showMenuButton(true);
        fab.setClosedOnTouchOutside(true);

        FloatingActionButton fab1 = getActivity().findViewById(R.id.fab1);
        FloatingActionButton fab2 = getActivity().findViewById(R.id.fab2);
        FloatingActionButton fab3 = getActivity().findViewById(R.id.fab3);
        FloatingActionButton fab4 = getActivity().findViewById(R.id.fab4);
        FloatingActionButton fab5 = getActivity().findViewById(R.id.fab5);

        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);
        fab5.setOnClickListener(this);

        /*fab = getActivity().findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {

            }

            @Override
            public void onScrollUp() {

            }
        },new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                //GridLayoutManager llm = (GridLayoutManager) mRecyclerView.getLayoutManager();

               /* StaggeredGridLayoutManager llm = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
                int[] aux = llm.findLastCompletelyVisibleItemPositions(null);
                int max = -1;
                for(int i = 0; i < aux.length;i++){
                    max = aux[i] > max ? aux[i] : max;
                }*/

                /*Personagens_Adapter adapter = (Personagens_Adapter) mRecyclerView.getAdapter();


                if(mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1){
                    //if(mList.size() == max + 1){
                    List<Personagens> listAux = ((MainActivity) getActivity()).getSetPersonagensList(11);

                    for(int i = 0; i < listAux.size(); i ++){
                        adapter.addListItem(listAux.get(i), mList.size());
                    }
                }
            }
        });
       fab.setOnClickListener(this);*/

        return view;
    }
    @Override
    public void onClickListiner(View view, int position) {
       // Toast.makeText(getActivity(), "onClickListiner: "+position, Toast.LENGTH_SHORT).show();

        /*Personagens_Adapter adapter = (Personagens_Adapter) mRecyclerView.getAdapter();
        adapter.removeListItem(position);*/

        try{
            YoYo.with(Techniques.FadeIn)
                    .duration(700)
                    .playOn(view);
        }
        catch (Exception  e){

        }
    }

    public void onLongPressClickListiner(View view, int position) {
       // Toast.makeText(getActivity(), "onLongPressClickListiner: "+position, Toast.LENGTH_SHORT).show();

        try{
            YoYo.with(Techniques.FadeOut)
                    .duration(700)
                    .playOn(view);
        }
        catch (Exception  e){

        }

        /*Personagens_Adapter adapter = (Personagens_Adapter) mRecyclerView.getAdapter();
        adapter.removeListItem(position);*/
    }

    @Override
    public void onClick(View v) {
        Intent it = null;

        switch (v.getId()){
            case R.id.fab1:
                it = new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse("http://www.facebook.com"));
                break;
            case R.id.fab2:
                it = new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse("http://plus.google.com"));
                break;
            case R.id.fab3:
                it = new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse("http://www.whatsapp.com"));
                break;
            case R.id.fab4:
                it = new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse("http://www.linkedin.com"));
                break;
            case R.id.fab5:
                it = new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse("http://www.youtube.com"));
                break;
        }
        startActivity(it);
    }

    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener{

        private Context mContex;
        private GestureDetector mGestureDetector;
        private RecycleViewOnClickListinerHack mRecycleViewOnClickListinerHack;

        public RecyclerViewTouchListener(Context c, final RecyclerView rv, RecycleViewOnClickListinerHack rvoclh){
            mContex = c;
            mRecycleViewOnClickListinerHack = rvoclh;

            mGestureDetector = new GestureDetector(mContex, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(),e.getY());
                    if (cv != null && mRecycleViewOnClickListinerHack != null){
                        mRecycleViewOnClickListinerHack.onLongPressClickListiner(cv, rv.getChildPosition(cv));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = rv.findChildViewUnder(e.getX(),e.getY());

                    if (cv != null && mRecycleViewOnClickListinerHack != null){
                        mRecycleViewOnClickListinerHack.onClickListiner(cv, rv.getChildPosition(cv));
                    }
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
