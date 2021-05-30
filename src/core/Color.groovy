package core

class Color extends Vec3 {
	
	public Color(double r, double g, double b) {
		super(r, g, b)
	}
	
	public Color(double r, double g, double b, double intensity) {
		super(r * intensity, g * intensity, b * intensity)
	}
	
	double r() { e[0] }
	double g() { e[1] }
	double b() { e[2] }
	
	Color plus(Color c) { new Color( e[0]+c.r(), e[1]+c.g(), e[2]+c.b()) }
	Color multiply(double t) { new Color( e[0]*t, e[1]*t, e[2]*t) }
	
	public String toString() {
		int ir = (int)(255.999 * e[0])
		int ig = (int)(255.999 * e[1])
		int ib = (int)(255.999 * e[2])
		"$ir $ig $ib"
	}
	
}