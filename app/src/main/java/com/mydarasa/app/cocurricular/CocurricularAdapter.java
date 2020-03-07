package com.mydarasa.app.cocurricular;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydarasa.app.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CocurricularAdapter extends RecyclerView.Adapter<CocurricularViewHolder> {

    private Context mContext;
    List<CocurricularModel> cocurricularModelList;

   // private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CocurricularModel cocurricularModel);
    }

    public  CocurricularAdapter(Context context, List<CocurricularModel> cocurricularModelList ){
       this.mContext = context;
       this.cocurricularModelList = cocurricularModelList;
       //this.listener = listener;

    }
    @NonNull
    @Override
    public CocurricularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.cocuricular_item_list,parent,false);
        return new CocurricularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CocurricularViewHolder holder, int position) {
           holder.bind(cocurricularModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return cocurricularModelList.size();
    }
}
