/*
Assignment number         :         8
File Name                 :         VicSimulator.java
Name (First Last)         :         Maor Shriki
Student ID                :         208274118
Email                     :         maor.shriki@post.idc.ac.il
*/

public class VicSimulator {
    public static void main(String[] args) {
        String filename = args[0];
        Computer vic = new Computer();
        vic.loadProgram(filename);
        if (args.length > 1) {
            String inputFilename = args[1];
            vic.setInput(inputFilename);
        }
        vic.run();
        System.out.println(vic.toString());
    }
}