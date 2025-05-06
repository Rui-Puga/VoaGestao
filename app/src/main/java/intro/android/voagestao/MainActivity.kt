package intro.android.voagestao

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = intent.getStringExtra("username")

        findViewById<TextView>(R.id.textWelcome).text = "Bem-vindo, $username"

        findViewById<Button>(R.id.buttonLogout).setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}
