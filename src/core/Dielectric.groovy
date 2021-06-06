package core

class Dielectric extends Material {

    double ir // Index of refraction
    Color attenuation = new Color(1,1,1)

    Dielectric(double index_of_refraction) {
        ir = index_of_refraction
    }

    @Override
    Ray scatter(Ray r, HitRecord rec) {
        def refraction_ratio = rec.front_face ? (1.0/ir) : ir

        def unit_direction = Vec3.unit_vector(r.direction())
        def refracted = Vec3.refract( unit_direction, rec.normal, refraction_ratio )

        return new Ray(rec.p, refracted)
    }

    @Override
    Color getAttenuation() {
        return attenuation
    }
}
