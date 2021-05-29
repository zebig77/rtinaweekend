package main

import java.awt.Point

import core.Color
import core.Ray
import core.Vec3

Color ray_color(final Ray r) {
	Vec3 unit_direction = r.direction().unit_vector()
	def t = 0.5*( unit_direction.y() + 1.0 )
	return (1.0 - t)*new Color(1,1,1) + t*new Color(0.5, 0.7, 1.0)
}

// Image
final aspect_ratio = 16.0 / 9.0
final int image_width = 400
final int image_height = image_width / aspect_ratio

// Camera
def viewport_height = 2.0
def viewport_width = aspect_ratio * viewport_height
def focal_length = 1.0

def origin = new Point()

def f = new File('sample.ppm')

def sb = new StringBuilder()

for (int j = image_height-1; j >= 0; --j) {
	System.err << "\rScanlines remaining: $j"
	for (int i = 0; i < image_width; ++i) {
		def r = (double)i / (image_width-1)
		def g = (double)j / (image_height-1)
		def b = 0.25
		
		def color = new Color(r,g,b)
		
		sb << "$color\n"
	}
}

f.text = "P3\n$image_width $image_height\n255\n$sb" 
