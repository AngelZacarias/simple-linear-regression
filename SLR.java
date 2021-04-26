/***********************************
*
*   Simple Lineal Regression Solver
*   Luis Angel Zacarias Magaña
*   217758632
*
************************************/
public class SLR {   
    //Constants of prediction eq
    private static double a;
    private static double b;
    //Variables to determine the constants
    private static double xAvg;
    private static double yAvg;

    //Constructor
    public SLR(){
        System.out.println("Initializing SLR algorithm...");
        a = 0;
        b = 0;
        xAvg = 0;
        yAvg = 0;
    }

    //Method to train the SLR / Find the constants
    public static void train(double[][] data){
        System.out.println("Training...");

        //Calculates Avgs
        xAvg = 0;
        yAvg = 0;
        for (double[] register : data){
            xAvg += register[0];
            yAvg += register[1];
        }
        xAvg = xAvg / data.length;
        yAvg = yAvg / data.length;
        
        //Calculates a
        double aux1 = 0 , aux2 = 0;
        for (double[] register : data){
            aux1 += ((register[0] - xAvg) * (register[1] - yAvg));
            aux2 += (register[0] - xAvg)*(register[0] - xAvg);
        }
        a = aux1 / aux2;
        System.out.println("Value found for a = "+ a);

        //Calculates b
        b = yAvg - a * xAvg;
        System.out.println("Value found for b = "+ b);
    }

    //Method to predict given x
    public static double predict(double x){
        double y = 1;
        System.out.println("predict");
        return y;
    }

    //Method to predict given an array X
    public static double[] predict(double[] X){
        double[] y = {1, 2};
        System.out.println("2");
        return y;
    }

    public static void main(String[] args){
        System.out.println("SLR Predictor");
        double[][] dataSet = {
            {23, 651},
            {26, 762},
            {30, 856},
            {34, 1063},
            {43, 1190},
            {48, 1298},
            {52, 1421},
            {57, 1440},
            {58, 1518}
        };
        SLR slr = new SLR();
        slr.train(dataSet);
    }

}
