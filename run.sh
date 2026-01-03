#!/bin/bash

# Download SQLite JDBC driver if it doesn't exist
if [ ! -f "sqlite-jdbc-3.42.0.0.jar" ]; then
    echo "Downloading SQLite JDBC driver..."
    wget https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar
fi

# Run the application
# We use the Java source launcher (Java 11+) to run directly without compiling to .class files manually
# -cp sets the classpath to include the current directory (src) and the JDBC driver
java -cp sqlite-jdbc-3.42.0.0.jar:src src/com/university/management/Main.java
