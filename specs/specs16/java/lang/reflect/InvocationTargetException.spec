package java.lang.reflect;

// FIXME - needs completion
 
public class InvocationTargetException extends Exception {
  
    /*@ public normal_behavior
      @   ensures standardThrowable(null);
      @*/
    //@ pure
    public InvocationTargetException(Throwable t);
  
    /*@ public normal_behavior
      @   ensures standardThrowable(s);
      @*/
    //@ pure
    public InvocationTargetException(Throwable t, String s);

    //@ pure
    public Throwable getCause();

    //@ pure
    public Throwable getTargetException();
}