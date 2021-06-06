package core

class HittableList {

    List<Hittable> objects = []

    HittableList() {}

    HittableList(Hittable object) {
        add(object)
    }

    void add(Hittable object) {
        objects << object
    }

    void clear() {
        objects = []
    }

    HitRecord hit(Ray r, double t_min, double t_max) {
        def hit_anything = false
        def closest_so_far = t_max
        HitRecord result = null

        objects.each {
            def rec = it.hit(r, t_min, closest_so_far)
            if (rec) {
                result = rec
                closest_so_far = rec.t
            }
        }

        return result
    }
}
