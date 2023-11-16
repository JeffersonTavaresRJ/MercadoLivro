package fundamentos

fun main(){
    var mutavel="Jefferson";
    val imutavel="Tavares";

    mutavel="Silva";
    /*mutavel=123;*/  /*o tipo deve ser o mesmo que a variavel foi iniciada..*/

   /* imutavel="Olá";*/
}

class variaveis{
    lateinit /*utilizada para que a variável receba seu valor depois..*/ var teste:String;

    fun iniciaVariaveis(){
        teste="Teste";
    }
}