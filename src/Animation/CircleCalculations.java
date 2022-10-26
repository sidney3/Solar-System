package Animation;

import java.lang.Math;
public class CircleCalculations {  //output the list of coordinates travelling in a circle
    //This is called only in Constants
    //But I didn't want to clutter that file

    public static double[][] circleCoords(){
            /*This comes from equation 187.5^{2}=l(x-187.5)^{2}+ y^{2}
            Or the equation of a circle with radius 187.5 translated right 187.5 units

            We will make two lists, one with all the positive values of this function
            (the top half of the circle starting at x=0) - this is gotten just from sqrt((187.5^2 - (x-187.5)^2))

            And then the second list the bottom half of the circle, starting at x=375 (the end of the circle)
            by which each element of the list is the inverse of the element in the first list in the "opposite position"

            e.x. first element of our second list = -last element of our first list
            */
        double[][]arr=new double[374*2+2][2]; //list extends out, iterating by single x values, to 374
        //so looping back it will be of length 374*2



        int i;
        int n;
        for(i=0;i<375;i++){ //iterate through x values 0-375

            arr[i][1]=Math.sqrt(
                    Math.pow(187.5,2)-Math.pow((i)-187.5,2)); //set y-values
            arr[i][0]=i; //set x-values
        }


        arr[375][0] = 375;
        arr[375][1] = 0;
        n=376;

        for(i=374;i>0;i--){//this time iterate back through x-values

            arr[n][1]=-1*(arr[i][1]);//work backwards through the list.
            //x iterates up through our values (because we want a list of 749 elements)
            //While i iterates down through the prior elements (because we want elements 376-749 to be counting down)

            arr[n][0]= i; //set x values
            ++n;
        }

        return arr;
    }
        }