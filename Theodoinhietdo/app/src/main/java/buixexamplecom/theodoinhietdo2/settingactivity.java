package buixexamplecom.theodoinhietdo2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.tech.NfcBarcode;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static buixexamplecom.theodoinhietdo2.R.xml.activity_setting;

public class settingactivity extends AppCompatActivity {
    Button button_Ok, button_cancel;
    EditText editText_nguongBaodong, editText_nguongCanhbao;
    TextView textView5, textView6;
    Double a1, b1;

    @Override


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_setting);

        button_Ok = (Button) findViewById (R.id.button_OK);
        button_cancel = (Button) findViewById (R.id.button_cancel);
        editText_nguongBaodong = (EditText) findViewById (R.id.textViewbaodong_setting);
        editText_nguongCanhbao = (EditText) findViewById (R.id.textView_canhbao_setting);
        textView5 = (TextView) findViewById (R.id.textView5);
        textView6 = (TextView) findViewById (R.id.textView6);
        editText_nguongBaodong.setTextColor (Color.BLACK);
        editText_nguongCanhbao.setTextColor (Color.BLACK);
        textView5.setTextColor (Color.WHITE);
        textView6.setTextColor (Color.WHITE);
        final Intent myintenttomain = new Intent (settingactivity.this, MainActivity.class);
        final Intent myintenttosevice = new Intent (settingactivity.this, ListenData_MyService.class);
        //Tao SHarePreferences de luudu lieu
        SharedPreferences mSharedPreferences=this.getSharedPreferences("MyData",MODE_APPEND);
        final SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        Boolean rememberdata=mSharedPreferences.getBoolean("Remember",false);
        //Hien thi ban dau cho nguong canh bao va  nguong bao dong khi mo setting

        String b1_string=mSharedPreferences.getString("Nguongbaodong","40.0");//doc gia tri b1 tu SharePreferences, neu do loi thi lay mac dinh b2 la 40.0
        String a1_string=mSharedPreferences.getString("Nguongcanhbao","35.0");
        editText_nguongCanhbao.setText(a1_string);
        editText_nguongBaodong.setText(b1_string);
        //..................

        button_Ok.setOnClickListener (new View.OnClickListener ( ) {// hoan thanh cai dat va mo lai activity_main
            @Override
            public void onClick(View v) {

                String nguongbaodong = editText_nguongBaodong.getText ( ).toString ( );
                String nguongcanhbao = editText_nguongCanhbao.getText ( ).toString ( );

                if((editText_nguongBaodong.getText().toString().equals("")==true)||(editText_nguongCanhbao.getText().toString().equals("")==true))
                {
                    Toast.makeText (getBaseContext ( ), "Chưa có dữ liệu, nhâp lại", Toast.LENGTH_SHORT).show ( );
                }
                else
                {
                try {
                    b1 = Double.parseDouble (nguongbaodong);
                    a1 = Double.parseDouble (nguongcanhbao);
                } catch (Exception e) {

                }

                if (b1 <= a1) {
                    Toast.makeText (getBaseContext ( ), "Ngưỡng báo động phải lớn hơn ngưỡng cảnh báo, hay nhập lại", Toast.LENGTH_SHORT).show ( );
                } else {
                    try {

                        try {
                            mEditor.putString("Nguongbaodong", b1.toString());
                            mEditor.putString("Nguongcanhbao", a1.toString());
                            mEditor.putBoolean("Remember", true);
                            mEditor.commit();

                            editText_nguongBaodong.setText(a1.toString());
                            editText_nguongCanhbao.setText(b1.toString());
                            Toast.makeText(getBaseContext(),"Da luu muc canh bao va muc bao dong",Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getBaseContext(),"Có lỗi xảy ra"+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                        startActivity (myintenttomain);
                        startService (myintenttosevice);
                        finish ();
                        myintenttomain.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        myintenttomain.addFlags (Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    } catch (Exception e) {
                        return;
                    }
                }


            }}
        });
        button_cancel.setOnClickListener (new View.OnClickListener ( ) {// bo qua cai dat, quay tro lai activity_main
            @Override
            public void onClick(View v) {
                myintenttomain.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myintenttomain.addFlags (Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity (myintenttomain);
            }
        });
    }
    @Override
    protected void onStop(){// khi tat activity_setting  boi nut back thi se quay tro lai main_activity
        super.onStop();
        final Intent myintenttomain = new Intent (settingactivity.this, MainActivity.class);
        myintenttomain.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        myintenttomain.addFlags (Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(myintenttomain);
    }
  /**@Override
  protected void onDestroy(){
        super.onDestroy();
      final Intent myintenttomain = new Intent (settingactivity.this, MainActivity.class);
       myintenttomain.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
       myintenttomain.addFlags (Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(myintenttomain);

    }*/




}