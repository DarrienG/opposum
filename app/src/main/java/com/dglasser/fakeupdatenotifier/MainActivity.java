package com.dglasser.fakeupdatenotifier;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    CheckBox endlessNotificationsBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        endlessNotificationsBox = (CheckBox) findViewById(R.id.endlessCheck);

        Intent boringIntent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getService(this, 0, boringIntent, 0);

        NotificationCompat.Action laterAction =
            new NotificationCompat.Action.Builder(
                R.drawable.ic_later, getString(R.string.update_later), pIntent).build();

        NotificationCompat.Action updateNowAction =
            new NotificationCompat.Action.Builder(
                R.drawable.ic_start_now, getString(R.string.download_now), pIntent).build();

        final EditText editText = (EditText) findViewById(R.id.contentTitle);

        Button button = (Button) findViewById(R.id.updateButton);
        button.setOnClickListener(view -> {
            String editTextText = editText.getText().toString();
            String contextText =
                editTextText.isEmpty() ? getString(R.string.sys_update_small) : editTextText;
            android.support.v4.app.NotificationCompat.Builder builder =
                new NotificationCompat.Builder(MainActivity.this)
                    .setContentTitle(getString(R.string.sys_update_bold))
                    .setContentText(contextText)
                    .setSmallIcon(R.drawable.ic_updater)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            getApplicationContext().getResources(), R.mipmap.update))
                    .addAction(laterAction)
                    .addAction(updateNowAction);

            NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(
                endlessNotificationsBox.isChecked() ? (int) (Math.random() * Integer.MAX_VALUE) : 1,
                builder.build());
        });
    }
}
