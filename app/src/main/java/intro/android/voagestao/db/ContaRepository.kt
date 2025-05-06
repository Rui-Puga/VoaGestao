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

    suspend fun autenticar(email: String, password: String): Conta? {
        return contaDao.autenticar(email, password)
    }

    suspend fun buscarPorEmail(email: String): Conta? {
        return contaDao.buscarPorEmail(email)
    }

    suspend fun atualizarConta(id: Long, username: String, password: String) {
        contaDao.atualizarConta(id, username, password)
    }

    suspend fun eliminarConta(id: Long) {
        contaDao.eliminarConta(id)
    }
}