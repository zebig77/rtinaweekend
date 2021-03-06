package core

class HitRecord {
	
	Point3 p
	Vec3 normal
	Material material
	double t

	boolean front_face

	void set_face_normal( Ray r, Vec3 outward_normal) {
		if (Vec3.dot(r.direction(), outward_normal) < 0) {
			front_face = true
			normal = outward_normal
		}
		else {
			front_face = false
			normal = -outward_normal
		}
	}

}
