package com.fd.socket_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fd.socket_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //connecting socket
        SocketHandler.setSocket()
        val socket = SocketHandler.getSocket()
        socket.connect()


        binding.apply {
            btnAddNumber.setOnClickListener {
                socket.emit("counter") // the name of "counter" should be the exact same as server side

            }
            socket.on("counter"){ args->
                if (args[0] !=null){
                    val  counter = args[0] as Int

                    runOnUiThread{
                        tvCounter.text = counter.toString()
                    }
                }

            }
        }
    }
}