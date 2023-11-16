package fundamentos




fun main() {
    //ponto de interrogação: aceita valor nulo
    val nome: String? = null;
    val tamanho: Int = nome?.length ?: 0;
    println(tamanho);

    //ponto de exclamação: garanto que mesmo aceitando nulo no código a variável será informada
    val garanteNomeInformado: String? = "Jefferson";
    val toShort: Short = garanteNomeInformado!!.length.toShort();
    println(toShort);

    //Lista nula
    val listaNula: List<Int>? = null;

    //Conteúdos nulos de uma lista
    val conteudosNulosLista: List<Int?> = listOf(1, 2, null, 3);

    for (item in conteudosNulosLista) {
        println(item);
    }


}