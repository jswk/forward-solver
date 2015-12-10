import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

class Executor extends Thread {
	public synchronized void run() {
		Vertex S = new Vertex(null, null, null, "S");
		try {
			Vertex.dt = 0.001;
			Vertex.h = 1;
			
			// [(P1)]
			CountDownLatch barrier = new CountDownLatch(1);
			P1 p1 = new P1(S, barrier);
			p1.start();
			barrier.await();
			// [(P2)1(P2)2]
			barrier = new CountDownLatch(2);
			P2 p2_1 = new P2(p1.m_vertex.m_left, barrier);
			P2 p2_2 = new P2(p1.m_vertex.m_right, barrier);
			p2_1.start();
			p2_2.start();
			barrier.await();
			// [(P2)3(P2)4(P3)5(P3)6]
			barrier = new CountDownLatch(4);
			P2 p3_1 = new P2(p2_1.m_vertex.m_left, barrier);
			P2 p3_2 = new P2(p2_1.m_vertex.m_right, barrier);
			P2 p3_3 = new P2(p2_2.m_vertex.m_left, barrier);
			P2 p3_4 = new P2(p2_2.m_vertex.m_right, barrier);
			p3_1.start();
			p3_2.start();
			p3_3.start();
			p3_4.start();
			barrier.await();
			// [(P3)1(P3)2(P3)3(P3)4]
			barrier = new CountDownLatch(8);
			P3 p4_1 = new P3(p3_1.m_vertex.m_left, barrier);
			P3 p4_2 = new P3(p3_1.m_vertex.m_right, barrier);
			P3 p4_3 = new P3(p3_2.m_vertex.m_left, barrier);
			P3 p4_4 = new P3(p3_2.m_vertex.m_right, barrier);
			P3 p4_5 = new P3(p3_3.m_vertex.m_left, barrier);
			P3 p4_6 = new P3(p3_3.m_vertex.m_right, barrier);
			P3 p4_7 = new P3(p3_4.m_vertex.m_left, barrier);
			P3 p4_8 = new P3(p3_4.m_vertex.m_right, barrier);
			p4_1.start();
			p4_2.start();
			p4_3.start();
			p4_4.start();
			p4_5.start();
			p4_6.start();
			p4_7.start();
			p4_8.start();
			barrier.await();
			
			for (int t = 0; t < 100; t++) {
				// MULTI-FRONTAL SOLVER ALGORITHM
				// [(A1)1(A)2(A)3(A)4(A)5(AN)6]
				barrier = new CountDownLatch(8);
				// localMat
				A1 lm4_1 = new A1(p4_1.m_vertex, barrier);
				A  lm4_2 = new A (p4_2.m_vertex, barrier);
				A  lm4_3 = new A (p4_3.m_vertex, barrier);
				A  lm4_4 = new A (p4_4.m_vertex, barrier);
				A  lm4_5 = new A (p4_5.m_vertex, barrier);
				A  lm4_6 = new A (p4_6.m_vertex, barrier);
				A  lm4_7 = new A (p4_7.m_vertex, barrier);
				AN lm4_8 = new AN(p4_8.m_vertex, barrier);
				lm4_1.start();
				lm4_2.start();
				lm4_3.start();
				lm4_4.start();
				lm4_5.start();
				lm4_6.start();
				lm4_7.start();
				lm4_8.start();
				barrier.await();
				
				// [(A2)2(A2)3(A2)4]
				barrier = new CountDownLatch(4);
				// mergedMat
				A2 mm3_1 = new A2(p3_1.m_vertex, barrier);
				A2 mm3_2 = new A2(p3_2.m_vertex, barrier);
				A2 mm3_3 = new A2(p3_3.m_vertex, barrier);
				A2 mm3_4 = new A2(p3_4.m_vertex, barrier);
				mm3_1.start();
				mm3_2.start();
				mm3_3.start();
				mm3_4.start();
				barrier.await();
				
				// [(E2)2(E2)3(E2)4]
				barrier = new CountDownLatch(4);
				// gaussElimMat
				E2 gem3_1 = new E2(p3_1.m_vertex, barrier);
				E2 gem3_2 = new E2(p3_2.m_vertex, barrier);
				E2 gem3_3 = new E2(p3_3.m_vertex, barrier);
				E2 gem3_4 = new E2(p3_4.m_vertex, barrier);
				gem3_1.start();
				gem3_2.start();
				gem3_3.start();
				gem3_4.start();
				barrier.await();
				
				// [(A2)1]
				barrier = new CountDownLatch(2);
				// mergedMat
				A2 mm2_1 = new A2(p2_1.m_vertex, barrier);
				A2 mm2_2 = new A2(p2_2.m_vertex, barrier);
				mm2_1.start();
				mm2_2.start();
				barrier.await();
				
				barrier = new CountDownLatch(2);
				// gaussElimMat
				E2 gem2_1 = new E2(p2_1.m_vertex, barrier);
				E2 gem2_2 = new E2(p2_2.m_vertex, barrier);
				gem2_1.start();
				gem2_2.start();
				barrier.await();
				
				// [(Aroot)]
				barrier = new CountDownLatch(1);
				Aroot mergedRootMat = new Aroot(p1.m_vertex, barrier);
				mergedRootMat.start();
				barrier.await();
				
				// [(Eroot)]
				barrier = new CountDownLatch(1);
				Eroot fullElimMat = new Eroot(p1.m_vertex, barrier);
				fullElimMat.start();
				barrier.await();
				
				// [(BS)1]
				barrier = new CountDownLatch(1);
				// backSub
				BS bs1 = new BS(p1.m_vertex, barrier);
				bs1.start();
				barrier.await();
				
				// [(BS)2(BS)3]
				barrier = new CountDownLatch(2);
				// backSub
				BS bs2_1 = new BS(p2_1.m_vertex, barrier);
				BS bs2_2 = new BS(p2_2.m_vertex, barrier);
				bs2_1.start();
				bs2_2.start();
				barrier.await();
				// [(BS)4(BS)5]
				barrier = new CountDownLatch(4);
				BS bs3_1 = new BS(p3_1.m_vertex, barrier);
				BS bs3_2 = new BS(p3_2.m_vertex, barrier);
				BS bs3_3 = new BS(p3_3.m_vertex, barrier);
				BS bs3_4 = new BS(p3_4.m_vertex, barrier);
				bs3_1.start();
				bs3_2.start();
				bs3_3.start();
				bs3_4.start();
				barrier.await();
	
	//			GraphDrawer drawer = new GraphDrawer();
	//			drawer.draw(S);
				
				// System.out.println(p1.m_vertex.m_x[]);
				List<P3> p = new ArrayList<>(8);
				p.add(p4_1);
				p.add(p4_2);
				p.add(p4_3);
				p.add(p4_4);
				p.add(p4_5);
				p.add(p4_6);
				p.add(p4_7);
				p.add(p4_8);
				
				for (P3 p4: p) {
					System.out.print(p4.m_vertex.m_x[1]+" ");
				}
				System.out.println(p.get(p.size()-1).m_vertex.m_x[2]);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}