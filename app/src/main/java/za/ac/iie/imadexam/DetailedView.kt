package za.ac.iie.imadexam

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailedview)

        // Get data from MainActivity
        val titles = intent.getStringArrayExtra("songTitles") ?: arrayOf()
        val artists = intent.getStringArrayExtra("artistNames") ?: arrayOf()
        val ratings = intent.getIntArrayExtra("ratings") ?: intArrayOf()
        val comments = intent.getStringArrayExtra("comments") ?: arrayOf()

        val tvDetails = findViewById<TextView>(R.id.tvSongDetails)
        val tvAverage = findViewById<TextView>(R.id.tvAverageRating)

        // Display all songs
        val sb = StringBuilder()
        for (i in titles.indices) {
            sb.append("${i + 1}. ${titles[i]}\n")
            sb.append("   Artist: ${artists[i]}\n")
            sb.append("   Rating: ${ratings[i]}/5\n")
            sb.append("   Comments: ${comments[i]}\n\n")
        }
        tvDetails.text = sb.toString()

        // Calculate average rating
        if (ratings.isNotEmpty()) {
            val average = ratings.average()
            tvAverage.text = "Average Rating: ${"%.1f".format(average)}/5"
        } else {
            tvAverage.text = "No ratings available"
        }
    }
}