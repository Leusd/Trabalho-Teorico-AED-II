import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        int forma=1;//0 para nao ordenar
        //ordenando
        switch (forma){
            case 0:{
                // Caso medio
                break;
            }
            //ordena os dados
            case 1:{
                v2000= v2000.stream().sorted(Comparator.comparing(Item::chave)).collect(Collectors.toList());
                v4000= v4000.stream().sorted(Comparator.comparing(Item::chave)).collect(Collectors.toList());
                v8000= v8000.stream().sorted(Comparator.comparing(Item::chave)).collect(Collectors.toList());
                v16000= v16000.stream().sorted(Comparator.comparing(Item::chave)).collect(Collectors.toList());
                v32000= v32000.stream().sorted(Comparator.comparing(Item::chave)).collect(Collectors.toList());
                v64000= v64000.stream().sorted(Comparator.comparing(Item::chave)).collect(Collectors.toList());
                v128000= v128000.stream().sorted(Comparator.comparing(Item::chave)).collect(Collectors.toList());
               break;
            }
            //ordena os dados de forma invertida
            case 2:{
                v2000= v2000.stream().sorted(Comparator.comparing(Item::chave).reversed()).collect(Collectors.toList());
                v4000= v4000.stream().sorted(Comparator.comparing(Item::chave).reversed()).collect(Collectors.toList());
                v8000= v8000.stream().sorted(Comparator.comparing(Item::chave).reversed()).collect(Collectors.toList());
                v16000= v16000.stream().sorted(Comparator.comparing(Item::chave).reversed()).collect(Collectors.toList());
                v32000= v32000.stream().sorted(Comparator.comparing(Item::chave).reversed()).collect(Collectors.toList());
                v64000= v64000.stream().sorted(Comparator.comparing(Item::chave).reversed()).collect(Collectors.toList());
                v128000= v128000.stream().sorted(Comparator.comparing(Item::chave).reversed()).collect(Collectors.toList());
                break;
            }
        }
        data.add(v2000.toArray(new Item[v2000.size()]));
        data.add(v4000.toArray(new Item[v4000.size()]));
        data.add(v8000.toArray(new Item[v8000.size()]));
        data.add(v16000.toArray(new Item[v16000.size()]));
        data.add(v32000.toArray(new Item[v32000.size()]));
        data.add(v64000.toArray(new Item[v64000.size()]));
        data.add(v128000.toArray(new Item[v128000.size()]));

        long[][][] result = new long[7][6][2];
        String[] fileNames={"bolha.csv","selecao.csv","insercao.csv","merge.csv","quick.csv","mergeLindao.csv"};
        int k=0;
        long max=0;
        long min=0;
        long[][][] resultFiltered = new long[7][4][2];
        long[][][] resultFinal = new long[7][1][2];
        int i;
        long soma=0;
        System.out.println("Executando as ordenacoes...");
        for (int ord = 0;ord<6;ord++) {
            System.out.println("Ordenando e gerando o arquivo "+fileNames[ord]);
            k=0;
            for (Item[] dataset : data) {
                for (i = 0; i < 6; i++) {
                    long startTime = System.nanoTime();
                    if (ord == 0) bolha(dataset, dataset.length - 1);
                    if (ord == 1) selecao(dataset, dataset.length - 1);
                    if (ord == 2) insercao(dataset, dataset.length - 1);
                    if (ord == 3) MergeSort(dataset,0, dataset.length - 1);
                    if (ord == 4) QuickSort(dataset,0, dataset.length - 1);
                    //if (ord == 5) MergeLindao(dataset,0, dataset.length - 1);
                    long stopTime = System.nanoTime();
                    result[k][i][0] = stopTime - startTime;
                    if (k == 0) result[k][i][1] = 2000;
                    if (k == 1) result[k][i][1] = 4000;
                    if (k == 2) result[k][i][1] = 8000;
                    if (k == 3) result[k][i][1] = 16000;
                    if (k == 4) result[k][i][1] = 32000;
                    if (k == 5) result[k][i][1] = 64000;
                    if (k == 6) result[k][i][1] = 128000;
                }
                k++;
            }
            //REMOVENDO O MAIOR E MENOR
            k=0;
            for (long[][] ele : result){
                i=0;
                min=ele[0][0];
                max=ele[0][0];
                for (long[] t: ele) {
                    if (t[0]>max)max=t[0];
                    if (t[0]<min)min=t[0];
                }
                for (long[] t: ele) {
                    if (t[0]==max){
                      //skip
                    }else if (t[0]==min){
                        //skip
                    }else {
                        resultFiltered[k][i][0]=t[0];
                        resultFiltered[k][i][1]=t[1];
                        i++;
                    }
                }
                soma=0;
                for (long[] item:resultFiltered[k]) {
                    soma+=item[0];

                    if(i>=3){
                        resultFinal[k][0][0]=soma/4;
                        resultFinal[k][0][1]=resultFiltered[k][0][1];
                    }
                }
                k++;
            }

            writeTofile(fileNames[ord], resultFinal);
            System.out.println(fileNames[ord]+" criado com sucesso!");
        }
        Instant endGeral = Instant.now();
        System.out.print("Tempo geral de execucao: ");
        System.out.println(Duration.between(startGeral, endGeral));
        System.out.println("FIM");
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
    }

    public static void MergeLindao(Item v[], int inicio, int fim) {
        c = new ComparaTroca();
        c.tamanhoArray= v.length;
        c.tipoOrdenacao= "Merge";
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            MergeLindao(v, inicio, meio);
            MergeLindao(v, meio + 1, fim);
            intercalaIsso(v, inicio, meio, fim);
        }
        result.add(c);
    }

    private static void intercalaIsso(Item v[], int inicio, int meio, int fim) {

        c.comparaçao++;
        int posicaoLivre, inicioDoVetor1,inicioDoVetor2,i;
        Item aux[]=new Item[v.length];
        inicioDoVetor1=inicio;
        inicioDoVetor2=meio+1;
        posicaoLivre=inicio;
        while(inicioDoVetor1<=meio && inicioDoVetor2<=fim){
            if(v[inicioDoVetor1].room_id<=v[inicioDoVetor2].room_id){
                aux[posicaoLivre]=v[inicioDoVetor1];
                inicioDoVetor1++;
                c.troca++;
            }else{
                aux[posicaoLivre]=v[inicioDoVetor2];
                inicioDoVetor2++;
                c.troca++;
            }

            posicaoLivre++;
        }
        for(i=inicioDoVetor1;i<=meio;i++){
            aux[posicaoLivre]=v[i];
            posicaoLivre++;
        }

        for(i=inicioDoVetor2;i<=fim;i++){
            aux[posicaoLivre]=v[i];
            posicaoLivre++;

        }

        for(i=inicio;i<=fim;i++){
            v[i]=aux[i];
        }
    }

}
