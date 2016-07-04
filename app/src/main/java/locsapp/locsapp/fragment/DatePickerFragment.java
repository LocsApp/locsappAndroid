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


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private DateListener listener = null;
    private String date = null;
    private String title = null;

    public void setListener(DateListener callback, String date) {
        this.listener = callback;
        this.date = date;
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
            out = date.split("-");
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
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd", Locale.FRENCH);
        String date = simpleDateFormat.format(c.getTime());
        if (listener != null) {
            listener.returnDate(date);
        }
    }

    public interface DateListener {
        void returnDate(String date);
    }
}
