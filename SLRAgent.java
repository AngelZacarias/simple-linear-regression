/***********************************
*
*   Simple Lineal Regression Solver
*   Luis Angel Zacarias Magaña
*   217758632
*
************************************/

package examples.simpleLinearRegression;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.*;

public class SLRAgent extends Agent {
    // x value to use in the prediction
    private double x;
    // The GUI by means of which the user can add books in the catalogue
    private SLRGui myGui;

    // Agent initializations
    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");

        // Create and show the GUI
        myGui = new SLRGui(this);
        myGui.showGui();
    }

    // Agent clean-up operations
    protected void takeDown() {
        // Close the GUI
        myGui.dispose();
        // Printout a dismissal message
        System.out.println("SLR-agent " + getAID().getName() + " terminating.");
    }

    /**
     * This is invoked by the GUI when the user adds a new book for sale
     */
    public void updateXValue(final Double xValue) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                x = xValue;
                System.out.println("x value recieved.");
            }
        });
        addBehaviour(new SLR(x));
    }

    private class SLR extends OneShotBehaviour {
        // Constants of prediction eq
        private double a;
        private double b;
        // Variables to determine the constants
        private double xAvg;
        private double yAvg;

        // x value used to predict y_est
        private double x;

        public SLR(double _x) {
            System.out.println("Initializing SLR algorithm...");
            a = 0;
            b = 0;
            xAvg = 0;
            yAvg = 0;
            x = _x;
        }

        // Method to train the SLR / Find the constants
        public void train(double[][] data) {
            System.out.println("Training...");

            // Calculates Avgs
            xAvg = 0;
            yAvg = 0;
            for (double[] register : data) {
                xAvg += register[0];
                yAvg += register[1];
            }
            xAvg = xAvg / data.length;
            yAvg = yAvg / data.length;

            // Calculates a
            double aux1 = 0, aux2 = 0;
            for (double[] register : data) {
                aux1 += ((register[0] - xAvg) * (register[1] - yAvg));
                aux2 += (register[0] - xAvg) * (register[0] - xAvg);
            }
            a = aux1 / aux2;
            System.out.println("Value found for a = " + a);

            // Calculates b
            b = yAvg - a * xAvg;
            System.out.println("Value found for b = " + b);

            System.out.println(String.format("y_hat = %.2f + %.2fx", b, a));
        }

        // Method to predict given x
        public double predict(double z) {
            return b + a * z;
        }

        // Method to predict with our x
        public double predict() {
            return b + a * x;
        }

        // Method to predict given an array X
        public double[] predict(double[] X) {
            double[] y = new double[X.length];
            int i = 0;
            for (double z : X) {
                y[i] = predict(z);
                i++;
            }
            return y;
        }

        public void action() {
            System.out.println("SLR Predictor");
            double[][] dataSet = { { 23, 651 }, { 26, 762 }, { 30, 856 }, { 34, 1063 }, { 43, 1190 }, { 48, 1298 },
                    { 52, 1421 }, { 57, 1440 }, { 58, 1518 } };
            train(dataSet);

            // Predictions
            double y_hat = predict(62);
            System.out.println("The predicted result for " + x + " is:" + y_hat);
        }

        public int onEnd() {
            System.out.println("Behaviour has been finished...");
            myAgent.doDelete();
            return super.onEnd();
        }
    }// End of inner class

}
