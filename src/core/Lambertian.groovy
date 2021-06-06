package core

class Lambertian extends Material {
    Color albedo

    Lambertian(Color c) { albedo = c }

    @Override
    Ray scatter(Ray r, HitRecord rec) {
        def scatter_direction = rec.normal + Vec3.random_unit_vector()

        if (scatter_direction.near_zero()) {
            scatter_direction = rec.normal
        }

        return new Ray(rec.p, scatter_direction)
    }

    Color getAttenuation() {
        return albedo
    }
}
