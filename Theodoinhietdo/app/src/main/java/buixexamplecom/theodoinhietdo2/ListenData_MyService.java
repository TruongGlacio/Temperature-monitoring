package buixexamplecom.theodoinhietdo2;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.List;

//import android.support.annotation.Nullable;


public class ListenData_MyService extends Service {


    String newtime,Newzone1_String,Newzone2_String,Newzone3_String,Newzone4_String,time;
    Double Newzone1,Newzone2,Newzone3,Newzone4,Nhietdo_max;
    MediaPlayer mediaPlayer;
    Checktimeout mchecktimeout;
    Xulytungsensor mXulytungsensor;
    double a1=35;
    double b1=40;
    String UrlCloud[]={"https://canhbaonhietdotvtqb.firebaseio.com/","https://thongtin3.firebaseio.com/","https://thongtin1.firebaseio.com/"};
    String UrlChilden[]={"C9","NhaTHay","ThongTinCanhBao","ThongTinLuuDu","ThuVien"};
    String Urlchilden=UrlChilden[4];
    String Urlcloud=UrlCloud[0];
    SharedPreferences mSharedPreferencesUrl;
    public void ListenData(){
    try
        {
            Firebase ref = new Firebase(Urlcloud);
            Query query = ref.orderByChild(Urlchilden).limitToLast(1);
            ChildEventListener childEventListener = ref.child(Urlchilden).addChildEventListener(new ChildEventListener() {
                                                                                                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {
                                                                                                        newtime = (String) dataSnapshot.child("time").getValue();
                                                                                                        Newzone1_String = (String) dataSnapshot.child("zone1").getValue();
                                                                                                        Newzone2_String = (String) dataSnapshot.child("zone2").getValue();
                                                                                                        Newzone3_String = (String) dataSnapshot.child("zone3").getValue();
                                                                                                        Newzone4_String = (String) dataSnapshot.child("zone4").getValue();
                                                                                                        displayNotificationError(Newzone1_String,Newzone2_String,Newzone3_String,Newzone4_String,newtime);
                                                                                                    try{
                                                                                                       mXulytungsensor.XulytungsensorforSevice(Newzone1_String,Newzone2_String,Newzone3_String,Newzone4_String);
                                                                                                        String timeYear = newtime.substring(newtime.length() - 4, newtime.length());
                                                                                                        String timeDate = newtime.substring(11, 19);
                                                                                                        String timeDay = newtime.substring(8, 10);
                                                                                                        String month = newtime.substring(3, 7);
                                                                                                        time=timeDay+month+timeYear+timeDate;
                                                                                                        String timeDate_fix=timeDate.substring(0,2)+"h:"+timeDate.substring(3,8);
                                                                                                        mXulytungsensor.Nhietdomax=Nhietdo_max;
                                                                                                        displayNotification(timeDate_fix,Nhietdo_max,timeDate);

                                                                                                    }
                                                                                                    catch (Exception e){

                                                                                                        Toast.makeText(getBaseContext(),"Đã có lỗi xảy ra, Chuổi data trả về bị lỗi",Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                      //  Toast.makeText(getBaseContext(),"Nhiet do:"+Nhietdo_max+"\n Cập nhật lúc: "+timeDate,Toast.LENGTH_SHORT).show();

                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onChildChanged(DataSnapshot dataSnapshot, String previousChildKey) {
                                                                                                        newtime = (String) dataSnapshot.child("time").getValue();
                                                                                                        Newzone1_String = (String) dataSnapshot.child("zone1").getValue();
                                                                                                        Newzone2_String = (String) dataSnapshot.child("zone2").getValue();
                                                                                                        Newzone3_String = (String) dataSnapshot.child("zone3").getValue();
                                                                                                        Newzone4_String = (String) dataSnapshot.child("zone4").getValue();
                                                                                                        displayNotificationError(Newzone1_String,Newzone2_String,Newzone3_String,Newzone4_String,newtime);
                                                                                                        try{
                                                                                                            mXulytungsensor.XulytungsensorforSevice(Newzone1_String,Newzone2_String,Newzone3_String,Newzone4_String);
                                                                                                            String timeYear = newtime.substring(newtime.length() - 4, newtime.length());
                                                                                                            String timeDate = newtime.substring(11, 19);
                                                                                                            String timeDay = newtime.substring(8, 10);
                                                                                                            String month = newtime.substring(3, 7);

                                                                                                            time=timeDay+month+timeYear+timeDate;
                                                                                                            String timeDate_fix=timeDate.substring(0,2)+"h:"+timeDate.substring(3,8);



                                                                                                            mXulytungsensor.Nhietdomax=Nhietdo_max;
                                                                                                            if(Nhietdo_max>=b1)
                                                                                                            { Intent myIntent_activity = new Intent(ListenData_MyService.this, MainActivity.class);
                                                                                                                try{myIntent_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                                                                                                 //   if(! isForeground("MainActivity"))//kiểm tra xem MainActivity có đang tắt không
                                                                                                                      startActivity(myIntent_activity);
                                                                                                                }
                                                                                                                catch (Exception e)
                                                                                                                {
                                                                                                                    return;
                                                                                                                }

                                                                                                            }
                                                                                                            displayNotification(timeDate_fix,Nhietdo_max,timeDate);
                                                                                                        }
                                                                                                        catch (Exception e){

                                                                                                            Toast.makeText(getBaseContext(),"Đã có lỗi xảy ra, Chuổi data trả về bị lỗi",Toast.LENGTH_SHORT).show();
                                                                                                        }

                                                                                                       // Toast.makeText(getBaseContext(),"Nhiet do:"+Nhietdo_max+"\n Cập nhật lúc: "+timeDate,Toast.LENGTH_SHORT).show();
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                                                                                                    }

                                                                                                    public void onChildMoved(DataSnapshot dataSnapshot, String previousChildKey) {
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(FirebaseError firebaseError) {
                                                                                                        Toast.makeText(getBaseContext(), "Not Connect to Server", Toast.LENGTH_SHORT).show();
                                                                                                    }
// Retrieve new posts as they are added to the database
                                                                                                }

            );
        }
    catch (Exception e1)
        {
//            Toast.makeText(getBaseContext(), "loi"+e1.toString(), Toast.LENGTH_LONG).show();
        }
        }
    public boolean isForeground(String myPackage) {
        //kiem tra xem activity nào đó có đang chạy hay không
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        return componentInfo.getPackageName().equals(myPackage);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        Firebase.setAndroidContext(this);
        mXulytungsensor=new Xulytungsensor();
        Toast.makeText(this, "Service Running", Toast.LENGTH_LONG).show();
        // đọc ngưỡng cảnh báo va báo động từ SharePreferences lưu trong bộ nhớ
        SharedPreferences mSharedPreferences=this.getSharedPreferences("MyData",MODE_APPEND);
        //lay URL va URLClo
        final SharedPreferences.Editor mEditor=mSharedPreferences.edit();


        try{

            Boolean rememberdata=mSharedPreferences.getBoolean("Remember",false);
            String b1_string=mSharedPreferences.getString("Nguongbaodong","40.0");//doc gia tri b1 tu SharePreferences, neu do loi thi lay mac dinh b2 la 40.0
            String a1_string=mSharedPreferences.getString("Nguongcanhbao","35.0");//doc gia tri a1 tu SharePreferences, neu do loi thi lay mac dinh b2 la 35.0
            a1=Double.parseDouble(a1_string);
            b1=Double.parseDouble(b1_string);
            Toast.makeText(getBaseContext(),"Sevice đã nhận ngưỡng cảnh báo mới",Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),"Đã xảy ra lỗi"+e.toString(),Toast.LENGTH_SHORT).show();
        }

        SharepreferenceUrl();
        //......................
        //Tạo luồng mới để lắng nghe du liêu từ fire base
        new Thread (new Runnable () {
            @Override
            public void run() {

                ListenData();
            }
        }).start ();
        return START_STICKY;
    }
    public void SharepreferenceUrl(){
        // Lay dia chi UrlCloud va UrlChildren tu SharePreferences

        mSharedPreferencesUrl=this.getSharedPreferences("Url",MODE_APPEND);
        final SharedPreferences.Editor mEditorUrl=mSharedPreferencesUrl.edit();
        try {
            Boolean rememberdata=mSharedPreferencesUrl.getBoolean("RememberUrl",false);
            String Urlchilden_Share=mSharedPreferencesUrl.getString("UrlChilden","NhaTHay");
            String Urlcloud_share=mSharedPreferencesUrl.getString("UrlCloud","https://canhbaonhietdotvtqb.firebaseio.com/");

            if(Urlchilden_Share!="" && Urlcloud_share!="")
            {
                Urlchilden=Urlchilden_Share;
                Urlcloud=Urlcloud_share;
            }
            else {
                Urlcloud="https://canhbaonhietdotvtqb.firebaseio.com/";
                Urlchilden="NhaTHay";
            }

            Toast.makeText(getBaseContext(),"UrlChilden:"+Urlchilden+"\n UrlCloud"+Urlcloud,Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),"Lỗi lấy Url"+e,Toast.LENGTH_SHORT).show();
        }
    }
    public void displayNotificationError(String sen1, String sen2 ,String sen3, String sen4, String timeupdate ){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        try{
            builder.setContentTitle("Giám sát nhiệt độ đang chạy")
                    .setContentText("Bấm để xem")
                    .setSmallIcon(R.drawable.tile_circle_red)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true);
        }
        catch (Exception e)
        {

        }
        if(timeupdate==null)
        {
            builder.setContentTitle("Cập nhật lúc \t"+time)
                    .setContentText("Không có dữ liệu, kiểm tra lại tủ phân tán\t + fail")
                    .setSmallIcon(R.drawable.tile_circle_red)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true);
        }
        if(sen1=="fail")
        {
            builder.setContentTitle("Cập nhật lúc \t"+time)
                    .setContentText("Sensor vị trí 1 bị ngắt kết nôi,kiểm tra lại Sensor1\t + fail")
                    .setSmallIcon(R.drawable.tile_circle_red)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true);
        }
        if (sen2=="fail")
        {
            builder.setContentTitle("Cập nhật lúc \t"+time)
                    .setContentText("Sensor vị trí 2 bị ngắt kết nôi,kiểm tra lại Sensor2\t + fail")
                    .setSmallIcon(R.drawable.tile_circle_red)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true);
        }
        if (sen3=="fail")
        {
            builder.setContentTitle("Cập nhật lúc \t"+time)
                    .setContentText("Sensor vị trí 3 bị ngắt kết nôi,kiểm tra lại Sensor3\t + fail")
                    .setSmallIcon(R.drawable.tile_circle_red)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true);
        }
        if (sen4=="fail")
        {
            builder.setContentTitle("Cập nhật lúc \t"+time)
                    .setContentText("Sensor vị trí 4 bị ngắt kết nôi,kiểm tra lại Sensor4\t + fail")
                    .setSmallIcon(R.drawable.tile_circle_red)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true);
        }
    }
    public void displayNotification(String time, Double Template,String timeupDate ) {//hien thị thông báo nhiệt do, trạng thai và thời gian cập nhật nhiệt độ trên thanh Notification

       try
       {
           Intent intent = new Intent(this, MainActivity.class);
           PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
           NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

           if(Template==0||time==null)
           {
               builder.setContentTitle("Cập nhật lúc \t"+time)
                       .setContentText("Các sensor đã bị ngắt kết nối\t"+Template+"°C")
                       .setSmallIcon(R.drawable.tile_circle_red)
                       .setContentIntent(pIntent)
                       .setAutoCancel(true);
           }
           else
           {
               if (Template<=a1&&Template!=0.0)
               {
                   builder.setContentTitle("Cập nhật lúc \t"+time)
                           .setContentText("Nhiệt độ bình thường\t"+Template+"°C")
                           .setSmallIcon(R.drawable.tile_circle_red)
                           .setContentIntent(pIntent)
                           .setAutoCancel(true);
               }
               else
               {
                   if(a1<=Template&&Template<=b1)
                   {
                       builder.setContentTitle("Câp nhật lúc\t"+time)
                               .setContentText("Cảnh báo \t"+Template+"°C")
                               .setSmallIcon(R.drawable.tile_circle_red)
                               .setContentIntent(pIntent)
                               .setAutoCancel(true);
                   }

                   else
                   {
                       builder.setContentTitle("Câp nhật lúc\t \t"+time)
                               .setContentText("Báo động \t"+Template+"°C")
                               .setSmallIcon(R.drawable.tile_circle_red)
                               .setContentIntent(pIntent)
                               .setAutoCancel(true);
                   }
               }
           }
           mchecktimeout=new Checktimeout();
           boolean a=true;
           if ((!mchecktimeout.TimeoutNotification(timeupDate)&&a==true))
           {
               Intent myIntent_activity = new Intent(ListenData_MyService.this, MainActivity.class);
               try{
                   myIntent_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   builder.setContentTitle("Câp nhật lúc\t \t"+time)
                           .setContentText("Mất kết nối tới hệ thống, kiểm tra kết nối internet hoặc tủ phân tán")
                           .setSmallIcon(R.drawable.tile_circle_red)
                           .setContentIntent(pIntent)
                           .setAutoCancel(true);
                   Toast.makeText(getBaseContext(),"Mất kết nối tới hệ thống kiểm tra internet hoặc tủ phân tán", Toast.LENGTH_SHORT).show();
                   if(! isForeground("MainActivity"))//kiểm tra xem MainActivity có đang tắt không
                   {startActivity(myIntent_activity);}
               }
               catch (Exception e)
               {
                   return;
               }
               a=false;
           }

           Notification notificaion = builder.build();
           NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
           int idNotifitcation = 2;
           manager.notify(idNotifitcation, notificaion);
       }
       catch (Exception e1)
       {
           Toast.makeText(getBaseContext(), "Đã xay ra lỗi"+e1.toString(), Toast.LENGTH_LONG).show();
       }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
