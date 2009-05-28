package org.brainkandy.oci.context;

public interface IBitmap {
  /**
   * Returns a string representing the bitmap for rendering this character.
   * 
   * For instance to render an arrow you might return f0/c0/a0/90/08/04/02/01,
   * which will render like so:
   * 
   * <pre>
   * ****    
   * **      
   * * *     
   * *  *    
   *     *   
   *      *  
   *       * 
   *        *
   * </pre>
   */
  byte[] getBitmap();
}
