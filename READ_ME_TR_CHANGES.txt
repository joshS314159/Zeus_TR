TR Changes/Added Features/Etc.

Moved depots, trucks, days, nodes, shipments, into a Solution Hierarchy folder

Everything in the Solution Hierarchy folder extends from Zeus core classes and implements an interface (either for a doubly linked list or an object in that list)

These Solution Hierarchy interfaces are meant to force-add methods/features such as: getFirst(), getLast(), insertAfterLastIndex(..), getIndexOfObject(..), isValidHeadTail(), getSize(), removeByIndex(..), isEmpty(), removeByObject(..), insertAfterIndex(..), getAtIndex(..), etc.
Not all of these are used in the program.

The Solution Hierachy also contains a folder containing various heuristics.


The trReadFile folder contains an interface, abstract class, and an implementation to read TR format files and PVRP format files.

The trWriteFile folder contains an interface, abstract class, and an implementation to write TR format files and PVRP format files.


TRCoordinates handles longitude/latitude (default) and standard cartesian points. Probably would be better to inherit from and create specific implementations of each. Either way, handles these values, calculates distances and angles, etc. Prevents painful distance and angle calculations from showing up everywhere in the code.