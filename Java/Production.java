import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @(#)Production.java
 *
 *
 * @author
 * @version 1.00 2015/2/23
 */

abstract class Production extends Thread {
	Production(Vertex Vert, CountDownLatch Barrier) {
		m_vertex = Vert;
		m_barrier = Barrier;
	}

	// returns first vertex from the left
	abstract Vertex apply(Vertex v);

	// run the thread
	public void run() {
		// apply the production
		m_vertex = apply(m_vertex);
		m_barrier.countDown();
	}

	// vertex where the production will be applied
	Vertex m_vertex;
	// productions counter
	CountDownLatch m_barrier;
}