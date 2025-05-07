package intro.android.voagestao

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import intro.android.voagestao.databinding.ActivityLoginBinding


class Login : AppCompatActivity() {
        val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(binding.root)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            binding.buttonEntrar.setOnClickListener {
                var usernameC = "admin"
                var passwordC = "admin"
                val username = binding.etUser.text.toString()
                val password = binding.etPass.text.toString()


                if(username == usernameC && password == passwordC){

                    Toast.makeText(applicationContext, "login com sucesso", Toast.LENGTH_SHORT).show()

                }else{
                    binding.etUser.text.clear()
                    binding.etPass.text.clear()
                    Toast.makeText(applicationContext, "username ou password erradas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }