package intro.android.voagestao

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import intro.android.voagestao.db.AppDatabase
import intro.android.voagestao.db.ContaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    private lateinit var contaRepository: ContaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "voagestao.db"
        ).build()

        contaRepository = ContaRepository(database.contaDao())

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoToSignUp = findViewById<Button>(R.id.btnGoToSignUp)

        btnLogin.setOnClickListener {
            val email = findViewById<EditText>(R.id.editEmail).text.toString()
            val password = findViewById<EditText>(R.id.editPassword).text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Preenche todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val conta = withContext(Dispatchers.IO) {
                    contaRepository.buscarPorEmail(email)
                }

                if (conta == null || conta.Password != password) {
                    runOnUiThread {
                        Toast.makeText(this@Login, "Email ou palavra-passe inválidos!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Salvar o email do utilizador no SharedPreferences após o login
                    val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("email", conta.Email)
                    editor.apply()

                    val intent = Intent(this@Login, MainActivity::class.java)
                    intent.putExtra("username", conta.Username) // Ainda passa o username para a MainActivity
                    startActivity(intent)
                    finish()
                }
            }
        }

        btnGoToSignUp.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }
}