package com.mydarasa.app.fees;

import android.view.View;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeeItemViewHolder extends RecyclerView.ViewHolder {
    TextView tvfeeItemSubject, tvFeeItemScore;

    public FeeItemViewHolder(@NonNull View itemView) {
        super(itemView);

    tvfeeItemSubject = itemView.findViewById(R.id.feeItemSubject);
    tvFeeItemScore = itemView.findViewById(R.id.feeItemScore);
    }
}
