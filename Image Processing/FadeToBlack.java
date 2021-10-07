/* 
Assignment number         :         6
File Name                 :         FadeToBlack.java 
Name (First Last)         :         Maor Shriki
Student ID                :         208274118
Email                     :         maor.shriki@post.idc.ac.il
*/

/*
* The program produces a segmented version of 
* the image and then displays an animated step-wise 
* view of how the given colorful image fades
* into a black and white segmented image.
* 
* @param args[0] - A file name.
* @param args[1] - Integr - desired number of
*                  fading steps. 
*/

public class FadeToBlack {
	public static void main(String[] args) {
		String filename = args[0];
		int n = Integer.parseInt(args[1]);
		ImageEditing.morph(ImageEditing.read(filename), ImageEditing.segement(ImageEditing.read(filename)), n);
	}
}
