package intro.android.voagestao.db
import androidx.room.Database
import androidx.room.RoomDatabase
import intro.android.voagestao.dao.ContaDao
import intro.android.voagestao.entities.Conta
@Database(entities = [Conta::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contaDao(): ContaDao
}