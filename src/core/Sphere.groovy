package core

class Sphere implements Hittable {

	Point3 center
	double radius
	Material material
	
	Sphere(Point3 cen, double r, Material m) {
		this.center = cen
		this.radius = r
		this.material = m
	}

	@Override
	HitRecord hit(Ray r, double t_min, double t_max) {
    	Vec3 oc = r.origin() - center
    	def a = r.direction().length_squared()
    	def half_b = Vec3.dot(oc, r.direction())
    	def c = oc.length_squared() - radius*radius

    	double discriminant = half_b*half_b - a*c
    	if (discriminant < 0) return null
    	double sqrtd = Math.sqrt(discriminant)

		// Find the nearest root that lies in the acceptable range.
		double root = (-half_b - sqrtd) / a
		if (root < t_min || t_max < root) {
			root = (-half_b + sqrtd) / a
			if (root < t_min || t_max < root)
				return null
		}

		def rec = new HitRecord()
		rec.t = root
		rec.p = r.at(rec.t)
		Vec3 outward_normal = (rec.p - center) / radius
		rec.set_face_normal(r, outward_normal)
		rec.material = material

    	return rec
	}
	
}
