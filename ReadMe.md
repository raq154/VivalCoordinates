# vivaldi-coordinates
Based on Implementation of Vivaldi in Vuze (https://github.com/svn2github/vuze) and Vivaldi Coordinates NodeJS library (https://github.com/reklatsmasters/vivaldi-coordinates)

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

