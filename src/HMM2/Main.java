package HMM2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HMM hmm = new HMM();
		BaumWelch baumWelch = new BaumWelch();
		String path = "C:\\Users\\zhangzhizhi\\Documents\\Everyone\\��־��\\�γ�\\����ѧϰ\\����ҵ\\letter.data\\Step3_train\\";
		try {
			List list = new ArrayList<String>();
			String s = null;
			//��д�ļ�
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(path+"word_uniq.data")));
			//ѭ������
			while ((s = br.readLine()) != null) {
				
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int[][] O = {{1,2},{1,2}};
		baumWelch.coreByMutiObj(hmm, O, 1000);
	}

}
