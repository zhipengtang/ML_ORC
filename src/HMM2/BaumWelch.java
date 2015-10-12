package HMM2;

import HMM2.Backward;
import HMM2.Forward;
import HMM2.HMM;

public class  BaumWelch {
   /*
	double logProbF,logProbFall, logProbB;
	double logProbInit;// ��ʼ����
	double logProbFinal;// ���ո���
	double logProbPrev;
	double[] scale;
	// ��������
	int nIter;
	double delta;
	// ���ӷ�ĸ��ʱ����
	double[][] gamma;
	//double[][][] gammaall;
    double[][][] xi;
	//double[][][][] xiall;
	double[][] alpha;
	double[][] beta;
	double numeratorA, denominatorA;
	double numeratorB, denominatorB;
	
	Forward forward;
	Backward backward;*/
	
	/**
	 * ����ģ�飬�������޹�
	 * @param hmm hmmģ��
	 * @param o �۲����
	 * @return
	 */
	
	//O[][]�۲������Ƕ����е����
    public double coreByMutiObj(HMM hmm, int[][] O,int inter) {
    	double[][] gamma;   //gamma[t][i] t:[0,T-1],i:[0,N]
    	double[][][] xi;    //xi[t][i][j] t:[0,T-2],i:[0,N],j:[0,N]
		double[][] alpha;   //alpha[t][i] t:[0,T-1],i:[0,N]
		double[][] beta;    //beta[t][i]   t:[0,T-1],i:[0,N]
		double[] scale;     // scale[t] t:[0,T-1]
		double delta=0.0,probf,probPrev=0.0,probFinal=0.0;
		double[][] numeratorA = new double[hmm.N][hmm.N];  //numeratorA[i][j]
		double[][] numeratorB = new double[hmm.N][hmm.M];  //numeratorB[i][j]
		double[] denominatorA = new double[hmm.N];         //denominatorA[i]
		double[] denominatorB = new double[hmm.N];         //denominatorB[i]
		double[] pi = new double[hmm.N];		
		Forward forward = new Forward();
		Backward backward = new Backward();
		
		int nIter = 0;
		
		do{
			probf = 0;
			//��һ��probfֵ
			//probf ��ÿ���������еĸ���֮�ͣ�denominatorA,denominatorB��ÿ�ζ��������е�gamma[t][i]��ͣ�numeratorA,numeratorB:ÿ�ζ��������е�xi[t][i][j]��� 		
			for(int l = 0;l < O.length;l ++ ){
			  
			   probf = forward.coreByScale(hmm,O[l],alpha = new double[O[l].length][hmm.N], scale = new double[O[l].length], probf);
			  // System.out.println(probf);
			   backward.coreByScale(hmm, O[l],alpha = new double[O[l].length][hmm.N], beta = new double[O[l].length][hmm.N],scale = new double[O[l].length] , 0);
			   getGamma(hmm, O[l].length, alpha, beta,gamma = new double[O[l].length][hmm.N]);
		       getXi(hmm, O[l], alpha, beta,xi = new double[O[l].length][hmm.N][hmm.N]);
		             
		       for (int i = 0; i < hmm.N; i++) {
		    	   // hmm.pi[i] = 0.001 + 0.999 * gamma[0][i];  //pi[i] ����
		    	    pi[i] = gamma[0][i];
					for (int t = 0; t < O[l].length-1; t++) {
						denominatorA[i] += gamma[t][i];
						denominatorB[i] += gamma[t][i];
					}
					for (int j = 0; j < hmm.N; j++) {
						for (int t = 0; t < O[l].length-1; t++)
							numeratorA[i][j] += xi[t][i][j];   //gamma[t][i] t:[0,T-2],i:[0,N]���
					}
					denominatorB[i] += gamma[O[l].length-1][i];

					for (int k = 0; k < hmm.M; k++) {
						for (int t = 0; t < O[l].length; t++) {
							if (O[l][t] == k) {
								numeratorB[i][k] += gamma[t][i];  //gamma[t][i] t:[0,T-1],i:[0,N]���
							}
						}
					}
				}
		   	}
			System.out.println(probf);
			for (int i = 0; i < hmm.N; i++) {
				//hmm.pi[i] = 0.001 / hmm.N + 0.999 * pi[i] / o.length;
				hmm.pi[i] = 0.001  + 0.999 * pi[i] ;//pi[i] ����
				for (int j = 0; j < hmm.N; j++) {
					//hmm.A[i][j] = 0.001 / hmm.N + 0.999 * numeratorA[i][j] / denominatorA[i];
					hmm.A[i][j] = 0.001 + 0.999 * numeratorA[i][j] / denominatorA[i];   // A[i][j] ����
					numeratorA[i][j] = 0.0;  //  ����
				}
				for (int k = 0; k < hmm.M; k++) {
					//hmm.B[i][k] = 0.001 / hmm.M + 0.999 * numeratorB[i][k] / denominatorB[i];
					hmm.B[i][k] = 0.001 + 0.999 * numeratorB[i][k] / denominatorB[i];   // B[i][k] ����
					numeratorB[i][k] = 0.0;  //  ����
				}
				denominatorA[i] = denominatorB[i] = 0.0; // ����
			}
			
		   	probPrev = probf;   // ��ʱֵ
		   	probf = 0;   // ����
		   	//�ڶ���probfֵ
		   	for(int l = 0;l < O.length;l ++ ){
		  		probf = forward.coreByScale(hmm,O[l], alpha = new double[O[l].length][hmm.N], scale = new double[O[l].length],probf);
		   	   	backward.coreByScale(hmm, O[l],alpha = new double[O[l].length][hmm.N], beta = new double[O[l].length][hmm.N],scale = new double[O[l].length], 0);
			    getGamma(hmm, O[l].length, alpha, beta,gamma = new double[O[l].length][hmm.N]);
		        getXi(hmm, O[l], alpha, beta,xi = new double[O[l].length][hmm.N][hmm.N]);
	       	}
			System.out.println(probf);
		   	delta = Math.abs(probf - probPrev); //���αȽ�
			System.out.println(delta);
			probPrev = probf;
			nIter++;

			} while (delta >0.1);// ��������,�������
		    //} while (nIter< 1);
		    //} while (nIter< inter);
			System.out.println("BW������" + nIter + "�Σ�");
			probFinal = probf;
			return probFinal;  //�������ո���
	}
   
