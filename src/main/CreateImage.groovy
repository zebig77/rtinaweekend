package main

import core.Color
import core.Point3
import core.Ray
import core.Vec3


double hit_sphere(final Point3 center, double radius, final Ray r) {
	Vec3 oc = r.origin() - center
	double a = Vec3.dot(r.direction(), r.direction())
	double b = 2.0 * Vec3.dot(oc, r.direction());
	double c = Vec3.dot(oc, oc) - radius*radius;
	double discriminant = b*b - 4*a*c;
	if (discriminant < 0) {
		return -1.0
	}
	else {
		return (-b - Math.sqrt(discriminant) ) / (2.0*a)
	}
}

Color ray_color(final Ray r) {
	def t = hit_sphere(new Point3(0,0,-1), 0.5, r)
	if (t > 0.0) {
		def N = Vec3.unit_vector(r.at(t) - new Vec3(0,0,-1))
		return new Color(N.x()+1, N.y()+1, N.z()+1)*0.5
	}
	Vec3 unit_direction = r.direction().unit_vector()
	t = 0.5*( unit_direction.y() + 1.0 )
	new Color(1,1,1, 1.0 - t) + new Color(0.5, 0.7, 1.0, t)
}

// Image
final aspect_ratio = 16.0 / 9.0
final int image_width = 400
final int image_height = image_width / aspect_ratio

// Camera
def viewport_height = 2.0
def viewport_width = aspect_ratio * viewport_height
def focal_length = 1.0

def origin = new Point3(0,0,0)
def horizontal = new Vec3(viewport_width, 0, 0)
def vertical = new Vec3(0, viewport_height, 0)
def lower_left_corner = origin - horizontal/2 - vertical/2 - new Vec3(0, 0, focal_length)


// Render

def f = new File('sample3.ppm')
def sb = new StringBuilder(image_height*image_width*12)

for (int j = image_height-1; j >= 0; --j) {
	System.err << "\rScanlines remaining: $j"
	def v = (double)j / (image_height-1)
	for (int i = 0; i < image_width; ++i) {
		def u = (double)i / (image_width-1)
		def r = new Ray( origin, lower_left_corner + horizontal*u + vertical*v - origin)

		def pixel_color = ray_color(r)

		sb << "$pixel_color\n"
	}
}

f.text = "P3\n$image_width $image_height\n255\n$sb"
