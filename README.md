# silence-trimmer

## Intro
Hi there! Welcome to my little project called the silence trimmer. This Java application trims the silence from the beginning and end of audio clips, thus shortening the file length and reducing the file size. I find this particularly useful when grabbing sound effects from Youtube, which is often filled with many seconds of silence. This application currently only supports WAV audio files, however I may include other audio file formats at a later date.

## How To Use
Upon cloning this repository there are a few ways to run the application. As a heads up, this Java application was made using Java 8. 

In the /res folder, I have included a .jar file of the program that I have compiled myself. If you double-click on the file you should see a file selector pop up. Select the audio file that you would like trimmed and be on your merry way!

If the provided .jar file doesn't do anything, you can always compile the application and run it through the command line or create a .jar file yourself.

If you would like to compile the program in the command line, I would cd into the src folder and run the following (I use a Windows machine, please refer to the equivalent for other operating systems):

_javac SilenceTrimmer.java_

_java SilenceTrimmer_

If you would like to create your own .jar file, you could run the following as well (again within the src folder):

_javac SilenceTrimmer.java_

_jar cmvf ../META-INF/MANIFEST.MF SilenceTrimmer.jar *.class_

## Notes
For trimming this application currently requires aboslute silence, so if any noise is detected the application will trim early. In the future I may incorporate noise profiling to help with silence detection which will allow the application to be more dynamic.
