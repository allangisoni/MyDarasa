package com.mydarasa.app.fees;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydarasa.app.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeesStatementDetailsAdapter extends RecyclerView.Adapter<FeeStatementDetailViewHolder> {

    private Context mContext;
    private List<FeeStatementDetailsModel> feeStatementDetailsList;

    public FeesStatementDetailsAdapter(Context context,List<FeeStatementDetailsModel> feeStatementDetailsList ){

        this.mContext = context;
        this.feeStatementDetailsList = feeStatementDetailsList;
    }


    @NonNull
    @Override
    public FeeStatementDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fee_statement_item, parent, false);
        return new FeeStatementDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeeStatementDetailViewHolder holder, int position) {
        FeeStatementDetailsModel feeStatementDetailsModel = feeStatementDetailsList.get(position);
        holder.tvPaymentType.setText(feeStatementDetailsModel.getPaymentType());
        holder.tvPaymentAmount.setText("Amount: " +" " +feeStatementDetailsModel.getPaymentAmount());
        holder.tvPaymentDate.setText(feeStatementDetailsModel.getTransactionDate());
        holder.tvStudent.setText(feeStatementDetailsModel.getStudentName());


    }

    @Override
    public int getItemCount() {
        return feeStatementDetailsList.size();
    }
}
