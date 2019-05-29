import java.util.*;
import java.lang.*;
public class Pesquisa extends No{

  //Print do Tempo/Passos/Caminho/Memoria
  static void print(long tempoFinal, long tempoInicial, int NosGerados, No atual, int NumPassos, long actualMemUsed){
    String caminho = "";
    System.out.println("Encontrou solução!");
    System.out.println("Tempo: " + (tempoFinal - tempoInicial) + "ms");
    caminho = No.caminho(atual,caminho);
    System.out.println("Caminho: " + caminho.toUpperCase());
    System.out.println("Numero de Passos: " + NumPassos);
    System.out.println("Nós Gerados: " + NosGerados);
    System.out.println("Memoria: " + actualMemUsed/1000000 + "MB");
  }

  //Pesquisa em largura
  public static void BFS(Matriz matrizInicial, Matriz matrizFinal){
	   long tempoInicial = System.currentTimeMillis();
     long beforeUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
     Queue<No> ListaNos = new LinkedList<No>();
     HashMap<String, No> visitados = new HashMap<String, No>();
     No raiz = new No(matrizInicial);
     ListaNos.offer(raiz);
     int NumPassos=0;
     int NosGerados=0;
     while(ListaNos.peek()!=null){
       No atual = ListaNos.poll();
       if(atual.matriz_jogo.verifica(matrizFinal)){
         long tempoFinal= (long)(System.currentTimeMillis());
         long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
         long actualMemUsed=afterUsedMem-beforeUsedMem;
         print(tempoFinal,tempoInicial,NosGerados,atual,NumPassos,actualMemUsed);
         return;
       }

         char[] movimentos = atual.matriz_jogo.alternativas();
         No[] filhos = atual.fazerFilhos(movimentos);
         if(atual.NumPassos > NumPassos)
         NumPassos=atual.NumPassos;
         for(int i=0; i<4; i++){
           if(filhos[i]!=null){
             if(!visitados.containsKey(filhos[i].id)){
             ListaNos.add(filhos[i]);
             ++NosGerados;}
           }
        }
        visitados.put(atual.id, atual);

    }
     System.out.println("Não encontrou solução!");
  }

  //Pesquisa em profundidade
  public static void DFS(Matriz matrizInicial, Matriz matrizFinal){
    long tempoInicial = System.currentTimeMillis();
    long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    Stack<No> StackNos = new Stack<No>();
    No raiz = new No(matrizInicial);
    StackNos.push(raiz);
    int NumPassos=0;
    int NosGerados=0;
    while(!(StackNos.empty())){
      No atual = StackNos.pop();
      if(atual.matriz_jogo.verifica(matrizFinal)){
        long tempoFinal= (long)(System.currentTimeMillis());
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        NumPassos=atual.NumPassos;
        print(tempoFinal,tempoInicial,NosGerados,atual,NumPassos,actualMemUsed);
        return;
      }
        if(atual.NumPassos < 80){
          char[] movimentos = atual.matriz_jogo.alternativas();
          No[] filhos = atual.fazerFilhos(movimentos);
          for(int i=0; i<4; i++){
            if(filhos[i]!=null){
              StackNos.push(filhos[i]);
              ++NosGerados;
            }
          }
        }
      }
    System.out.println("Não encontrou solução!");
  }

  //Pesquisa em profundidade iterativa
  public static void IDFS(Matriz matrizInicial, Matriz matrizFinal, int profundidade_maxima){
    long tempoInicial = System.currentTimeMillis();
    long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    int sol=0;
    for(int j=0; j<profundidade_maxima; j++){
      Stack<No> StackNos = new Stack<No>();
      No raiz = new No(matrizInicial);
      StackNos.push(raiz);
      int NumPassos=0;
      int NosGerados=1;
      while(!(StackNos.empty())){
        No atual = StackNos.pop();
        if(atual.matriz_jogo.verifica(matrizFinal)){
          long tempoFinal= (long)(System.currentTimeMillis());
          long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
          long actualMemUsed=afterUsedMem-beforeUsedMem;
          NumPassos=atual.NumPassos;
          print(tempoFinal,tempoInicial,NosGerados,atual,NumPassos,actualMemUsed);
          return;
        }
        if(atual.NumPassos <= j){
         char[] movimentos = atual.matriz_jogo.alternativas();
         No[] filhos = atual.fazerFilhos(movimentos);
          for(int i=0; i<4; i++){
            if(filhos[i]!=null){
              StackNos.push(filhos[i]);
            }
            ++NosGerados;
          }
        }
      }
    }
    System.out.println("Não encontrou solução!");
  }

