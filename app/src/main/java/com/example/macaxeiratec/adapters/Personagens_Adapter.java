package com.example.macaxeiratec.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macaxeiratec.R;
import com.example.macaxeiratec.domain.Personagens;
import com.example.macaxeiratec.interfaces.RecycleViewOnClickListinerHack;

import java.util.List;

public class Personagens_Adapter extends RecyclerView.Adapter<Personagens_Adapter.MyViewHolder> {

    private List<Personagens> mList;
    private LayoutInflater mLayoutInflater;
    private RecycleViewOnClickListinerHack mRecycleViewOnClickListinerHack;

    public Personagens_Adapter(Context c, List<Personagens> l){
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_personagens, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.iv_personagem.setImageResource(mList.get(position).getFoto());
        holder.tv_nome.setText(mList.get(position).getNome());
        holder.tv_clan.setText(mList.get(position).getClan());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecycleViewOnClickListinerHack(RecycleViewOnClickListinerHack r){
        mRecycleViewOnClickListinerHack = r;
    }

    public void addListItem(Personagens personagens, int position){
        mList.add(personagens);
        notifyItemInserted(position);
    }

    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView iv_personagem;
        public TextView tv_nome;
        public TextView tv_clan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_personagem = itemView.findViewById(R.id.iv_personagem);
            tv_clan = itemView.findViewById(R.id.tv_clan);
            tv_nome = itemView.findViewById(R.id.tv_nome);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mRecycleViewOnClickListinerHack != null){
                mRecycleViewOnClickListinerHack.onClickListiner(v,getPosition());
            }
        }
    }
}
