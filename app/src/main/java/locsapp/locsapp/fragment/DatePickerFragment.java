package locsapp.locsapp.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import locsapp.locsapp.interfaces.DateCallback;


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private DateListener listener = null;
    private DateCallback listenerBis = null;
    private String date = null;
    private String title = null;
    private String tag = null;
    private long minDate = -1;
    private long maxDate = -1;

    public void setMinDate(long date) {
        this.minDate = date;
    }

    public void setMaxDate(long date) {
        this.maxDate = date;
    }

    public void setMinDate(String date) {
        String[] out = new String[]{};
        if (date != null) {
            if (date.length() > 10) {
                date = date.substring(0,10);
            }
            out = date.split("-");
        }
        if (out.length == 3) {
            int year = Integer.parseInt(out[0]);
            int month = Integer.parseInt(out[1]) - 1;
            int day = Integer.parseInt(out[2]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            final Calendar cbis = Calendar.getInstance();
            if (c.getTimeInMillis() < cbis.getTimeInMillis()) {
                this.minDate = cbis.getTimeInMillis();
            }
            else {
                this.minDate = c.getTimeInMillis();
            }
        }
    }

    public void setMaxDate(String date) {
        String[] out = new String[]{};
        if (date != null) {
            if (date.length() > 10) {
                date = date.substring(0,10);
            }
            out = date.split("-");
        }
        if (out.length == 3) {
            int year = Integer.parseInt(out[0]);
            int month = Integer.parseInt(out[1]) - 1;
            int day = Integer.parseInt(out[2]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            final Calendar cbis = Calendar.getInstance();
            if (c.getTimeInMillis() < cbis.getTimeInMillis()) {
                this.maxDate = cbis.getTimeInMillis();
            }
            else {
                this.maxDate = c.getTimeInMillis();
            }
        }
    }

    public void setListener(DateListener callback, String date) {
        this.listener = callback;
        this.date = date;
    }

    public void setListener(DateCallback callback, String date, String tag) {
        this.listenerBis = callback;
        this.date = date;
        this.tag = tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setListener(DateListener callback) {
        listener = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year;
        int month;
        int day;

        String[] out = new String[]{};
        if (date != null) {
            out = date.split("/");
            if (out.length != 3) {
                out = date.split("-");
            }
        }

        if (out.length == 3) {
            year = Integer.parseInt(out[0]);
            month = Integer.parseInt(out[1]) - 1;
            day = Integer.parseInt(out[2]);
        }
        else {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        if (this.title != null) {
            datePickerDialog.setTitle(this.title);
        }
        if (minDate != -1) {
            datePickerDialog.getDatePicker().setMinDate(minDate);
        }
        if (maxDate != -1) {
            datePickerDialog.getDatePicker().setMaxDate(maxDate);
        }
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy/MM/dd", Locale.FRENCH);
        String date = simpleDateFormat.format(c.getTime());
        if (listener != null) {
            listener.returnDate(date);
        }
        if (listenerBis != null) {
            listenerBis.dateSetCallback(tag, date);
        }
    }

    public interface DateListener {
        void returnDate(String date);
    }
}
