package com.hfad.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import com.hfad.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    lateinit var stopwatch: Chronometer
    var running = false
    var offset:Long = 0

    val OFFET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stopwatch=binding.stopwatch

        if (savedInstanceState!=null){
            offset = savedInstanceState.getLong(OFFET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running){
                stopwatch.base=savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            }
            else setBaseTime()
        }

        binding.startButton.setOnClickListener {
            if(!running){
                setBaseTime()
                stopwatch.start()
                running=true
            }
        }

        binding.pauseButton.setOnClickListener {
            if (running){
                saveOffset()
                stopwatch.stop()
                running = false
            }
        }
        binding.resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY,running)
        savedInstanceState.putLong(BASE_KEY,stopwatch.base)
        if (savedInstanceState != null) {
            super.onSaveInstanceState(savedInstanceState)
        }
    }

    override fun onStop() {
        super.onStop()
        if (running){
            saveOffset()
            stopwatch.stop()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (running){
            setBaseTime()
            stopwatch.start()
            offset=0
        }
    }
    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }

    private fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }
}