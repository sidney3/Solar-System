/*
A special class of values that I want to access universally,
but I want to change
 */

package cartoon;

public class Universals{
    public static boolean paused = false;
    //THIS VALUE IS ACCESSED ONLY BY THE PLANETS AND CLASS CARTOON

    /***
     The choice to make a universal value was not an easy one.

     Under normal conditions, this would be poor design choice, but
     the key element of this program is that the planets are not
     created as instance variables, and so they cannot have their
     values dynamically changed (through, for example, a "setPause"
     function).

     Without this choice, the code grows a lot. Each planet needs to be
     initialized without the function, and so I made the simple choice
     to add a universal variable just to simplify it
     */
}