package dataprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * ��������������Ӣ�ĵ�������
 * @author zhangzhizhi
 *
 */
public class PureEnglish {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "C:\\Users\\zhangzhizhi\\Documents\\Everyone\\��־��\\�γ�\\����ѧϰ\\����ҵ\\letter.data\\Step4_word\\";
		try {
			List list = new ArrayList<String>();
			String s = null;
			//��д�ļ�
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(path+"word.txt")));
			BufferedWriter w = new BufferedWriter(new FileWriter(path+"Step1_pureEnglish.txt"));
			//ѭ������
			while ((s = br.readLine()) != null) {
				w.write(s.substring(0,s.indexOf(' ')).replaceAll("-", "").replaceAll("\\.", "")+"\r\n");
			}
			br.close();
			w.flush();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
