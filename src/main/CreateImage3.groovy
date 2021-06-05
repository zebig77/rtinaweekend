package main

import core.Camera
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
int samples_per_pixel = 10

// World
def world = new HittableList()
world.add(new Sphere(new Point3(0,0,-1), 0.5))
world.add(new Sphere(new Point3(0,-100.5,-1), 100))

// Camera
def cam = new Camera()

// Render
def f = new File('../../../images/sample4_aa10.ppm')
println "Creating "+f.getName()+"..."

def sb = new StringBuilder(image_height*image_width*12)

def rand = new Random()

for (int j = image_height-1; j >= 0; --j) {
	System.err.print "\rScanlines remaining: $j"
	for (int i = 0; i < image_width; ++i) {
		def pixel_color = new Color(0,0,0)
		for (int s = 0; s < samples_per_pixel; ++s) {
			def u = (i + rand.nextDouble()) / (image_width-1)
			def v = (j + rand.nextDouble()) / (image_height-1)
			def r = cam.get_ray(u,v)
			pixel_color += ray_color(r, world)
		}
		sb << "${pixel_color.toString(samples_per_pixel)}\n"
	}
}

f.text = "P3\n$image_width $image_height\n255\n$sb"
