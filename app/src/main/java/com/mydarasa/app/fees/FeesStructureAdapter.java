package com.mydarasa.app.fees;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mydarasa.app.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeesStructureAdapter extends RecyclerView.Adapter<FeesStructureViewHolder> {

    private Context mContext;
    private List<FeeStructureModel> feeStructureModelList;

    public FeesStructureAdapter(Context context, List<FeeStructureModel> feeStructureModelList ){

        this.mContext = context;
        this.feeStructureModelList = feeStructureModelList;
    }

    @NonNull
    @Override
    public FeesStructureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fee_structure_list_item, parent, false);
        return new FeesStructureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeesStructureViewHolder holder, int position) {

        final FeeStructureModel feeStructureModel = feeStructureModelList.get(position);
        holder.tvfeeStructureType.setText("Fees Structure");
        holder.tvfeeAmount.setText("Amt:" +" "+feeStructureModel.getTotalCoast());
        holder.tvStudent.setText(feeStructureModel.getStudentName());
       // holder.tvFeeStructureDate.setText("2020/01/02 6:43");
        holder.tvReportTerm.setText(feeStructureModel.getPeriod());
        holder.tvReportYear.setText(feeStructureModel.getYear());

        holder.cvfeeStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FeesStructureDetailsActivity.class);
                Bundle b = new Bundle();

                String schoolName = "", studentName="", className="", period="", year=" ";

                FeeItemModel[] feeItem = new FeeItemModel[]{};


                schoolName =feeStructureModel.getSchoolName();
                studentName = feeStructureModel.getStudentName();
                className = feeStructureModel.getClassName();
                period = feeStructureModel.getPeriod();
                year = feeStructureModel.getYear();
                feeItem = feeStructureModel.getFeeItem();

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

               String gsonString =  gson.toJson(feeStructureModel);


               // List newlist = new ArrayList<>(Arrays.asList(feeItem));

               // period = feeStructureModel.
               // year = feeStructureModel.

                intent.putExtra("studentName", studentName);
                intent.putExtra("className", className);
                intent.putExtra("schoolName", schoolName);
                intent.putExtra("period", period);
                intent.putExtra("year", year);
                intent.putExtra("gsonString", gsonString);

               // b.putSerializable("feestructure", feeStructureModel);
                //intent.putExtra("feestructure", feeStructureModel);
                mContext.startActivity(intent);



            }
        });

    }

    @Override
    public int getItemCount() {
        return feeStructureModelList.size();
    }
}
