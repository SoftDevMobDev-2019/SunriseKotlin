package au.edu.swin.sdmd.sunrisekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import au.edu.swin.sdmd.sunrisekotlin.calc.AstronomicalCalendar
import au.edu.swin.sdmd.sunrisekotlin.calc.GeoLocation
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUI()
    }

    private fun initializeUI() {
        val dp = findViewById<DatePicker>(R.id.datePicker)
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        dp.init(year, month, day, dateChangeHandler) // setup initial values and reg. handler
        updateTime(year, month, day)
    }

    private fun updateTime(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val tz = TimeZone.getDefault()
        val geolocation = GeoLocation("Melbourne", -37.50, 145.01, tz)
        val ac = AstronomicalCalendar(geolocation)
        ac.getCalendar().set(year, monthOfYear, dayOfMonth)
        val srise = ac.getSunrise()
        val sset = ac.getSunset()

        val sdf = SimpleDateFormat("HH:mm")

        val sunriseTV = findViewById<TextView>(R.id.sunriseTimeTV)
        val sunsetTV = findViewById<TextView>(R.id.sunsetTimeTV)
        Log.d("SUNRISE Unformatted", srise.toString())

        sunriseTV.setText(sdf.format(srise))
        sunsetTV.setText(sdf.format(sset))
    }

    internal var dateChangeHandler: DatePicker.OnDateChangedListener =
        DatePicker.OnDateChangedListener { dp, year, monthOfYear, dayOfMonth ->
            updateTime(
                year,
                monthOfYear,
                dayOfMonth
            )
        }
}
