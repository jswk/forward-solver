class GraphDrawer {
	GraphDrawer() {
	}

	// draw the graph
	void draw(Vertex v) {
		draw(v, "");
	}
	
	void draw(Vertex v, String prefix) {
		String vString = "("+v.m_label;
		String newPrefix = prefix+String.format("%"+(vString.length()+1)+"s", "");
		System.out.print(prefix+vString);
		if (v.m_left != null) {
			System.out.println();
			draw(v.m_left, newPrefix);
		}
		if (v.m_right != null) {
			System.out.println();
			draw(v.m_right, newPrefix);
		}
		System.out.print(")");
	}
}
