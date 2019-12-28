package com.mydarasa.app.fees;

import android.view.View;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeeStatementViewHolder extends RecyclerView.ViewHolder {

    TextView tvFeeAmount, tvFeePaid, tvFeeBal;
    public FeeStatementViewHolder(@NonNull View itemView) {
        super(itemView);

        tvFeeAmount = itemView.findViewById(R.id.tv_feeAmount);
        tvFeePaid = itemView.findViewById(R.id.tv_feePaid);
        tvFeeBal = itemView.findViewById(R.id.tv_feebal);
    }
}