	//�۲������ǵ������е����
	public double core(HMM hmm, int[] o) {
		double[][] gamma;
		double[][][] xi;
		double[] scale;
		double[][] alpha = new double[o.length][hmm.N];
		double[][] beta = new double[o.length][hmm.N];
		Forward forward = new Forward();
		Backward backward = new Backward();
		gamma = new double[o.length][hmm.N];
		xi = new double[o.length][hmm.N][hmm.N];
		scale = new double[o.length];
		int nIter = 0;
		double pprob=0.0;
		//double logProbInit=0.0;
		double logProbPrev=0.0;
		double logProbFinal=0.0;
		double denominatorA,denominatorB;
		double numeratorA,numeratorB;
		double delta;
		// ---��ʼ��
		// ǰ���������
		// logProbF = forward.core(hmm, o);
		pprob = forward.coreByScale(hmm, o, alpha,scale,pprob);
		System.out.println(pprob);
		// log P(o|��ʼ����)
		//logProbInit = pprob;
		// �����������
		// logProbB����
		// logProbB = backward.core(hmm, o);
		
		backward.coreByScale(hmm, o, alpha,beta,scale,0);
		getGamma(hmm, o.length, alpha, beta,gamma);
		
		getXi(hmm, o, alpha, beta,xi);
		
		logProbPrev = pprob;
		System.out.println(logProbPrev);
		// ---��ʼ������

		// ������������
		do {
			// ���¹���t=1ʱ,״̬Ϊi��Ƶ��
			for (int i = 0; i < hmm.N; i++) {
				// Ϊ�˾���
				
				hmm.pi[i] = 0.001 + 0.999 * gamma[0][i];
				//hmm.pi[i] = gamma[0][i];
			}
			// ���¹���ת�ƾ���͹۲����
			for (int i = 0; i < hmm.N; i++) {
				denominatorA = 0;
				for (int t = 0; t < o.length - 1; t++) {
					denominatorA += gamma[t][i];
				}
				for (int j = 0; j < hmm.N; j++) {
					numeratorA = 0;
					for (int t = 0; t < o.length - 1; t++) {
						numeratorA += xi[t][i][j];
					}
					
					hmm.A[i][j] = 0.001 + 0.999 * numeratorA / denominatorA;
					//hmm.A[i][j] = numeratorA / denominatorA;
					if (Double.isNaN(hmm.A[i][j])) {
						System.out.println("fly...sdf");
					}
				}
				denominatorB = denominatorA + gamma[o.length - 1][i];
				for (int k = 0; k < hmm.M; k++) {
					numeratorB = 0;
					for (int t = 0; t < o.length; t++) {
						if (o[t] == k) {
							numeratorB += gamma[t][i];
						}
					}
					hmm.B[i][k] = 0.001 + 0.999 * numeratorB / denominatorB;
					//hmm.B[i][k] = numeratorB / denominatorB;
				}
			}
			// logProbF = forward.core(hmm, o);
			// logProbB = backward.core(hmm, o);
			alpha = new double[o.length][hmm.N];
			beta = new double[o.length][hmm.N];
			scale = new double[o.length];
			pprob = 0;
			gamma = new double[o.length][hmm.N];
			xi = new double[o.length][hmm.N][hmm.N];
			pprob = forward.coreByScale(hmm, o, alpha,scale,pprob);
			System.out.println(pprob);
			
			backward.coreByScale(hmm, o, alpha,beta,scale,0);
			getGamma(hmm, o.length, alpha, beta,gamma);
			getXi(hmm, o, alpha, beta,xi);

			// ��������ֱ�ӵĸ��ʲ�
			delta = Math.abs(pprob - logProbPrev);
			System.out.println(delta);
			logProbPrev = pprob;
			nIter++;

		} while (delta >0.001);// ��������,�������
		System.out.println("BW������" + nIter + "�Σ�");
		logProbFinal = pprob;
		return logProbFinal;
		//return pprob;
	}

