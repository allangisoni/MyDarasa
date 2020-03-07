package com.mydarasa.app.fees;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydarasa.app.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeeItemAdapter extends RecyclerView.Adapter<FeeItemViewHolder> {
    private Context mContext;
    private List<FeeItemModel> feeItemModelList;

    public FeeItemAdapter(Context context,List<FeeItemModel> feeItemModelList ){
        this.mContext = context;
        this.feeItemModelList = feeItemModelList;
    }

    @NonNull
    @Override
    public FeeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.fee_structure_item_list, parent, false);
        return new FeeItemViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull FeeItemViewHolder holder, int position) {
       FeeItemModel  feeItemModel = feeItemModelList.get(position);

       holder.tvfeeItemSubject.setText(feeItemModel.getName());
       holder.tvFeeItemScore.setText(feeItemModel.getCost());
    }

    @Override
    public int getItemCount() {
        return feeItemModelList.size();
    }
}
