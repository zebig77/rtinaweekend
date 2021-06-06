package core

abstract class Material {
    abstract Ray scatter( Ray r, HitRecord rec)
    abstract Color getAttenuation()
}
