package com.example.michaellevy.exercise_04;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Random;

public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<String> quotes = new ArrayList<>();
        quotes.add("a mind needs books as a sword needs a whetstone, if it is to keep its edge - Tyrion Lannister");
        quotes.add("Fear cuts deeper than swords - Arya Stark");
        quotes.add("Winter is coming - House Stark");
        quotes.add("When you play a game of thrones you win or you die - Cersei Lannister");

        String quote = getRandomQuote(quotes);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "M_CH_ID");

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("A Games Of Thrones Quote")
                .setContentText(quote);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    private String getRandomQuote (ArrayList<String> quotes) {
        Random rand = new Random();
        String randomQuote = quotes.get(rand.nextInt(quotes.size()));
        return randomQuote;
    }
}