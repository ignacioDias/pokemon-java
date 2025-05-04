#!/bin/bash

# Compilar el proyecto usando Maven
mvn clean compile -e

# Ejecutar la clase Main
java -cp target/classes org.example.Main
