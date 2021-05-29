package core

class Ray {
	
	Point3 orig
	Vec3 dir
	
	Ray(Point3 orig, Vec3 dir) {
		this.orig = orig
		this.dir = dir
	}
	
	Point3 origin() { return orig }
	Vec3 direction() { return dir }
	
	Point3 at(double t) { orig + dir*t	}
	
}
