import java.util.*;

class Matriz{
  int[][] matriz_jogo;
  int linha_0;
  int coluna_0;
  //Construtor da Matriz usada nas pesquisas
  Matriz(int[] vetor){
    this.matriz_jogo = new int[4][4];
    int i=0;

    for(int l=0; l<4; l++){
      for(int c=0; c<4; c++){
        this.matriz_jogo[l][c] = vetor[i];
        if(this.matriz_jogo[l][c]==0){
          this.linha_0=l;
          this.coluna_0=c;
        }
        i++;
      }
    }
  }
  //Construtor da matriz antes de efetuar movimento
  Matriz(Matriz matrizCopia) {
    this.matriz_jogo = new int[4][4];
    for (int l = 0 ; l < 4 ; l++){
      for (int c = 0; c<4 ; c++) {
        matriz_jogo[l][c] = matrizCopia.matriz_jogo[l][c];
      }
    }
    this.linha_0 = matrizCopia.linha_0;
    this.coluna_0 = matrizCopia.coluna_0;
  }

  //Ver as jogadas possíveis
  char[] alternativas(){
    char[] movimentosPossiveis = new char[4];
    for (int i = 0 ; i < 4 ; i++)
    			movimentosPossiveis[i] = 'n';
		char[] tipoMovimento = new char[4];
			tipoMovimento[0] = 'c';
			tipoMovimento[1] = 'b';
			tipoMovimento[2] = 'e';
			tipoMovimento[3] = 'd';
		int j= 0;
  		for (int i=0 ; i < 4 ; i++) {
  			if (tipoMovimento[i] == 'c' && this.linha_0==0) //Tentar mover cima mas o "0" está na na primeira linha
  				continue;
  			else if (tipoMovimento[i] == 'b' && this.linha_0==3) //Tentar mover para baixo mas o "0" está na última linha
  				continue;
  			else if (tipoMovimento[i] == 'e' && this.coluna_0==0) //Tentar mover para a esquerda mas o "0" está na primeira coluna
  				continue;
  			else if (tipoMovimento[i] == 'd' && this.coluna_0==3) //Tentar mover para a direita mas o "0" está na última coluna
  				continue;
  			movimentosPossiveis[j] = tipoMovimento[i]; //Se é possível fazer um movimento válido no "0" então guarda-se
  			j++;
  		}
		return movimentosPossiveis;
  }

  //Efetuar uma jogada
  void mover(char movimento){
    switch(movimento){
      case 'c':
        this.matriz_jogo[linha_0][coluna_0] = this.matriz_jogo[linha_0-1][coluna_0]; //Passar o valor que está em cima do "0" para o lugar do "0"
        this.matriz_jogo[linha_0-1][coluna_0] = 0; //Colocar "0" na posição em cima dele
        this.linha_0-=1; //Atualizar a linha do "0"
        break;
      case 'b':
        this.matriz_jogo[linha_0][coluna_0] = this.matriz_jogo[linha_0+1][coluna_0]; //Passar o valor que está em baixo do "0" para o lugar do "0"
        this.matriz_jogo[linha_0+1][coluna_0] = 0; //Colocar "0" na posição em baixo dele
        this.linha_0+=1; //Atualizar a linha do "0"
        break;
      case 'e':
        this.matriz_jogo[linha_0][coluna_0] = this.matriz_jogo[linha_0][coluna_0-1]; //Passar o valor que está à esquerda do "0" para o lugar do "0"
        this.matriz_jogo[linha_0][coluna_0-1] = 0; //Colocar "0" na posição à esquerda dele
        this.coluna_0-=1; //Atualizar a coluna do "0"
        break;
      case 'd':
        this.matriz_jogo[linha_0][coluna_0] = this.matriz_jogo[linha_0][coluna_0+1]; //Passar ovalor que está à direita do "0" para o lugar do "0"
        this.matriz_jogo[linha_0][coluna_0+1] = 0; //Colocar "0" na posição à esquerda dele
        this.coluna_0+=1; //Atualizar a coluna do "0"
        break;
    }
  }

  //Verifica se a ConfigInicial é igual ao ConfigFinal
  boolean verifica(Matriz Jogo){
    for (int l = 0 ; l < 4 ; l++){
      for (int c = 0 ; c < 4 ; c++){
    		if (this.matriz_jogo[l][c] != Jogo.matriz_jogo[l][c])
  			   return false;
      }
    }
    return true;
  }

}
