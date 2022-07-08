package com.example.mobiledroneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mobiledroneapp.db.DBHelperAdapter;
import com.example.mobiledroneapp.models.Assignments;
import com.example.mobiledroneapp.models.FlightLogger;
import com.example.mobiledroneapp.models.StatusLogger;
import com.example.mobiledroneapp.models.TableHistoryModel;

import org.w3c.dom.Text;

import java.sql.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class History extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button datePickerButton;
    private TableLayout tableLayoutHistory;
    private Button btnTempInsert;

    DBHelperAdapter dbAdapter;

    List<TableHistoryModel> history = new ArrayList<>();
    List<FlightLogger> flightLogs = new ArrayList<>();
    List<StatusLogger> statusLogs = new ArrayList<>();
    //flightLogs = dbAdapter.findFlightLogsByAssignmentID(assignment.getId());
    //statusLogs = dbAdapter.findStatusLogsByFlightLogID(flightLog.getId());
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);
        datePickerButton = (Button) findViewById(R.id.datePickerButton);
        datePickerButton.setText(getTodaysDate());
        tableLayoutHistory = (TableLayout) findViewById(R.id.tableLayoutHistory);

        /*
        ID, FLIGHT, RISKS, DURATION
                #96c885
                @color/black
         */
        TableRow tbrow0 = new TableRow(context);
        tbrow0.setBackgroundResource(R.color.trHeader);
        TextView tv0 = new TextView(this);
        tv0.setPadding(10, 0, 0 , 0);
        tv0.setTextSize(14);
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);

        tv0.setText(" ID ");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setPadding(10, 0, 0 , 0);
        tv1.setTextSize(14);
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        tv1.setText(" FLIGHT ");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setPadding(10, 0, 0 , 0);
        tv2.setTextSize(14);
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        tv2.setText(" RISKS ");
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setPadding(10, 0, 0 , 0);
        tv3.setTextSize(14);
        tv3.setGravity(Gravity.CENTER_HORIZONTAL);
        tv3.setText(" DURATION ");
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv3);
        tableLayoutHistory.addView(tbrow0);

        dbAdapter = new DBHelperAdapter(context);
        initDatePicker();

//        btnTempInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //dbAdapter.populateDummyData();
//            }
//        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                datePickerButton.setText(date);

                // Find in database and display

                String dateParseNormal = month + " " + day + " " + year;

                // Step 1: find assignments of that day
                String displayFormat = "mm dd yyyy";
                SimpleDateFormat spDisplay = new SimpleDateFormat(displayFormat);
                Date displayDate = null;
                try {
                    displayDate = spDisplay.parse(dateParseNormal);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String timeFormat = "dd-mm-yyyy";
                SimpleDateFormat dbFormat = new SimpleDateFormat(timeFormat);
                String _creationDate = dbFormat.format(displayDate);
                List<Assignments> assignments = dbAdapter.getAssignmentsByDate(_creationDate);

                if(assignments.size() > 0) {
                    for(Assignments assignment : assignments) {
                        TableHistoryModel tbH = new TableHistoryModel();
                        tbH.setId(assignment.getId());
                        tbH.setFlight("Flights");
                        tbH.setRisk("Risks");
                        tbH.setDuration(Integer.parseInt(assignment.getFlightDuration()));
                        history.add(tbH);
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
                        tv0.setGravity(Gravity.CENTER);
                        tv0.setText(assignment.getId() + "");
                        row.addView(tv0);
                        TextView tv1 = new TextView(context); // button
                        tv1.setTextColor(Color.BLACK);
                        tv1.setTextSize(14);
                        tv1.setPadding(10, 0, 0, 0);
                        //tv1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                        tv1.setGravity(Gravity.CENTER);
                        tv1.setText("Flights");
                        row.addView(tv1);
                        TextView tv2 = new TextView(context); // button
                        tv2.setTextColor(Color.BLACK);
                        tv2.setTextSize(14);
                        tv2.setPadding(10, 0, 0, 0);
                        //tv2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                        tv2.setGravity(Gravity.CENTER);
                        tv2.setText("Risks");
                        row.addView(tv2);
                        TextView tv3 = new TextView(context);
                        tv3.setTextColor(Color.BLACK);
                        tv3.setTextSize(14);
                        tv3.setPadding(10, 0, 0, 0);
                        //tv3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                        tv3.setGravity(Gravity.CENTER);
                        tv3.setText(assignment.getFlightDuration());
                        row.addView(tv3);
                        tableLayoutHistory.addView(row);
                    }
                }
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1) {
            return "JAN";
        }
        if(month == 2) {
            return "FEB";
        }
        if(month == 3) {
            return "MAR";
        }
        if(month == 4) {
            return "APR";
        }
        if(month == 5) {
            return "MAY";
        }if(month == 6) {
            return "JUN";
        }if(month == 7) {
            return "JUL";
        }
        if(month == 8) {
            return "AUG";
        }
        if(month == 9) {
            return "SEP";
        }
        if(month == 10) {
            return "OCT";
        }
        if(month == 11) {
            return "NOV";
        }
        if(month == 12) {
            return "DEC";
        }
        return "JAN";
    }


    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}