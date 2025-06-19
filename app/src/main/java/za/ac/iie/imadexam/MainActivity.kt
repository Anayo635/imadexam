package za.ac.iie.imadexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // Song data (using lists for easier manipulation)
    private val songTitles = mutableListOf("Blinding Lights", "Save Your Tears", "Stay", "Good 4 U")
    private val artistNames = mutableListOf("The Weeknd", "The Weeknd", "The Kid LAROI", "Olivia Rodrigo")
    private val ratings = mutableListOf(5, 4, 4, 3)
    private val comments = mutableListOf("Perfect driving song", "Great vocals", "Catchy chorus", "Pop punk revival")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        val btnAddToPlaylist = findViewById<Button>(R.id.btnAddToPlaylist)
        val btnViewDetails = findViewById<Button>(R.id.btnViewDetails)
        val btnExit = findViewById<Button>(R.id.btnExit)
        val etSongTitle = findViewById<EditText>(R.id.etSongTitle)
        val etArtistName = findViewById<EditText>(R.id.etArtistName)
        val etRating = findViewById<EditText>(R.id.etRating)
        val etComments = findViewById<EditText>(R.id.etComments)
        val songsContainer = findViewById<LinearLayout>(R.id.songsContainer)

        // Display existing songs
        displaySongs(songsContainer)

        // Add to Playlist button click handler
        btnAddToPlaylist.setOnClickListener {
            try {
                val title = etSongTitle.text.toString()
                val artist = etArtistName.text.toString()
                val rating = etRating.text.toString().toInt()
                val comment = etComments.text.toString()

                if (title.isBlank() || artist.isBlank() || comment.isBlank()) {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (rating < 1 || rating > 5) {
                    Toast.makeText(this, "Rating must be between 1-5", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Add new song to collections
                songTitles.add(title)
                artistNames.add(artist)
                ratings.add(rating)
                comments.add(comment)

                // Update UI
                displaySongs(songsContainer)
                Toast.makeText(this, "Song added to playlist", Toast.LENGTH_SHORT).show()

                // Clear input fields
                etSongTitle.text.clear()
                etArtistName.text.clear()
                etRating.text.clear()
                etComments.text.clear()

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Please enter a valid rating (1-5)", Toast.LENGTH_SHORT).show()
            }
        }

        // View Details button click handler
        btnViewDetails.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("songTitles", songTitles.toTypedArray())
                putExtra("artistNames", artistNames.toTypedArray())
                putExtra("ratings", ratings.toIntArray())
                putExtra("comments", comments.toTypedArray())
            }
            startActivity(intent)
        }

        // Exit button click handler
        btnExit.setOnClickListener {
            finish()
        }
    }

    private fun displaySongs(container: LinearLayout) {
        container.removeAllViews() // Clear existing views

        if (songTitles.isEmpty()) {
            val emptyText = TextView(this).apply {
                text = "No songs in playlist yet"
                setPadding(0, 16, 0, 16)
            }
            container.addView(emptyText)
            return
        }

        for (i in songTitles.indices) {
            val songView = TextView(this).apply {
                text = "${i + 1}. ${songTitles[i]} - ${artistNames[i]} (${ratings[i]}/5)"
                setPadding(16, 8, 16, 8)
                textSize = 16f
            }
            container.addView(songView)

            val divider = TextView(this).apply {
                text = "----------------------------"
                setPadding(16, 0, 16, 0)
            }
            container.addView(divider)
        }
    }
}