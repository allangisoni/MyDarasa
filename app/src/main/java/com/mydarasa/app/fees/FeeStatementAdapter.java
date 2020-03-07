package com.mydarasa.app.fees;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydarasa.app.R;
import com.mydarasa.app.events.EventsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeeStatementAdapter extends RecyclerView.Adapter<FeeStatementViewHolder> {
    List<FeeStatementModel> feeStatementModelList;
    Context mContext;

    public  FeeStatementAdapter(Context context,List<FeeStatementModel> feeStatementModelList ){
       this.mContext = context;
       this.feeStatementModelList = feeStatementModelList;
    }
    @NonNull
    @Override
    public FeeStatementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.fee_item_list, parent, false);
        return new FeeStatementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeeStatementViewHolder holder, int position) {
         FeeStatementModel feeStatementModel = feeStatementModelList.get(position);
         String feeAmount = feeStatementModel.getFeeAmount();
         String feeBal = feeStatementModel.getFeeBal();
         double feePaid = (Double.parseDouble(feeAmount) - Double.parseDouble(feeBal));
         String feesPaid = Double.toString(feePaid);
         holder.tvFeeAmount.setText("Amt: " + feeStatementModel.getFeeAmount());
         holder.tvFeeBal.setText("Bal: " +feeStatementModel.getFeeBal());
         holder.tvFeePaid.setText("Paid: " +feesPaid);
         holder.tvStudent.setText(feeStatementModel.getStudentName());

         holder.cvFeeDetails.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(mContext, FeeStatementDetailsActivity.class);
                 //intent.putExtra("studentName", )
                 mContext.startActivity(intent);
             }
         });

    }

    @Override
    public int getItemCount() {
        return feeStatementModelList.size();
    }
}
