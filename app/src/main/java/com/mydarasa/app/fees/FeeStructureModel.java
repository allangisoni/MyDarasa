package com.mydarasa.app.fees;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Array;

public class FeeStructureModel implements Parcelable {

    @SerializedName("className")
    @Expose
    private String className;

    @SerializedName("studentName")
    @Expose
    private String StudentName;

    @SerializedName("totalCoast")
    @Expose
    private String totalCoast;

    @SerializedName("period")
    @Expose
    private String period;


    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("schoolName")
    @Expose
    private String schoolName;

    @SerializedName("feeItem")
    @Expose
    private FeeItemModel[] feeItem;



    public String getClassName() {
        return className;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public FeeItemModel[] getFeeItem() {
        return feeItem;
    }

    public void setFeeItem(FeeItemModel[] feeItem) {
        this.feeItem = feeItem;
    }

    public String getTotalCoast() {
        return totalCoast;
    }

    public void setTotalCoast(String totalCoast) {
        this.totalCoast = totalCoast;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    protected FeeStructureModel(Parcel in){
        className= in.readString();
        StudentName = in.readString();

        //feeItem = in.readArray(FeeItemModel.class);

       // feeItem =(FeeItemModel[]) in.readArray(FeeItemModel[].class.getClassLoader());

  }

    public static final Creator<FeeStructureModel> CREATOR = new Creator<FeeStructureModel>() {
        @Override
        public FeeStructureModel createFromParcel(Parcel in) {
            return new FeeStructureModel(in);
        }

        @Override
        public FeeStructureModel[] newArray(int size) {
            return new FeeStructureModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(className);
        dest.writeString(StudentName);
        //dest.writeArray(feeItem);
    }


}
