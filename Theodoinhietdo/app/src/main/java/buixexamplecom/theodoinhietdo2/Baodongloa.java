package buixexamplecom.theodoinhietdo2;

import android.media.MediaPlayer;
import android.widget.ImageView;

/**
 * Created by Buixu on 01/06/2016.
 */
public class Baodongloa {
    Integer[] imIDs = {
            R.drawable.icongreen,
            R.drawable.iconyelow,
            R.drawable.iconred,
            R.drawable.icongray,
            R.drawable.iconsound1,
            R.drawable.iconsound2,
            R.drawable.tile_circle_red

    };
    void Baodongloa(Double Nhietdomax, Double nguongBaodong, Boolean a, ImageView Im_Sound, MediaPlayer mediaPlayer)
    {
        if (Nhietdomax <= nguongBaodong)
        {
            try {
                Im_Sound.setImageResource(imIDs[5]);
                mediaPlayer.stop();
            } catch (Exception e) {
                //   Toast.makeText(getBaseContext(), "Đã có lỗi xảy ra" + e, Toast.LENGTH_SHORT).show();

            }

        } else {
            if (Nhietdomax > nguongBaodong && a == true) {
                try {
                    if (!mediaPlayer.isPlaying())
                        Im_Sound.setImageResource(imIDs[4]);
                    mediaPlayer.start();
                    //      mediaPlayer.setLooping(true);
                } catch (Exception e1) {
                    //      Toast.makeText(getBaseContext(), "Đã có lỗi xảy ra" + e1, Toast.LENGTH_SHORT).show();
                }
            }
            if (Nhietdomax > nguongBaodong && a == false)
            {
                try {
                    Im_Sound.setImageResource(imIDs[5]);
                    mediaPlayer.pause();
                } catch (Exception e1) {
                    //        Toast.makeText(getBaseContext(), "Đã có lỗi xảy ra" + e1, Toast.LENGTH_SHORT).show();
                }
            }


        }
    }
}
