package uz.tis.alarmnotification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver:BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val i=Intent(p0,MainActivity::class.java)
        p1!!.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent=PendingIntent.getActivity(p0,0,i,0)

        val builder=NotificationCompat.Builder(p0!!,"foxandroid")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Foxandroid alarm manager")
            .setContentTitle("Subscribe for more")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager=NotificationManagerCompat.from(p0)
        notificationManager.notify(123,builder.build())
    }
}