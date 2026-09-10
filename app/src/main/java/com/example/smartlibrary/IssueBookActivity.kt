package com.example.smartlibrary

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

/**
 * IssueBookActivity allows the user to confirm issuing a selected book.
 * Displays selected book name and author received via Intent extras.
 * Upon confirmation, displays a Toast, logs to Logcat, sends an Android Notification,
 * and disables the confirmation button.
 */
class IssueBookActivity : AppCompatActivity() {

    private val CHANNEL_ID = "smart_library_channel"
    private val NOTIFICATION_ID = 101
    private val PERMISSION_REQUEST_CODE = 1001

    private var bookName: String? = null
    private var authorName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_book)

        // Retrieve book details passed via Intent extras
        bookName = intent.getStringExtra("BOOK_NAME") ?: "Unknown Book"
        authorName = intent.getStringExtra("AUTHOR") ?: "Unknown Author"

        val tvBookName = findViewById<TextView>(R.id.tvBookName)
        val tvAuthor = findViewById<TextView>(R.id.tvAuthor)
        val tvIssueStatus = findViewById<TextView>(R.id.tvIssueStatus)
        val btnConfirmIssue = findViewById<Button>(R.id.btnConfirmIssue)

        tvBookName.text = bookName
        tvAuthor.text = authorName

        // Create Notification Channel for Android O and above
        createNotificationChannel()

        // Request POST_NOTIFICATIONS permission for Android 13+
        requestNotificationPermission()

        btnConfirmIssue.setOnClickListener {
            val selectedBook = bookName ?: "Book"

            // 1. Display Toast
            Toast.makeText(this, getString(R.string.toast_book_issued), Toast.LENGTH_SHORT).show()

            // 2. Logcat message
            Log.d("SmartLibrary", "Book issued successfully: $selectedBook")

            // 3. Generate Android Notification
            sendNotification(selectedBook)

            // 4. Disable button after issue confirmation to prevent duplicate clicks
            btnConfirmIssue.isEnabled = false
            btnConfirmIssue.text = getString(R.string.status_issued)
            tvIssueStatus.text = getString(R.string.status_issued_full)
            tvIssueStatus.setTextColor(ContextCompat.getColor(this, R.color.available_green))
        }
    }

    /**
     * Creates a NotificationChannel required for Android 8.0 (API 26) and higher.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "SmartLibrary Notifications"
            val descriptionText = "Notifications for book issue confirmation"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Requests POST_NOTIFICATIONS permission for Android 13 (API 33) and higher.
     */
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    /**
     * Sends an Android Notification confirming the book issuance.
     */
    private fun sendNotification(bookTitle: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.library_logo)
            .setContentTitle("SmartLibrary")
            .setContentText("$bookTitle has been successfully issued.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                try {
                    NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build())
                } catch (e: SecurityException) {
                    Log.e("SmartLibrary", "SecurityException while sending notification: ${e.message}")
                }
            } else {
                Log.w("SmartLibrary", "Notification permission not granted.")
            }
        } else {
            try {
                NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build())
            } catch (e: SecurityException) {
                Log.e("SmartLibrary", "SecurityException while sending notification: ${e.message}")
            }
        }
    }
}
