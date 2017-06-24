#!/bin/bash
mvn package && java -cp target/flij-1.0-SNAPSHOT.jar com.github.fauu.flij.Launcher
