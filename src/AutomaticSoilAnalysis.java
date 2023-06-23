import java.util.Random;
import java.util.Scanner;

/*
INTEGRANTES DA GLOBAL SOLUTIONS:
FELIPE RODRIGUES MAIA RM: 94234
ITHAN CASSIANO RM: 94931
MARCELO FILHO RM: 93124
 */

public class AutomaticSoilAnalysis {
	public static final int SENSORES = 20;
	public static final int DIAS = 3;
	public static final int ID_MIN = 4600;
	public static final int ID_MAX = 4800 - ID_MIN;
	public static final int IDS = 20;
	public static final int PH_MIN = 40;
	public static final int PH_MAX = 81 - PH_MIN;
	public static final int UMIDADE_MIN = 20;
	public static final int UMIDADE_MAX = 91 - UMIDADE_MIN;
	public static final float PH_OK_MIN = (float) 5.5;
	public static final float PH_OK_MAX = (float) 6.5;
	public static final int CRESCENTE = 1;
	public static final int DECRESCENTE = 2;
	public static class Sensor{
		public Sensor(int id, float pH, int umidade) {
			this.id = id;
			this.pH = pH;
			this.umidade = umidade;
		}

		public int id;
		public float pH;
		public int umidade;
	}

    public static void orderdarArray(Sensor mapaSensores[]){
        Scanner sc = new Scanner(System.in);
        int ordenacao = 1;
        System.out.println("Como deseja organizar o Mapa de Sensores?");
        boolean escolha_invalida = true;
        do{
            System.out.println("1 - CRESCENTE, 2 - DECRESCENTE\n");
            System.out.println("DIGITE O NÚMERO: ");
            try{
                ordenacao = sc.nextInt();

                if(ordenacao == CRESCENTE || ordenacao == DECRESCENTE){
                    escolha_invalida = false;
                }else{
                    System.out.println("Digite um número válido!!\n");
                    escolha_invalida = true;
                }
            } catch (Exception e) {
                System.out.println("Digite um número válido e não um texto !!!\n");
                break;
            }

        }while(escolha_invalida);
        
        if(ordenacao == CRESCENTE){
            for (int i = 0; i < SENSORES; i++) {
                for (int j = 0; j < SENSORES-1; j++) {
                    if(mapaSensores[i].id < mapaSensores[j].id){
                        int aux = mapaSensores[i].id;
                        mapaSensores[i].id = mapaSensores[j].id;
                        mapaSensores[j].id = aux;
                    }
                }
            }
        }else{
            for (int i = 0; i < SENSORES; i++) {
                for (int j = 0; j < SENSORES-1; j++) {
                    if(mapaSensores[i].id > mapaSensores[j].id){
                        int aux = mapaSensores[i].id;
                        mapaSensores[i].id = mapaSensores[j].id;
                        mapaSensores[j].id = aux;
                    }
                }
            }
        }
        
    }

    public static boolean verificarId(int[] vetor, int id) {
        boolean existe = false;
        for (int id_no_array : vetor) {
            if (id_no_array == id) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    public static void buscarId(Sensor mapaSensores[]){
        Scanner sc = new Scanner(System.in);
        boolean escolha_errada = true;
        int id;
        String resultado = "Sensor não disponível!\n";
        System.out.println("Busca linear, digite um Id entre 4600 e 4800: ");
        do{
            id = sc.nextInt();

            if(id>4600 && id<4800){
                escolha_errada = false;
                for (int i = 0; i< SENSORES;i++) {
                    if(id == mapaSensores[i].id){
                        resultado = "O indíce que você procura é: " + i + ".\n";
                    }
                }
            }else{
                System.out.println("Digite um Id no intervalo 4600 e 4800!");
                escolha_errada = true;
            }
        }
        while(escolha_errada);

        System.out.println(resultado);;
    }

    public static void proximoDia(Sensor mapaSensores[]){
        Random r = new Random();
        for (int i = 0; i < SENSORES; i++) {
            mapaSensores[i].pH = (float) (r.nextInt(PH_MAX) + PH_MIN) / 10;
            mapaSensores[i].umidade = r.nextInt(UMIDADE_MAX) + UMIDADE_MIN;
        }
    }
	public static void main(String[] args) {
		Sensor mapaSensores[] = new Sensor[SENSORES];
        int pH_Alterado[] = new int[IDS];
        Scanner sc_main = new Scanner(System.in);
		Random r = new Random();
        int buscar_novamente = 1;

		for (int i = 0; i < SENSORES; i++) {
			int id = r.nextInt(ID_MAX) + ID_MIN;
			float ph = (float) (r.nextInt(PH_MAX) + PH_MIN) / 10;
			int umidade = r.nextInt(UMIDADE_MAX) + UMIDADE_MIN;
			mapaSensores[i] = new Sensor(id,ph,umidade);

            if(ph < PH_OK_MIN || ph > PH_OK_MAX){
                pH_Alterado[i] = id;
            }else{
                pH_Alterado[i] = 0;
            }
		}

        for (int i = 0; i < DIAS; i++) {
            proximoDia(mapaSensores);
            for (int j = 0; j < SENSORES; j++) {
                if(mapaSensores[j].pH < PH_OK_MIN || mapaSensores[j].pH > PH_OK_MAX){
                    if(verificarId(pH_Alterado,mapaSensores[j].id)){
                        continue;
                    }else{
                        for (int k = 0; k < SENSORES; k++) {
                            if(pH_Alterado[k] == 0){
                                pH_Alterado[k] = mapaSensores[j].id;
                                break;
                            }
                        }
                    }
                }
            }
        }

        orderdarArray(mapaSensores);
        buscarId(mapaSensores);


        while (buscar_novamente != 2){
            System.out.println("\n Deseja realizar outra busca?");
            System.out.println("1 - SIM, 2 - NÃO");
            try {
                buscar_novamente = sc_main.nextInt();
            }catch (Exception e){
                System.out.println("Opção inválida, fim do programa.");
            }
            if(buscar_novamente == 1){
                buscarId(mapaSensores);
            }
        }
        System.out.println("\nFim do programa.");
	}
}
