package intro.android.voagestao

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import intro.android.voagestao.databinding.ActivityProfileBinding
import intro.android.voagestao.db.AppDatabase
import intro.android.voagestao.db.ContaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Profile : AppCompatActivity() {
    val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }

    private lateinit var contaRepository: ContaRepository
    private lateinit var textViewUsername: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var buttonRemove: Button
    private lateinit var buttonBackToMain: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Inicializar o banco de dados e repositório
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "voagestao.db").build()
        contaRepository = ContaRepository(db.contaDao())

        // Mapear os elementos da UI
        textViewUsername = findViewById(R.id.textViewUsername)
        textViewEmail = findViewById(R.id.textViewEmail)
        buttonRemove = findViewById(R.id.buttonRemove)
        buttonBackToMain = findViewById(R.id.buttonBackToMain)

        // Tentar obter o email do Intent primeiro
        val email = intent.getStringExtra("email")

        if (email.isNullOrEmpty()) {
            // Se o email não foi passado pelo Intent, tentar recuperar do SharedPreferences (opcional)
            val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
            val storedEmail = sharedPreferences.getString("email", null)
            if (storedEmail.isNullOrEmpty()) {
                finish() // Fecha a activity se não tiver email de nenhuma forma
                return
            }
            // Usar o email recuperado do SharedPreferences
            buscarDadosDoUtilizador(storedEmail)
        } else {
            // Usar o email passado pelo Intent
            buscarDadosDoUtilizador(email)
        }

        buttonRemove.setOnClickListener {
            lifecycleScope.launch {
                val emailToRemove = intent.getStringExtra("email") ?: run {
                    val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
                    sharedPreferences.getString("email", null)
                }

                if (!emailToRemove.isNullOrEmpty()) {
                    contaRepository.removerPorEmail(emailToRemove)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Profile, "Conta removida com sucesso!", Toast.LENGTH_SHORT).show()
                        // Limpar a sessão ao remover a conta
                        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.remove("email")
                        editor.apply()

                        // Redirecionar para login
                        val intent = Intent(this@Profile, Login::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Profile, "Erro ao obter o email para remover.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Configuração do botão de voltar para a tela principal
        buttonBackToMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun buscarDadosDoUtilizador(email: String) {
        lifecycleScope.launch {
            val conta = contaRepository.buscarPorEmail(email)
            if (conta != null) {
                withContext(Dispatchers.Main) {
                    textViewUsername.text = "Nome: ${conta.Username}"
                    textViewEmail.text = "Email: ${conta.Email}"
                }
            }
        }
    }
}