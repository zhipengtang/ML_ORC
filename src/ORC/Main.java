package ORC;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * ����ڳ���
 * 
 * @author zhangzhizhi
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// HMM����ռ�ı��أ���Ҫ����
		double factor = 0.1;
		// HMMѵ���õ���ģ��
		double[] HMM_model = { 0.0776255707762557, 0.0228310502283105,
				0.0410958904109589, 0.0273972602739726, 0.0958904109589042,
				0.0182648401826484, 0.0433789954337900, 0.0159817351598174,
				0.0936073059360730, 0.00456621004566210, 0.0159817351598174,
				0.0616438356164384, 0.0319634703196347, 0.0958904109589042,
				0.0730593607305936, 0.0251141552511415, 0.00684931506849315,
				0.0502283105022831, 0.0273972602739726, 0.0479452054794520,
				0.0479452054794520, 0.0136986301369863, 0.00913242009132420,
				0.00684931506849315, 0.0251141552511415, 0.0205479452054795 };
		try {
			/**
			 * 1.����������
			 */
			Classifier[] classifier = new Classifier[1];
			double[] result = new double[classifier.length];
			/**
			 * 2.����ѵ������������
			 */
			String path = "C:\\Users\\zhangzhizhi\\Documents\\Everyone\\��־��\\�γ�\\����ѧϰ\\����ҵ\\letter.data\\";
			File inputFile = new File(path + "Step3_train\\hog.arff");// ѵ�������ļ�
			ArffLoader atf = new ArffLoader();
			atf.setFile(inputFile);
			Instances instancesTrain = atf.getDataSet(); // ����ѵ���ļ�

			inputFile = new File(path + "Step3_test\\hog.arff");// ���������ļ�
			atf.setFile(inputFile);
			Instances instancesTest = atf.getDataSet(); // ��������ļ�

			/**
			 * 3.���÷������������кţ���һ��Ϊ0�ţ���instancesTest.numAttributes()����ȡ����������
			 */
			instancesTest.setClassIndex(0);
			instancesTrain.setClassIndex(0);

			/**
			 * 4.��ʼ������������
			 */
			//weka.classifiers.functions.LibSVM
			//weka.classifiers.bayes.NaiveBayes
			//weka.classifiers.trees.J48
			//weka.classifiers.rules.ZeroR
			
			// LibSVM
			classifier[0] = (Classifier) Class.forName(
					"weka.classifiers.lazy.IBk").newInstance();

			/**
			 * 5.ѵ��������
			 */
			for (int i = 0; i < classifier.length; i++){
				classifier[i].buildClassifier(instancesTrain);
			}
			
			/**
			 * 6.���Ϸ�������׼ȷ��
			 */
			double[] distribution;
			Evaluation eval = new Evaluation(instancesTrain);
			for (int i = 0; i < classifier.length; i++){
				int right = 0;	//�÷�������ȷ��
				int wrong = 0;	//�÷�����������
				for (int j = 0; j < instancesTest.numInstances(); j++){
					distribution = classifier[i].distributionForInstance(instancesTest.instance(j));
					for (int k = 0; k < distribution.length; k++){
						distribution[k] += factor*HMM_model[k];
					}
					double max = 0;
					int index = 0;
					
					for (int k = 0; k < distribution.length; k++){
						if (distribution[k] > max){
							max = distribution[k];
							index = k;
						}
					}
					
					int rightValue = (int)instancesTest.instance(j).classValue();
					if (index == rightValue){
						right++;
					}
					else {
						wrong++;
					}
				}
				//������
				result[i] = right*1.0/(right+wrong);
				//�������������
				eval.evaluateModel(classifier[i], instancesTest);
				System.out.println("����������׼ȷ�ʣ�"+(1-eval.errorRate()));
				System.out.println("����HMM׼ȷ�ʣ�"+result[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
