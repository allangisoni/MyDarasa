package com.mydarasa.app.cocurricular;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.mydarasa.app.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CocurricularViewHolder extends RecyclerView.ViewHolder {

    public static final String SCHOOL_TYPE = "2";
    TextView tvCocurricularName, tvCocurricularType, tvCocurricularStudentName;
    CardView cvCocurrAct;

    public CocurricularViewHolder(@NonNull View itemView) {
        super(itemView);

        tvCocurricularName = itemView.findViewById(R.id.tvCocuricularName);
        tvCocurricularType = itemView.findViewById(R.id.tvCocuricularType);
        tvCocurricularStudentName = itemView.findViewById(R.id.tvCocuricularStudentName);
        cvCocurrAct = itemView.findViewById(R.id.cvCocurrAct);
    }


    public void bind(CocurricularModel cocurricularModel){
        tvCocurricularName.setText(cocurricularModel.getName());
        tvCocurricularType.setText(cocurricularModel.getType());
        tvCocurricularStudentName.setText(cocurricularModel.getStudentName());

        cvCocurrAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cocurricularModel.getSchoolTypeNo().equals(SCHOOL_TYPE)){
                    Intent intent = new Intent(itemView.getContext(), CocurricularDetailsActivity.class);
                    intent.putExtra("studentNo", cocurricularModel.getStudentNo());
                    intent.putExtra("schoolName", cocurricularModel.getName());
                    intent.putExtra("studentName", cocurricularModel.getStudentName());
                    itemView.getContext().startActivity(intent);

                }
            }
        });
    }

}
