package HMM2;

/**
 * ǰ���㷨�����ڼ���۲쵽���еĸ���
 * @author yang
 */
public class Forward {

	/**
	 * �ֲ�����
	 */
	//public double[][] alpha;

	/**
	 * ǰ���㷨����
	 * @param hmm HMMģ��
	 * @param o �۲�����
	 * @return �۲����и���
	 */
	public double core(HMM hmm, int[] o,double[][] alpha,double pprob) {
		double sum;
		//double retVal = 0;
		//alpha = new double[o.length][hmm.N];
		// ��ʼ��t=1
		for (int i = 0; i < hmm.N; i++) {
			alpha[0][i] = hmm.pi[i] * hmm.B[i][o[0]];
		}
		// �ݹ�
		for (int t = 0; t < o.length - 1; t++) {
			for (int j = 0; j < hmm.N; j++) {
				sum = 0;
				for (int i = 0; i < hmm.N; i++) {
					sum += alpha[t][i] * hmm.A[i][j];
				}
				alpha[t + 1][j] = sum * hmm.B[j][o[t + 1]];

			}
		}
		// ��ֹ
		for (int i = 0; i < hmm.N; i++) {
			// System.out.println(alpha[o.length-1][i]);
			pprob += alpha[o.length - 1][i];
		}
		return pprob;
	}

	/**
	 * ǰ���㷨���Ʋ�����������������ȡ���� ���alpha����0�������磬��0���ֵ�NaN
	 * @param hmm HMMģ��
	 * @param o �۲�ֵ����
	 * @param scale ��������
	 * @return
	 */
	public double coreByScale(HMM hmm, int[] o, double[][] alpha,double[] scale,double pprob) {
		double sum;
		// double retVal = 1;
		//double retVal = 0;
		//alpha = new double[o.length][hmm.N];
		// ��ʼ��t=1
		scale[0] = 0;
		for (int i = 0; i < hmm.N; i++) {
			alpha[0][i] = hmm.pi[i] * hmm.B[i][o[0]];
			scale[0] += alpha[0][i];
		}
		for (int i = 0; i < hmm.N; i++) {
			alpha[0][i] /= scale[0];
		}

		// �ݹ�
		for (int t = 0; t < o.length - 1; t++) {
			scale[t + 1] = 0;
			for (int j = 0; j < hmm.N; j++) {
				sum = 0;
				for (int i = 0; i < hmm.N; i++) {
					sum += alpha[t][i] * hmm.A[i][j];
				}
				alpha[t + 1][j] = sum * hmm.B[j][o[t + 1]];
				scale[t + 1] += alpha[t + 1][j];
			}
			for (int i = 0; i < hmm.N; i++) {
				alpha[t + 1][i] /= scale[t + 1];
			}
		}

		// �������alphaֵ
		// for(int t = 0; t < o.length; t++){
		// for (int j = 0; j < hmm.N; j++) {
		// System.out.print(alpha[t][j]);
		// }
		// System.out.println();
		// }

		// ��ֹ
		// for (int i = 0; i < hmm.N; i++) {
		// System.out.println(alpha[o.length-1][i]);
		// retVal += scale[i];
		// retVal += Math.log(scale[i]);
		// }

		// ��������ĸ�����ԭ��һ�µ�ת��
		for (int i = 0; i < o.length; i++) {
			// System.out.println(scale[i]);
			// retVal *= scale[i];
			pprob += Math.log(scale[i]);
		}
		return pprob;
		// return Math.log(retVal);
	}

}
