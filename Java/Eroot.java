import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

class Eroot extends Production {
	Eroot(Vertex Vert, CountDownLatch Barrier) {
		super(Vert, Barrier);
	}

	Vertex apply(Vertex T) {
//		System.out.println("Eroot");

		// divide first row by diagonal
		T.m_b[1] /= T.m_a[1][1];
		T.m_a[1][2] /= T.m_a[1][1];
		T.m_a[1][0] /= T.m_a[1][1];
		T.m_a[1][1] /= T.m_a[1][1];
		// 2nd=2nd-1st*diag
		T.m_b[0] -= T.m_b[1] * T.m_a[0][1];
		T.m_a[0][2] -= T.m_a[1][2] * T.m_a[0][1];
		T.m_a[0][0] -= T.m_a[1][0] * T.m_a[0][1];
		T.m_a[0][1] -= T.m_a[1][1] * T.m_a[0][1];
		// 3rd=3rd-1st*diag
		T.m_b[2] -= T.m_b[1] * T.m_a[2][1];
		T.m_a[2][2] -= T.m_a[1][2] * T.m_a[2][1];
		T.m_a[2][0] -= T.m_a[1][0] * T.m_a[2][1];
		T.m_a[2][1] -= T.m_a[1][1] * T.m_a[2][1];

		// divide second row by diagonal
		T.m_b[0] /= T.m_a[0][0];
		T.m_a[0][2] /= T.m_a[0][0];
		T.m_a[0][0] /= T.m_a[0][0];

		// 3rd=3rd-2nd*diag
		T.m_b[2] -= T.m_b[0] * T.m_a[2][0];
		T.m_a[2][2] -= T.m_a[0][2] * T.m_a[2][0];
		T.m_a[2][0] -= T.m_a[0][0] * T.m_a[2][0];

		// divide third row by diagonal
		T.m_b[2] /= T.m_a[2][2];
		T.m_a[2][2] /= T.m_a[2][2];

		// b.s.
		T.m_x[2] = T.m_b[2] / T.m_a[2][2];
		T.m_x[0] = (T.m_b[0] - T.m_a[0][2] * T.m_x[0]) / T.m_a[0][0];
		T.m_x[1] = (T.m_b[1] - T.m_a[0][1] * T.m_x[1] - T.m_a[0][2] * T.m_x[2]) / T.m_a[1][1];

		
//		for (int i = 0; i < 3; i++) {
//			double pivot = T.m_a[i][i];
//			for (int j = i; j < 3; j++) {
//				T.m_a[i][j] /= pivot;
//			}
//			T.m_b[i] /= pivot;
//			for (int i2 = 0; i < 3; i++) {
//				if (i2 != i) {
//					double val = T.m_a[i2][i];
//					for (int j2 = i; j2 < 3; j2++) {
//						T.m_a[i2][j2] -= T.m_a[i][j2] * val;
//					}
//					T.m_b[i2] -= T.m_b[i] * val;
//				}
//			}
//		}
//
//		T.m_x[0] = T.m_b[0];
//		T.m_x[1] = T.m_b[1];
//		T.m_x[2] = T.m_b[2];

		return T;
	}
}
