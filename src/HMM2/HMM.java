package HMM2;

/**
 * HMM������
 * @author yang
 */
public class HMM implements Cloneable{
	/**
	 * ����״̬��Ŀ
	 */
	public int N;
	/**
	 * �۲�״̬��Ŀ
	 */
	public int M;
	/**
	 * ״̬ת�ƾ���
	 * һ����״̬����һ����״̬�ĸ���
	 */
	public double[][] A;
	

	/**
	 * �����������(��������)
	 * ��״̬�͹۲�״̬��ӳ��
	 */
	public double[][] B;
	/**
	 * ��ʼ����
	 */
	public double[] pi;
	
	
	@Override
	public Object clone() {
		HMM hmm = null;
		try {
			hmm = (HMM) super.clone();
			hmm.A = A.clone();
			for (int i = 0; i < A.length; i++) {
				hmm.A[i] = A[i].clone();
			}
			hmm.B = B.clone();
			for (int i = 0; i < B.length; i++) {
				hmm.B[i] = B[i].clone();
			}
			hmm.pi = pi.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return hmm;
	}

	public HMM() {
		super();
	}

	/**
	 * @param n ����״̬��Ŀ
	 * @param m �۲�״̬��Ŀ
	 * @param a ״̬ת�ƾ���
	 * @param b ��������
	 * @param pi ��ʼ����
	 */
	public HMM(int n, int m, double[][] a, double[][] b, double[] pi) {
		super();
		N = n;
		M = m;
		A = a;
		B = b;
		this.pi = pi;
	}
	/**
	 * ���ڲ�������
	 * @param n ����״̬��Ŀ
	 * @param m �۲�״̬��Ŀ
	 */
	public HMM(int n, int m) {
		super();
		N = n;
		M = m;
		A = new double[N][N];
		B = new double[N][M];
		pi = new double[N];		                
	}
	/**���ڲ�����֪ģ��
	 * @param n ����״̬��Ŀ
	 * @param m �۲�״̬��Ŀ
	 * @param a ״̬ת�ƾ���
	 * @param b �����������
	 * @param pi ��ʼ����
	 */
	public HMM(double[][] a, double[][] b, double[] pi) {
		super();
		N = a.length;
		if (a.length == b[0].length) {
			M = b.length;
		}else {
			M = b[0].length;
		}
		A = a;
		B = b;
		this.pi = pi;
	}

	// geter/seter
	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public int getM() {
		return M;
	}

	public void setM(int m) {
		M = m;
	}

	public double[][] getA() {
		return A;
	}

	public void setA(double[][] a) {
		A = a;
	}

	public double[][] getB() {
		return B;
	}

	public void setB(double[][] b) {
		B = b;
	}

	public double[] getPi() {
		return pi;
	}

	public void setPi(double[] pi) {
		this.pi = pi;
	}

}
