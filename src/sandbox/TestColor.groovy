package sandbox

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class TestColor {

	@Test
	void testToString() {
		def c = new Color(0.5, 0, 1)
		assert c.toString() == "127 0 255"
	}

}
