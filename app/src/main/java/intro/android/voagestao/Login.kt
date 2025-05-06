package intro.android.voagestao

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import intro.android.voagestao.databinding.ActivityLoginBinding
import intro.android.voagestao.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "voagestao.db").build()
        val contaDao = db.contaDao()

        binding.buttonEntrar.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Preenche todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val conta = contaDao.autenticar(email, password)
                runOnUiThread {
                    if (conta != null) {
                        Toast.makeText(this@Login, "Login com sucesso!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Login, MainActivity::class.java)
                        intent.putExtra("id", conta.id)
                        intent.putExtra("username", conta.Username)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@Login, "Credenciais incorretas!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.buttonCriarConta.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }
    }
}
