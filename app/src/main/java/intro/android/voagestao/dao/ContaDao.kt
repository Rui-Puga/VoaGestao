package intro.android.voagestao.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import intro.android.voagestao.entities.Conta

@Dao
interface ContaDao {
    @Insert
    suspend fun inserir(conta: Conta)

    @Query("SELECT * FROM contas")
    suspend fun listarContas(): List<Conta>

    @Query("SELECT * FROM contas WHERE Email = :email AND Password = :password LIMIT 1")
    suspend fun autenticar(email: String, password: String): Conta?

    @Query("SELECT * FROM contas WHERE Email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): Conta?

    @Query("UPDATE contas SET Username = :username, Password = :password WHERE id = :id")
    suspend fun atualizarConta(id: Long, username: String, password: String)

    @Query("DELETE FROM contas WHERE id = :id")
    suspend fun eliminarConta(id: Long)

}