package com.example.mobiledroneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mobiledroneapp.db.DBHelperAdapter;
import com.example.mobiledroneapp.helpers.FlightLoggersArrayAdapter;
import com.example.mobiledroneapp.models.Assignments;
import com.example.mobiledroneapp.models.FlightLogger;
import com.example.mobiledroneapp.models.StatusLogger;
import com.example.mobiledroneapp.models.TableHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class History_2 extends AppCompatActivity {

    final Context context = this;
    TableLayout stk;
    DBHelperAdapter dbAdapter;
    List<Assignments> assignments = new ArrayList<>();
    List<TableHistoryModel> history = new ArrayList<>();
    List<StatusLogger> statusLogs = new ArrayList<>();
    ConstraintLayout constraintLayout;
    //PopupWindow popupWindow;

    boolean click1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);
        stk = (TableLayout) findViewById(R.id.table_main);
        dbAdapter = new DBHelperAdapter(context);
        assignments = dbAdapter.getAssignments();
        //popupWindow = new PopupWindow(context);
        //constraintLayout = new ConstraintLayout(this);
        init();
    }


    public void init() {

        TableRow tbrow0 = new TableRow(context);
        tbrow0.setBackgroundResource(R.color.trHeader);
        TextView tv0H = new TextView(this);
        tv0H.setPadding(10, 0, 0 , 0);
        tv0H.setTextSize(14);
        tv0H.setGravity(Gravity.CENTER_HORIZONTAL);
        tv0H.setText(" ID ");
        tv0H.setTextColor(Color.BLACK);
        tbrow0.addView(tv0H);
        TextView tv1H = new TextView(this);
        tv1H.setPadding(10, 0, 0 , 0);
        tv1H.setTextSize(14);
        tv1H.setGravity(Gravity.CENTER_HORIZONTAL);
        tv1H.setText(" FLIGHT ");
        tv1H.setTextColor(Color.BLACK);
        tbrow0.addView(tv1H);
        TextView tv2H = new TextView(this);
        tv2H.setPadding(10, 0, 0 , 0);
        tv2H.setTextSize(14);
        tv2H.setGravity(Gravity.CENTER_HORIZONTAL);
        tv2H.setText(" RISKS ");
        tv2H.setTextColor(Color.BLACK);
        tbrow0.addView(tv2H);
        TextView tv3H = new TextView(this);
        tv3H.setPadding(10, 0, 0 , 0);
        tv3H.setTextSize(14);
        tv3H.setGravity(Gravity.CENTER_HORIZONTAL);
        tv3H.setText(" DURATION ");
        tv3H.setTextColor(Color.BLACK);
        tbrow0.addView(tv3H);
        TextView tv4H = new TextView(this);
        tv4H.setPadding(10, 0, 0 , 0);
        tv4H.setTextSize(14);
        tv4H.setGravity(Gravity.CENTER_HORIZONTAL);
        tv4H.setText(" FLIGHT PLAN ");
        tv4H.setTextColor(Color.BLACK);
        tbrow0.addView(tv4H);
        stk.addView(tbrow0);
        for(Assignments assignment : assignments) {
            TableHistoryModel tbH = new TableHistoryModel();
            tbH.setId(assignment.getId());
            tbH.setFlight("Flights");
            tbH.setRisk("Risks");
            tbH.setDuration(Integer.parseInt(assignment.getFlightDuration()));
            //history.add(tbH);
            TableRow row = new TableRow(context);
            row.setBackgroundColor(Color.CYAN);
//                        row.setLayoutParams(new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.MATCH_PARENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT));
            TextView tv0 = new TextView(context);
            tv0.setTextColor(Color.BLACK);
            tv0.setTextSize(14);
            tv0.setPadding(10, 0, 0, 0);
            //tv0.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            tv0.setGravity(Gravity.CENTER_HORIZONTAL);
            tv0.setText(assignment.getId() + "");
            row.addView(tv0);
//            TextView tv1 = new TextView(context); // button
//            tv1.setTextColor(Color.BLACK);
//            tv1.setTextSize(14);
//            tv1.setPadding(10, 0, 0, 0);
//            //tv1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
//            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
//            tv1.setText("Flights");
            Button btn1 = new Button(context);
            btn1.setTextSize(12);
            btn1.setPadding(10, 0 ,0 , 0);
            btn1.setGravity(Gravity.CENTER);
            btn1.setTextColor(Color.BLACK);
            btn1.setText("Flight");
            row.addView(btn1);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int assignmentID = assignment.getId();
                    if(click1) {
                        // OPEN POPUP WITH Flight Log
                        //popupWindow.showAtLocation(constraintLayout, Gravity.BOTTOM,
                        //        10, 10);
                        //popupWindow.update(50,50,300, 80);
                        click1 = false;
                        //onButtonShowPopupWindowClick(v, assignmentID);
                        Intent intent = new Intent(context, PopupMenuActivityView.class);
                        intent.putExtra("assignmentID", assignmentID + "");
                        startActivity(intent);
                    } else {
                        //popupWindow.dismiss();
                        click1 = true;
                    }


                }
            });
