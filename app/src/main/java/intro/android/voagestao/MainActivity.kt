package intro.android.voagestao

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = intent.getStringExtra("username")

        findViewById<TextView>(R.id.textWelcome).text = "Bem-vindo, $username"

        // Botão de Logout
        findViewById<Button>(R.id.buttonLogout).setOnClickListener {
            // Limpar a sessão ao fazer logout
            val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("email")
            editor.apply()

            startActivity(Intent(this, Login::class.java))
            finish()
        }

        // Botão para ir ao perfil
        findViewById<Button>(R.id.buttonGoToProfile).setOnClickListener {
            // Recuperar o email do SharedPreferences
            val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
            val emailDoUtilizador = sharedPreferences.getString("email", null)

            if (!emailDoUtilizador.isNullOrEmpty()) {
                val intent = Intent(this, Profile::class.java)
                intent.putExtra("email", emailDoUtilizador) // Passando o email para o Profile Activity
                startActivity(intent)
            } else {
                // Lidar com o caso em que o email não está na sessão (opcional: voltar para login?)
                Toast.makeText(this, "Erro: Email da sessão não encontrado.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}