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

        // Botão de Logout
        findViewById<Button>(R.id.buttonLogout).setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        // Botão para ir ao perfil
        findViewById<Button>(R.id.buttonGoToProfile).setOnClickListener {
            // Enviar o email do usuário ou outras informações, se necessário
            val emailDoUsuario = "usuario@exemplo.com"  // Coloque o email do usuário logado
            val intent = Intent(this, Profile::class.java)
            intent.putExtra("email", emailDoUsuario) // Passando o email para o Profile Activity
            startActivity(intent)
        }
    }
}