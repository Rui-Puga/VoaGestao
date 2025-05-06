package intro.android.voagestao.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contas")
data class Conta(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val Username: String,
    val Email: String,
    val Password: String


)