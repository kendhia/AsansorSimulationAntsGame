import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class OlasiliklarProgrami {

	static Integer inisZamani = 0;
	static double enKucukOITS = 90;
	static Random random = new Random();
	static Integer[] gelisSirasi = {2, 5, 7, 4, 8, 6, 3, 1, 7, 9, 4, 5, 8, 4, 5, 6, 2, 7};
	static Hashtable<Worker, Integer> enKucukOITSResults;
	static int[] arrA;
	static String resultFilter;
	static int count = 0;
	
	public OlasiliklarProgrami(int n) {
		arrA = new int[n];
	}
	
	public static void displayOlasilikar(List<Worker> workers) {
		System.out.println();
		System.out.println("******** According to our beautiful algorithm the best result is ********");
		System.out.println(resultFilter);
		for (Worker worker: workers) {
			System.out.format("The %s went to the floor number %d in: %d . \n", worker.getName(), worker.getNum(), enKucukOITSResults.get(worker));
		}
		System.out.format("The OITS for this Asansor is : %.2f . \n", enKucukOITS);
	}

	private static Hashtable<Worker, Integer> dorderITSHesaplama(Hashtable<Worker, Integer> results, List<Worker> dorder, int hizi) {
		for (int i =0; i < dorder.size(); i++) {
			Worker worker = dorder.get(i);
			int its =  0;
			if (i == 0) {
				its = worker.getNum() * hizi + 4 + inisZamani;
			}
			else if (i > 0 && worker.getNum() == dorder.get(i-1).getNum()) {
				its = results.get(dorder.get(i-1));
			}
			else {
				its = (worker.getNum() - dorder.get(i-1).getNum()) * hizi + 4 + results.get(dorder.get(i-1));
			}
			results.put(worker, its);
		}
		int newInisZamani = dorder.get(dorder.size()-1).getNum()*hizi + results.get(dorder.get(dorder.size()-1)) + 4;
		inisZamani = newInisZamani;
		return results;
	}

	private static  double OITSHesplama(List<Worker> workers, Hashtable<Worker, Integer> results) {
		if (workers == null || workers.size() == 0) return 0.0;
		int toplam = 0;
		for (Worker worker : workers) {
			toplam += results.get(worker);
		}
		return toplam/workers.size();
	}

	private static Hashtable<Worker, Integer> pqITSHesplama(AsansorPQ kuyruk,  Hashtable<Worker, Integer> results) {
		int count = 0;
		int size = kuyruk.size();
		List<Worker> dorder = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			int its;
			if (kuyruk.size() < 4 && dorder.isEmpty()) {
				dorder.addAll(kuyruk.pollAll());
				dorderITSHesaplama(results, dorder, kuyruk.getHizi());
			} else {
				dorder.add(kuyruk.poll());
				count++;
				if (count == 4) {
					dorderITSHesaplama(results, dorder, kuyruk.getHizi());
					dorder.clear();
					count = 0;
				}
			}

		}
		return results;
	}

	private static Hashtable<Worker, Integer> fifoITSHesplama(AsansorFIFO kuyruk,  Hashtable<Worker, Integer> results) {
		int count = 0;
		int size = kuyruk.getSize();
		List<Worker> dorder = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			if (kuyruk.getSize() < 4 && dorder.isEmpty()) {
				dorder.addAll(kuyruk.getAll());
				Collections.sort(dorder);
				dorderITSHesaplama(results, dorder, (int)kuyruk.getHizi());
			}
			else {
				dorder.add(kuyruk.deQueueWorker());
				count++;
				if (count == 4) {
					Collections.sort(dorder);
					dorderITSHesaplama(results, dorder, (int)kuyruk.getHizi());
					dorder.clear();
					count = 0;
				}
			}

		}

		return results;
	}

	public static String getBestFilter() {
		return resultFilter;
	}
	
	public static double getBestOITS() {
		return enKucukOITS;
	}
	
	private static String generateRandomName() {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		final int N = 1;
		String randomName="";
		for (int i = 0; i < N; i++) {
			randomName += alphabet.charAt(random.nextInt(alphabet.length()));
		}
		return randomName;
	}

	public static void olasilikRndm(Hashtable<Worker, Integer> results, List<Worker> workers, int n) {
		if (n <= 0) {
			prepareKuyrukOlasilik(workers, arrA, results);
		} else {
			arrA[n - 1] = 0;
			olasilikRndm(results, workers, n - 1);
			arrA[n - 1] = 1;
			olasilikRndm(results, workers, n - 1);
		}
	}
	
	
	private static List<Worker> getListOfWorkers() {
		List<Worker> workers = new ArrayList<>();
		for (int num : gelisSirasi) {
			Worker worker = new Worker();
			worker.setName(generateRandomName());
			worker.setNum(num);
			workers.add(worker);
		}
		return workers;
	}


	private static void prepareKuyrukOlasilik(List<Worker> workers, int[] filter, Hashtable<Worker, Integer> results) {
		inisZamani = 0;

		AsansorFIFO fifoKuyruk = new AsansorFIFO();
		AsansorPQ pqKuyruk = new AsansorPQ();
		for (int i = 0; i < workers.size(); i++) {
			if ( 0 == filter[i]) {
				fifoKuyruk.enQueueWorker(workers.get(i));
			} else {
				pqKuyruk.add(workers.get(i));
			}
		}
		if (fifoKuyruk.isEmpty()) {
			pqITSHesplama(pqKuyruk, results);
		} else if (pqKuyruk.isEmpty()) {
			fifoITSHesplama(fifoKuyruk, results);
		} else {
			fifoITSHesplama(fifoKuyruk, results);
			inisZamani = 0;
			pqITSHesplama(pqKuyruk, results);

		}

		double simdikiOITS = OITSHesplama(workers, results);
		if ( enKucukOITS > simdikiOITS) {
			enKucukOITS = simdikiOITS;
			enKucukOITSResults = new Hashtable(results);
			resultFilter = Arrays.toString(filter); 
		}
	}
}
