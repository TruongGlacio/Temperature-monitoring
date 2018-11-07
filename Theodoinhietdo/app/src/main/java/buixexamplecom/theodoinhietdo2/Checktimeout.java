package buixexamplecom.theodoinhietdo2;

/**
 * Created by Buixu on 11/03/2016.
 */
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by Buixu on 11/03/2016.
 */
public class Checktimeout {

    public boolean TimeoutNotification(String timeupdate)
    { Date today=new Date(System.currentTimeMillis());

        SimpleDateFormat dayFormat= new SimpleDateFormat("dd");
        SimpleDateFormat hourFormat= new SimpleDateFormat("HH");
        SimpleDateFormat minuteFormat= new SimpleDateFormat("mm");
        SimpleDateFormat seconsFormat= new SimpleDateFormat("ss");
        int timeday=Integer.parseInt(dayFormat.format(today.getTime()));
        int timehour=Integer.parseInt(hourFormat.format(today.getTime()));
        int timeminute=Integer.parseInt(minuteFormat.format(today.getTime()));
        int timesecons=Integer.parseInt(seconsFormat.format(today.getTime()));
        int timesys=timehour*3600+timeminute*60+timesecons;

        int timeupdate_int=Integer.parseInt(timeupdate.substring(0,2))*3600+Integer.parseInt(timeupdate.substring(3,5))*60+Integer.parseInt(timeupdate.substring(6,8));
        //timeDate.substring(0,2)+"h:"+timeDate.substring(3,8);
        if (timesys>timeupdate_int+360)
        {
            return false;
        }
        else
            return true;
    }
    }
