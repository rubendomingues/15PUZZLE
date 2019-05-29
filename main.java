import java.util.*;

public class main extends Pesquisa{

  public static void main(String[] args){
    Scanner scan =  new Scanner (System.in);
    MenuInicial();
    System.out.println("Insira a configuação inicial:");

    //Configuração Inicial
    int JogoInicial[][] = new int[4][4];
    for(int l=0; l<4; l++){
      for(int c=0; c<4; c++){
        JogoInicial[l][c]=scan.nextInt();
      }
    }

    //Transformar Matriz Inicial em Vetor
    int VetorInicial[] = new int[16];
    int k=0;
    for(int l=0; l<4; l++){
      for(int c=0; c<4; c++){
        VetorInicial[k]=JogoInicial[l][c];
        k++;
      }
    }
    System.out.println("Insira a configuração final:");
    //Configuração Final
    int JogoFinal[][] = new int[4][4];
    for(int l=0; l<4; l++){
      for(int c=0; c<4; c++){
        JogoFinal[l][c]=scan.nextInt();
      }
    }

    //Transformar Matriz Final em Vetor
    int VetorFinal[] = new int[16];
    k=0;
    for(int l=0; l<4; l++){
      for(int c=0; c<4; c++){
        VetorFinal[k]=JogoFinal[l][c];
        k++;
      }
    }

    if(solucao(JogoInicial,VetorInicial)==0){
      System.out.println("Não existe solução possível!");
      return;
    }
    else if(solucao(JogoFinal,VetorFinal)==0){
      System.out.println("Não existe solução possível!");
      return;
    }
    Matriz matrizInicial = new Matriz(VetorInicial);
    Matriz matrizFinal = new Matriz(VetorFinal);
    MenuBuscas();
    System.out.println();
    System.out.print("Opção:");
    String opcao = scan.next();
    switch(opcao){
      case "1":
        Pesquisa.DFS(matrizInicial, matrizFinal);
        break;
      case "2":
        Pesquisa.BFS(matrizInicial, matrizFinal);
        break;
      case "3":
        System.out.print("Profundidade Maxima:");
        int profundidade_maxima = scan.nextInt();
        Pesquisa.IDFS(matrizInicial, matrizFinal, profundidade_maxima);
        break;
      case "4":
        MenuHeuristicas();
        int opcao2 = scan.nextInt();
        if(opcao2==1)
          Pesquisa.ASTAR1(matrizInicial, matrizFinal);
        if(opcao2==2)
          Pesquisa.ASTAR2(matrizInicial, matrizFinal);
        break;
      case "5":
        MenuHeuristicas();
        int opcao3 = scan.nextInt();
        if(opcao3==1)
          Pesquisa.GREEDY1(matrizInicial, matrizFinal);
        if(opcao3==2)
          Pesquisa.GREEDY2(matrizInicial, matrizFinal);
        break;
    }
  }

  //----------------------FUNÇÕES------------------------------------------
  //Verificar se existe solução possível
  public static int solucao(int jogo[][], int vec[]){
    int zero_pos=0;
    int inversoes_counter=0;
    //Guardar a posição do zero
    for(int l=0; l<4; l++){
      for(int c=0; c<4; c++){
        if(jogo[l][c]==0)
          zero_pos=l;
      }
    }
    //Contar o nº de inversões possíveis(Paridade)
    for(int i=0; i<16; i++){
      for(int j=i+1; j<16; j++){
        if((vec[i]>vec[j]) && (vec[j]!=0))
          inversoes_counter++;
      }
    }
    //Verificar se é possível ou não
    if((zero_pos==3 || zero_pos==1) && (inversoes_counter%2==0))
      return 1;
    else if((zero_pos==2 || zero_pos==0) && (inversoes_counter%2!=0))
      return 1;
    else
      return 0;

  }



  //---------------------------FIM----------------------------------------
  //---------------------------MENUS--------------------------------------
  static void MenuInicial(){
    System.out.println("******************************************");
    System.out.println("**                                      **");
    System.out.println("**             BEM-VINDO                **");
    System.out.println("**                AO                    **");
    System.out.println("**            JOGO DOS 15               **");
    System.out.println("**                                      **");
    System.out.println("******************************************");
  }

  static void MenuBuscas(){
    System.out.println();
    System.out.println("Selecione o tipo de busca a utilizar:");
    System.out.println("1) DFS");
    System.out.println("2) BFS");
    System.out.println("3) IDFS");
    System.out.println("4) A*");
    System.out.println("5) GREEDY");
  }

  static void MenuHeuristicas(){
    System.out.println();
    System.out.println("Selecione o tipo de heuristica a utilizar:");
    System.out.println("1) Somatório do número de peças fora do lugar");
    System.out.println("2) Manhattan distance");
  }
  //---------------------------FIM----------------------------------------
}
