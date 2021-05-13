package exercise.find.roots;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class CalculateRootsService extends IntentService {


  public CalculateRootsService() {
    super("CalculateRootsService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    if (intent == null) return;

    Intent toSendIntent = new Intent();
    long timeStartMs = System.currentTimeMillis();
    long numberToCalculateRootsFor = intent.getLongExtra("number_for_service", 0);
    if (numberToCalculateRootsFor <= 0) {
      Log.e("CalculateRootsService", "can't calculate roots for non-positive input" + numberToCalculateRootsFor);
      return;
    }
    /*
    TODO:
     calculate the roots.
     check the time (using `System.currentTimeMillis()`) and stop calculations if can't find an answer after 20 seconds
     upon success (found a root, or found that the input number is prime):
      send broadcast with action "found_roots" and with extras:
       - "original_number"(long)
       - "root1"(long)
       - "root2"(long)
     upon failure (giving up after 20 seconds without an answer):
      send broadcast with action "stopped_calculations" and with extras:
       - "original_number"(long)
       - "time_until_give_up_seconds"(long) the time we tried calculating

      examples:
       for input "33", roots are (3, 11)
       for input "30", roots can be (3, 10) or (2, 15) or other options
       for input "17", roots are (17, 1)
       for input "829851628752296034247307144300617649465159", after 20 seconds give up

     */
    long long_time = 0;
    long cur_success_time = 0;
    boolean flag_pass_20 = false;
    long sqrt = (long) Math.ceil(Math.sqrt(numberToCalculateRootsFor) + 1);
    boolean flag_found = false;
    long root1 = 0, root2 = 0;
//    while (!flag_found && !flag_pass_20)
//    {
      for (long i = 2; i < sqrt; i++) {
        if (numberToCalculateRootsFor % i == 0) {
          root1 = i;
          root2 = (long) (numberToCalculateRootsFor / i);
          cur_success_time = System.currentTimeMillis();

          flag_found = true;
          break;
        }
        long time_dif = System.currentTimeMillis() - timeStartMs;
        long dif = TimeUnit.MILLISECONDS.toSeconds(time_dif);
        if(dif >= 20)
        {
          long_time = dif;
          flag_pass_20 = true;
          break;
        }
      }
      if(!flag_found && !flag_pass_20) {
        root1 = 1;
        root2 = numberToCalculateRootsFor;
        cur_success_time = System.currentTimeMillis();
        flag_found = true;
//        break;
      }
//  }
    long good_time_dif = TimeUnit.MILLISECONDS.toSeconds(cur_success_time - timeStartMs);

    if(flag_found && good_time_dif < 20) {
      toSendIntent.setAction("found_roots");
      toSendIntent.putExtra("original_number", numberToCalculateRootsFor);
      toSendIntent.putExtra("root1", root1);
      toSendIntent.putExtra("root2", root2);
      toSendIntent.putExtra("time_until_give_up_seconds", (long)(good_time_dif / 1000));
    }
    else {
    toSendIntent.setAction("stopped_calculations");
    toSendIntent.putExtra("original_number", numberToCalculateRootsFor);
    toSendIntent.putExtra("time_until_give_up_seconds", (long)(long_time / 1000));
  }
    sendBroadcast(toSendIntent);


  }

}
