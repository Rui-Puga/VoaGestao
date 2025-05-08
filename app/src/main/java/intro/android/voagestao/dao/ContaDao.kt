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

    @Query("SELECT * FROM contas WHERE Email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): Conta?
}
