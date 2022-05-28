 package clientSide.entities;

/**
 *    Barber cloning.
 *
 *      It specifies his own attributes.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */

public interface StudentCloning
{
 /**
   *   Set Student id.
   *
   *     @param id Student id
   */

  public void setStudentId (int id);
  /**
  *   Change the flag StudentMenu
  */
  public void setStudentMenu ();

 /**
  *   Get Student id.
  *
  *     @return Student id
  */

  public int getStudentId ();

 /**
  *   Set Student state.
  *
  *     @param state new Student state
  */

  public void setStudentState (int state);

 /**
  *   Get Student state.
  *
  *     @return Student state
  */

  public int getStudentState ();

  /**
  *   Get flag Student menu.
  *
  *     @return Student state
  */

  public boolean getStudentMenu();

  /**
  *   Get Student course.
  *
  *     @return Student state
  */

  public int getStudentCourse();

   /**
     *   Increment Student course
     */
  public void incrementStudentCourse();

  /**
     *  Set this student as the first student
     */

  public void setFirst();

  /**
     *   Set this student as the last student
     */

  public void setlast();

  /**
     *   Student enters the bar - Wake threads at the bar
     */

  public void enterBar();
  /**
     *  Get flag hasPortion
     *  @return hasPortion
     */
  public boolean getHasPortion();
  /**
     *  Change flag hasPortion
     */
  public void setHasPortion();

  /**
     *  Get flag read
     *  @return read
     */
  public boolean getRead();

  /**
     *  Change flag read
     */
  public void setRead();
}
