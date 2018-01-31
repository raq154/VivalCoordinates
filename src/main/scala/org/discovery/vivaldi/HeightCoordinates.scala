package org.discovery.vivaldi

/**
  * Created by mac on 31/01/2018.
  */
class HeightCoordinates(val x: Double, val y: Double, val h: Double) {
  val MAX_X = 30000
  val MAX_Y = 30000
  val MAX_H = 30000

  def add(other: HeightCoordinates): HeightCoordinates = {
    primitive(this, other, 1)
  }

  def sub(other: HeightCoordinates): HeightCoordinates = {
    primitive(this, other, -1)
  }

  def scale(scale: Double): HeightCoordinates = {
    new HeightCoordinates(
      scale * this.x,
      scale * this.y,
      scale * this.h
    )
  }

  def measure(): Double = {
    Math.sqrt(this.x * this.x + this.y * this.y) + this.h
  }

  def atOrigin(): Boolean = {
    this.x == 0 && this.y == 0
  }

  def isValid(): Boolean = {
    Math.abs(this.x) <= MAX_X &&
      Math.abs(this.y) <= MAX_Y &&
      Math.abs(this.h) <= MAX_H
  }

  def distance(other: HeightCoordinates): Double = {
    this.sub(other).measure()
  }

  def unity(): HeightCoordinates = {
    val measure = this.measure()
    this.scale(1 / measure)
  }

  def getCoordinates(): (Double, Double) = {
    (this.x, this.y)
  }


  def equals(other: HeightCoordinates): Boolean = {
    if (other.x != this.x || other.y != this.y || other.h != this.h) {
      return false
    }
    true
  }

  def primitive(c1: HeightCoordinates, c2: HeightCoordinates, scale: Double): HeightCoordinates = {
    new HeightCoordinates(
      c1.x + c2.x * scale,
      c1.y + c2.y * scale,
      Math.abs(c1.h + c2.h)
    )
  }
}
