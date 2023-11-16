package fundamentos

fun main(){
    var lista = listOf(1,2,3,4,5,6);

    var pares= lista.filter { it%2==0 }

    lista.forEach{ println(it) };
    println("");
    pares.forEach({ println(it) });
    println("");


    //lista para alterar valores
    var listaMutavel = mutableListOf(1,2,3,5,8,9,6);
    println("mutableListOf: altera valores numa lista");
    listaMutavel.removeAt(1);
    listaMutavel.remove(5);
    listaMutavel.forEach({ println(it) });

    println("setOf: não considera valores duplicados");
    var setNumeros = setOf(1,2,3,2);
    setNumeros.forEach({ println(it) });

    println("mapOf: valores com chaves");

    val mapNomeIdade = mapOf("Gustavo" to 24, "Henrique" to 36);
    mapNomeIdade.forEach({ println(it) });


}