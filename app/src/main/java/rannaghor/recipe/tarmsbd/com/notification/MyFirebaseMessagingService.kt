package rannaghor.recipe.tarmsbd.com.notification


import android.R
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {
    var intent: Intent? = null
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
// Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d(
                TAG,
                "Message data payload: " + remoteMessage.data
            )
            try {
                val data = JSONObject(remoteMessage.data as Map<*, *>)
                val jsonMessage = data.getString("extra_information")
                Log.d(
                    TAG, """
     onMessageReceived: 
     Extra Information: $jsonMessage
     """.trimIndent()
                )
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification!!.title //get title
            val message = remoteMessage.notification!!.body //get message
            val click_action =
                remoteMessage.notification!!.clickAction //get click_action
            Log.d(
                TAG,
                "Message Notification Title: $title"
            )
            Log.d(
                TAG,
                "Message Notification Body: $message"
            )
            Log.d(
                TAG,
                "Message Notification click_action: $click_action"
            )
            sendNotification(title, message, click_action)
        }
    }

    private fun sendNotification(
        title: String?,
        messageBody: String?,
        click_action: String?
    ) {
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat.Builder(this)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.sym_def_app_icon)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(
                    Notification.DEFAULT_VIBRATE or
                            Notification.DEFAULT_SOUND or
                            Notification.FLAG_SHOW_LIGHTS
                )
                .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /* ID of notification */
        val id = (Math.random() * 10).toInt() + 1
        notificationManager.notify(id /* ID of notification */, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "MyFirebaseMessagingServ"
    }
}