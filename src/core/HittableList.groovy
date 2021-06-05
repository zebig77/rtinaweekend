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

    boolean hit(Ray r, double t_min, double t_max, HitRecord rec) {
        def temp_rec = new HitRecord()
        def hit_anything = false
        def closest_so_far = t_max

        objects.each {
            if (it.hit(r, t_min, closest_so_far, temp_rec)) {
                hit_anything = true
                closest_so_far = temp_rec.t
            }
        }

        temp_rec.copy_to(rec)
        return hit_anything

    }
}
