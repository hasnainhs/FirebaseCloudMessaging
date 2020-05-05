package com.example.firebasemessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        TextView token_text = (TextView) findViewById(R.id.token);
                        if (task.isSuccessful()){
                            String token = task.getResult().getToken();
                            if (!token.isEmpty()){
                                System.out.println(token);
                                token_text.setText("Token generated");
                            }

                        }
                        else{
                            token_text.setText("Token generation failed");
                        }
                    }
                });

        if(getIntent()!=null && getIntent().hasExtra("score")){
            Bundle bundle = getIntent().getExtras();
            String score = bundle.getString("score");
            String target = bundle.getString("target");
            String overs = bundle.getString("overs");
            TextView score_text = (TextView) findViewById(R.id.score);
            TextView target_text = (TextView) findViewById(R.id.target);
            TextView overs_text = (TextView) findViewById(R.id.overs);
            score_text.setText(score);
            target_text.setText(target);
            overs_text.setText(overs);
        }
        Button btnUnsubscribe = findViewById(R.id.unsubscribe_topic);
        Button btnSubscribe = findViewById(R.id.subscribe_topic);
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().subscribeToTopic("cricket-match")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Topic Subscribed",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Subscription failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        btnUnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("cricket-match")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Topic Unsubscribed",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"UnSubscription failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
