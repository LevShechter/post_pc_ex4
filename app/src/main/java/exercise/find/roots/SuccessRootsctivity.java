package exercise.find.roots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SuccessRootsctivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_roots);
        TextView original_number = findViewById(R.id.original_number);
        TextView root1 = findViewById(R.id.root1);
        TextView root2 = findViewById(R.id.root2);
        TextView time_sec = findViewById(R.id.time_sec);
        Intent intent = getIntent();
        String origin_num_str = "given number: " + intent.getLongExtra("original_number", 0);
        String root_1_str = "root A: " + intent.getLongExtra("root1", 0);
        String root_2_str = "root B: " + intent.getLongExtra("root2", 0);
        String time_sec_str = "time in sec: " + intent.getLongExtra("time_until_give_ip_seconds", 0);
        original_number.setText(origin_num_str);
        root1.setText(root_1_str);
        root2.setText(root_2_str);
        time_sec.setText(time_sec_str);


    }
}
