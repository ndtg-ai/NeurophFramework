package org.neuroph.util.data.norm;

import java.io.Serializable;
import org.neuroph.util.DataSetStats;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

/**
 * Normalizes data sets by shifting all values in such way that data set has mean of 0 and std deviation of 1.
 */
public class ZeroMeanNormalizer implements Normalizer, Serializable {

    private double[] maxInput;
    private double[] minInput;
    private double[] meanInput;

    /**
     * Flag to indicate that normalizer is initialized
     */
    private boolean intialized=false;

    public void init(DataSet dataSet) {
        maxInput = DataSetStats.inputsMax(dataSet);
        minInput = DataSetStats.inputsMin(dataSet);
        meanInput = DataSetStats.inputsMean(dataSet);
        intialized = true;
    }

    @Override
    public void normalize(DataSet dataSet) {
        if (!intialized) init(dataSet);

        for (DataSetRow row : dataSet.getRows()) {
            double[] normalizedInput = row.getInput();

            for (int i = 0; i < dataSet.getInputSize(); i++) {
                double divider = maxInput[i] - minInput[i] == 0 ? 1 : maxInput[i] - minInput[i];
                normalizedInput[i] = (normalizedInput[i] - meanInput[i]) / divider; // use std instead of this
            }
            row.setInput(normalizedInput);
        }
    }
}