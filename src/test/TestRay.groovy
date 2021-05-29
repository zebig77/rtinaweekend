package test

import org.junit.jupiter.api.Test

import core.Point3
import core.Ray
import core.Vec3

class TestRay {

	@Test
	void testAt() {
		def orig = new Point3(1,1,1)
		def dir = new Vec3(0,1,0)
		def ray = new Ray(orig, dir)
		def target = ray.at(3)
		assert target.equals(new Point3(1,4,1))
	}

}