  //Pesquisa A*
  //opcao = 1 -> somatório do número de peças fora do lugar
  public static void ASTAR1(Matriz matrizInicial, Matriz matrizFinal){
    long tempoInicial = System.currentTimeMillis();
    long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    No raiz = new No(matrizInicial);
	  PriorityQueue<No> ListaPrioridade = new PriorityQueue<No>();
    HashMap<String, No> visitados = new HashMap<String, No>();
    ListaPrioridade.offer(raiz);
    int NumPassos = 0;
    int NosGerados = 0;
    while(ListaPrioridade.peek()!=null){
      No atual = ListaPrioridade.poll();
      if(atual.matriz_jogo.verifica(matrizFinal)){
        long tempoFinal= (long)(System.currentTimeMillis());
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        NumPassos=atual.NumPassos;
        print(tempoFinal,tempoInicial,NosGerados,atual,NumPassos,actualMemUsed);
        return;
      }

        char[] movimentos = atual.matriz_jogo.alternativas();
         No[] filhos = atual.fazerFilhos(movimentos);
        for(int i=0; i<4; i++){
          if(filhos[i]!=null){
            if(!visitados.containsKey(filhos[i].id)){
            filhos[i].custo = OutOfPlace(filhos[i], matrizFinal) + filhos[i].NumPassos;
            ListaPrioridade.offer(filhos[i]);
            ++NosGerados;
            }
          }
       }
       visitados.put(atual.id, atual);

    }
    System.out.println("Não encontrou solução!");
  }

  //Pesquisa A*
  //opcao = 2 -> manhattan distance (somatório das distâncias de cada peça ao seu lugar na configuração final).
  public static void ASTAR2(Matriz matrizInicial, Matriz matrizFinal){
    long tempoInicial = System.currentTimeMillis();
    long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    No raiz = new No(matrizInicial);
	  PriorityQueue<No> ListaPrioridade = new PriorityQueue<No>();
    HashMap<String, No> visitados = new HashMap<String, No>();
    ListaPrioridade.offer(raiz);
    int NumPassos = 0;
    int NosGerados = 0;
    while(ListaPrioridade.peek()!=null){
      No atual = ListaPrioridade.poll();
      if(atual.matriz_jogo.verifica(matrizFinal)){
        long tempoFinal= (long)(System.currentTimeMillis());
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        NumPassos=atual.NumPassos;
        print(tempoFinal,tempoInicial,NosGerados,atual,NumPassos,actualMemUsed);
        return;
      }

    char[] movimentos = atual.matriz_jogo.alternativas();
         No[] filhos = atual.fazerFilhos(movimentos);
        for(int i=0; i<4; i++){
          if(filhos[i]!=null){
            if(!visitados.containsKey(filhos[i].id)){
            filhos[i].custo = Distance(filhos[i], matrizFinal) + filhos[i].NumPassos;
            ListaPrioridade.offer(filhos[i]);
            ++NosGerados;
            }
          }
       }
       visitados.put(atual.id, atual);

    }
    System.out.println("Não encontrou solução!");
  }

