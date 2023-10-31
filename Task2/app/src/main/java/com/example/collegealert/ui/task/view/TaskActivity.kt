package com.example.collegealert.ui.task.view

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.collegealert.R
import com.example.collegealert.brodcastreceiver.TaskAlarmReceiver
import com.example.collegealert.local.DatabaseClient
import com.example.collegealert.model.Task
import com.example.collegealert.ui.task.repository.TaskRepositoryImp
import com.example.collegealert.ui.task.viewmodel.TaskViewModel
import com.example.collegealert.ui.task.viewmodel.TaskViewModelFactory
import com.example.collegealert.utils.Constants.Companion.TASK_DATE
import com.example.collegealert.utils.Constants.Companion.TASK_ID
import com.example.collegealert.utils.Constants.Companion.TASK_NAME
import com.example.collegealert.utils.Constants.Companion.TASK_TIME
import com.example.collegealert.utils.UtilMethods.compareDates
import com.example.collegealert.utils.UtilMethods.toEditable
import com.example.collegealert.utils.enums.Priority
import com.example.collegealert.utils.enums.Reminder
import com.example.collegealert.utils.enums.TaskField
import com.example.collegealert.utils.enums.TaskType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TaskActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var dateLayout: ConstraintLayout
    private lateinit var dateTextView: TextView
    private lateinit var reminderLayout: ConstraintLayout
    private lateinit var reminderSpinner: Spinner
    private lateinit var startTimeLayout: ConstraintLayout
    private lateinit var startTimeTextView: TextView
    private lateinit var endTimeLayout: ConstraintLayout
    private lateinit var endTimeTextView: TextView
    private lateinit var priorityGroup: RadioGroup
    private lateinit var fieldGroup: RadioGroup
    private lateinit var saveButton: Button

    private lateinit var adapter: ArrayAdapter<String>
    private var minutes: Int = 0
    private var reminder: Int = 0
    private var currentTaskId: Int = -1

    private lateinit var viewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        initComponents()
        dateLayout.setOnClickListener { showDatePicker() }
        reminderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("ALAAAAARM", "setupAlarm: $p2 $p3")
                reminder = p2
                minutes = Reminder.values()[p2].minutes
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("onNothingSelected: ", "onNothingSelected: ")
            }
        }
        startTimeLayout.setOnClickListener { showTimePicker(startTimeTextView) }
        endTimeLayout.setOnClickListener { showTimePicker(endTimeTextView) }

        saveButton.setOnClickListener {
            if (compareDates(
                    dateTextView.text.toString(),
                    endTimeTextView.text.toString(),
                )
            ) {
                Toast.makeText(
                    this,
                    "You can't add new task on date that have passed",
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                when (currentTaskId) {
                    -1 -> viewModel.addTask(
                        Task(
                            title = titleEditText.text.toString(),
                            description = descriptionEditText.text.toString(),
                            location = locationEditText.text.toString(),
                            deadlineDate = dateTextView.text.toString(),
                            deadlineStartTime = startTimeTextView.text.toString(),
                            deadlineEndTime = endTimeTextView.text.toString(),
                            addedDate = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().time),
                            reminder = Reminder.values()[reminder],
                            field = when (fieldGroup.checkedRadioButtonId) {
                                R.id.task_field_lecture_radio_btn -> TaskField.LECTURE
                                R.id.task_field_milestone_radio_btn -> TaskField.MILESTONE
                                else -> TaskField.EXAM
                            },
                            type = TaskType.UPCOMING,
                            priority = when (priorityGroup.checkedRadioButtonId) {
                                R.id.task_priority_important_radio_btn -> Priority.IMPORTANT
                                R.id.task_priority_normal_radio_btn -> Priority.NORMAL
                                else -> Priority.NOT_IMPORTANT
                            },
                            isDone = false,
                        ),
                    )

                    else -> viewModel.editTask(
                        Task(
                            id = currentTaskId,
                            title = titleEditText.text.toString(),
                            description = descriptionEditText.text.toString(),
                            location = locationEditText.text.toString(),
                            deadlineDate = dateTextView.text.toString(),
                            deadlineStartTime = startTimeTextView.text.toString(),
                            deadlineEndTime = endTimeTextView.text.toString(),
                            addedDate = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().time),
                            reminder = Reminder.values()[reminder],
                            field = when (fieldGroup.checkedRadioButtonId) {
                                R.id.task_field_lecture_radio_btn -> TaskField.LECTURE
                                R.id.task_field_milestone_radio_btn -> TaskField.MILESTONE
                                else -> TaskField.EXAM
                            },
                            type = TaskType.UPCOMING,
                            priority = when (priorityGroup.checkedRadioButtonId) {
                                R.id.task_priority_important_radio_btn -> Priority.IMPORTANT
                                R.id.task_priority_normal_radio_btn -> Priority.NORMAL
                                else -> Priority.NOT_IMPORTANT
                            },
                            isDone = false,
                        ),
                    )
                }
                finish()
                setupAlarm()
            }
        }

        viewModel.taskLiveData.observe(this) {
            titleEditText.text = it.title.toEditable()
            descriptionEditText.text = it.description.toEditable()
            locationEditText.text = it.location.toEditable()
            dateTextView.text = it.deadlineDate.toEditable()
            startTimeTextView.text = it.deadlineStartTime.toEditable()
            endTimeTextView.text = it.deadlineEndTime.toEditable()
            reminderSpinner.setSelection(Reminder.values().indexOf(it.reminder))
            fieldGroup.check(
                when (it.field) {
                    TaskField.LECTURE -> R.id.task_field_lecture_radio_btn
                    TaskField.MILESTONE -> R.id.task_field_milestone_radio_btn
                    TaskField.EXAM -> R.id.task_field_exam_radio_btn
                },
            )
            priorityGroup.check(
                when (it.priority) {
                    Priority.IMPORTANT -> R.id.task_priority_important_radio_btn
                    Priority.NORMAL -> R.id.task_priority_normal_radio_btn
                    Priority.NOT_IMPORTANT -> R.id.task_priority_not_radio_btn
                },
            )
        }
    }

    private fun initComponents() {
        titleEditText = findViewById(R.id.task_title_et)
        descriptionEditText = findViewById(R.id.task_description_et)
        locationEditText = findViewById(R.id.task_location_et)
        dateLayout = findViewById(R.id.task_date_layout)
        dateTextView = findViewById(R.id.task_date_tv)
        reminderLayout = findViewById(R.id.task_reminder_layout)
        reminderSpinner = findViewById(R.id.task_reminder_spinner)
        startTimeLayout = findViewById(R.id.task_start_time_layout)
        startTimeTextView = findViewById(R.id.task_start_time_tv)
        endTimeLayout = findViewById(R.id.task_end_time_layout)
        endTimeTextView = findViewById(R.id.task_end_time_tv)
        priorityGroup = findViewById(R.id.task_priority_group)
        fieldGroup = findViewById(R.id.task_field_group)
        saveButton = findViewById(R.id.task_save_btn)

        setupSpinner()

        prepareViewModel()
        currentTaskId = intent.getIntExtra(TASK_ID, -1)
        viewModel.fetchTask(currentTaskId)
    }

    private fun setupSpinner() {
        adapter = ArrayAdapter(this, R.layout.item_spinner, Reminder.values().map { it.value })
        adapter.setDropDownViewResource(R.layout.item_spinner)
        reminderSpinner.adapter = adapter
    }

    private fun prepareViewModel() {
        viewModel = ViewModelProvider(
            this,
            TaskViewModelFactory(
                this.application,
                TaskRepositoryImp(DatabaseClient),
            ),
        )[TaskViewModel::class.java]
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val formattedDate =
                    android.text.format.DateFormat.format("dd, MMM yyyy", selectedDate)
                dateTextView.text = formattedDate
            },
            currentYear,
            currentMonth,
            currentDay,
        )
        datePickerDialog.show()
    }

    private fun showTimePicker(textView: TextView) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _: TimePicker, hourOfDay: Int, minute: Int ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                val formattedTime = formatTime(hourOfDay, minute)
                textView.text = formattedTime
            },
            currentHour,
            currentMinute,
            false,
        )
        timePickerDialog.show()
    }

    private fun formatTime(hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val format = if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
            "AM"
        } else {
            "PM"
        }

        val formattedHour = when (calendar.get(Calendar.HOUR)) {
            0 -> 12
            else -> calendar.get(Calendar.HOUR)
        }

        return String.format("%02d:%02d %s", formattedHour, minute, format)
    }

    private fun setupAlarm() {
        val taskDateTime = SimpleDateFormat(
            "dd, MMM yyyy hh:mm a",
            Locale.getDefault(),
        ).parse("${dateTextView.text} ${startTimeTextView.text}")

        Log.d("ALAAAAARM", "setupAlarm: $taskDateTime")

        val notificationTime = Calendar.getInstance().apply {
            timeInMillis = taskDateTime.time
            Log.d("ALAAAAARM", "setupAlarm: $timeInMillis")
            add(Calendar.MINUTE, Reminder.values()[reminder].minutes * -1)
            Log.d("ALAAAAARM", "setupAlarm: $reminder")
            Log.d("ALAAAAARM", "setupAlarm: ${Reminder.values()[reminder].minutes * -1}")
        }.timeInMillis

        Log.d("ALAAAAARM", "setupAlarm: $notificationTime")
        // Create an intent to be triggered by the alarm
        val alarmIntent = Intent(this, TaskAlarmReceiver::class.java)
        alarmIntent.putExtra(TASK_NAME, titleEditText.text.toString())
        alarmIntent.putExtra(TASK_TIME, dateTextView.text.toString())
        alarmIntent.putExtra(TASK_DATE, startTimeTextView.text.toString())
        alarmIntent.action = "com.example.collegealert.ACTION_BUTTON_CLICK"
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE,
        )

        // Get the AlarmManager instance and schedule the alarm
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val nextAlarmClock = alarmManager.nextAlarmClock

        if (nextAlarmClock != null) {
            val alarmTime = nextAlarmClock.triggerTime
            val currentTime = Calendar.getInstance().timeInMillis
            val timeRemaining = alarmTime - currentTime
            Log.d("ALAAAAARM", "setupAlarm: $alarmTime")
            Log.d("ALAAAAARM", "setupAlarm: $currentTime")

            println("Alarm set successfully. Time remaining: $timeRemaining milliseconds")
            Log.d("ALAAAAARM", "setupAlarm: Alarm set successfully. Time remaining: $timeRemaining milliseconds")
        } else {
            println("Failed to set the alarm.")
            Log.d("ALAAAAARM", "setupAlarm: Failed to set the alarm.")
        }

        val canScheduleExactAlarms = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            false // Handle the case when VERSION.SDK_INT < S
        }

        if (canScheduleExactAlarms) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                notificationTime,
                pendingIntent,
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                notificationTime,
                pendingIntent,
            )
        }
    }
}
