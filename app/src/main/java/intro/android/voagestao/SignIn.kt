package intro.android.voagestao

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import intro.android.voagestao.databinding.ActivitySignInBinding
import intro.android.voagestao.db.AppDatabase
import intro.android.voagestao.entities.Conta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "voagestao.db").build()
        val contaDao = db.contaDao()

        binding.buttonVoltarLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }


        binding.buttonSignIn.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (username.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Preenche todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val existente = contaDao.buscarPorEmail(email)
                if (existente != null) {
                    runOnUiThread {
                        Toast.makeText(this@SignIn, "Email já registado!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    contaDao.inserir(Conta(Username = username, Email = email, Password = password))
                    runOnUiThread {
                        Toast.makeText(this@SignIn, "Conta criada!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignIn, Login::class.java))
                        finish()
                    }
                }
            }
        }
    }
}
