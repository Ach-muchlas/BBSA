package com.am.bbsa.utils

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.am.bbsa.R

object NotificationHelper {
    private const val CHANNEL_ID = "notification_channel"
    private const val PERMISSION_REQUEST_CODE = 1001

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(context: Context, title: String, body: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        ) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Jika izin belum diberikan, minta izin kepada pengguna
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.RECEIVE_BOOT_COMPLETED),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                // Jika izin sudah diberikan, lanjutkan untuk menampilkan notifikasi
                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.icon_notification)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)

                with(NotificationManagerCompat.from(context)) {
                    notify(0, builder.build())
                }
            }
        } else {
            // Notifikasi dimatikan oleh pengguna, tidak perlu menampilkan notifikasi
        }
    }
}
