package buixexamplecom.theodoinhietdo2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public class MainActivity extends AppCompatActivity {
    //   @Override
    TextView  Text_Thoi_Gian_Cap_Nhat, Text_tenphanmem;
    TextView Edit_Nhiet_Do_Max;
    TextView Edit_Sen1, Edit_Sen2, Edit_Sen3,Edit_Sen4, textViewTime, TextView_Sen1, TextView_Sen2, TextView_Sen3,TextView_Sen4, TextView_nen, TextView, TextView2, TextView3,TextView4;

    ImageView Text_connect;
    ImageView Im_sen1, Im_sen2, Im_sen3,Im_sen4, Im_Sound, Im_CanhBao;
    ImageButton Button_to_bieudo;

    String newtime;
    String Newzone1_String;
    String Newzone2_String;
    String Newzone3_String;
    String Newzone4_String;
    Double Newzone1;
    Double Newzone2;
    Double Newzone3;
    Double Newzone4;
    String time;
    final String UrlCloud[]={"https://canhbaonhietdotvtqb.firebaseio.com/","https://thongtin3.firebaseio.com/","https://thongtin1.firebaseio.com/"};

    final String UrlChilden[]={"C9","NhaTHay","ThongTinCanhBao","ThongTinLuuDu1","ThuVien","TestPow"};
    String Urlchilden;
    String Urlcloud;
    // khai bao vung luu tru SharePrferences
    SharedPreferences mSharedPreferencesUrl;
    Boolean remember1;

    int i;
    double a1 = 35.0;
    double b1 = 40.0;

    Double Nhietdo_max;
    Boolean a;
    Integer[] imIDs = {
            R.drawable.icongreen,
            R.drawable.iconyelow,
            R.drawable.iconred,
            R.drawable.icongray,
            R.drawable.iconsound1,
            R.drawable.iconsound2
    };
    private Xulytungsensor mXulytungsensor;
    MediaPlayer mediaPlayer;
    Baodongloa mBaodongloa;
    Checktimeout mchChecktimeout;
    public void SetupGui(){//cai dat cac thong so lien quan den Mainactivity.xml
        Text_Thoi_Gian_Cap_Nhat = (TextView) findViewById(R.id.textView_ThoiGianCapNhat);

        Edit_Sen1 = (TextView) findViewById(R.id.EditText_Sensor1);
        Edit_Sen2 = (TextView) findViewById(R.id.Edit_text_Sensor2);
        Edit_Sen3 = (TextView) findViewById(R.id.EditText_Sensor3);
        Edit_Sen4 = (TextView) findViewById(R.id.EditText_Sensor4);
        Edit_Nhiet_Do_Max = (TextView) findViewById(R.id.Edit_NhietDoMax);
        Im_sen1 = (ImageView) findViewById(R.id.imageView_sensor1);
        Im_sen2 = (ImageView) findViewById(R.id.imageView_sensor2);
        Im_sen3 = (ImageView) findViewById(R.id.imageView_sensor3);
        Im_sen4 = (ImageView) findViewById(R.id.imageView_sensor4);
        Im_Sound = (ImageButton) findViewById(R.id.imageButton_Sound);
        Im_CanhBao = (ImageView) findViewById(R.id.imageView_CanhBao);
        Text_connect = (ImageView) findViewById(R.id.Button_Connect);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        TextView_Sen1 = (TextView) findViewById(R.id.textViewTrangthai_sen1);
        TextView_Sen2 = (TextView) findViewById(R.id.textView3_trangthaisen2);
        TextView_Sen3 = (TextView) findViewById(R.id.textView_trangthaisen3);
        TextView_Sen4 = (TextView) findViewById(R.id.textView_trangthaisen4);
        TextView_nen = (TextView) findViewById(R.id.imageViewNen);
        TextView = (TextView) findViewById(R.id.textView);
        TextView2 = (TextView) findViewById(R.id.textView2);
        TextView3 = (TextView) findViewById(R.id.textView3);
        Button_to_bieudo = (ImageButton) findViewById(R.id.button_to_bieu_do);
        Text_tenphanmem = (TextView) findViewById(R.id.textView_tenphanmem);

        Text_Thoi_Gian_Cap_Nhat.setTextColor(Color.BLUE);
        Edit_Sen1.setTextColor(Color.RED);
        Edit_Sen2.setTextColor(Color.RED);
        Edit_Sen3.setTextColor(Color.RED);
        Edit_Sen4.setTextColor(Color.RED);
        Edit_Nhiet_Do_Max.setTextColor(Color.RED);
        TextView_Sen1.setTextColor(Color.WHITE);
        TextView_Sen2.setTextColor(Color.WHITE);
        TextView_Sen3.setTextColor(Color.WHITE);
        TextView_Sen4.setTextColor(Color.WHITE);
        TextView_nen.setTextColor(Color.WHITE);
        TextView.setTextColor(Color.BLUE);
        TextView2.setTextColor(Color.BLUE);
        TextView3.setTextColor(Color.BLUE);
        Text_tenphanmem.setTextColor(Color.WHITE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetupGui();
        // day cac url vao SharedPreferences

        mXulytungsensor=new Xulytungsensor();
        mBaodongloa=new Baodongloa();
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep81);
        mchChecktimeout=new Checktimeout();
        Firebase.setAndroidContext(this);


       Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        a = true;
        // khai bao SharePreference de lay du lieu ngưỡng cảnh báo
        SharedPreferences mSharedPreferences=this.getSharedPreferences("MyData",MODE_APPEND);
        final SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        try{// lấy ngưỡng cảnh báo và báo động từ SharePreference
                Boolean rememberdata=mSharedPreferences.getBoolean("Remember",false);
                String b1_string=mSharedPreferences.getString("Nguongbaodong","40.0");//doc gia tri b1 tu SharePreferences, neu do loi thi lay mac dinh b2 la 40.0
                String a1_string=mSharedPreferences.getString("Nguongcanhbao","35.0");//doc gia tri a1 tu SharePreferences, neu do loi thi lay mac dinh b2 la 35.0
                a1=Double.parseDouble(a1_string);
                b1=Double.parseDouble(b1_string);
            Toast.makeText(getBaseContext(),"Nguong canh bao moi"+a1_string+"\n Nguong bao dong moi"+b1_string,Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),"Đã xảy ra lỗi"+e.toString(),Toast.LENGTH_SHORT).show();
        }

        SharepreferenceUrl();
        final Intent myIntent_service = new Intent(MainActivity.this, ListenData_MyService.class);
        try {
            startService(myIntent_service);
        } catch (Exception e) {

        }


        ListenData();

        Button_to_bieudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(MainActivity.this, Vebieudo.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    myIntent.putExtra("keep", false);
                    startActivity(myIntent);

                } catch (Exception e) {

                }
            }
        });

        TextView_nen.setOnClickListener(new View.OnClickListener()

                                        {
                                            @Override
                                            public void onClick(View v) {
                                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://aselab.edu.vn/index.php/vi/"));
                                                startActivity(i);
                                            }
                                        }

        );
        Im_Sound.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View v) {
                                            a = !a;

                                            if (a == false) {
                                                try {
                                                    Im_Sound.setImageResource(imIDs[5]);
                                                    mediaPlayer.pause();
                                                    stopService(myIntent_service);

                                                    Toast.makeText(getBaseContext(), "đã bấm tắt loa" , Toast.LENGTH_SHORT).show();
                                                } catch (Exception e) {

                                                }
                                            } else {
                                                try {
                                                    Im_Sound.setImageResource(imIDs[4]);
                                                    mediaPlayer.start();
                                                    myIntent_service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startService(myIntent_service);

                                                    Toast.makeText(getBaseContext(), "đã bấm bật loa" , Toast.LENGTH_SHORT).show();
                                                } catch (Exception e) {

                                                }
                                            }
                                        }

                                    }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        Intent Myintent = new Intent(this, ListenData_MyService.class);
        Myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(Myintent);
    }
    public void ListenData() {

        Firebase ref = new Firebase(Urlcloud);
        Query query = ref.orderByChild(Urlchilden).limitToLast(1);
        ChildEventListener childEventListener = ref.child(Urlchilden).addChildEventListener(new ChildEventListener() {
                                                                                                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {
                                                                                                    newtime = (String) dataSnapshot.child("time").getValue();
                                                                                                    Newzone1_String = (String) dataSnapshot.child("zone1").getValue();
                                                                                                    Newzone2_String = (String) dataSnapshot.child("zone2").getValue();
                                                                                                    Newzone3_String = (String) dataSnapshot.child("zone3").getValue();
                                                                                                    Newzone4_String = (String) dataSnapshot.child("zone4").getValue();
                                                                                                    NotificationError(newtime);


                                                                                                  try{

                                                                                                      String timeYear = newtime.substring(newtime.length() - 4, newtime.length());
                                                                                                      String timeDate = newtime.substring(11, 19);
                                                                                                      String timeDay = newtime.substring(8, 10);
                                                                                                      String dayofweek = newtime.substring(0, 3);
                                                                                                      String month = newtime.substring(3, 7);
                                                                                                      time = timeDay + month + timeYear + timeDate;
                                                                                                      Text_Thoi_Gian_Cap_Nhat.setText(dayofweek + "\t" + timeDay + ":" + month + ":" + timeYear + "\t" + timeDate);

                                                                                                      Text_connect.setImageResource(R.drawable.connect_database);
                                                                                                      mXulytungsensor.Xulytungsensor(a1, b1, Newzone1_String, Newzone2_String, Newzone3_String,Newzone4_String, Im_sen1, Im_sen2, Im_sen3,Im_sen4, Im_CanhBao, TextView_Sen1, TextView_Sen2, TextView_Sen3,TextView_Sen4, TextView2);
                                                                                                      Nhietdo_max= mXulytungsensor.Nhietdomax;
                                                                                                      Newzone1_String=mXulytungsensor.Newzone1_string;
                                                                                                      Newzone2_String=mXulytungsensor.Newzone2_string;
                                                                                                      Newzone3_String=mXulytungsensor.Newzone3_string;
                                                                                                      Newzone4_String=mXulytungsensor.Newzone4_string;
                                                                                                      Edit_Sen1.setText(Newzone1_String);
                                                                                                      Edit_Sen2.setText(Newzone2_String);
                                                                                                      Edit_Sen3.setText(Newzone3_String);
                                                                                                      Edit_Sen4.setText(Newzone4_String);


                                                                                                      mBaodongloa.Baodongloa(Nhietdo_max,b1,a,Im_Sound,mediaPlayer);

                                                                                                      Edit_Nhiet_Do_Max.setText(Nhietdo_max + "°C");


                                                                                                }

                                                                                                  catch (Exception e){
                                                                                                     NotificationError(newtime);
                                                                                                      Edit_Nhiet_Do_Max.setText("°C");

                                                                                                      Toast.makeText(getBaseContext(),"Đã có lỗi xảy ra, Chuổi data trả về không có  hoặc bị lỗi",Toast.LENGTH_SHORT).show();
                                                                                                  }
                                                                                                    }

                                                                                                @Override
                                                                                                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildKey) {
                                                                                                    newtime = (String) dataSnapshot.child("time").getValue();
                                                                                                    Newzone1_String = (String) dataSnapshot.child("zone1").getValue();
                                                                                                    Newzone2_String = (String) dataSnapshot.child("zone2").getValue();
                                                                                                    Newzone3_String = (String) dataSnapshot.child("zone3").getValue();
                                                                                                    Newzone4_String = (String) dataSnapshot.child("zone4").getValue();
                                                                                                    NotificationError(newtime);



                                                                                                    try{

                                                                                                     String timeYear = newtime.substring(newtime.length() - 4, newtime.length());
                                                                                                     String timeDate = newtime.substring(11, 19);
                                                                                                     String timeDay = newtime.substring(8, 10);
                                                                                                     String dayofweek = newtime.substring(0, 3);
                                                                                                     String month = newtime.substring(3, 7);
                                                                                                     Text_Thoi_Gian_Cap_Nhat.setText(dayofweek + "\t" + timeDay + ":" + month + ":" + timeYear + "\t" + timeDate);

                                                                                                     Text_connect.setImageResource(R.drawable.connect_database);
                                                                                                     time = timeDay + month + timeYear + timeDate;
                                                                                                     mXulytungsensor.Xulytungsensor(a1, b1, Newzone1_String, Newzone2_String, Newzone3_String,Newzone4_String, Im_sen1, Im_sen2, Im_sen3,Im_sen4, Im_CanhBao, TextView_Sen1, TextView_Sen2, TextView_Sen3,TextView_Sen4, TextView2);
                                                                                                     Nhietdo_max= mXulytungsensor.Nhietdomax;

                                                                                                     mBaodongloa.Baodongloa(Nhietdo_max,b1,a,Im_Sound,mediaPlayer);

                                                                                                     if (!mchChecktimeout.TimeoutNotification(timeDate))
                                                                                                     {
                                                                                                         Text_connect.setImageResource(R.drawable.disconnect_database);
                                                                                                     }
                                                                                                        Newzone1_String=mXulytungsensor.Newzone1_string;
                                                                                                        Newzone2_String=mXulytungsensor.Newzone2_string;
                                                                                                        Newzone3_String=mXulytungsensor.Newzone3_string;
                                                                                                        Newzone4_String=mXulytungsensor.Newzone4_string;
                                                                                                        Edit_Sen1.setText(Newzone1_String);
                                                                                                        Edit_Sen2.setText(Newzone2_String);
                                                                                                        Edit_Sen3.setText(Newzone3_String);
                                                                                                        Edit_Sen4.setText(Newzone4_String);
                                                                                                        Edit_Nhiet_Do_Max.setText(Nhietdo_max.toString() + "°C");

                                                                                                }
                                                                                                catch (Exception e){
                                                                                                    NotificationError(newtime);

                                                                                                    Toast.makeText(getBaseContext(),"Đã có lỗi xảy ra, Chuổi data trả về không có hoặc bị lỗi",Toast.LENGTH_SHORT).show();

                                                                                                }}

                                                                                                @Override
                                                                                                public void onChildRemoved(DataSnapshot dataSnapshot) {
                                                                                                }

                                                                                                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildKey) {
                                                                                                }

                                                                                                @Override
                                                                                                public void onCancelled(FirebaseError firebaseError) {
                                                                                                    Toast.makeText(getBaseContext(), "Not Connect to Server", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }
        );
    }

    public void NotificationError(String timeupdate){
        if(timeupdate==null)
        {
            Text_Thoi_Gian_Cap_Nhat.setText("fail");
        }
    }
    public void SharepreferenceUrl(){
    // Lay dia chi UrlCloud va UrlChildren tu SharePreferences

    mSharedPreferencesUrl=this.getSharedPreferences("Url",MODE_APPEND);
    final SharedPreferences.Editor mEditorUrl=mSharedPreferencesUrl.edit();
    try {
        Boolean RememberUrl=mSharedPreferencesUrl.getBoolean("RememberUrl",false);
        String Urlchilden_Share=mSharedPreferencesUrl.getString("UrlChilden","NhaTHay");
        String Urlcloud_share=mSharedPreferencesUrl.getString("UrlCloud","https://canhbaonhietdotvtqb.firebaseio.com/");

        if(Urlchilden_Share!="" && Urlcloud_share!="")
        {
            Urlchilden=Urlchilden_Share;
            Urlcloud=Urlcloud_share;
            SetUrlChirden();
            SetUrlcloud();
        }
        else {
            Urlcloud="https://canhbaonhietdotvtqb.firebaseio.com/";
            Urlchilden="NhaTHay";
            SetUrlChirden();
            SetUrlcloud();
        }

        Toast.makeText(getBaseContext(),"UrlChilden:"+Urlchilden+"\n UrlCloud"+Urlcloud,Toast.LENGTH_SHORT).show();

    }
    catch (Exception e){
        Toast.makeText(getBaseContext(),"Lỗi lấy Url"+e,Toast.LENGTH_SHORT).show();
    }
}
    public String SetUrlcloud(){
        return Urlcloud;
    }
    public String SetUrlChirden(){
        return Urlchilden;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /**
     * On selecting action bar icons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.changeAlarm:
                // search action
                changeAlarm();
                return true;
            case R.id.changeLinkCloud:
                changeLinkCloud();
                // location found
                return true;
            case R.id.FireBaseCloud:

                return true;
            case R.id.FireBaseCloud1:
                ChangCloudtoFireBaseCloud1();
                return true;
            case R.id.FireBaseCloud2:
                ChangCloudtoFireBaseCloud2();
                return true;
            case R.id.FireBaseCloud3:
                ChangCloudtoFireBaseCloud3();
                return true;
            case R.id.FireBaseCloud4:
                return true;

            case R.id.TruyCapFireBase:
                // refresh
                truycapFireBase();
                return true;
            case R.id.ThingSpeakCloud:
                truycapThingSpeak();
                return true;
            case R.id.ExitAll:
                // help action
                ExitAll();
                return true;
            case R.id.are1:
                // Action
                OpenAre1();
                return true;
            case R.id.are2:
                //Action
                OpenAre2();
                return true;
            case R.id.are3:
                OpenAre3();
                //Action
                return true;
            case R.id.are4:
                //Action
                OpenAre4();
                return true;
            case R.id.are5:
                //Action
                OpenAre5();
                return true;
            case R.id.are6:
                OpenAre6();
                //Action
                return true;
            case R.id.Setting:
                // search action

                return true;
            case R.id.SelectSaveCloud:

                // location found
                return true;
            case R.id.SaveCloud1:

                // location found
                return true;
            case R.id.SaveCloud2:

                // location found
                return true;
            case R.id.SaveCloud3:

                // location found
                return true;
            case R.id.SaveCloud4:

                // location found
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void changeAlarm(){
        Intent intent_to_settingAlarm=new Intent(MainActivity.this,settingactivity.class);
        intent_to_settingAlarm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent_to_settingAlarm.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        onStop();
        startActivity(intent_to_settingAlarm);
    }
// Hàm thay dổi địa chỉ lấy dữ liệu, hiên tại chỉ có firebase và thingspeak
    public void changeLinkCloud(){

    }
    public void truycapThingSpeak() {
    }

    public void truycapFireBase(){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://canhbaonhietdotvtqb.firebaseio.com/"));
        startActivity(i);
    }
    //    final String UrlChilden[]={"C9","NhaTHay","ThongTinCanhBao","ThongTinLuuDu","ThuVien","TestPow"};

    public void OpenAre1(){
        mSharedPreferencesUrl=this.getSharedPreferences("Url",MODE_APPEND);
        final SharedPreferences.Editor mEditorUrl=mSharedPreferencesUrl.edit();
        remember1=mSharedPreferencesUrl.getBoolean("RememberUrl",false);
        mEditorUrl.putString("UrlChilden",UrlChilden[0]);
        mEditorUrl.commit();
        Toast.makeText(getBaseContext(), "Đã thay đổi vùng giám sát \n Vùng giám sát hiện tại:\t "+Urlchilden, Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void OpenAre2(){
        Intent intent = getIntent();
        mSharedPreferencesUrl=this.getSharedPreferences("Url",MODE_APPEND);
        final SharedPreferences.Editor mEditorUrl=mSharedPreferencesUrl.edit();
        remember1=mSharedPreferencesUrl.getBoolean("RememberUrl",false);
        mEditorUrl.putString("UrlChilden",UrlChilden[1]);
        mEditorUrl.commit();
        Toast.makeText(getBaseContext(), "Đã thay đổi vùng giám sát \n Vùng giám sát hiện tại:\t "+Urlchilden, Toast.LENGTH_SHORT).show();
        finish();
        startActivity(intent);
    }

    public void OpenAre3(){

        Intent intent = getIntent();
        mSharedPreferencesUrl=this.getSharedPreferences("Url",MODE_APPEND);
        final SharedPreferences.Editor mEditorUrl=mSharedPreferencesUrl.edit();
        remember1=mSharedPreferencesUrl.getBoolean("RememberUrl",false);
        mEditorUrl.putString("UrlChilden",UrlChilden[4]);
        mEditorUrl.commit();
        Toast.makeText(getBaseContext(), "Đã thay đổi vùng giám sát \n Vùng giám sát hiện tại:\t "+Urlchilden, Toast.LENGTH_SHORT).show();
        finish();
        startActivity(intent);

      }
    public void OpenAre4(){// hien chua co them vùng giam sát- câp nhật ngày 14/04/2016

    }
    public void OpenAre5(){ //hien chua co them vùng giam sát- câp nhật ngày 14/04/2016

    }
    public void OpenAre6(){// hien chua co them vùng giam sát- câp nhật ngày 14/04/2016

    }
    public void ChangCloudtoFireBaseCloud1(){
        Intent intent = getIntent();
        mSharedPreferencesUrl=this.getSharedPreferences("Url",MODE_APPEND);
        final SharedPreferences.Editor mEditorUrl=mSharedPreferencesUrl.edit();
        remember1=mSharedPreferencesUrl.getBoolean("RememberUrl",false);
        mEditorUrl.putString("UrlCloud",UrlCloud[0]);
        mEditorUrl.commit();
        finish();
        startActivity(intent);
    }
    public void ChangCloudtoFireBaseCloud2(){
        Intent intent = getIntent();
        mSharedPreferencesUrl=this.getSharedPreferences("Url",MODE_APPEND);
        final SharedPreferences.Editor mEditorUrl=mSharedPreferencesUrl.edit();
        remember1=mSharedPreferencesUrl.getBoolean("RememberUrl",false);
        mEditorUrl.putString("UrlCloud",UrlCloud[1]);
        mEditorUrl.commit();
        finish();
        startActivity(intent);
    }
    public void ChangCloudtoFireBaseCloud3(){
        Intent intent = getIntent();
        mSharedPreferencesUrl=this.getSharedPreferences("Url",MODE_APPEND);
        final SharedPreferences.Editor mEditorUrl=mSharedPreferencesUrl.edit();
        remember1=mSharedPreferencesUrl.getBoolean("RememberUrl",false);
        mEditorUrl.putString("UrlCloud",UrlCloud[2]);
        mEditorUrl.commit();
        finish();
        startActivity(intent);
    }

    public void ChangCloudtoFireBaseCloud4(){

    }

    public void ExitAll(){
        Intent myIntent = new Intent(MainActivity.this, ListenData_MyService.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        stopService(myIntent);
        finish();
        System.exit(0);
    }

    /**
     * Launching new activity
*/
    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API
    }


}
