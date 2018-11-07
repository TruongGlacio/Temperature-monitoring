package buixexamplecom.theodoinhietdo2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver_Completed extends BroadcastReceiver {
    public MyReceiver_Completed() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent mIntent=new Intent(context,MainActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