  //Pesquisa GREEDY
  //opcao = 1 -> somatório do número de peças fora do lugar
  public static void GREEDY1(Matriz matrizInicial, Matriz matrizFinal){
    long tempoInicial = System.currentTimeMillis();
    long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    No raiz = new No(matrizInicial);
	  PriorityQueue<No> ListaPrioridade = new PriorityQueue<No>();
    HashMap<String, No> visitados = new HashMap<String, No>();
    ListaPrioridade.offer(raiz);
    int NumPassos = 0;
    int NosGerados = 0;
    while(ListaPrioridade.peek()!=null){
      No atual = ListaPrioridade.poll();
      if(atual.matriz_jogo.verifica(matrizFinal)){
        long tempoFinal= (long)(System.currentTimeMillis());
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        NumPassos = atual.NumPassos;
        print(tempoFinal,tempoInicial,NosGerados,atual,NumPassos,actualMemUsed);
        return;
      }

        char[] movimentos = atual.matriz_jogo.alternativas();
         No[] filhos = atual.fazerFilhos(movimentos);
        for(int i=0; i<4; i++){
          if(filhos[i]!=null){
            if(!visitados.containsKey(filhos[i].id)){
            filhos[i].custo = OutOfPlace(filhos[i], matrizFinal);
            ListaPrioridade.offer(filhos[i]);
            ++NosGerados;
            }
          }
       }
       visitados.put(atual.id, atual);

    }
    System.out.println("Não encontrou solução!");
  }

  //Pesquisa GREEDY
  //opcao = 2 -> manhattan distance (somatório das distâncias de cada peça ao seu lugar naconfiguração final).
  public static void GREEDY2(Matriz matrizInicial, Matriz matrizFinal){
    long tempoInicial = System.currentTimeMillis();
    long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    No raiz = new No(matrizInicial);
    PriorityQueue<No> ListaPrioridade = new PriorityQueue<No>();
    HashMap<String, No> visitados = new HashMap<String, No>();
    ListaPrioridade.offer(raiz);
    int NumPassos = 0;
    int NosGerados = 0;
    while(ListaPrioridade.peek()!=null){
      No atual = ListaPrioridade.poll();
      if(atual.matriz_jogo.verifica(matrizFinal)){
        long tempoFinal= (long)(System.currentTimeMillis());
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        NumPassos = atual.NumPassos;
        print(tempoFinal,tempoInicial,NosGerados,atual,NumPassos,actualMemUsed);
        return;
      }
        char[] movimentos = atual.matriz_jogo.alternativas();
        No[] filhos = atual.fazerFilhos(movimentos);
        for(int i=0; i<4; i++){
          if(filhos[i]!=null){
            if(!visitados.containsKey(filhos[i].id)){
            filhos[i].custo = Distance(filhos[i], matrizFinal);
            ListaPrioridade.offer(filhos[i]);
            ++NosGerados;
            }
          }
       }
       visitados.put(atual.id, atual);


    }
    System.out.println("Não encontrou solução!");
  }

  //Somatório do número de peças fora do lugar
  public static int OutOfPlace(No atual, Matriz matrizFinal){
    int NumPecas = 0;
    for (int l = 0 ; l < 4 ; l++) {
      for (int c = 0 ; c < 4 ; c++) {
        if (atual.matriz_jogo.matriz_jogo[l][c] != matrizFinal.matriz_jogo[l][c])
          NumPecas++;
      }
    }
    return NumPecas;
  }

  static int Distance(No no, Matriz objetivo) {
    int peca = 0;
    int distance = 0;
    int valor = 0;
    int[] lc = new int[2];
    for (int l = 0 ; l < 4 ; l++) {
      for (int c = 0 ; c < 4 ; c++) {
        valor = no.matriz_jogo.matriz_jogo[l][c];
        lc = finalpos(objetivo,valor);
        peca++;
        if (valor != 0 && valor != peca)
          distance += Math.abs((int) l - lc[0]) + Math.abs((int)c - lc[1]);
      }
    }
    return distance;
  }

  static int[] finalpos(Matriz Final, int valor) {
      int[] lc = new int[2];
      for(int l = 0; l < 4; l++) {
          for(int c = 0; c < 4; c++){
              if(Final.matriz_jogo[l][c] == valor){
                  lc[0] = l;
                  lc[1] = c;
                  return lc;
              }
          }
      }
      return lc;
  }

  //-----------------------------FIM-----------------------------------------------------
}
