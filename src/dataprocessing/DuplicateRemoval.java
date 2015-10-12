package dataprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ȥ����
 * @author zhangzhizhi
 *
 */
public class DuplicateRemoval {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "C:\\Users\\zhangzhizhi\\Documents\\Everyone\\��־��\\�γ�\\����ѧϰ\\����ҵ\\letter.data\\Step3_train\\";
		try {
			List list = new ArrayList<String>();
			String s = null;
			//��д�ļ�
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(path+"word.data")));
			BufferedWriter w = new BufferedWriter(new FileWriter(path+"word_uniq.data"));
			//ѭ������
			while ((s = br.readLine()) != null) {
				if (list.indexOf(s) == -1){
					list.add(s);
					w.write(s+"\r\n");
				}
			}
			br.close();
			w.flush();
			w.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
