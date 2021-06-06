package core

class Camera {

    Point3 origin
    Point3 lower_left_corner
    Vec3 horizontal
    Vec3 vertical
    Vec3 u, v, w
    double lens_radius

    Camera(
            Point3 lookfrom,
            Point3 lookat,
            Vec3 vup,  // world up
            double vfov, // vertical field of view in degrees
            double aspect_ratio,
            double aperture,
            double focus_dist
    ) {
        def theta = Math.toRadians(vfov)
        def h = Math.tan(theta/2)
        def viewport_height = 2.0 * h
        def viewport_width = aspect_ratio * viewport_height

        w = Vec3.unit_vector(lookfrom - lookat)
        u = Vec3.unit_vector(Vec3.cross(vup, w))
        v = Vec3.cross(w, u)

        origin = lookfrom
        horizontal = u * viewport_width * focus_dist
        vertical = v * viewport_height * focus_dist
        lower_left_corner = origin - horizontal/2 - vertical/2 - w*focus_dist

        lens_radius = aperture/2
    }

    Ray get_ray(double s, double t) {
        new Ray(origin, lower_left_corner + horizontal*s + vertical*t - origin)
    }

}
