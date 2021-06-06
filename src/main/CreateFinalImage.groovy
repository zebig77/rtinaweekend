package main

import core.*
import groovy.time.TimeCategory
import groovyx.gpars.GParsExecutorsPool

import java.util.concurrent.ConcurrentHashMap

static Color ray_color(Ray r, HittableList world, int depth) {
	if (depth <= 0) {
		return Color.BLACK
	}
	def rec = world.hit(r, 0.001, Double.POSITIVE_INFINITY)
	if (rec) {
		def scattered = rec.material.scatter(r, rec)
		if (scattered) {
			def attenuation = rec.material.getAttenuation()
			return attenuation * ray_color(scattered, world, depth-1)
		}
		else {
			return Color.BLACK
		}
	}

	Vec3 unit_direction = r.direction().unit_vector()
	def t = ( unit_direction.y() + 1.0 ) * 0.5
	return new Color(1,1,1, 1.0 - t) + new Color(0.5, 0.7, 1.0, t)
}

// Image
double aspect_ratio = 3.0 / 2.0
int image_width = 1200
int image_height = (int) (image_width / aspect_ratio)
int samples_per_pixel = 500
int max_depth = 50

// World
def world = new HittableList()
def rand = new Random()

def ground_material = new Lambertian(new Color(0.5, 0.5, 0.5))
world.add new Sphere(new Point3(0, -1000,0), 1000, ground_material)

for( int a = -11; a < 11; a++ ) {
	for( int b = -11; b < 11; b++ ) {
		def choose_mat = rand.nextDouble()
		def center = new Point3( a + 0.9*rand.nextDouble(), 0.2, b + 0.9*rand.nextDouble())

		if ((center - new Point3(4, 0.2, 0)).length() > 0.9) {
			def sphere_material

			if (choose_mat < 0.8) {
				// diffuse
				def albedo = Color.random() * Color.random()
				sphere_material = new Lambertian(albedo)
			}
			else if (choose_mat < 0.95) {
				// metal
				def albedo = Color.random()/2 + new Color(0.5,0.5,0.5)	// between 0.5 and 1
				def fuzz = rand.nextDouble()/2		// between 0 and 0.5
				sphere_material = new Metal(albedo, fuzz)
			}
			else {
				// glass
				sphere_material = new Dielectric(1.5)
			}
			world.add( new Sphere(center, 0.2, sphere_material))
		}
	}
}

def material1 = new Dielectric(1.5)
world.add new Sphere(new Point3(0,1,0), 1, material1)

def material2 = new Lambertian(new Color(0.4, 0.2, 0.1))
world.add new Sphere(new Point3(-4,1,0), 1, material2)

def material3 = new Metal(new Color(0.7, 0.6, 0.5), 0)
world.add new Sphere(new Point3( 4,1,0), 1, material3)


// Camera


def lookfrom = new Point3(13,2,3)
def lookat = new Point3(0,0,0)
def vup = new Vec3(0,1,0)
def dist_to_focus = 10.0
def aperture = 0.1
def cam = new Camera( lookfrom, lookat, vup, 20, aspect_ratio, aperture, dist_to_focus)

// Render
def f = new File('../../../images/FinalImage1200.ppm')
println "Creating "+f.getName()+"..."

def sb = new StringBuilder(image_height*image_width*12)

def range_x = 0..(image_width-1)

def start = new Date()
for (int j = image_height-1; j >= 0; --j) {
	System.err.print "\rScanlines remaining: $j"
	GParsExecutorsPool.withPool() {
		def line_colors = new ConcurrentHashMap(image_width)
		range_x.eachParallel { i->
			def pixel_color = Color.BLACK
			for (int s = 0; s < samples_per_pixel; ++s) {
				def u = (i + rand.nextDouble()) / (image_width-1)
				def v = (j + rand.nextDouble()) / (image_height-1)
				def r = cam.get_ray(u,v)
				pixel_color += ray_color(r, world, max_depth)
			}
			line_colors[i] = pixel_color
		}
		range_x.each {i->
			Color c = (Color)line_colors[i]
			sb << "${c.toString(samples_per_pixel)}\n"
		}
	}
}

f.text = "P3\n$image_width $image_height\n255\n$sb"
def stop = new Date()
def td = TimeCategory.minus( stop, start )
println "\nDuration: $td"
