package startup.juls.com.myyoutube

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_course_lesson.*
import kotlinx.android.synthetic.main.course_lesson.view.*
import okhttp3.*
import java.io.IOException

class CourseLessonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_lesson)
        course_lesson_recicleview.layoutManager=LinearLayoutManager(this)
        var title=intent.getStringExtra(CustomViewHolder.LESSON_TITLE)
        supportActionBar?.title=title
        fetchVideoDetails()


    }

    private fun fetchVideoDetails(){
        val videoId=intent.getIntExtra(CustomViewHolder.VIDEO_ID,0)

        val courseDetailUrl="https://api.letsbuildthatapp.com/youtube/course_detail?id=$videoId"

        val request=Request.Builder().url(courseDetailUrl).build()
        val client=OkHttpClient()
        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("The request has failed")
            }

            override fun onResponse(call: Call, response: Response) {
                val body=response.body()?.string()
                val gson= GsonBuilder().create()
                var detailFeed=gson.fromJson(body,Array<VideoDetail>::class.java)
                runOnUiThread {

                    course_lesson_recicleview.adapter=LessonAdapter(detailFeed)

                }
            }

        })
    }
}


class LessonAdapter(val feed:Array<VideoDetail>):RecyclerView.Adapter<LessonAdapterHolder>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): LessonAdapterHolder {
        val inflater=LayoutInflater.from(viewGroup.context)
        val view=inflater.inflate(R.layout.course_lesson,viewGroup,false)
        return LessonAdapterHolder(view)
    }

    override fun getItemCount(): Int {
        return feed.size
    }

    override fun onBindViewHolder(holder: LessonAdapterHolder, position: Int) {
        val videoDetail=feed[position]
        val lessonImage=holder.view.lessonImage
        Picasso.get().load(videoDetail.imageUrl).into(lessonImage)
        holder.view.lessonTitle.text=videoDetail.name
        holder.view.lessonDetail.text=videoDetail.duration
    }


}

class LessonAdapterHolder(val view: View):RecyclerView.ViewHolder(view){

}