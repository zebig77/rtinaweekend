package core

class Dielectric extends Material {

    double ir // Index of refraction
    Color attenuation = new Color(1, 1, 1)

    Dielectric(double index_of_refraction) {
        ir = index_of_refraction
    }

    @Override
    Ray scatter(Ray r, HitRecord rec) {
        def refraction_ratio = rec.front_face ? (1.0 / ir) : ir

        def unit_direction = Vec3.unit_vector(r.direction())

        double cos_theta = Math.min(Vec3.dot(-unit_direction, rec.normal), 1)
        double sin_theta = Math.sqrt(1 - cos_theta * cos_theta)

        boolean cannot_refract = refraction_ratio * sin_theta > 1
        Vec3 direction
        if (cannot_refract ||
                reflectance(cos_theta, refraction_ratio) > new Random().nextDouble() )
            direction = Vec3.reflect(unit_direction, rec.normal)
        else
            direction = Vec3.refract(unit_direction, rec.normal, refraction_ratio)

        return new Ray(rec.p, direction)
    }

    @Override
    Color getAttenuation() {
        return attenuation
    }

    static double reflectance(double cosine, double ref_idx) {
        // Use Schlick's approximation for reflectance.
        def r0 = (1 - ref_idx) / (1 + ref_idx)
        r0 = r0 * r0
        return r0 + (1 - r0) * Math.pow((1 - cosine), 5)
    }
}
