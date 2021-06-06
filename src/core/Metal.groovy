package core

class Metal extends Material {

    Color albedo

    Metal(Color c) {
        albedo = c
    }

    @Override
    Ray scatter(Ray r_in, HitRecord rec) {
        Vec3 reflected = Vec3.reflect(Vec3.unit_vector(r_in.direction()), rec.normal)
        def scattered = new Ray(rec.p, reflected)
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
