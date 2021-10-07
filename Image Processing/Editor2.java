/* 
Assignment number         :         6
File Name                 :         Editor2.java 
Name (First Last)         :         Maor Shriki
Student ID                :         208274118
Email                     :         maor.shriki@post.idc.ac.il
*/

/*
 * The program reads the source PPM file using 
 * the read function, scales the image to 
 * specified dimensions, and displays the result.
 *
 * @param args[0] - The file name.
 * @param args[1] - Integer of the desired (rows) width to rescale.
 * @param args[1] - Integer of the desired (col) height to rescale.
 */

public class Editor2 {
	public static void main(String[] args) {
		String filename = args[0];
		int rows = Integer.parseInt(args[1]);
		int col = Integer.parseInt(args[2]);
		ImageEditing.show(ImageEditing.scale(ImageEditing.read(filename), rows, col));
	}
}
