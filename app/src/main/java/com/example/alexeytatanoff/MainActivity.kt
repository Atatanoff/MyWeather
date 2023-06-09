package com.example.alexeytatanoff


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alexeytatanoff.ui.theme.MyWeatherTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(this)
                }
            }
        }
    }
}

@Composable
fun Greeting(context: Context) {
    val tState = remember{
        mutableStateOf("Unknow")
    }
    val ftState = remember{
        mutableStateOf("Unknow")
    }
    val pState = remember{
       mutableStateOf("Unknow")
   }
    val hState = remember{
        mutableStateOf("Unknow")
    }
    val wState = remember{
        mutableStateOf("Unknow")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center){
            Column() {
                Text(text = "Температура: ${tState.value} градусов")
                Text(text = "Ощущается как: ${ftState.value} градусов")
               Text(text = "Давление: ${pState.value} мм/рт.с")
                Text(text = "Влажность: ${hState.value} %")
               Text(text = "Ветер: ${wState.value} м/c")
            }

        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter){
            Row() {
                Button(onClick = {
                    getResult(tState, ftState, pState, hState, wState, context)
                },
                modifier = Modifier.padding(5.dp)) {
                    Text(text = "1 day")
                }
                Button(onClick = {},
                    modifier = Modifier.padding(5.dp)) {
                    Text(text = "3 day")
                }
                Button(onClick = {},
                    modifier = Modifier.padding(5.dp)) {
                    Text(text = "10 day")
                }


            }
        }
    }
}



private fun getResult(t: MutableState<String>,
                        ft: MutableState<String>,
                     p: MutableState<String>,
                    h: MutableState<String>,
                     w: MutableState<String>,
                      context: Context) {
    val url =
        "https://api.openweathermap.org/data/2.5/weather?lat=46.3497&lon=48.0408&appid=c54ab1e8603726032b352a64491ed740&units=metric"
    val queue = Volley.newRequestQueue(context)
    val stringReq = StringRequest(Request.Method.GET,
        url,
        { res ->
            val obj = JSONObject(res)
            val temp = obj.getJSONObject("main")
            val wind = obj.getJSONObject("wind")
            t.value = temp.getString("temp")
            ft.value = temp.getString("feels_like")
            p.value = temp.getString("pressure")
            h.value = temp.getString("humidity")
            w.value = wind.getString("speed")
        },
        { err -> Log.d("MYT", "$err")
        })
    queue.add(stringReq)
}