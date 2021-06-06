package core

interface Hittable {
	
	HitRecord hit( Ray r, double t_min, double t_max)
	
}
