package core

class Vec3 {
	
	def e = []
	
	Vec3(double e0, double e1, double e2) {
		e = [ e0, e1, e2 ]
	}
	
	Vec3() {
		this( 0, 0, 0)
	}
	
	double x() { e[0] }
	double y() { e[1] }
	double z() { e[2] }

	static double clamp(double e, double min, double max) {
		if (e < min) return min
		if (e > max) return max
		return e
	}

	// returns a random vector with a length between min and max
	static Vec3 random_in_unit_sphere() {
		def rand = new Random()
		while (true) {
			def v = new Vec3(rand.nextDouble()*2 -1, rand.nextDouble()*2 -1, rand.nextDouble()*2 -1)
			if (v.length() > 1.0) continue
			return v
		}
	}

	// operators overloading
	Vec3 negative() { new Vec3( -e[0]+0.0, -e[1]+0.0, -e[2]+0.0) }
	double getAt(int n) { e[n] }
	void add(Vec3 v) {	e[0] += v.x(); e[1] += v.y(); e[2] += v.z() }
	Vec3 plus(Vec3 v) { new Vec3( e[0]+v.x(), e[1]+v.y(), e[2]+v.z()) }
	Vec3 minus(Vec3 v) { new Vec3( e[0]-v.x()+0.0, e[1]-v.y()+0.0, e[2]-v.z()+0.0) }
	Vec3 multiply(Vec3 v) {	new Vec3( e[0]*v.x(), e[1]*v.y(), e[2]*v.z()) }
	Vec3 multiply(double t) {	new Vec3( e[0]*t, e[1]*t, e[2]*t ) }
	Vec3 div(Vec3 v) {	new Vec3( e[0]/v.x(), e[1]/v.y(), e[2]/v.z() ) }
	Vec3 div(double t) { new Vec3( e[0]/t, e[1]/t, e[2]/t ) }
	
	double length_squared() { e[0]*e[0] + e[1]*e[1] + e[2]*e[2]	}
	double length() { Math.sqrt(length_squared()) } 
	static double dot( Vec3 u, Vec3 v ) {
		u.e[0] * v.e[0] + u.e[1] * v.e[1] + u.e[2] * v.e[2]
	}
	double dot( Vec3 v ) {
		dot(this, v)
	}
	
	static Vec3 cross(Vec3 u, Vec3 v) {
		new Vec3(
			u.e[1] * v.e[2] - u.e[2] * v.e[1],
			u.e[2] * v.e[0] - u.e[0] * v.e[2],
			u.e[0] * v.e[1] - u.e[1] * v.e[0])
	}
	
	Vec3 cross(Vec3 v) {
		cross(this, v)
	}
	
	static Vec3 unit_vector(Vec3 v) {
		v / v.length()
	}
	
	Vec3 unit_vector() {
		unit_vector(this)
	}
	
	boolean equals(Vec3 v) {
		e[0] == v.x() && e[1] == v.y() && e[2] == v.z() 
	}
	
	String toString() { e.toString() }
	
}

