package tests;

import org.jmlspecs.openjml.JmlOptionName;

// This file contains miscellaneous cases that once were bugs.
// I made tests to reproduce them and test the fixes.  I leave
// them here just to make sure they do not reappear, though mostly
// they are simple situations.
public class bugs extends TCBase {

    static String testspecpath = "$A"+z+"$B"+z+"$SY";

    @Override
    public void setUp() throws Exception {
//        noCollectDiagnostics = true;
//        jmldebug = true;
        super.setUp();
        JmlOptionName.putOption(context,JmlOptionName.NOPURITYCHECK);
    }

    
    public void testMiscBug() {
        helpTCF("A.java","public class A { \n void m() { Object o = null; }}"
        );
    }
    
    public void testMiscBug2() {
        helpTCF("A.java","public class A { \n void m(int j) { a.append((j+1) + q[j] ); } String N; StringBuffer a; int[] q; }"
        );
    }
    
    public void testMiscBug3() {
        helpTCF("A.java","public class A { //@ ensures \\result[1].equals(b(c)); \n Object[] m(int j) { return null; } String N; StringBuffer a; int[] q; }"
        ,"/A.java:1: cannot find symbol\nsymbol  : variable c\nlocation: class A",50);
    }
    
    public void testMiscBug4() {
        helpTCF("A.java","public class A { //@ ensures equals(\\result.equals(b).c(p(0))); \n Object m(int j) { return null; } String b; StringBuffer a; int[] q; /*@pure*/int p(int i) { return 0; }}"
                ,"/A.java:1: boolean cannot be dereferenced",54);
    }

    /** There was a problem with a JML keyword being unrecognized after a JML statement 
    * because the keyword mode was not turned on soon enough
    * */
    public void testMiscBug5() {  
        helpTCF("A.java","public class A {  int p(A a) { /*@ set a = null; set a = null; */\n return 0; }}"
                );
    }

    /** A problem with backslashes in character literals because of the special
    * handling of backslashes - which has now all been simplified in the process
    * of fixing this problem */
    public void testMiscBug6() {
        helpTCF("A.java","public class A { //@ requires '\\t' != '\\n'; \n void p() {  }}"
                );
    }

    /** A problem with backslashes in string literals because of the special
     * handling of backslashes - which has now all been simplified in the process
     * of fixing this problem */
    public void testMiscBug7() {
        helpTCF("A.java","public class A { //@ requires \"\\tA\\\\B\" != null; \n void p() {  }}"
                );
    }

    /** Checking for mixed implications */
    public void testMiscBug8() {
        helpTCF("A.java","public class A { //@ requires true ==> false <== true; \n void p() {  }}"
                ,"/A.java:1: ==> and <== operators may not be mixed without parentheses",46
                );
    }
    
    /** Check that 'this' is defined in interface specifications */
    public void testMisc9() {
        helpTCF("A.java","public interface A { //@ ensures \\typeof(this) != null; \n void p();}"
                );
    }

    public void testMisc10() {
        helpTCF("A.java","public interface A { //@ instance ghost int i; \n } class B implements A { void p(A a) { //@ set a.i = 0; \n}}"
                );
    }
    
    public void testMisc11() {
        options.put("-specs",   testspecpath);
        helpTCF("A.java","public class A { private /*@ spec_public */ java.util.Vector pending; \n //@ invariant pending.elementCount == 0; \n} "
                );
    }

    public void testMisc12() {
        helpTCF("A.java","abstract public class A { Object x; \n //@ ensures \\old(a) == null;  \n abstract void m(A a);  \n} "
                );
    }

    public void testMisc13() {
        helpTCF("A.java","abstract public class A { \n //@ signals (Exception) true; signals (Exception) true; \n  void m(A a) {}  \n} "
                );
    }

    public void testMisc14() {
        helpTCF("A.java","abstract public class A { \n //@ signals (Exception e) true; signals (Exception e) true; \n  void m(A a) {}  \n} "
                );
    }

    public void testMisc15() {
        helpTCF("A.java","abstract public interface A { \n //@ public model void m(); \n  \n} class B implements A {}"
                );
    }

    public void testMisc16() {
        helpTCF("A.java","public class A { \n int i; //@ in j; model int j; \n} "
                );
    }

    public void testMisc17() {
        helpTCF("A.java","public class A { \n int k = m; int m = 0; \n//@ ghost int i = j; ghost int j = 0; \n} "
                ,"/A.java:2: illegal forward reference",10
                ,"/A.java:3: illegal forward reference",19
                );
    }

    public void testMisc18() {  // FIXME - this sort of thing ought to fail - all Java keywords need to be in Java land
        helpTCF("A.java","public class A { /*@ public non_null */ Object j;  \n} "
                );
    }



}