package cisc.awas.image_shifter.service;

public interface ImageShiftService {

	/**
	 * Primary method to shift images.
	 */
	public void shiftImage();
	
	/**
	 * Retrieve rows count in the original image table.
	 * @return The rows count in the original image table
	 */
	public long imageCounts();
	
	/**
	 * Reset current image id to resume the task if exception occurs.
	 */
	public void resetCurrentImageId();
	
}
