package com.sailegnik.shuttledrive_g4

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.sailegnik.shuttledrive_g4.about.AboutActivity
import com.sailegnik.shuttledrive_g4.booking.BookingListActivity
import com.sailegnik.shuttledrive_g4.cart.CartActivity
import com.sailegnik.shuttledrive_g4.databinding.ActivityMainBinding
import com.sailegnik.shuttledrive_g4.feedback.FeedbackActivity
import com.sailegnik.shuttledrive_g4.login_firebase.ProfileActivity
import com.sailegnik.shuttledrive_g4.ticket.TicketActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var toggle:ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var bnsearch: Button
    private lateinit var toolbar: Toolbar
    private lateinit var tvJourneyDate: TextView
    private lateinit var from: TextInputEditText
    private lateinit var to: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar)
        tvJourneyDate = findViewById(R.id.tv_journey_date)
        bnsearch = findViewById(R.id.bnsearch)
        from = findViewById(R.id.from_locET)
        to = findViewById(R.id.to_locET)

        //drawer toggle
        toggle = ActionBarDrawerToggle(this,binding.drawerL,toolbar,R.string.openDrawer,R.string.closeDrawer)
        binding.drawerL.addDrawerListener(toggle)
        toggle.syncState()

        val myCalendar = Calendar.getInstance()

        val datePickerJ = DatePickerDialog.OnDateSetListener{view, year,month,dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateJourney(myCalendar)
        }

        //listener for date button
        binding.journeyDate.setOnClickListener{
            DatePickerDialog(this, datePickerJ, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        //switching activity after filling fields
            binding.bnsearch.setOnClickListener{
                if(from.text.isNullOrBlank()){
                    Toast.makeText(this, "Please Enter Departure Location", Toast.LENGTH_SHORT).show()
                }
                else if(to.text.isNullOrBlank()){
                    Toast.makeText(this, "Please Enter Destination", Toast.LENGTH_SHORT).show()
                }
                else if(tvJourneyDate.text == "_"){
                    Toast.makeText(this, "Please select a Date", Toast.LENGTH_SHORT).show()
                }
                else {
                    val intent = Intent(this, BookingListActivity::class.java)
                    intent.putExtra("From", from.text.toString())
                    intent.putExtra("To", to.text.toString())
                    intent.putExtra("Date", tvJourneyDate.text)
                    startActivity(intent)
                }
            }


        //switching activity with drawer
        binding.navView.setNavigationItemSelectedListener {
            it.isChecked = true

            when(it.itemId){
//                R.id.home-> {val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)}
                R.id.ticket-> {val intent = Intent(this, TicketActivity::class.java)
                    startActivity(intent)}
                R.id.about-> {val intent = Intent(this, AboutActivity::class.java)
                    startActivity(intent)}
                R.id.cart-> {val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)}
                R.id.profile-> {val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)}
                R.id.feedback-> {val intent = Intent(this, FeedbackActivity::class.java)
                    startActivity(intent)}
            }

            true
        }

    }


    //setup for date format and set text
    private fun updateJourney(myCalendar: Calendar){
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvJourneyDate.setText(sdf.format(myCalendar.time))
    }


}