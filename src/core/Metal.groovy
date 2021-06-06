package core

class Metal extends Material {

    Color albedo
    double fuzziness

    Metal(Color c, double f) {
        albedo = c
        if (f > 1)
            f = 1
        fuzziness = f
    }

    @Override
    Ray scatter(Ray r_in, HitRecord rec) {
        Vec3 reflected = Vec3.reflect(Vec3.unit_vector(r_in.direction()), rec.normal)
        def scattered = new Ray(rec.p, reflected + Vec3.random_in_unit_sphere()*fuzziness)
        if (Vec3.dot(scattered.direction(), rec.normal) > 0) {
            return scattered
        }
        else { return null }
    }

    @Override
    Color getAttenuation() {
        return albedo
    }
}
