package fundamentos

class MinhaClasse(
    var nome: String,
    var idade: Int
){
    companion object{
        fun criarComValoresPadrao(segundaClasse: SegundaClasse): MinhaClasse{
            return MinhaClasse(segundaClasse.nome, segundaClasse.idade);
        }
    }
}

class SegundaClasse(
    var nome: String,
    var idade: Int
){
    fun criarComValoresPadrao(): SegundaClasse{
        return SegundaClasse("Jefferson", 24);
    }
}

fun main(){
    //sem o companion object, ele não permite instanciar o método parecido com a forma estática
    //já que o conteúdo de cada propriedade já é preenchido internamente dentro do método
    //o valores setados na instância do objeto não fazem sentido..
    var segundaClasse = SegundaClasse("Gustavo", 36).criarComValoresPadrao();

    //com o companion object já funciona..
    var minhaClasse = MinhaClasse.criarComValoresPadrao(segundaClasse);

    println(minhaClasse.nome);
    println(minhaClasse.idade);
}

