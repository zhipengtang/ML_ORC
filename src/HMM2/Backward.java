package HMM2;

/**
 * �����㷨
 * @author yang
 */
public class Backward {

	/**
	 * �������(�ֲ�����)
	 */
	//public double[][] beta;

	/**
	 * �����㷨����
	 * @param hmm HMMģ��
	 * @param o �۲�����
	 * @return ���ﷵ�ع۲����еĸ���
	 */
	public double core(HMM hmm, int[] o,double[][] beta,double pprob) {

		//double retVal = 0;
		double sum;
		//beta = new double[o.length][hmm.N];

		// ��ʼ��
		for (int i = 0; i < hmm.N; i++) {
			beta[o.length - 1][i] = 1;
		}
		// ��������
		for (int t = o.length - 2; t >= 0; t--) {
			for (int i = 0; i < hmm.N; i++) {
				sum = 0;
				for (int j = 0; j < hmm.N; j++) {
					sum += hmm.A[i][j] * hmm.B[j][o[t + 1]] * beta[t + 1][j];
				}
				beta[t][i] = sum;
			}
		}
		// �������betaֵ
		/*for(int t = 0; t < o.length; t++){ for (int j = 0; j < hmm.N; j++) {
		 * System.out.print(beta[t][j]+","); } System.out.println(); } */
		// ��ֹ
		for (int i = 0; i < hmm.N; i++) {
			// System.out.println(beta[0][i]);
			// System.out.println(hmm.pi[i] * hmm.B[i][o[0]] * beta[0][i]);
			pprob += hmm.pi[i] * hmm.B[i][o[0]] * beta[0][i];
		}
		return pprob;
	}

	/**
	 * �����㷨���Ʋ���������������������
	 * @param hmm HMMģ��
	 * @param o �۲�����
	 * @param scale ��������
	 * @return ���ظ���
	 */

	public double coreByScale(HMM hmm, int[] o,double[][] alpha,double[][] beta, double[] scale,double pprob) {
		// double retVal = 1;
		//double retVal = 0;// ȡ�����õ�
		// double sum;
		double sum, tmp;
		tmp = 0;
		//beta = new double[o.length][hmm.N];

		new Forward().coreByScale(hmm, o, alpha,scale,pprob);// ��������Ҫһ�£�����forward���scaleֵ��
		/*for (int i = 0; i < o.length; i++) { System.out.println(scale[i]+",");
		 * } */
		// ��ʼ��
		for (int i = 0; i < hmm.N; i++) {
			beta[o.length - 1][i] = 1;
			// scale[o.length - 1]=1;
			// beta[o.length - 1][i] = 1/scale[o.length -1];
		}

		// ��������
		for (int t = o.length - 2; t >= 0; t--) {
			for (int i = 0; i < hmm.N; i++) {
				sum = 0;
				for (int j = 0; j < hmm.N; j++) {
					sum += hmm.A[i][j] * hmm.B[j][o[t + 1]] * beta[t + 1][j];
				}
				// beta[t][i] = sum;
				// scale[t] += beta[t][i];
				// beta[t][i] = sum / scale[o.length -1];
				beta[t][i] = sum / scale[t + 1];
			}

			// for (int i = 0; i < hmm.N; i++) {
			// beta[t][i] /= scale[t];
			// }
		}

		// �������betaֵ
		/*for(int t = 0; t < o.length; t++){ for (int j = 0; j < hmm.N; j++) {
		 * System.out.print(beta[t][j]+","); } System.out.println(); } */

		for (int i = 0; i < hmm.N; i++) {
			// System.out.print(beta[0][i]+",");
			// System.out.println(hmm.pi[i] * hmm.B[i][o[0]] * beta[0][i]+",");
			tmp += hmm.pi[i] * hmm.B[i][o[0]] * beta[0][i];
		}

		for (int i = 1; i < o.length; i++) {
			// System.out.println(scale[i]);
			pprob += Math.log(scale[i]);
		}
		pprob += Math.log(tmp);
		// ������������betaΪ��ҪĿ��,�۲����и�����ǰ���㷨�ṩ
		// ����������ĸ���*/
		return pprob;
	}
}