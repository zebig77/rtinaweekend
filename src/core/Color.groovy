package core

class Color extends Vec3 {
	
	Color(double r, double g, double b) {
		super(r, g, b)
	}
	
	Color(double r, double g, double b, double intensity) {
		super(r * intensity, g * intensity, b * intensity)
	}
	
	double r() { e[0] }
	double g() { e[1] }
	double b() { e[2] }

	Color plus(Color c) { new Color( e[0]+c.r(), e[1]+c.g(), e[2]+c.b()) }
	Color plus(Vec3 v) { new Color( e[0]+v.x(), e[1]+v.y(), e[2]+v.z()) }
	Color multiply(double t) { new Color( e[0]*t, e[1]*t, e[2]*t) }
	
	String toString() {
		int ir = (int)(255.999 * e[0])
		int ig = (int)(255.999 * e[1])
		int ib = (int)(255.999 * e[2])
		"$ir $ig $ib"
	}
	
}