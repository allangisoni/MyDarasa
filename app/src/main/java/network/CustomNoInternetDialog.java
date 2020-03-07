package network;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mydarasa.app.R;

import androidx.annotation.Nullable;

public class CustomNoInternetDialog extends LinearLayout implements View.OnClickListener {
    LinearLayout layout = null;
    ImageView imageView = null;
    TextView textview1 = null;
    TextView textview2 = null;
    TextView textview3 = null;
    Button btnTry = null;
    Context mContext = null;
    public CustomNoInternetDialog(Context context){
     super(context);

     mContext = context;
    }

    public CustomNoInternetDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomNoInternetDialog);
        String text1 = a.getString(R.styleable.CustomNoInternetDialog_text1);
        String text2 = a.getString(R.styleable.CustomNoInternetDialog_text2);
        String text3 = a.getString(R.styleable.CustomNoInternetDialog_text3);
        Drawable drawable = a.getDrawable(R.styleable.CustomNoInternetDialog_image);
        String buttonText = a.getString(R.styleable.CustomNoInternetDialog_button);

        text1 = text1 == null ? "" : text1;
        text2 = text2 == null ? "" : text2;
        text3 = text3 == null ? "" : text3;
        buttonText = buttonText == null ? "" : buttonText;
        drawable = drawable == null ? null : drawable;

        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        layout = (LinearLayout) li.inflate(R.layout.custom_no_internet_dialog, this, true);

        textview1 = (TextView) layout.findViewById(R.id.tvDialog);
        textview2 = (TextView) layout.findViewById(R.id.tvInternet);
        textview3 = (TextView) layout.findViewById(R.id.tvInternetCheck);
        imageView = layout.findViewById(R.id.imageViewInternet);
        btnTry = layout.findViewById(R.id.btnTryAgain);


        textview1.setText(text1);
        textview2.setText(text2);
        textview3.setText(text3);
        imageView.setImageDrawable(drawable);
        btnTry.setText(buttonText);

        btnTry.setOnClickListener(this);

        a.recycle();
    }

    public Drawable getImageView() {
        return imageView.getDrawable();
    }

    public void setImageView(Drawable drawable) {
        this.imageView.setImageDrawable(drawable);
    }

    public String getTextview1() {
        return textview1.getText().toString();
    }

    public void setTextview1(String text1) {
        this.textview1.setText(text1);
    }

    public String getTextview2() {
        return textview2.getText().toString();
    }

    public void setTextview2(String text2) {
        this.textview2.setText(text2);
    }


    public String getTextview3() {
        return textview3.getText().toString();
    }

    public void setTextview3(String text3) {
        this.textview3.setText(text3);
    }


    public String getButtonText() {
        return btnTry.getText().toString();
    }

    public void setButtonText(String buttonText) {
        this.btnTry.setText(buttonText);
    }

    @Override
    public void onClick(View v) {
       // Toast.makeText(mContext, "" + "Hurray", Toast.LENGTH_SHORT).show();
    }
}
