package sandbox

import core.CartesianCategory
import groovy.time.TimeCategory

double x = 0
assert x == (-x+0.0)

def start = new Date()
CartesianCategory.multiply(1..10000, 1..1000)
def stop = new Date()
def td = TimeCategory.minus( stop, start )
println td