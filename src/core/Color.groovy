package core

class Color extends Vec3 {
	
	public Color(double r, double g, double b) {
		super(r, g, b)
	}
	
	public String toString() {
		int ir = (int)(255.999 * e[0])
		int ig = (int)(255.999 * e[1])
		int ib = (int)(255.999 * e[2])
		"$ir $ig $ib"
	}
	
}