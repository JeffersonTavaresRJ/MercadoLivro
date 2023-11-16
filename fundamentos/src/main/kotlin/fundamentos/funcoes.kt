package fundamentos

fun main(){
    println(dizOi(retornaNome(), calculaIdade(anoNasc = 1976, anoAtual = 2023)));
    println(dizOi(retornaNome()));
}

fun retornaNome():String{
    return  "Jefferson";
}

fun calculaIdade(anoAtual: Int, anoNasc: Int):Int{
    return anoAtual-anoNasc;
}

fun dizOi(nome: String, idade: Int=22):String{
    return "Oi ${nome}! Parabéns pelos seus ${idade} anos!";
}