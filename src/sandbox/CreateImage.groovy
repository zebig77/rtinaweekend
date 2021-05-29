package sandbox

final int image_width = 256
final int image_height = 256

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
