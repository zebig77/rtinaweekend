package main

import core.Color
import core.Point3
import core.Ray
import core.Vec3


boolean hit_sphere(final Point3 center, double radius, final Ray r) {
    Vec3 oc = r.origin() - center
    def a = Vec3.dot(r.direction(), r.direction())
    def b = 2.0 * Vec3.dot(oc, r.direction());
    def c = Vec3.dot(oc, oc) - radius*radius;
    def discriminant = b*b - 4*a*c;
    return (discriminant > 0);
}

Color ray_color(final Ray r) {
	Vec3 unit_direction = r.direction().unit_vector()
	def t = 0.5*( unit_direction.y() + 1.0 )
	if (hit_sphere(new Point3(0,0,-1), 0.5, r)) {
		return new Color(1,0,0, 2*t)
	}
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
def sb = new StringBuilder()

for (int j = image_height-1; j >= 0; --j) {
	System.err << "\rScanlines remaining: $j"
	for (int i = 0; i < image_width; ++i) {
		def u = (double)i / (image_width-1)
		def v = (double)j / (image_height-1)
		def r = new Ray( origin, lower_left_corner + horizontal*u + vertical*v - origin)
		
		def pixel_color = ray_color(r)
		
		sb << "$pixel_color\n"
	}
}

f.text = "P3\n$image_width $image_height\n255\n$sb" 
