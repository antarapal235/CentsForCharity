package com.example.smartpad.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.smartpad.CallBackListener;
import com.example.smartpad.R;
import com.example.smartpad.objects.HourlyInstance;
import com.example.smartpad.objects.Period;
import com.example.smartpad.objects.PeriodDay;
import com.example.smartpad.objects.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import sqip.InAppPaymentsSdk;

import static com.example.smartpad.utils.PadContract.DAYS;
import static com.example.smartpad.utils.PadContract.HOURLY_INSTANCES;
import static com.example.smartpad.utils.PadContract.PADS_CHANGED;
import static com.example.smartpad.utils.PadContract.PAD_NUMBER;
import static com.example.smartpad.utils.PadContract.PERIODS;
import static com.example.smartpad.utils.PadContract.USERS;

public class DashboardFragment extends Fragment {

    private User user;
    private View rootView;
    private DatabaseReference databaseReference;
    private CallBackListener callBackListener;
    public void setUser(User user){
        this.user=user;

        if(databaseReference==null){
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            databaseReference=firebaseDatabase.getReference().child(USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }

        if(rootView!=null) {
            setUp();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener)
            callBackListener = (CallBackListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.dashboard_fragment,container,false);

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child(USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        setUp();

        return rootView;
    }

    private void setUp(){
        View reproductiveOverlay=rootView.findViewById(R.id.reproductive_overlay);
        View independenceOverlay=rootView.findViewById(R.id.independence_overlay);
        View schoolOverlay = rootView.findViewById(R.id.school_overlay);
        ImageView filledImage = rootView.findViewById(R.id.filled_image);

        reproductiveOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(getContext());
                if (dialog.getWindow()!=null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                View contentView=LayoutInflater.from(getContext()).inflate(R.layout.alert_color_period,null);
                dialog.setContentView(contentView);

                TextView header=dialog.findViewById(R.id.header);
                TextView content=dialog.findViewById(R.id.content);
                Button doneButton=dialog.findViewById(R.id.done);
                Button donateButton=dialog.findViewById(R.id.donate);



                String text="The Center for Reproductive Rights uses the power of law to advance reproductive rights as fundamental human rights around the world.";



                header.setText("Center for Reproductive Rights");
                content.setText(text);

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });



                donateButton.setOnClickListener(v -> {
                    Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.example.supercookie1");
                    if (launchIntent != null) {
                        startActivity(launchIntent);//null pointer check in case package name was not found
                    }
                });


                dialog.show();
            }
        });

        independenceOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(getContext());
                if (dialog.getWindow()!=null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                View contentView=LayoutInflater.from(getContext()).inflate(R.layout.alert_color_period,null);
                dialog.setContentView(contentView);

                TextView header=dialog.findViewById(R.id.header);
                TextView content=dialog.findViewById(R.id.content);
                Button doneButton=dialog.findViewById(R.id.done);
                Button donateButton=dialog.findViewById(R.id.donate);

                String text="Since 1993, Women for Women International has helped more than 500,000 marginalized women in countries affected by war and conflict. We serve women in 8 countries offering support, tools, and access to life-changing skills to move from crisis and poverty to stability and economic self-sufficiency.";



                header.setText("Women for Women International");
                content.setText(text);

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                donateButton.setOnClickListener(v -> {
                    Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.example.supercookie1");
                    if (launchIntent != null) {
                        startActivity(launchIntent);//null pointer check in case package name was not found
                    }
                });



                //donateButton.setOnClickListener(new View.OnClickListener() {
                //    @Override
                //   public void onClick(View view) {
                //       dialog.dismiss();
                //   }
                // });

                dialog.show();
            }
        });


        schoolOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(getContext());
                if (dialog.getWindow()!=null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                View contentView=LayoutInflater.from(getContext()).inflate(R.layout.alert_color_period,null);
                dialog.setContentView(contentView);

                TextView header=dialog.findViewById(R.id.header);
                TextView content=dialog.findViewById(R.id.content);
                Button doneButton=dialog.findViewById(R.id.done);
                Button donateButton=dialog.findViewById(R.id.donate);

                String text="Our mission at School Girls Unite is to tackle prejudice against girls worldwide and expand their freedom and opportunities through education and leadership.";



                header.setText("School Girls Unite");
                content.setText(text);

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                donateButton.setOnClickListener(v -> {
                    Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.example.supercookie1");
                    if (launchIntent != null) {
                        startActivity(launchIntent);//null pointer check in case package name was not found
                    }
                });




                dialog.show();
            }
        });

        filledImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(getContext());
                if (dialog.getWindow()!=null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                View contentView=LayoutInflater.from(getContext()).inflate(R.layout.alert_color_period,null);
                dialog.setContentView(contentView);

                TextView header=dialog.findViewById(R.id.header);
                TextView content=dialog.findViewById(R.id.content);
                Button doneButton=dialog.findViewById(R.id.done);
                Button donateButton=dialog.findViewById(R.id.donate);

                String text="The mission of Women’s Global Empowerment Fund is to support women through economic, social and political programs; creating opportunities while addressing inequality, strengthening families and communities.";



                header.setText("Women's Global Empowerment Fund");
                content.setText(text);

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                donateButton.setOnClickListener(v -> {
                    Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.example.supercookie");
                    if (launchIntent != null) {
                        startActivity(launchIntent);//null pointer check in case package name was not found
                    }
                });
                //donateButton.setOnClickListener(v -> {

                //    if (InAppPaymentsSdk.INSTANCE.getSquareApplicationId().equals("REPLACE_ME")) {
                 //       showMissingSquareApplicationIdDialog();
                 //  } else {
                  //      showOrderSheet();
                 //   }
               // });

                //donateButton.setOnClickListener(new View.OnClickListener() {
                //    @Override
                //   public void onClick(View view) {
                //       dialog.dismiss();
                //   }
                // });

                dialog.show();
            }
        });
    }
}