//            TextView tv2 = new TextView(context); // button
//            tv2.setTextColor(Color.BLACK);
//            tv2.setTextSize(14);
//            tv2.setPadding(10, 0, 0, 0);
//            //tv2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
//            tv2.setGravity(Gravity.CENTER_HORIZONTAL);
//            tv2.setText("Risks");
            Button btn2 = new Button(context);

            btn2.setTextSize(12);
            btn2.setPadding(10,0,0,0);
            btn2.setGravity(Gravity.CENTER);
            btn2.setTextColor(Color.BLACK);
            btn2.setText("Risk");
            row.addView(btn2);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int assignmentID = assignment.getId();
                    // OPEN POPUP With Risks.

                    Intent intent = new Intent(context, PopupMenuStatusLogs.class);
                    intent.putExtra("assignmentID", assignmentID + "");
                    startActivity(intent);
                }
            });
            TextView tv3 = new TextView(context);
            tv3.setTextColor(Color.BLACK);
            tv3.setTextSize(14);
            tv3.setPadding(10, 0, 0, 0);
            //tv3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            tv3.setGravity(Gravity.CENTER_HORIZONTAL);
            tv3.setText(assignment.getFlightDuration());
            row.addView(tv3);
            Button btn3 = new Button(context);
            btn3.setTextSize(12);
            btn3.setPadding(10,0,0,0);
            btn3.setGravity(Gravity.CENTER);
            btn3.setTextColor(Color.BLACK);
            btn3.setText("Show Flight");
            row.addView(btn3);
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int assignmentID = assignment.getId();
                    Intent intent = new Intent(context, ShowFlightPlan.class);
                    intent.putExtra("assignmentID", assignmentID + "");
                    startActivity(intent);
                }
            });
            stk.addView(row);
        }

//        for (int i = 0; i < 25; i++) {
//            TableRow tbrow = new TableRow(this);
//            TextView t1v = new TextView(this);
//            t1v.setText("" + i);
//            t1v.setTextColor(Color.WHITE);
//            t1v.setGravity(Gravity.CENTER);
//            tbrow.addView(t1v);
//            TextView t2v = new TextView(this);
//            t2v.setText("Product " + i);
//            t2v.setTextColor(Color.WHITE);
//            t2v.setGravity(Gravity.CENTER);
//            tbrow.addView(t2v);
//            TextView t3v = new TextView(this);
//            t3v.setText("Rs." + i);
//            t3v.setTextColor(Color.WHITE);
//            t3v.setGravity(Gravity.CENTER);
//            tbrow.addView(t3v);
//            TextView t4v = new TextView(this);
//            t4v.setText("" + i * 15 / 32 * 10);
//            t4v.setTextColor(Color.WHITE);
//            t4v.setGravity(Gravity.CENTER);
//            tbrow.addView(t4v);
//            stk.addView(tbrow);
//        }

    }



}