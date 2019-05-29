import java.util.*;

class No implements Comparable<No>{
  Matriz matriz_jogo; //Matriz correspondente ao nó
  int NumPassos; //Numero Passos é igual à profundidade em que o nó se encontra
  char movimento; //Movimento que foi efetuado para chegar à este nó
  No pai; //MatrizPai que gerou nova matriz
  int custo; //Custo usado para pesquisa ASTAR/GREEDY
  String id; //Chave da HashMap para verificar visitados

  //Construtor do nó vazio
  No(){
      this.matriz_jogo = null;
      this.movimento = 'n'; //null
      this.NumPassos = 0;
      this.pai = null;
      this.custo = 0;
      this.id = "";
  }
  //Construtor do pai
  No(Matriz matrizInicial){
    	this.matriz_jogo = matrizInicial;
    	this.movimento = 'n';
    	this.NumPassos = 0;
      this.pai= null;
      this.custo = 0;
      this.id = HashMapKey(matrizInicial);
  }

  //Construtor dos filhos
  No(Matriz matrizTemp,char movimento, No pai){
    matriz_jogo = matrizTemp;
    matriz_jogo.mover(movimento);
    this.movimento = movimento;
    this.pai = pai;
    this.NumPassos= pai.NumPassos+1;
    this.custo = 0;
    this.id = HashMapKey(matriz_jogo);
  }

  //Matriz para string
  private String HashMapKey(Matriz matrix) {
    String string = "";
    for(int i=0;i<4;i++)
      for(int j=0; j<4; j++)
        string+=matrix.matriz_jogo[i][j] + ",";
    return string;
    }

  //Fazer Filhos
  No[] fazerFilhos(char[] movimentosPossiveis){
   Matriz[] matrizCopia = new Matriz[4];
   for(int i=0 ; i<4 ; i++){
     if(this.movimento == 'c' && movimentosPossiveis[i] == 'b')
      movimentosPossiveis[i]='n';
     if(this.movimento == 'b' && movimentosPossiveis[i] == 'c')
      movimentosPossiveis[i]='n';
     if(this.movimento == 'd' && movimentosPossiveis[i] == 'e')
      movimentosPossiveis[i]='n';
     if(this.movimento == 'e' && movimentosPossiveis[i] == 'd')
      movimentosPossiveis[i]='n';
   }
   No[] filhos = new No[4];
   int j=0;
   for (int i = 0 ; i < 4 ; i++) {
       matrizCopia[i] = new Matriz(matriz_jogo);
       if(movimentosPossiveis[i]!='n'){
        filhos[j] = new No(matrizCopia[j],movimentosPossiveis[i],this);
        j++;
      }
   }

   return filhos;
  }
  //Imprime o caminho da ConfInicial até à ConfFinal
  static String caminho(No atual, String caminho){
    if (atual.movimento == 'n')
    	return caminho;
    else {
  		caminho = atual.movimento + caminho;
  		atual = atual.pai;
      return caminho(atual,caminho);
    }
  }

  //Comparar Custo
  @Override
  public int compareTo(No filho){
    if(this.custo < filho.custo){
      return -1;
    }
    else if(this.custo == filho.custo)
      return 0;
    else
      return 1;
  }


}
