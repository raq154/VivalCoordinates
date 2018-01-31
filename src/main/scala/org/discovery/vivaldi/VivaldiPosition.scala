package org.discovery.vivaldi

/**
  * Created by raheel on 31/01/2018.
  * Based on the client implementation of Vuze JS library
  */
class VivaldiPosition(var heightCoordinates: HeightCoordinates) {

  val CONVERGE_EVERY = 5
  val CONVERGE_FACTOR = 50
  val ERROR_MIN = 0.1

  val cc = 0.25
  val ce = 0.5
  val initialError: Double = 10
  var localError: Double = initialError
  var totalUpdates = 0

  def getCoordinates(): HeightCoordinates = {
    this.heightCoordinates
  }

  def getLocation(): (Double, Double) = {
    this.heightCoordinates.getCoordinates()
  }

  def getErrorEstimate(): Double = {
    this.localError
  }

  def setErrorEstimate(e: Double) {
    this.localError = e
  }

  def isValid(): Boolean = {
    this.getCoordinates().isValid()
  }

  def estimateRTT(data: HeightCoordinates): Double = {
    this.heightCoordinates.distance(data)
  }

  def estimateRTT(data: VivaldiPosition) = {
    val coords = data.getCoordinates()

    if (coords.atOrigin() || this.heightCoordinates.atOrigin()) {
      throw new RuntimeException("invalid state")
    }

    this.heightCoordinates.distance(coords)
  }


  def equals(other: VivaldiPosition): Boolean = {
    var res = true
    if (other.localError != this.localError) {
      res = false
    }

    if (!other.heightCoordinates.equals(this.heightCoordinates)) {
      res = false
    }
    res
  }

  def update(rtt: Long, cj: HeightCoordinates, ej: Double): Boolean = {
    if (!cj.isValid()) {
      return false; // throw error may be
    }

    val error = this.localError

    // Ensure we have valid data in input
    // (clock changes lead to crazy rtt values)
    if (rtt <= 0 || rtt > 5 * 60 * 1000)
      return false

    if (error + ej == 0)
      return false

    // Sample weight balances local and remote error. (1)
    val w = error / (ej + error)

    // Real error
    val re = rtt - this.heightCoordinates.distance(cj)

    // Compute relative error of this sample. (2)
    val es = Math.abs(re) / rtt

    // Update weighted moving average of local error. (3)
    val new_error = es * ce * w + error * (1 - ce * w)

    // Update local coordinates. (4)
    val delta = cc * w
    val scale = delta * re

    val random_error = new HeightCoordinates(Math.random() / 10, Math.random() / 10, 0)
    val new_coordinates = this.heightCoordinates.add(this.heightCoordinates.sub(cj.add(random_error)).unity().scale(scale))

    if (new_coordinates.isValid()) {
      this.heightCoordinates = new_coordinates
      this.localError = if (new_error > ERROR_MIN) new_error else ERROR_MIN
    } else {
      this.heightCoordinates = new HeightCoordinates(0, 0, 0)
      this.localError = initialError
    }

    if (!cj.atOrigin()) {
      this.totalUpdates = totalUpdates + 1
    }
    if (this.totalUpdates > CONVERGE_EVERY) {
      this.totalUpdates = 0
      this.update(10, new HeightCoordinates(0, 0, 0), CONVERGE_FACTOR)
    }

    true
  }

}

object VivaldiPosition {
  def create(): VivaldiPosition = {
    new VivaldiPosition(new HeightCoordinates(0, 0, 0))
  }
}