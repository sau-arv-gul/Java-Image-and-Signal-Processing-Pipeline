# Java-Image-and-Signal-Processing-Pipeline
This project is a pipeline for processing image and signal data. The image processing component converts images to grayscale, while the signal processing component applies a simple moving average filter to the signal data. The project is implemented in Java using OpenCV for image processing and standard Java libraries for signal processing.
Project Description
This project is a pipeline for processing image and signal data. The image processing component converts images to grayscale, while the signal processing component applies a simple moving average filter to the signal data. The project is implemented in Java using OpenCV for image processing and standard Java libraries for signal processing.

Features
Convert images to grayscale
Apply a simple moving average filter to signals
Requirements
OpenCV library for Java
Java 8 or later
Maven (for building the project)
Usage
Set Up Directories:

Place your image files (.jpg or .png) and signal files (.csv) in the data directory.
Ensure the output directory exists or will be created by the program.
Build the Project:
mvn clean install

Run the Project:
java -cp target/ImageSignalProcessing-1.0-SNAPSHOT.jar com.example.ImageSignalProcessing

Processed Files:

Processed images will be saved in the output directory with grayscale conversion.
Processed signals will be saved in the output directory with the same filename as input but with filtered values.

Directory Structure:
project_root/
├── data/
│   ├── image1.jpg
│   ├── image2.png
│   └── signal.csv
├── output/
│   ├── image1.jpg
│   ├── image2.png
│   └── signal.csv
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── ImageSignalProcessing.java
│   │   └── resources/
├── pom.xml
└── README.md
