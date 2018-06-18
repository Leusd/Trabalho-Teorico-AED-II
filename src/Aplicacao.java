import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Aplicacao {
    static ComparaTroca c;
    static BufferedReader file;
    static List<Item> v2000;
    static List<Item> v4000;
    static List<Item> v8000;
    static List<Item> v16000;
    static List<Item> v32000;
    static List<Item> v64000;
    static List<Item> v128000;
    static List<Item[]> data;
    static  List <ComparaTroca> result;
    public static void main(String[] args) throws IOException {
        Instant startGeral = Instant.now();

        /*
        System.out.println("Lendo os dados...");

        file = new BufferedReader(new FileReader("dados_airbnb.txt"));

        v2000 = new ArrayList<Item>();
        v4000 = new ArrayList<Item>();
        v8000 = new ArrayList<Item>();
        v16000 = new ArrayList<Item>();
        v32000 = new ArrayList<Item>();
        v64000 = new ArrayList<Item>();
        v128000 = new ArrayList<Item>();
        data = new ArrayList<Item[]>();
        file.readLine();
        montarVetores();

        data.add(v2000.toArray(new Item[v2000.size()]));
        data.add(v4000.toArray(new Item[v4000.size()]));
        data.add(v8000.toArray(new Item[v8000.size()]));
        data.add(v16000.toArray(new Item[v16000.size()]));
        data.add(v32000.toArray(new Item[v32000.size()]));
        data.add(v64000.toArray(new Item[v64000.size()]));

        long[][][] result = new long[6][6][2];
        String[] fileNames={"bolha.csv","selecao.csv","insercao.csv","merge.csv","quick.csv"};
        int k=0;
        System.out.println("Executando as ordenacoes...");
        for (int ord = 0;ord<5;ord++) {
            System.out.println("Ordenando e gerando o arquivo "+fileNames[ord]);
            k=0;
            for (Item[] dataset : data) {
                for (int i = 0; i < 6; i++) {
                    long startTime = System.nanoTime();
                    if (ord == 0) bolha(dataset, dataset.length - 1);
                    if (ord == 1) selecao(dataset, dataset.length - 1);
                    if (ord == 2) insercao(dataset, dataset.length - 1);
                    if (ord == 3) MergeSort(dataset,0, dataset.length - 1);
                    if (ord == 4) QuickSort(dataset,0, dataset.length - 1);
                    long stopTime = System.nanoTime();
                    result[k][i][0] = stopTime - startTime;
                    if (k == 0) result[k][i][1] = 2000;
                    if (k == 1) result[k][i][1] = 4000;
                    if (k == 2) result[k][i][1] = 8000;
                    if (k == 3) result[k][i][1] = 16000;
                    if (k == 4) result[k][i][1] = 32000;
                    if (k == 5) result[k][i][1] = 64000;
                }
                k++;
            }
            writeTofile(fileNames[ord], result);
            System.out.println(fileNames[ord]+" criado com sucesso!");
        }
        Instant endGeral = Instant.now();
        System.out.print("Tempo geral de execucao: ");
        System.out.println(Duration.between(startGeral, endGeral));
        System.out.println("FIM");
        */

        teste();
    }
    public static  void teste(){
        result = new ArrayList<ComparaTroca>();
        Item []vetor1 = new Item[6];
        Item []vetor2 = new Item[6];
        Item []vetor3 = new Item[6];
        Item []vetor4 = new Item[6];
        Item []vetor5 = new Item[6];



        Item item1 = new Item(4);
        Item item2 = new Item(2);
        Item item3 = new Item(3);
        Item item4 = new Item(5);
        Item item5 = new Item(1);

        vetor1[1]= item1;
        vetor1[2]= item2;
        vetor1[3]= item3;
        vetor1[4]= item4;
        vetor1[5]= item5;

        vetor2[1]= item1;
        vetor2[2]= item2;
        vetor2[3]= item3;
        vetor2[4]= item4;
        vetor2[5]= item5;

        vetor3[1]= item1;
        vetor3[2]= item2;
        vetor3[3]= item3;
        vetor3[4]= item4;
        vetor3[5]= item5;

        vetor4[1]= item1;
        vetor4[2]= item2;
        vetor4[3]= item3;
        vetor4[4]= item4;
        vetor4[5]= item5;

        vetor5[1]= item1;
        vetor5[2]= item2;
        vetor5[3]= item3;
        vetor5[4]= item4;
        vetor5[5]= item5;


        System.out.println("Bolha");
        imprimir(vetor1);
        bolha(vetor1,5);
        imprimir(vetor1);

        System.out.println("Seleção");
        imprimir(vetor2);
        selecao(vetor2,5);
        imprimir(vetor2);

        System.out.println("Inserção");
        imprimir(vetor3);
        insercao(vetor3,5);
        imprimir(vetor3);

        System.out.println("Merge");
        imprimir(vetor4);
        MergeSort( vetor4, 1, vetor4.length-1);
        imprimir(vetor4);

        System.out.println("Quick");
        imprimir(vetor5);
        QuickSort(vetor5, 1, vetor4.length-1);
        imprimir(vetor5);


        imprimirResult(result);
    }
    public static void imprimir(Item [] vetor) {
        for (int i = 1; i < vetor.length; i++) {
            System.out.println(vetor[i].room_id);
        }
        System.out.println("<--------------------------------------------->");
    }
    public static void imprimirResult(List<ComparaTroca> list) {
       for (int i=0; i<list.size(); i++){
           System.out.println("Array " + list.get(i).tamanhoArray + "\nMétodo " + list.get(i).tipoOrdenacao + "\nComparações:" + list.get(i).comparaçao + "\nTrocas:" + list.get(i).troca + "\n-------------\n");
       }
    }
    public static void writeTofile (String filename, long[][][]x) throws IOException{
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        outputWriter.write("Amostra,Tempo(nS)");
        outputWriter.newLine();
        for (long[][] resultado : x) {
            for (long[] amostra : resultado) {
                outputWriter.write(amostra[1] + "," + amostra[0]);
                outputWriter.newLine();
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }
    public static void montarVetores() throws IOException {
        for(int i = 0; i<2000; i++) {
            Item item = escreverDados();

            v2000.add(item);v4000.add(item);
            v8000.add(item);v16000.add(item);
            v32000.add(item);v64000.add(item);v128000.add(item);
        }

        for(int i = 0; i<2000; i++) {
            Item item = escreverDados();

            v4000.add(item);v8000.add(item);
            v16000.add(item);v32000.add(item);
            v64000.add(item);v128000.add(item);
        }

        for(int i = 0; i<4000; i++) {
            Item item = escreverDados();

            v8000.add(item);v16000.add(item);
            v32000.add(item);v64000.add(item);v128000.add(item);
        }

        for(int i = 0; i<8000; i++) {
            Item item = escreverDados();

            v16000.add(item);v32000.add(item);
            v64000.add(item);v128000.add(item);
        }

        for(int i = 0; i<16000; i++) {
            Item item = escreverDados();

            v32000.add(item);v64000.add(item);v128000.add(item);
        }

        for(int i = 0; i<32000; i++) {
            Item item = escreverDados();

            v64000.add(item);v128000.add(item);
        }

        for(int i = 0; i<64000; i++) {
            Item item = escreverDados();

            v128000.add(item);
        }
    }

    public static Item escreverDados() throws IOException {
        String leitor = file.readLine();
        String[] dados = leitor.split("\t");

        Item item = new Item(Integer.parseInt(dados[0]),Integer.parseInt(dados[1]),
                dados[2], dados[3], dados[4], dados[5], Integer.parseInt(dados[6]),
                Float.parseFloat(dados[7]), Integer.parseInt(dados[8]), Float.parseFloat(dados[9]),
                Float.parseFloat(dados[10]), dados[11]);

        return item;
    }

    public static class ComparaTroca{
        int comparaçao = 0;
        int troca = 0;
        String tipoOrdenacao;
        int tamanhoArray;

    }

    public static  class Item {
        private int room_id, host_id, reviews, accommodates;
        private String room_type, country, city, neighborhood, property_type;
        private float overall_satisfactio, bedrooms, price;

        public Item(int room_id, int host_id, String room_type, String country, String city, String neighborhood, int reviews, float overall_satisfaction, int accommodates, float bedrooms, float price, String property_type){
            this.room_id = room_id;
            this.host_id = host_id;
            this.reviews = reviews;
            this.accommodates = accommodates;
            this.room_type = room_type;
            this.country = country;
            this.city = city;
            this.neighborhood = neighborhood;
            this.property_type = property_type;
            this.overall_satisfactio = overall_satisfactio;
            this.bedrooms = bedrooms;
            this.price = price;
        }

        public Item(int room_id){
            this.room_id = room_id;
        }

        public int chave() {
            return this.room_id;
        }
    }

    public static void bolha(Item v[], int n) {
        c = new ComparaTroca();
        int i, j;
        Item temp;
        c.tipoOrdenacao= "bolha";
        c.tamanhoArray = v.length;
        for (i = 1; i <= n - 1; i++) {
            c.comparaçao++;
            for (j = 1; j <= (n - i); j++) {
                if (v[j].chave() > v[j + 1].chave()) {
                    c.comparaçao++;
                    c.troca++;
                    temp = v[j];
                    v[j] = v[j + 1];
                    v[j + 1] = temp;
                }
            }
        }
        result.add(c);
    }

    public static void selecao(Item v[], int n) {
        c = new ComparaTroca();
        c.tipoOrdenacao= "Seleção";
        c.tamanhoArray = v.length;
        for (int i = 1; i <= n - 1; i++) {
            c.comparaçao++;
            int min = i;
            for (int j = i + 1; j <= n; j++)
                if (v[j].chave() < v[min].chave()){
                    min = j;
                    c.troca++;
                }
            c.comparaçao++;
            Item x = v[min];
            v[min] = v[i];
            v[i] = x;
        }
        result.add(c);
    }

    public static void insercao(Item v[], int n) {
        int j;
        c = new ComparaTroca();
        c.tipoOrdenacao= "Inserção";
        c.tamanhoArray = v.length;
        for (int i = 2; i <= n; i++) {
            Item x = v[i];
            j = i - 1;
            v[0] = x;
            c.comparaçao++;
            while (x.chave() < v[j].chave()) {
                v[j + 1] = v[j];
                j--;
                c.troca++;
            }
            v[j + 1] = x;
            c.comparaçao++;
        }
        result.add(c);
    }

    public static void MergeSort(Item v[], int inicio, int fim) {
        c = new ComparaTroca();
        c.tamanhoArray= v.length;
        c.tipoOrdenacao= "Merge";
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            MergeSort(v, inicio, meio);
            MergeSort(v, meio + 1, fim);
            merge(v, inicio, meio, fim);
        }
        result.add(c);
    }

    private static void merge(Item v[], int inicio, int meio, int fim) {
        c.comparaçao++;
        int n1, n2, i, j, k;
        n1 = meio - inicio + 1;
        n2 = fim - meio;

        Item A1[] = new Item[n1 + 1];
        Item A2[] = new Item[n2 + 1];

        for (i = 0; i < n1; i++)
            A1[i] = v[inicio + i];
        for (j = 0; j < n2; j++)
            A2[j] = v[meio + j + 1];

        A1[i] = new Item (Integer.MAX_VALUE);
        A2[j] = new Item (Integer.MAX_VALUE);

        i = 0; j = 0;

        for (k = inicio; k <= fim; k++) {
            if (A1[i].chave() <= A2[j].chave()){
                v[k] = A1[i++];
                c.troca++;
            }
            else{
                c.troca++;
                v[k] = A2[j++];
            }

        }
    }

    public static void QuickSort(Item v[], int esquerda, int direita) {
        c = new ComparaTroca();
        c.tamanhoArray= v.length;
        c.tipoOrdenacao= "Quick";
        Item temp, pivo;
        int i, j;
        i = esquerda;
        j = direita;
        pivo = v[(esquerda + direita) / 2];

        while(i <= j) {
             c.comparaçao++;
            while(v[i].chave() < pivo.chave() && i < direita) {
                c.comparaçao++;
                i++;
            }
            while(v[j].chave() > pivo.chave() && j > esquerda) {
                c.comparaçao++;
                j--;
            }

            if(i <= j) {
                c.troca++;
                temp = v[i];
                v[i] = v[j];
                v[j] = temp;
                i++;
                j--;
            }
        }

        if(j > esquerda){
            QuickSort(v, esquerda, j);
            c.comparaçao++;
        }

        if(i < direita){
            QuickSort(v, i, direita);
            c.comparaçao++;
        }

        result.add(c);

    }

}