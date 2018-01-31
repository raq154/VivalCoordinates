# vivaldi-coordinates
[![Build Status](https://travis-ci.org/reklatsmasters/vivaldi-coordinates.svg?branch=master)](https://travis-ci.org/reklatsmasters/vivaldi-coordinates)
[![npm](https://img.shields.io/npm/v/vivaldi-coordinates.svg)](https://npmjs.org/package/vivaldi-coordinates)
[![license](https://img.shields.io/npm/l/vivaldi-coordinates.svg)](https://npmjs.org/package/vivaldi-coordinates)
[![downloads](https://img.shields.io/npm/dm/vivaldi-coordinates.svg)](https://npmjs.org/package/vivaldi-coordinates)
[![Code Climate](https://codeclimate.com/github/ReklatsMasters/vivaldi-coordinates/badges/gpa.svg)](https://codeclimate.com/github/ReklatsMasters/vivaldi-coordinates)
[![Test Coverage](https://codeclimate.com/github/ReklatsMasters/vivaldi-coordinates/badges/coverage.svg)](https://codeclimate.com/github/ReklatsMasters/vivaldi-coordinates)

Vivaldi: A Decentralized Network Coordinate System. Originaly description [here](https://www.cs.umd.edu/class/spring2007/cmsc711/papers/vivaldi.pdf).This package based on source code of [Vuze](https://vuze.com/) 

## API

##### `create(data: HeightCoordinates): VivaldiPosition`
Create `VivaldiPosition` instance from `HeightCoordinates` instance.

##### `create(): VivaldiPosition`
Create new empty `VivaldiPosition` instance.

##### `update(rtt: Number, p2: HeightCoordinates, e2: Int): bool`
Update position by using the data from other node 


## Example
```scala
  var localPos = VivaldiPosition.create()
  var someOtherCoordinates =   ... //get coordinates here from some other node
  var rtt = 7;	  // ping time to remote host
  localPos.update(rtt, someOtherCoordinates.heightCoordinates, someOtherCoordinates.localError)	// update local position
```

