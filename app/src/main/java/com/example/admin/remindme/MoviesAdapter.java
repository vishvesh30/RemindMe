package com.example.admin.remindme;

/**
 * Created by admin on 24-01-2017.
 */

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.convert.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by vishvraj on 20-01-2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    static DataBaseModel ddd;
    ArrayList<DataBaseModel> arrayList=new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name,no,days_left1;
        public ProgressBar progressBar;
        public CheckBox chk;
        public SQLClass db;

        public MyViewHolder(final View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
            name=(TextView)view.findViewById(R.id.textView_name);
            no=(TextView)view.findViewById(R.id.textView_no);
            days_left1=(TextView)view.findViewById(R.id.days_left);
            chk=(CheckBox)view.findViewById(R.id.checkbox_paid);
            db=new SQLClass(view.getContext());
            chk.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            AlertDialog.Builder a_builder=new AlertDialog.Builder(view.getContext());
                            a_builder.setMessage("Are you sure want to quit?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Calendar cal=Calendar.getInstance();
                                            String date;
                                            int mMonth=cal.get(Calendar.MONTH);
                                            int mYear=cal.get(Calendar.YEAR);
                                            int mDay=cal.get(Calendar.DAY_OF_MONTH);
                                            date=mDay+"/"+mMonth+"/"+mYear;
                                            boolean isInserted=db.insertHistory(ddd.getId(),date);
                                            if(isInserted==true)
                                            {
                                                Toast.makeText(view.getContext(),"Deleted Successfully",Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(view.getContext(),"not deleted",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            chk.setChecked(false);
                                        }
                                    });
                            AlertDialog alert=a_builder.create();
                            alert.setTitle("Alert");
                            alert.show();
                        }
                    }
            );
        }
    }


    public MoviesAdapter(ArrayList<DataBaseModel> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        setAnimation(holder.itemView, position);
        ddd= arrayList.get(position);

        long total_days = 0;
        DataBaseModel dbm=arrayList.get(position);
        ddd=dbm;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        Date startDate=new Date();
        Date installDate=new Date();
        String installDateString;
        String currentDateTimeString;
        Calendar cal=Calendar.getInstance();
        Calendar cal1=Calendar.getInstance();
        String tmp=dbm.getInstall_Month();
        char x=tmp.charAt(0);
        int im= Integer.valueOf(x)-48;
        currentDateTimeString=mDay + "/" + (mMonth + 1) + "/" + mYear;
        try
        {
            startDate=simpleDateFormat.parse(dbm.getStart_Date());
            installDate = simpleDateFormat.parse(currentDateTimeString);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(startDate);
        cal1.setTime(installDate);
        while (cal.before(cal1))
        {
            cal.add(Calendar.MONTH, im);
        }
        Date date2;
        mDay=cal.get(Calendar.DAY_OF_MONTH);
        mMonth=cal.get(Calendar.MONTH);
        mYear=cal.get(Calendar.YEAR);
        installDateString=mDay + "/" + (mMonth + 1) + "/" + mYear;
        try {

            date2 = simpleDateFormat.parse(installDateString);
            installDate = simpleDateFormat.parse(currentDateTimeString);

            total_days=printDifference(installDate, date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.no.setText(ddd.getMobile_No());
        holder.name.setText(ddd.getName());
       holder.days_left1.setText(String.valueOf(total_days)+" Day left");

        int progress=365-(int)total_days;
        if(progress<=0){
            progress=0;
        }
        if(progress>350) {
            holder.progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }else if(progress>300 && progress<=350) {
            holder.progressBar.getProgressDrawable().setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_IN);
        }else if(progress>200 && progress<=300) {
            holder.progressBar.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
        }else if(progress>100 && progress<=200) {
            holder.progressBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        }else{
            holder.progressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        holder.progressBar.setProgress(progress);
    }
    private int lastPosition = -1;
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public long printDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);
        return elapsedDays;

    }
}
