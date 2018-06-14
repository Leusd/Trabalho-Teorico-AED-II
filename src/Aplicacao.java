import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Aplicacao {
    public static void main(String[] args) throws IOException {

        BufferedReader file = new BufferedReader(new FileReader("dados_airbnb.txt"));

        List<Item> v2000 = new ArrayList<Item>();

        file.readLine();

        for(int i = 0; i<2000; i++) {
            String leitor = file.readLine();
            String[] dados = leitor.split("\t");
            Item item = new Item(Integer.parseInt(dados[0]),Integer.parseInt(dados[1]),
                                dados[2], dados[3], dados[4], dados[5], Integer.parseInt(dados[6]),
                                Float.parseFloat(dados[7]), Integer.parseInt(dados[8]), Float.parseFloat(dados[9]),
                                Float.parseFloat(dados[10]), dados[11]);
            v2000.add(item);
        }
    }

    public static class ComparaTroca{
        int comparaçao = 0;
        int troca = 0;
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

        public int chave() {
            return this.room_id;
        }
    }

        public static void bolha(Item v[], int n) {

            int i, j;
            Item temp;
            for (i = 1; i <= n - 1; i++) {
                for (j = 1; j <= (n - i); j++) {
                    if (v[j].chave() > v[j + 1].chave()) {
                        temp = v[j];
                        v[j] = v[j + 1];
                        v[j + 1] = temp;
                    }
                }
            }
        }

        public static void selecao(Item v[], int n) {
            for (int i = 1; i <= n - 1; i++) {
                int min = i;
                for (int j = i + 1; j <= n; j++)
                    if (v[j].chave() < v[min].chave())
                        min = j;
                Item x = v[min];
                v[min] = v[i];
                v[i] = x;
            }
        }

        public static void insercao(Item v[], int n) {
            int j;
            for (int i = 2; i <= n; i++) {
                Item x = v[i];
                j = i - 1;
                v[0] = x;

                while (x.chave() < v[j].chave()) {
                    v[j + 1] = v[j];
                    j--;
                }
                v[j + 1] = x;
            }
        }

    public static void MergeSort(Item v[], int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            MergeSort(v, inicio, meio);
            MergeSort(v, meio + 1, fim);
            merge(v, inicio, meio, fim);
        }
    }

    private static void merge(Item v[], int inicio, int meio, int fim) {
        int n1, n2, i, j, k;
        n1 = meio - inicio + 1;
        n2 = fim - meio;

        Item A1[] = new Item[n1 + 1];
        Item A2[] = new Item[n2 + 1];

        for (i = 0; i < n1; i++)
            A1[i] = v[inicio + i];
        for (j = 0; j < n2; j++)
            A2[j] = v[meio + j + 1];

        //A1[i] = new Item (Integer.MAX_VALUE);
        //A2[j] = new Item (Integer.MAX_VALUE);

        i = 0; j = 0;

        for (k = inicio; k <= fim; k++) {
            if (A1[i].chave() <= A2[j].chave())
                v[k] = A1[i++];
            else
                v[k] = A2[j++];
        }
    }

    public static void QuickSort(Item v[], int esquerda, int direita) {
        Item temp, pivo;
        int i, j;
        i = esquerda;
        j = direita;
        pivo = v[(esquerda + direita) / 2];

        while(i <= j) {
            while(v[i].chave() < pivo.chave() && i < direita) i++;
            while(v[j].chave() > pivo.chave() && j > esquerda) j--;

            if(i <= j) {
                temp = v[i];
                v[i] = v[j];
                v[j] = temp;
                i++;
                j--;
            }
        }

        if(j > esquerda)
          QuickSort(v, esquerda, j);

        if(i < direita)
          QuickSort(v, i, direita);
    }
}
