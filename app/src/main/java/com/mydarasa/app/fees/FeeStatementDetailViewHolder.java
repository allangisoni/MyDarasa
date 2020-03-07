package com.mydarasa.app.fees;

import android.view.View;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FeeStatementDetailViewHolder  extends RecyclerView.ViewHolder {
    TextView tvPaymentType, tvPaymentAmount, tvPaymentDate, tvStudent;
    CardView cvFeeDetails;

    public FeeStatementDetailViewHolder(@NonNull View itemView) {
        super(itemView);

        tvPaymentType = itemView.findViewById(R.id.tvPaymentType);
        tvPaymentAmount = itemView.findViewById(R.id.tvfeeAmount);
        tvPaymentDate = itemView.findViewById(R.id.tvTransactDate);
        tvStudent = itemView.findViewById(R.id.tvStudent);

    }
}
