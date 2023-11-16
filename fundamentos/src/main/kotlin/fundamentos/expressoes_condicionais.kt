package fundamentos

fun main(){
 println(resultado(7));
}

fun resultado(nota: Int=0):String=
    when(nota){
        4 -> "Em Recuperação"
        5 -> "Em Recuperação"
        6 -> "Em Recuperação"
        7 -> "Aprovado"
        8 -> "Aprovado"
        9 -> "Aprovado"
        10 -> "Aprovado"
        else -> "Reprovado"
    }
