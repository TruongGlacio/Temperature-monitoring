package buixexamplecom.theodoinhietdo2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Vebieudo extends AppCompatActivity {
Xulytungsensor mXulytungsensor;
    ImageButton Button_toMain;
    String UrlCloud[]={"https://canhbaonhietdotvtqb.firebaseio.com/ThongTinLuuDu/","https://thongtin3.firebaseio.com/","https://thongtin1.firebaseio.com/ThongTinLuuDu1/"};
    final ArrayGetData mArrayGetData=new ArrayGetData();
    final ArrayList<Integer> SentdataZone1=new ArrayList<>();
    final ArrayList<Integer> SentdataZone2=new ArrayList<>();
    final ArrayList<Integer> SentdataZone3=new ArrayList<>();
    final ArrayList<Integer> SentdataZone4=new ArrayList<>();
    final ArrayList<String> SentdataTime=new ArrayList<>();
    final ArrayList<Integer>NhietdoMax=new ArrayList<>();
    String time;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vebieudo);
        Firebase.setAndroidContext(this);

        TextView TextView_tenLab = (TextView) findViewById(R.id.textView_Tenlab);
        TextView TextView_Tenphanmem = (TextView) findViewById(R.id.textViewTenphanmem);
        Button_toMain = (ImageButton) findViewById(R.id.button_to_Main);

        TextView_tenLab.setTextColor(Color.WHITE);
        TextView_Tenphanmem.setTextColor(Color.WHITE);
        mXulytungsensor=new Xulytungsensor();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Date today=new Date(System.currentTimeMillis());
                SimpleDateFormat timeFormat= new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat timessecons= new SimpleDateFormat("ss");
                SimpleDateFormat timeMinute= new SimpleDateFormat("mm");
                SimpleDateFormat timehour= new SimpleDateFormat("HH");

                String s=timeFormat.format(today.getTime());
                int secons=Integer.parseInt(timessecons.format(today.getTime()));
                int minute=Integer.parseInt(timeMinute.format(today.getTime()));
                int hour=Integer.parseInt(timehour.format(today.getTime()));
                int timesystem=hour*60+minute;
                ListenData(s,timesystem);


            }
        }).start();

        Button_toMain.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent myIntent = new Intent(Vebieudo.this, MainActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                myIntent.putExtra("keep", false);
                startActivity(myIntent);
            }


        });
    }

    public void ListenData(String s, final int timesys){


        try {
            Firebase ref = new Firebase(UrlCloud[2]);
            Query query = ref.orderByChild(s).limitToLast(10);

            ChildEventListener childEventListener = ref.child(s).addChildEventListener(new ChildEventListener()
            {   int Newzone1;
                int Newzone2;
                int Newzone3;
                int Newzone4;
                int Nhietdo_max;
                String newtime;
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    newtime = (String) dataSnapshot.child("time").getValue();
                    String Newzone1_String = (String) dataSnapshot.child("zone1").getValue();
                    String Newzone2_String = (String) dataSnapshot.child("zone2").getValue();
                    String Newzone3_String = (String) dataSnapshot.child("zone3").getValue();
                    String Newzone4_String = (String) dataSnapshot.child("zone4").getValue();

                   try{
                       String timeDate = newtime.substring(11, 19);
                       String hour=timeDate.substring(0,2)+"h";
                       int Timehour=Integer.parseInt(timeDate.substring(0,2));
                       int Timeminute=Integer.parseInt(timeDate.substring(3,5));
                       int timeupdate=Timehour*60+Timeminute;

                       mXulytungsensor.Xulytungsensorforbieudo(Newzone1_String,Newzone2_String,Newzone3_String,Newzone4_String);
                       Newzone1_String=mXulytungsensor.Newzone1_string;
                       Newzone2_String=mXulytungsensor.Newzone2_string;
                       Newzone3_String=mXulytungsensor.Newzone3_string;
                       Newzone4_String=mXulytungsensor.Newzone4_string;
                       Newzone1 = Integer.parseInt(Newzone1_String.substring(0, Newzone1_String.length() -3));
                       Newzone2 =Integer.parseInt(Newzone2_String.substring(0, Newzone2_String.length() - 3));
                       Newzone3 =Integer.parseInt(Newzone3_String.substring(0, Newzone3_String.length() - 3));
                       Newzone4 =Integer.parseInt(Newzone4_String.substring(0, Newzone4_String.length() - 3));
                       //Nhietdo_max=(int) mXulytungsensor.Nhietdomax;
                       Nhietdo_max = (Math.max(Newzone4,Math.max(Newzone1, Math.max(Newzone2, Newzone3))));
                       mArrayGetData.ArrayGetData(hour,Newzone1,Newzone2,Newzone3,Newzone4,Nhietdo_max,SentdataTime,SentdataZone1,SentdataZone2,SentdataZone3,SentdataZone4,NhietdoMax);
                       try {
                           if(timeupdate>=timesys-5)
                           { int a=SentdataTime.size();
                               chart(SentdataTime,NhietdoMax,SentdataZone1,SentdataZone2,SentdataZone3,SentdataZone4, a );
                           }}
                       catch (Exception e)
                       {
                           Toast.makeText(getBaseContext(),"Đã có lỗi xảy ra \n:"+e.toString(),Toast.LENGTH_SHORT).show();
                       }
                   }
                   catch (Exception e)
                   {
                       Toast.makeText(getBaseContext(),"Đã có lỗi: 1 hoặc vài" +
                               " chuỗi data nhận về bị hỏng \n"+e.toString(),Toast.LENGTH_SHORT).show();
                   }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }
        catch (Exception e)
        {
            Toast.makeText(getBaseContext(),"NOt Information from Firebase"+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }


    public void chart(ArrayList<String> arrayListtime,ArrayList<Integer> arrayList_max,ArrayList<Integer> arrayList1,ArrayList<Integer>arrayList2,ArrayList<Integer>arrayList3,ArrayList<Integer>arrayList4,int arraysize){
       Date today=new Date(System.currentTimeMillis());
       SimpleDateFormat timeFormat= new SimpleDateFormat("dd-MM-yyyy");
       String s=timeFormat.format(today.getTime());

        LineChart lineChart_max = (LineChart)findViewById(R.id.chart_container_max);
        LineChart lineChart1 = (LineChart)findViewById(R.id.chart_container1);
       LineChart lineChart2 = (LineChart)findViewById(R.id.chart_container2);
       LineChart lineChart3 = (LineChart)findViewById(R.id.chart_container3);
       LineChart lineChart4 = (LineChart)findViewById(R.id.chart_container4);

        ArrayList<Entry> entries_max = new ArrayList<>();
       ArrayList<Entry> entries1 = new ArrayList<>();
       ArrayList<Entry>entries2=new ArrayList<>();
       ArrayList<Entry>entries3=new ArrayList<>();
       ArrayList<Entry>entries4=new ArrayList<>();


        for(int i=0;i<=arraysize/60;i++) {
            entries_max.add(new Entry(arrayList_max.get(i*60),i));
            entries1.add(new Entry(arrayList1.get(i*60),i));
            entries2.add(new Entry(arrayList2.get(i*60),i));
            entries3.add(new Entry(arrayList3.get(i*60),i));
            entries4.add(new Entry(arrayList4.get(i*60),i));
        }

        LineDataSet dataset_max = new LineDataSet(entries_max, "Biểu đồ nhiệt độ trong ngày "+ s);
        LineDataSet dataset1 = new LineDataSet(entries1, "Nhiệt độ sensor 1");
        LineDataSet dataset2 = new LineDataSet(entries2, "Nhiệt độ sensor 2 ");
        LineDataSet dataset3 = new LineDataSet(entries3, "Nhiệt độ sensor 3 ");
        LineDataSet dataset4 = new LineDataSet(entries4, "Nhiệt độ sensor 4 ");

        ArrayList<String> labels_max = new ArrayList<String>();
       ArrayList<String> labels1 = new ArrayList<String>();
       ArrayList<String> labels2 = new ArrayList<String>();
       ArrayList<String> labels3 = new ArrayList<String>();
       ArrayList<String> labels4 = new ArrayList<String>();
       for (int i=0;i<=arraysize/60;i++) {
           labels_max.add(arrayListtime.get(60*i));
           labels1.add("");
           labels2.add("");
           labels3.add("");
           labels4.add("");
       }

       LineData data_max = new LineData(labels_max, dataset_max);
        LineData data1 = new LineData(labels1, dataset1);
       LineData data2 = new LineData(labels2, dataset2);
       LineData data3 = new LineData(labels3, dataset3);
       LineData data4 = new LineData(labels4, dataset4);


       dataset_max.setColors(ColorTemplate.COLORFUL_COLORS); //
       dataset1.setColors(ColorTemplate.COLORFUL_COLORS); //
       dataset2.setColors(ColorTemplate.JOYFUL_COLORS);
       dataset3.setColors(ColorTemplate.JOYFUL_COLORS);
       dataset4.setColors(ColorTemplate.COLORFUL_COLORS);


        dataset_max.setDrawCubic(true);
        dataset_max.setDrawFilled(true);
        dataset1.setDrawCubic(true);
        dataset1.setDrawFilled(true);
        dataset2.setDrawCubic(true);
        dataset2.setDrawFilled(true);
        dataset3.setDrawCubic(true);
        dataset3.setDrawFilled(true);
        dataset4.setDrawCubic(true);
        dataset4.setDrawFilled(true);

        lineChart_max.setData(data_max);
        lineChart1.setData(data1);
        lineChart2.setData(data2);
       lineChart3.setData(data3);
       lineChart4.setData(data4);

        lineChart_max.animateY(5);
        lineChart1.animateY(5);
        lineChart2.animateY(5);
        lineChart3.animateY(5);
        lineChart4.animateY(5);


   }
}