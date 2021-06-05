package core

class Point3 extends Vec3 {
	
	Point3(double x, double y, double z) {
		super(x, y, z)
	}

	Point3 plus(Vec3 v) { new Point3( e[0]+v.x(), e[1]+v.y(), e[2]+v.z()) }
	Point3 minus(Vec3 v) { new Point3( e[0]-v.x(), e[1]-v.y(), e[2]-v.z()) }

}