	/**
	 * ����ʱ���gamma �۲����д�i״̬������ת������������
	 * @param hmm
	 * @param T
	 * @param alpha
	 * @param beta
	 */
	/*
	private void getGamma(HMM hmm, int T, double[][] alpha, double[][] beta) {
		double tmp;

		for (int t = 0; t < T; t++) {
			tmp = 0;
			for (int i = 0; i < hmm.N; i++) {
				gamma[t][i] = alpha[t][i] * beta[t][i];
				tmp += gamma[t][i];
			}
			for (int i = 0; i < hmm.N; i++) {
				gamma[t][i] = gamma[t][i] / tmp;
			}
		}

	}*/

	public void getGamma(HMM hmm, int T, double[][] alpha, double[][] beta, double[][] gamma) {
		double tmp;

		for (int t = 0; t < T; t++) {
			tmp = 0;
			for (int i = 0; i < hmm.N; i++) {
				gamma[t][i] = alpha[t][i] * beta[t][i];
				tmp += gamma[t][i];
			}
			for (int i = 0; i < hmm.N; i++) {
				gamma[t][i] = gamma[t][i] / tmp;
				//System.out.println(gamma[t][i]);
			}
		}

	}

	/**
	 * �۲�����o�д�״̬i��״̬j��ת������������
	 * @param hmm
	 * @param o
	 * @param alpha
	 * @param beta
	 */
	/*
	private void getXi(HMM hmm, int[] o, double[][] alpha, double[][] beta) {
		double sum;
		for (int t = 0; t < o.length - 1; t++) {
			sum = 0;
			for (int i = 0; i < hmm.N; i++) {
				for (int j = 0; j < hmm.N; j++) {
					xi[t][i][j] = alpha[t][i] * beta[t + 1][j] * hmm.A[i][j] * hmm.B[j][o[t + 1]];
					sum += xi[t][i][j];
				}
			}
			for (int i = 0; i < hmm.N; i++) {
				for (int j = 0; j < hmm.N; j++) {
					xi[t][i][j] /= sum;
				}
			}
		}
	}*/

	private void getXi(HMM hmm, int[] o, double[][] alpha, double[][] beta, double[][][] xi) {
		double sum;
		for (int t = 0; t < o.length - 1; t++) {
			sum = 0;
			for (int i = 0; i < hmm.N; i++) {
				for (int j = 0; j < hmm.N; j++) {
					xi[t][i][j] = alpha[t][i] * beta[t + 1][j] * hmm.A[i][j] * hmm.B[j][o[t + 1]];
					sum += xi[t][i][j];
				}
			}
			for (int i = 0; i < hmm.N; i++) {
				for (int j = 0; j < hmm.N; j++) {
					xi[t][i][j] /= sum;
					//System.out.println(xi[t][i][j]);
				}
			}
		}
	}

}


