package pistalix.romanticvideostatus.romanticvideosong

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.json.JSONArray
import org.json.JSONObject



//https://i.ytimg.com/vi/I4wkxIUQi1g/default.jpg
class RecyleJson (var name: JSONArray,var playlistId:String): RecyclerView.Adapter<RecyleJson.ViewHolder>()
{
    lateinit var context1:Context
    val adRequest = AdRequest.Builder().build()
    var mInterstitialAd: InterstitialAd? = null
    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val json1:JSONObject = name.getJSONObject(position)
        if(position%3==0 && position !=0){
            holder.mAdView.visibility = View.VISIBLE
            holder.mAdView.loadAd(adRequest)
            holder.title.text= json1.getString("title")
            val videoid =json1.getString("id")
            val imageurl=json1.getString("imageurl")
            holder.video.setOnClickListener {
                val intent = Intent(context1, VideoView::class.java)
                intent.putExtra("videoid", videoid)
                intent.putExtra("playlistId", playlistId)
                intent.putExtra("Title",json1.getString("title"))
                see_ad()
                context1.startActivity(intent)
            }

            Picasso.with(context1).load(imageurl).fit().into(holder.thummail)
        }else{

            holder.mAdView.visibility = View.GONE
            holder.title.text= json1.getString("title")
            val videoid =json1.getString("id")
            val imageurl=json1.getString("imageurl")
            holder.video.setOnClickListener {

                val intent = Intent(context1, VideoView::class.java)
                intent.putExtra("videoid", videoid)
                intent.putExtra("playlistId", playlistId)
                intent.putExtra("Title",json1.getString("title"))
                see_ad()
                context1.startActivity(intent)
            }

            Picasso.with(context1).load(imageurl).fit().into(holder.thummail)
        }


    }

    override fun getItemCount(): Int {
        return name.length()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyleJson.ViewHolder {

        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.listview, parent, false)
        context1=parent.context
        return ViewHolder(itemView)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView = itemView.find(R.id.title)
        val thummail:ImageView = itemView.find(R.id.imagethum)
        val video:CardView = itemView.find(R.id.card_view2)
        var mAdView: AdView = itemView.find(R.id.adView)

        init {

        }
    }

    private fun showInterstitial() {
        if (mInterstitialAd!!.isLoaded()) {
            mInterstitialAd!!.show()
        }
    }
    fun see_ad(){

        val ads = context1.getResources().getString(R.string.interstial_ads)
        mInterstitialAd = InterstitialAd(context1)

        // set the ad unit ID
        mInterstitialAd!!.adUnitId = ads

        val adRequest1 = AdRequest.Builder()
                .build()

        mInterstitialAd!!.loadAd(adRequest1)

        mInterstitialAd!!.adListener = object : AdListener() {
            override fun onAdLoaded() {
                showInterstitial()
            }
        }
    }
}

