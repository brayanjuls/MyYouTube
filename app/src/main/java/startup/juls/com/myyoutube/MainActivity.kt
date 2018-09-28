package startup.juls.com.myyoutube

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleView_main.layoutManager= LinearLayoutManager(this)
        //recycleView_main.adapter=MainAdapter()
        fetchJson()
    }

    private fun fetchJson(){
        println("Attempting to Fetch JSON")
        val url="https://api.letsbuildthatapp.com/youtube/home_feed"
        val request=Request.Builder().url(url).build()
        val client =OkHttpClient()
        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("The request has failed")
            }

            override fun onResponse(call: Call, response: Response) {
                val body= response.body()?.string()
                val gson =GsonBuilder().create()
                val homeFeed=gson.fromJson(body,HomeFeed::class.java)

                runOnUiThread {
                    recycleView_main.adapter=MainAdapter(homeFeed)
                }

            }

        })
    }
}


