import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainClass {
	//0: FIFOITS || 1: PQITS || 2: RNDM
	static Hashtable<Worker, Double[]> results  = new Hashtable();
	static Hashtable<Worker, Integer> enKucukOITSResults = new Hashtable<>();
	static double enKucukOITS;
	static Double[] inisZamani = {0.0, 0.0, 0.0};
	static Integer[] gelisSirasi = {2, 5, 7, 4, 8, 6, 3, 1, 7, 9, 4, 5, 8, 4, 5, 6, 2, 7};
	static Random random = new Random();
	static List<Worker> workers;


	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		workers = getListOfWorkers(); 
		startTheProgram(1);
		System.out.println();
		System.out.println("Wanna find the best algorithm ? -y/n-");
		if (!scanner.nextLine().equals("y")) {
			return;
		}
		Hashtable<Worker, Integer> resultsOlasilik = new Hashtable<>();
		OlasiliklarProgrami olasiliklarProgrami = new OlasiliklarProgrami(18);
		olasiliklarProgrami.olasilikRndm(resultsOlasilik, workers, 18);
		olasiliklarProgrami.displayOlasilikar(workers);
		
		System.out.println("Wanna start the second program ? -y/n-");
		if (!scanner.nextLine().equals("y")) {
			return;
		}
		System.out.println("**************************");
		System.out.println("**************************");
		workers = new ArrayList(getRandomListOfWorkers(30));
		displayRandomList();
		startTheProgram(1);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		displayComparison();
		
		System.out.println("Wanna start the third program ? -y/n-");
		if (!scanner.nextLine().equals("y")) {
			return;
		}
		System.out.println("**************************");
		System.out.println("**************************");
		startTheProgram(2);
		displayComparison();

	}

	private static void displayRandomList() {
		
		for (Worker worker : workers) {
			System.out.format("The %s worker is going to %d floor . \n", worker.getName(), worker.getNum());
		}
	}
	
	
	
	
	private static void displayComparison() {
		double[] OITSResults = new double[2];
		for (int i=0; i< 2; i++) {
			OITSResults[i] = OITSHesplama(workers, i, results);
		}
		double fifoOITS = OITSResults[0];
		double pqOITS = OITSResults[1];
		
		AsansorPQ pqKuyruk = new AsansorPQ();
		AsansorFIFO fifoKuyruk = new AsansorFIFO();
		double ortalama = 0.0;
		for (Worker worker :workers) {
			ortalama += worker.getNum();
		}
		ortalama /= workers.size();
		
		for (Worker worker : workers) {
			if (worker.getNum() < ortalama) {
				fifoKuyruk.enQueueWorker(worker);
			} else {
				pqKuyruk.add(worker);
			}
		}
		//Make the calculation using PQ Asansor
		pqITSHesplama(pqKuyruk, false);
				
				//First make the calculation using FIFO Asansor
		fifoITSHesplama(fifoKuyruk, false);
		
		for (int i=0; i< 2; i++) {
			OITSResults[i] = OITSHesplama(workers, i, results);
		}
		
		comparingPQandFIFO((OITSResults[0]+OITSResults[0])/2, fifoOITS, pqOITS);

	}
	
	private static void startTheProgram(int hiziCarpimi) {
		
		//Make the calculation using PQ Asansor
		pqITSHesplama(getPQKuyruk(workers, hiziCarpimi), false);
		
		//First make the calculation using FIFO Asansor
		fifoITSHesplama(getFifoKuyruk(workers, hiziCarpimi), false);


		//Make the calculation using a Random Asansor
		randomITSHesplama(getFifoKuyruk(workers, hiziCarpimi));

		//Calculate the OITS for each of the asansors
		double[] OITSResults = new double[3];
		for (int i=0; i< 3; i++) {
			OITSResults[i] = OITSHesplama(workers, i, results);
		}

		displayResults(workers, OITSResults);
	}

	public static List<Worker> getListOfWorkers() {
		List<Worker> workers = new ArrayList<>();
		for (int num : gelisSirasi) {
			Worker worker = new Worker();
			worker.setName(generateRandomName());
			worker.setNum(num);
			workers.add(worker);
		}
		return workers;
	}


	private static void displayResults(List<Worker> workers, double[] OITSResults) {

		for (int i = 0; i < 3; i++) {
			if (i== 0 ) System.out.println("*********FIFoAsansor Results*********");
			else if (i==1) System.out.println("*********PQAsansor Results*********");
			else System.out.println("*********Random Asansor Results*********");

			for (Worker worker: workers) {
				System.out.format("The %s went to the floor number %d in: %.2f . \n", worker.getName(), worker.getNum(), results.get(worker)[i]);
			}
			System.out.format("The OITS for this Asansor is : %.2f . \n", OITSResults[i]);
		}

	}

	//this method calculate the OITS for FIFO and PQ, @indice 0: FIFO 1: PQ 2: RNDM
	private static  double OITSHesplama(List<Worker> workers, int indice, Hashtable<Worker, Double[]> results) {
		if (workers == null || workers.size() == 0) return 0.0;
		int toplam = 0;
		for (Worker worker : workers) {
			Double[] res = results.get(worker);
			if (res != null) {
				toplam += results.get(worker)[indice];
			}
		}
		return toplam/workers.size();
	}


	//Should we add the waiting for the asansor for the 2nd of 3rd ... workers or not !!
	private static void dorderITSHesaplama(List<Worker> dorder, double hizi, int indice) {
		for (int i =0; i < dorder.size(); i++) {
			Worker worker = dorder.get(i);
			double its = 0  ;
			if (i == 0) {
				its = worker.getNum() * hizi + 4 + inisZamani[indice];
			}
			else if (i > 0 && worker.getNum() == dorder.get(i-1).getNum()) {
				its = results.get(dorder.get(i-1))[indice];
			}
			else {
				its = (worker.getNum() - dorder.get(i-1).getNum()) * hizi + 4 + results.get(dorder.get(i-1))[indice];
			}
			Double[] itss = results.get(worker);
			if (itss == null)  itss = new Double[3];
			itss[indice] = its;
			results.put(worker, itss);
		}
		double newInisZamani = (dorder.get(dorder.size()-1).getNum()*hizi) + results.get(dorder.get(dorder.size()-1))[indice] + 4;
		inisZamani[indice] = newInisZamani;

	}

	private static void comparingPQandFIFO( double enKucukOITS, double fifoOITS, double pqOITS) {
		System.out.format("The difference between using PQAsansor and Best Case is %.2f  seconds. \n" , (enKucukOITS - pqOITS));
		System.out.format("The difference between using FIFOAsansor and Best Case is %.2f  seconds. \n" , (enKucukOITS - fifoOITS));
	}
	
	
	private static void pqITSHesplama(AsansorPQ kuyruk, boolean random) {
		int count = 0;
		int size = kuyruk.size();
		List<Worker> dorder = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			int its;
			if (kuyruk.size() < 5 && dorder.isEmpty()) {
				dorder.addAll(kuyruk.pollAll());
				if (random) {
					dorderITSHesaplama(dorder, kuyruk.getHizi(), 2);
				}
				else {
					dorderITSHesaplama(dorder, kuyruk.getHizi(), 1);
				}
			} else {
				dorder.add(kuyruk.poll());
				count++;
				if (count == 4) {
					if (random) {
						dorderITSHesaplama(dorder, kuyruk.getHizi(), 2);
					}
					else {
						dorderITSHesaplama(dorder, kuyruk.getHizi(), 1);
					}
					dorder.clear();
					count = 0;
				}
			}
		}
	}

	private static void fifoITSHesplama(AsansorFIFO kuyruk, boolean random) {
		int count = 0;
		int size = kuyruk.getSize();
		List<Worker> dorder = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			if (kuyruk.getSize() < 5 && dorder.isEmpty()) {
				dorder.addAll(kuyruk.getAll());
				Collections.sort(dorder);
				if (random)dorderITSHesaplama(dorder, kuyruk.getHizi(), 2);
				else dorderITSHesaplama(dorder, kuyruk.getHizi(), 0);

			}
			else {
				dorder.add(kuyruk.deQueueWorker());
				count++;
				if (count == 4) {
					Collections.sort(dorder);
					if (random) dorderITSHesaplama(dorder, kuyruk.getHizi(), 2);
					else dorderITSHesaplama(dorder, kuyruk.getHizi(), 0);
					dorder.clear();
					count = 0;
				}
			}

		}

		return ;
	}

	private static List<Worker> randomITSHesplama(AsansorFIFO kuyruk) {
		List<Worker> result = new ArrayList<>();

		AsansorFIFO fifoListe = new AsansorFIFO();
		AsansorPQ pqListe = new AsansorPQ();
		int size = kuyruk.getSize();
		for(int i=0;i<size;i++) {
			if(random.nextInt(2) == 0) {
				fifoListe.enQueueWorker(kuyruk.deQueueWorker());
			} else {
				pqListe.add(kuyruk.deQueueWorker());
			}
		}
		fifoITSHesplama(fifoListe, true);
		pqITSHesplama(pqListe, true);
		return result;
	}

	private static String generateRandomName() {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		final int N = 4;
		String randomName="";
		for (int i = 0; i < N; i++) {
			randomName += alphabet.charAt(random.nextInt(alphabet.length()));
		}
		return randomName;
	}



	private static AsansorFIFO getFifoKuyruk(List<Worker> workers, int hiziCarpimi) {
		AsansorFIFO kuyrukFifo = new AsansorFIFO();
		kuyrukFifo.setHizi(  kuyrukFifo.getHizi()/hiziCarpimi );
		for (Worker worker : workers) {
			kuyrukFifo.enQueueWorker(worker);
		}
		return kuyrukFifo;
	}

	private static AsansorPQ getPQKuyruk(List<Worker> workers, int hiziCarpimi) {
		AsansorPQ kuyrukPq = new AsansorPQ();
		kuyrukPq.setHizi(kuyrukPq.getHizi() / hiziCarpimi);

		for (Worker worker : workers) {
			kuyrukPq.add(worker);
		}
		return kuyrukPq;
	}


	private static List<Worker> getRandomListOfWorkers(int n) {
		List<Worker> workers = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			Worker worker = new Worker();
			worker.setName(generateRandomName());
			worker.setNum(random.nextInt(50)+ 1);
			workers.add(worker);
		}
		return workers;
	}


}
