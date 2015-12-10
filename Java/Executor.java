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
			P2 p2a = new P2(p1.m_vertex.m_left, barrier);
			P2 p2b = new P2(p1.m_vertex.m_right, barrier);
			p2a.start();
			p2b.start();
			barrier.await();
			// [(P2)3(P2)4(P3)5(P3)6]
			barrier = new CountDownLatch(4);
			P2 p2c = new P2(p2a.m_vertex.m_left, barrier);
			P2 p2d = new P2(p2a.m_vertex.m_right, barrier);
			P3 p3e = new P3(p2b.m_vertex.m_left, barrier);
			P3 p3f = new P3(p2b.m_vertex.m_right, barrier);
			p2c.start();
			p2d.start();
			p3e.start();
			p3f.start();
			barrier.await();
			// [(P3)1(P3)2(P3)3(P3)4]
			barrier = new CountDownLatch(4);
			P3 p3a = new P3(p2c.m_vertex.m_left, barrier);
			P3 p3b = new P3(p2c.m_vertex.m_right, barrier);
			P3 p3c = new P3(p2d.m_vertex.m_left, barrier);
			P3 p3d = new P3(p2d.m_vertex.m_right, barrier);
			p3a.start();
			p3b.start();
			p3c.start();
			p3d.start();
			barrier.await();
			
			for (int t = 0; t < 100; t++) {
				// MULTI-FRONTAL SOLVER ALGORITHM
				// [(A1)1(A)2(A)3(A)4(A)5(AN)6]
				barrier = new CountDownLatch(6);
				A1 localMat1 = new A1(p3a.m_vertex, barrier);
				A localMat2 = new A(p3b.m_vertex, barrier);
				A localMat3 = new A(p3c.m_vertex, barrier);
				A localMat4 = new A(p3d.m_vertex, barrier);
				A localMat5 = new A(p3e.m_vertex, barrier);
				AN localMat6 = new AN(p3f.m_vertex, barrier);
				localMat1.start();
				localMat2.start();
				localMat3.start();
				localMat4.start();
				localMat5.start();
				localMat6.start();
				barrier.await();
				// [(A2)2(A2)3(A2)4]
				barrier = new CountDownLatch(3);
				A2 mergedMat1 = new A2(p2b.m_vertex, barrier);
				A2 mergedMat2 = new A2(p2c.m_vertex, barrier);
				A2 mergedMat3 = new A2(p2d.m_vertex, barrier);
				mergedMat1.start();
				mergedMat2.start();
				mergedMat3.start();
				barrier.await();
				// [(E2)2(E2)3(E2)4]
				barrier = new CountDownLatch(3);
				E2 gaussElimMat1 = new E2(p2b.m_vertex, barrier);
				E2 gaussElimMat2 = new E2(p2c.m_vertex, barrier);
				E2 gaussElimMat3 = new E2(p2d.m_vertex, barrier);
				gaussElimMat1.start();
				gaussElimMat2.start();
				gaussElimMat3.start();
				barrier.await();
				// [(A2)1]
				barrier = new CountDownLatch(1);
				A2 mergedMat4 = new A2(p2a.m_vertex, barrier);
				mergedMat4.start();
				barrier.await();
				// [(E2)1]
				barrier = new CountDownLatch(1);
				E2 gaussElimMat4 = new E2(p2a.m_vertex, barrier);
				gaussElimMat4.start();
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
				BS backSub0 = new BS(p1.m_vertex, barrier);
				backSub0.start();
				barrier.await();
				// [(BS)2(BS)3]
				barrier = new CountDownLatch(2);
				BS backSub1 = new BS(p2a.m_vertex, barrier);
				BS backSub2 = new BS(p2b.m_vertex, barrier);
				backSub1.start();
				backSub2.start();
				barrier.await();
				// [(BS)4(BS)5]
				barrier = new CountDownLatch(2);
				BS backSub3 = new BS(p2c.m_vertex, barrier);
				BS backSub4 = new BS(p2d.m_vertex, barrier);
				backSub3.start();
				backSub4.start();
				barrier.await();
	
	//			GraphDrawer drawer = new GraphDrawer();
	//			drawer.draw(S);
				
				// System.out.println(p1.m_vertex.m_x[]);
	
				System.out.println("1 "+p3a.m_vertex.m_x[1]+" ");
				System.out.println("2 "+p3b.m_vertex.m_x[1]+" ");
				System.out.println("3 "+p3c.m_vertex.m_x[1]+" ");
				System.out.println("4 "+p3d.m_vertex.m_x[1]+" ");
				System.out.println("5 "+p3e.m_vertex.m_x[1]+" ");
				System.out.println("6 "+p3f.m_vertex.m_x[1]+"\n"+"7 "+p3f.m_vertex.m_x[2]);
				System.out.println();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}