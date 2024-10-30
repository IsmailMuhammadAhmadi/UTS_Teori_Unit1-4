package lat.pam.bcard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import lat.pam.bcard.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Tampilkan pesan toast saat kartu di-tap
        findViewById<View>(R.id.textView).setOnClickListener {
            Toast.makeText(
                this@MainActivity,
                "Happy Birthday!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
