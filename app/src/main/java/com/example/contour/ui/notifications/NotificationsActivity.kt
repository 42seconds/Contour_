package com.example.contour.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contour.R
import com.example.contour.data.DataStore
import com.example.contour.databinding.ActivityNotificationsBinding
import com.example.contour.util.UserPreferences
import java.text.NumberFormat
import java.util.Locale

sealed class NotificationItem {
    data class Header(val title: String) : NotificationItem()
    data class Notification(
        val icon: String,
        val title: String,
        val description: String,
        val time: String,
        val isUnread: Boolean = false
    ) : NotificationItem()
}

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        setupNotifications()
    }

    private fun setupNotifications() {
        val items = mutableListOf<NotificationItem>()
        val symbol = UserPreferences.getCurrencySymbol(this)
        val nf = NumberFormat.getNumberInstance(Locale.US)
        nf.minimumFractionDigits = 2
        nf.maximumFractionDigits = 2

        // Generate notifications based on actual data
        val transactions = DataStore.getTransactions(this)
        val budgets = DataStore.getBudgets(this)
        val goals = DataStore.getGoals(this)

        if (transactions.isNotEmpty() || budgets.isNotEmpty() || goals.isNotEmpty()) {
            items.add(NotificationItem.Header("Recent"))

            // Check budget alerts
            for (budget in budgets) {
                val spent = DataStore.getSpentForCategory(this, budget.category)
                if (spent > budget.limit) {
                    val over = spent - budget.limit
                    items.add(NotificationItem.Notification(
                        "💰", "Budget Alert",
                        "You've exceeded your ${budget.category} budget by $symbol${nf.format(over)}",
                        "Now", true
                    ))
                }
            }

            // Show last transaction as notification
            if (transactions.isNotEmpty()) {
                val last = transactions[0]
                val prefix = if (last.isIncome) "+" else "-"
                items.add(NotificationItem.Notification(
                    "💳", "New Transaction",
                    "${last.description} — $prefix$symbol${nf.format(last.amount)}",
                    last.date, true
                ))
            }

            // Goal progress
            for (goal in goals) {
                if (goal.currentAmount > 0) {
                    val pct = ((goal.currentAmount / goal.targetAmount) * 100).toInt()
                    items.add(NotificationItem.Notification(
                        "✅", "Goal Progress",
                        "${goal.name} is now $pct% complete!",
                        "Recently"
                    ))
                }
            }
        }

        if (items.isEmpty()) {
            items.add(NotificationItem.Header("No Notifications"))
            items.add(NotificationItem.Notification(
                "🔔", "All caught up!",
                "You'll see transaction alerts and goal updates here.",
                ""
            ))
        }

        binding.rvNotifications.layoutManager = LinearLayoutManager(this)
        binding.rvNotifications.adapter = NotificationAdapter(items)
    }
}

class NotificationAdapter(
    private val items: List<NotificationItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_NOTIFICATION = 1
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtHeader: TextView = view.findViewById(R.id.txtHeader)
    }

    class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: View = view.findViewById(R.id.notificationCard)
        val txtIcon: TextView = view.findViewById(R.id.txtIcon)
        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)
        val txtTime: TextView = view.findViewById(R.id.txtTime)
        val unreadDot: View = view.findViewById(R.id.unreadDot)
    }

    override fun getItemViewType(position: Int) = when (items[position]) {
        is NotificationItem.Header -> TYPE_HEADER
        is NotificationItem.Notification -> TYPE_NOTIFICATION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(inflater.inflate(R.layout.item_notification_header, parent, false))
            else -> NotificationViewHolder(inflater.inflate(R.layout.item_notification, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is NotificationItem.Header -> {
                (holder as HeaderViewHolder).txtHeader.text = item.title
            }
            is NotificationItem.Notification -> {
                val h = holder as NotificationViewHolder
                h.txtIcon.text = item.icon
                h.txtTitle.text = item.title
                h.txtDescription.text = item.description
                h.txtTime.text = item.time
                h.unreadDot.visibility = if (item.isUnread) View.VISIBLE else View.GONE
                h.card.setBackgroundResource(
                    if (item.isUnread) R.drawable.bg_notification_unread
                    else R.drawable.bg_notification_card
                )
            }
        }
    }

    override fun getItemCount() = items.size
}
