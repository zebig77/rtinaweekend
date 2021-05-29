package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import core.Vec3

class TestVec3 {

	@Test
	void testNewEmpty() {
		def v = new Vec3()
		assert v.x() == 0
		assert v.y() == 0
		assert v.z() == 0
		assert v.toString() == '[0.0, 0.0, 0.0]'
	}
	
	@Test
	void testNewValues() {
		def v = new Vec3(1,2,3)
		assert v.x() == 1
		assert v.y() == 2
		assert v.z() == 3
		assert v.toString() == '[1.0, 2.0, 3.0]'
	}

	@Test
	void testNegative() {
		def v = new Vec3(1,2,3)
		def vn = -v
		assert v.x() == 1
		assert v.y() == 2
		assert v.z() == 3
		assert vn.x() == -1
		assert vn.y() == -2
		assert vn.z() == -3
	}

	@Test
	void testGetAt() {
		def v = new Vec3(1,2,3)
		assert v[0] == 1
		assert v[1] == 2
		assert v[2] == 3
	}
	
	@Test
	void testPlus() {
		def v1 = new Vec3(1,2,3)
		def oldHashCode = v1.hashCode()
		def v2 = new Vec3(5,6,7)
		v1 += v2
		// new object allocated
		assert v1.hashCode() != oldHashCode
		assert v1[0] == 6
		assert v1[1] == 8
		assert v1[2] == 10
	}

	@Test
	void testPlus2() {
		def v1 = new Vec3(1,2,3)
		def v2 = new Vec3(5,6,7)
		def v3 = v1 + v2
		assert v1[0] == 1
		assert v1[1] == 2
		assert v1[2] == 3
		assert v2[0] == 5
		assert v2[1] == 6
		assert v2[2] == 7
		assert v3[0] == 6
		assert v3[1] == 8
		assert v3[2] == 10
	}
	
	@Test
	void testMinus() {
		def v1 = new Vec3(10,12,23)
		def v2 = new Vec3(1,2,3)
		v1 -= v2
		assert v2[0] == 1
		assert v2[1] == 2
		assert v2[2] == 3
		assert v1[0] == 9
		assert v1[1] == 10
		assert v1[2] == 20
	}

	@Test
	void testMinus2() {
		def v1 = new Vec3(10,12,23)
		def v2 = new Vec3(1,2,3)
		def v3 = v1 - v2
		assert v1[0] == 10
		assert v1[1] == 12
		assert v1[2] == 23
		assert v2[0] == 1
		assert v2[1] == 2
		assert v2[2] == 3
		assert v3[0] == 9
		assert v3[1] == 10
		assert v3[2] == 20
	}

	@Test
	void testAdd() {
		def v1 = new Vec3(1,2,3)
		def oldHashCode = v1.hashCode()
		def v2 = new Vec3(5,6,7)
		v1.add(v2)
		// same object
		assert v1.hashCode() == oldHashCode
		assert v1[0] == 6
		assert v1[1] == 8
		assert v1[2] == 10
	}
	
	@Test
	void testMultiplyVector() {
		def v1 = new Vec3(1,2,3)
		def v2 = new Vec3(5,6,7)
		v1 *= v2
		assert v1[0] == 5
		assert v1[1] == 12
		assert v1[2] == 21
	}
	
	@Test
	void testMultiplyScalar() {
		def v1 = new Vec3(1,2,3)
		v1 *= 3.0d
		assert v1[0] == 3
		assert v1[1] == 6
		assert v1[2] == 9
	}

	@Test
	void testMultiplyScalar2() {
		def v1 = new Vec3(1,2,3)
		def v2 = v1 * 3.0d
		assert v2[0] == 3
		assert v2[1] == 6
		assert v2[2] == 9
	}
	
	@Test
	void testDivVector() {
		def v1 = new Vec3(12,6,15)
		def v2 = new Vec3(2,3,3)
		v1 /= v2
		assert v1[0] == 6
		assert v1[1] == 2
		assert v1[2] == 5
	}

	@Test
	void testDivScalar() {
		def v1 = new Vec3(12,6,15)
		def v2 = v1 / 3.0d
		assert v2[0] == 4
		assert v2[1] == 2
		assert v2[2] == 5
	}
	
	@Test
	void testSquareLength() {
		def v1 = new Vec3(1,2,3)
		assert v1.length_squared() == 14
	}
	
	@Test
	void testDotUV() {
		def u = new Vec3(1,2,3)
		def v = new Vec3(0,1,2)
		assert Vec3.dot(u,v) == 8
	}
	
	@Test
	void testDotU() {
		def u = new Vec3(1,2,3)
		def v = new Vec3(1,1,2)
		assert u.dot(v) == 9
	}

	@Test
	void testEquals() {
		def u = new Vec3(1,2,3)
		def v = new Vec3(1,1,2)
		assert !u.equals(v)
		def w = new Vec3(1,2,3)
		assert u.equals(w)
	}

	@Test
	void testCross() {
		def vx = new Vec3(1,0,0)
		def vy = new Vec3(0,1,0)
		def vz = new Vec3(0,0,1)
		assert Vec3.cross(vx,vy).equals(vz)
		assert Vec3.cross(vy,vx).equals(-vz)
		assert Vec3.cross(vz,vx).equals(vy)
		assert Vec3.cross(vx,vz).equals(-vy)
		assert Vec3.cross(vy,vz).equals(vx)
		assert Vec3.cross(vz,vy).equals(-vx)
	}

	@Test
	void testLength() {
		def v1 = new Vec3(1,2,3)
		assert v1.length() == Math.sqrt(14)
	}

	
	@Test
	void testUnitVector() {
		def v1 = new Vec3(1,2,3)
		def l = v1.length()
		def v2 = new Vec3(1/l,2/l,3/l)
		assert Vec3.unit_vector(v1).equals(v2)
	}
}
