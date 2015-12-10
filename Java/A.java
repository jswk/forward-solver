import java.util.concurrent.CountDownLatch;

class A extends Production {
	A(Vertex Vert, CountDownLatch Barrier) {
		super(Vert, Barrier);
	}

	Vertex apply(Vertex T) {
//		System.out.println("A");
		// commented out in 6a
//		T.m_a[1][1] = 1.0;
//		T.m_a[2][1] = -1.0;
//		T.m_a[1][2] = -1.0;
//		T.m_a[2][2] = 1.0;
//		T.m_b[1] = 0.0;
//		T.m_b[2] = 0.0;

		T.m_a[1][1] = T.h/3.;
		T.m_a[2][1] = T.h/6.;
		T.m_a[1][2] = T.h/6.;
		T.m_a[2][2] = T.h/3.;

		for (int i = 0; i < 3; i++) {
			T.m_x_old[i] = T.m_x[i];
		}
		T.m_b[1] = T.m_x_old[1] * T.h / 3. + T.m_x_old[2] * T.h / 6.
				- T.dt * (-T.m_x_old[1] / T.h + T.m_x_old[2] / T.h);
		T.m_b[2] = T.m_x_old[1] * T.h / 6. + T.m_x_old[2] * T.h / 3.
				- T.dt * (T.m_x_old[1] / T.h - T.m_x_old[2] / T.h);
		
		return T;
	}
}