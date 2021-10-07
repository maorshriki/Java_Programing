/* 
Assignment number         :         6
File Name                 :         Editor1.java 
Name (First Last)         :         Maor Shriki
Student ID                :         208274118
Email                     :         maor.shriki@post.idc.ac.il
*/

/*
 * The program reads the image from the file,
 * opens a graphics window, and displays in it a new image
 * which is either the horizontally flipped, vertically flipped,
 * or segmented version of the original image.
 *
 * @param args[0] - String - The file name.
 * @param args[1] - String - action (fh, fv, se).
*/

public class Editor1 {
	public static void main(final String[] args) {
		final String filename = args[0];
		final String operation = args[1];
		if (operation.equals("fh")) {
			ImageEditing.show(ImageEditing.flipHorizontally(ImageEditing.read(filename)));
		}
		if (operation.equals("fv")) {
			ImageEditing.show(ImageEditing.flipVertically(ImageEditing.read(filename)));
		} else if (operation.equals("se")) {
			ImageEditing.show(ImageEditing.segement(ImageEditing.read(filename)));
		}
	}
}
