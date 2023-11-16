package fundamentos

class Carro(var cor: String, var ano: Int, var proprietario: Dono) {
}

data class Dono(var nome: String, var idade: Int){

}

fun main(){
    var carro = Carro("Preto",2022, Dono("Jefferson", idade=47));

    println(carro.cor);
    carro.cor="Branco";
    println(carro.cor);
    println(carro.proprietario);


}