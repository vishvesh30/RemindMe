package com.example.admin.remindme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by admin on 27-01-2017.
 */

public class History extends Activity {

    static DataBaseModel db;
    TextView name,date;
    static String CurrentDate;
    public static Intent newIntent(Context context,DataBaseModel dx,String date)
    {
        Intent i=new Intent(context,History.class);
        db=dx;
        CurrentDate=date;
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        name=(TextView) findViewById(R.id.textview_history_name);
        date=(TextView) findViewById(R.id.textview_history_date);

        name.setText(db.getName());
        date.setText(CurrentDate);

    }
}
