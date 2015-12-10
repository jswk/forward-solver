import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

class E2 extends Production {
	E2(Vertex Vert, CountDownLatch Barrier) {
		super(Vert, Barrier);
	}

	Vertex apply(Vertex T) {
//		System.out.println("E2");

		T.m_b[0] /= T.m_a[0][0];
		T.m_a[0][2] /= T.m_a[0][0];
		T.m_a[0][1] /= T.m_a[0][0];
		T.m_a[0][0] /= T.m_a[0][0];
		
		for (int i = 1; i < 3; i++) {
			double first = T.m_a[i][0];
			T.m_b[i] -= T.m_b[0] * first;
			T.m_a[i][0] = 0;
			for (int j = 1; j < 3; j++) {
				T.m_a[i][j] -= T.m_a[0][j] * first;
			}
		}

		return T;
	}
}