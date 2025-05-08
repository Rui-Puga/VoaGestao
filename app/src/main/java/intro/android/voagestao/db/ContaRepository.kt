package intro.android.voagestao.db

import intro.android.voagestao.dao.ContaDao
import intro.android.voagestao.entities.Conta

class ContaRepository(private val contaDao: ContaDao) {
    suspend fun inserirConta(conta: Conta) {
        contaDao.inserir(conta)
    }

    suspend fun listarContas(): List<Conta> {
        return contaDao.listarContas()
    }
    suspend fun buscarPorEmail(email: String): Conta? {
        return contaDao.buscarPorEmail(email)
    }
}