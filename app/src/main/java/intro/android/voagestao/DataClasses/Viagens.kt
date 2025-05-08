package intro.android.voagestao.DataClasses

data class Viagens (
    val id: Int,
    val origem: String,
    val destino: String,
    val data: String,
    val isReturn: Boolean
)