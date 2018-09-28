package startup.juls.com.myyoutube

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.videorow.view.*

class MainAdapter(val homeFeed: HomeFeed) : RecyclerView.Adapter<CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val view:View=layoutInflater.inflate(R.layout.videorow,parent,false)
        return  CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return homeFeed.videos.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val video=homeFeed.videos[position]
        holder.view.videoTitle.text=video.name
        holder.view.channelName.text=video.channel.name

        val videoImage=holder.view.videoImage
        val userImage=holder.view.userImage
        Picasso.get().load(video.imageUrl).into(videoImage)
        Picasso.get().load(video.channel.profileImageUrl).into(userImage)
        holder.video=video
    }

}


class CustomViewHolder(val view: View,var video:Video?=null):RecyclerView.ViewHolder(view){

    companion object {
        val LESSON_TITLE="Title"
        val VIDEO_ID="VIDEO_ID"
    }

    init {
        view.setOnClickListener {
            val intent=Intent(view.context,CourseLessonActivity::class.java)
            intent.putExtra( LESSON_TITLE,video?.name)
            intent.putExtra( VIDEO_ID,video?.id)
            view.context.startActivity(intent)
        }
    }

}


