BLE-Advertisement-Graph-For-Ubertooth
=====================================

Bluetooth Low Energy advertisement debugger using Java, Ubertooth and spectool_raw

//
// Graph.java - Will display BLE advertisement packets when output from spectool_raw is piped to it.
// Written by Jens Olsson <www.jensolsson.se>
//
// Compile: javac Graph.java
//
// Sample usage: sudo spectool_raw | java Graph
// Sample usage (remote machine has the ubertooth dongle): ssh -t user@host sudo spectool_raw | java Graph
//
// Open issues: Is the Ubertooth spectool fast enough to catch all advertisement packets or will some of them be ignored?

![Screenshot, looking at advertisement packages channel 37, 38 and 39](Screenshot.png?raw=true "Screenshot, looking at advertisement packages channel 37, 38 and 39")