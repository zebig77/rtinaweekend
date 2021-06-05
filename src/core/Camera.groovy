package core

class Camera {

    Point3 origin
    Point3 lower_left_corner
    Vec3 horizontal
    Vec3 vertical

    Camera() {
        def aspect_ratio = 16.0 / 9.0
        def viewport_height = 2.0
        def viewport_width = aspect_ratio * viewport_height
        def focal_length = 1.0

        origin = new Point3(0, 0, 0)
        horizontal = new Vec3(viewport_width, 0, 0)
        vertical = new Vec3(0, viewport_height, 0)
        lower_left_corner = origin - horizontal/2 - vertical/2 - new Vec3(0, 0, focal_length)
    }

    Ray get_ray(double u, double v) {
        new Ray(origin, lower_left_corner + horizontal*u + vertical*v - origin)
    }

}
