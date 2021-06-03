package main

import core.Color
import core.HitRecord
import core.HittableList
import core.Point3
import core.Ray
import core.Sphere
import core.Vec3

static Color ray_color(Ray r, HittableList world) {
	Color white = new Color(1,1,1)

	def rec = new HitRecord()
	if (world.hit(r, 0, Double.POSITIVE_INFINITY, rec)) {
		return ( white + rec.normal ) * 0.5
	}

	Vec3 unit_direction = r.direction().unit_vector()
	def t = ( unit_direction.y() + 1.0 ) * 0.5
	new Color(1,1,1, 1.0 - t) + new Color(0.5, 0.7, 1.0, t)
}

// Image
double aspect_ratio = 16.0 / 9.0
int image_width = 400
int image_height = (int) (image_width / aspect_ratio)

// World
def world = new HittableList()
world.add(new Sphere(new Point3(0,0,-1), 0.5))
world.add(new Sphere(new Point3(0,-100.5,-1), 100))

// Camera
def viewport_height = 2.0
def viewport_width = aspect_ratio * viewport_height
def focal_length = 1.0

def origin = new Point3(0,0,0)
def horizontal = new Vec3(viewport_width, 0, 0)
def vertical = new Vec3(0, viewport_height, 0)
def lower_left_corner = origin - horizontal/2 - vertical/2 - new Vec3(0, 0, focal_length)


// Render

def f = new File('../../../images/sample4.ppm')
def sb = new StringBuilder(image_height*image_width*12)

for (int j = image_height-1; j >= 0; --j) {
	System.err.print "\rScanlines remaining: $j"
	def v = (double)j / (image_height-1)
	for (int i = 0; i < image_width; ++i) {
		def u = (double)i / (image_width-1)
		def r = new Ray( origin, lower_left_corner + horizontal*u + vertical*v - origin)

		def pixel_color = ray_color(r, world)

		sb << "$pixel_color\n"
	}
}

f.text = "P3\n$image_width $image_height\n255\n$sb"
