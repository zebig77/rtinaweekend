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
double aspect_ratio = 16.0 / 9.0
int image_width = 400
int image_height = (int) (image_width / aspect_ratio)
int samples_per_pixel = 100
int max_depth = 50

// World
def world = new HittableList()
def R = Math.cos(Math.PI/4)

def material_left  = new Lambertian(Color.BLUE)
def material_right = new Lambertian(Color.RED)

world.add(new Sphere(new Point3(-R, 0, -1), R, material_left))
world.add(new Sphere(new Point3( R, 0, -1), R, material_right))

//def material_ground = new Lambertian(	new Color(0.8, 0.8, 0.0))
//def material_center = new Lambertian(	new Color(0.1, 0.2, 0.5))
//def material_left   = new Dielectric(	1.5 )
//def material_right  = new Metal(		new Color(0.8, 0.6, 0.2), 0.0)
//
//world.add(new Sphere(new Point3( 0.0, -100.5, -1.0), 100.0, material_ground))
//world.add(new Sphere(new Point3( 0.0,    0.0, -1.0),   0.5, material_center))
//world.add(new Sphere(new Point3(-1.0,    0.0, -1.0),   0.5, material_left))
//world.add(new Sphere(new Point3(-1.0,    0.0, -1.0),  -0.4, material_left))
//world.add(new Sphere(new Point3( 1.0,    0.0, -1.0),   0.5, material_right))

// Camera
def cam = new Camera(90, aspect_ratio)

// Render
def f = new File('../../../images/sample15_aa10.ppm')
println "Creating "+f.getName()+"..."

def sb = new StringBuilder(image_height*image_width*12)

def rand = new Random()
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
println "Duration: $td"
