package buixexamplecom.theodoinhietdo2;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Buixu on 01/06/2016.
 */
public class Xulytungsensor {
    int i;
    Double Nhietdomax=0.0;
    String Newzone1_string,Newzone2_string,Newzone3_string,Newzone4_string;
    double Newzone1_double,Newzone2_double,Newzone3_double,Newzone4_double;
    double[]Nhietdosensor_double={ Newzone1_double,Newzone2_double,Newzone3_double,Newzone4_double};
    Integer[] imIDs = {
            R.drawable.icongreen,
            R.drawable.iconyelow,
            R.drawable.iconred,
            R.drawable.icongray,
            R.drawable.iconsound1,
            R.drawable.iconsound2,
            R.drawable.tile_circle_red

    };
   public void Xulytungsensor(Double a, Double b, String Newzone1_String, String Newzone2_String, String Newzone3_String, String Newzone4_String , ImageView Im_sen1, ImageView Im_sen2, ImageView Im_sen3, ImageView Im_sen4, ImageView Im_Nhietdomax, TextView Textsen1, TextView Textsen2, TextView Textsen3, TextView Textsen4, TextView TextNhietdomax_trangthai)
    {
        String[] Nhietdosensor_string={Newzone1_String,Newzone2_String,Newzone3_String,Newzone4_String};

        ImageView [] Imviewsensor={Im_sen1,Im_sen2,Im_sen3,Im_sen4};
        TextView [] Textsensor={Textsen1,Textsen2,Textsen3,Textsen4};

        for(int i=0;i<Nhietdosensor_string.length;i++)
        {
            if (Nhietdosensor_string[i]=="fail")
            {   Nhietdosensor_string[i]="0.0";
                Nhietdosensor_double[i]=0;
                Textsensor[i].setText("Mất kết nối");
                Textsensor[i].setTextColor(Color.RED);
                Imviewsensor[i].setImageResource(imIDs[3]);

            }
            else
            {
                try {
                    Nhietdosensor_double[i]=Double.parseDouble(Nhietdosensor_string[i].substring(0, Nhietdosensor_string[i].length()-2));

                }
                catch (Exception e )
                {
                    Nhietdosensor_double[i]=0.0;
                    Nhietdosensor_string[i]="No Data";
                    Textsensor[i].setText("Mất kết nối");
                    Textsensor[i].setTextColor(Color.RED);
                    Imviewsensor[i].setImageResource(imIDs[3]);
                }
                Newzone1_string=Nhietdosensor_string[0];
                Newzone2_string=Nhietdosensor_string[1];
                Newzone3_string=Nhietdosensor_string[2];
                Newzone4_string=Nhietdosensor_string[3];
                if(Nhietdosensor_double[i]>0 && Nhietdosensor_double[i] <a)
                {
                    Textsensor[i].setText("Bình thường");
                    Textsensor[i].setTextColor(Color.WHITE);
                    Imviewsensor[i].setImageResource(imIDs[0]);

                }
                if(Nhietdosensor_double[i]>=a && Nhietdosensor_double[i] <b)
                {
                    Textsensor[i].setText("Cảnh báo");
                    Textsensor[i].setTextColor(Color.YELLOW);
                    Imviewsensor[i].setImageResource(imIDs[1]);

                }
                if(Nhietdosensor_double[i]>=b)
                {
                    Textsensor[i].setText("Báo động");
                    Textsensor[i].setTextColor(Color.RED);
                    Imviewsensor[i].setImageResource(imIDs[2]);
                }



            }

            Nhietdomax=Math.max(Nhietdosensor_double[0],Math.max(Nhietdosensor_double[1],Math.max(Nhietdosensor_double[2],Nhietdosensor_double[3])));
           // Toast.makeText()
            if(Nhietdomax>0 && Nhietdomax<a)
            {
                Im_Nhietdomax.setImageResource(imIDs[0]);

                TextNhietdomax_trangthai.setText("Bình thường");
                TextNhietdomax_trangthai.setTextColor(Color.WHITE);

            }
            if(Nhietdomax>=a && Nhietdomax<b)
            {
                Im_Nhietdomax.setImageResource(imIDs[1]);
                TextNhietdomax_trangthai.setText("Cảnh báo");
                TextNhietdomax_trangthai.setTextColor(Color.YELLOW);
            }
            if(Nhietdomax>b)
            {
                Im_Nhietdomax.setImageResource(imIDs[6]);
                TextNhietdomax_trangthai.setText("Báo động");
                TextNhietdomax_trangthai.setTextColor(Color.RED);
            }
            if(Nhietdomax==0)
            {
                Im_Nhietdomax.setImageResource(imIDs[3]);
                TextNhietdomax_trangthai.setText("No data");
                TextNhietdomax_trangthai.setTextColor(Color.RED);
            }

        }

        Newzone1_String= String.valueOf(Nhietdosensor_double[0]);
        Newzone2_String= String.valueOf(Nhietdosensor_double[1]);
        Newzone3_String= String.valueOf(Nhietdosensor_double[2]);
        Newzone4_String= String.valueOf(Nhietdosensor_double[3]);

    }
    public void XulytungsensorforSevice(String Newzone1_String, String Newzone2_String, String Newzone3_String, String Newzone4_String)
    {
        String[] Nhietdosensor_string={Newzone1_String,Newzone2_String,Newzone3_String,Newzone4_String};

        for(int i=0;i<Nhietdosensor_string.length;i++)
        {
            if (Nhietdosensor_string[i]=="fail")
            {   Nhietdosensor_string[i]="0.0";
                Nhietdosensor_double[i]=0;

            }
            else
            {
                try {
                    Nhietdosensor_double[i]=Double.parseDouble(Nhietdosensor_string[i].substring(0, Nhietdosensor_string[i].length()-2));

                }
                catch (Exception e )
                {
                    Nhietdosensor_double[i]=0.0;

                }

            }

            Nhietdomax=Math.max(Nhietdosensor_double[0],Math.max(Nhietdosensor_double[1],Math.max(Nhietdosensor_double[2],Nhietdosensor_double[3])));
            // Toast.makeText()

        }

    }
    public void Xulytungsensorforbieudo(String Newzone1_String, String Newzone2_String, String Newzone3_String, String Newzone4_String)
    {
        String[] Nhietdosensor_string={Newzone1_String,Newzone2_String,Newzone3_String,Newzone4_String};

        for(int i=0;i<Nhietdosensor_string.length;i++)
        {
            if (Nhietdosensor_string[i]=="fail")
            {   Nhietdosensor_string[i]="00.0";
                Nhietdosensor_double[i]=0;

            }
            else
            {
                try {
                    Nhietdosensor_double[i]=Double.parseDouble(Nhietdosensor_string[i].substring(0, Nhietdosensor_string[i].length()-2));

                }
                catch (Exception e )
                {
                    Nhietdosensor_double[i]=0.0;

                }

            }

            Nhietdomax=Math.max(Nhietdosensor_double[0],Math.max(Nhietdosensor_double[1],Math.max(Nhietdosensor_double[2],Nhietdosensor_double[3])));
            // Toast.makeText()

        }

    }

    }
