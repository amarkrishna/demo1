package magpie.data.utilities.splitters;

import java.util.LinkedList;
import java.util.List;
import magpie.data.Dataset;
import magpie.user.CommandHandler;

/**
 * This class splits data into multiple properties based on the attributes of each entry. User 
 * supplies the intervals, which are first sorting into ascending order. Then, the data is 
 * partitioned into bins with these points as edges.
 * 
 * <usage><p><b>Usage</b>: &lt;attribute> &lt;bin edges...>
 * <br><pr><i>attribute</i>: Name of attribute on which to split dataset
 * <br><pr><i>bin edges...</i>: Values on which to split data (i.e. "1" to split into (-Inf, 1) and [1, Inf])</usage>
 * 
 * @author Logan Ward
 * @version 0.1
 */
public class AttributeIntervalSplitter extends BaseDatasetSplitter {
    /** Name of attribute used for split */
    protected String AttributeName;
    /** ID of attribute used for split */
    protected int AttributeID = -1;
    /** Values to use as bin edges */
    protected double[] BinEdges = null;

    @Override
    public AttributeIntervalSplitter clone() {
        AttributeIntervalSplitter x = 
                (AttributeIntervalSplitter) super.clone();
        x.BinEdges = BinEdges.clone();
        return x;
    }

    @Override
    public int[] label(Dataset D) {
        int[] output = new int[D.NEntries()];
        for (int i=0; i<output.length; i++) {
            double x = D.getEntry(i).getAttribute(AttributeID);
            output[i] = 0;
            for (int j=0; j<BinEdges.length; j++)
                if (x >= BinEdges[j]) output[i] = j+1;
        }
        return output;
    }

    @Override
    public void setOptions(List OptionsObj) throws Exception {
        String[] Options = CommandHandler.convertCommandToString(OptionsObj);
        if (Options.length < 2)
            throw new Exception(printUsage());
        AttributeName = Options[0];
        BinEdges = new double[Options.length - 1];
        try {
            for (int i=0; i<BinEdges.length; i++)
                BinEdges[i] = Double.valueOf(Options[i+1]);
        } catch (Exception e) {
            throw new Exception(printUsage());
        }
    }

    @Override
    public String printUsage() {
        return "Usage: <attribute> <bin edges...>";
    }

    /**
     * Define which attribute to use for splitting
     * @param name Name of desired attribute
     */
    public void setAttributeName(String name) {
        this.AttributeName = name;
    }

    /**
     * Define the edges of bins on which to split data.
     * @param binEdges Desired bin edges
     */
    public void setBinEdges(double[] binEdges) {
        this.BinEdges = binEdges.clone();
    }

    @Override
    public void train(Dataset TrainingSet) {
        AttributeID = TrainingSet.getAttributeIndex(AttributeName);
        if (AttributeID == -1) {
            throw new Error("Dataset does not contain feature: "+AttributeName);
        }
    }

    @Override
    protected List<String> getSplitterDetails(boolean htmlFormat) {
        List<String> output = new LinkedList<>();
        
        // Add attribute range
        output.add("Attribute Name: " + AttributeName);
        
        // Print out bin edges
        String line = "Bin Edges:";
        for (double edge : BinEdges) {
            line += String.format(" %.4e", edge);
        }
        output.add(line);
        
        return output;
    }

    @Override
    public List<String> getSplitNames() {
        List<String> splits = new LinkedList<>();
        
        // Split 1: Less than bottom edge
        splits.add(String.format("%s < %.4e", AttributeName, BinEdges[0]));
        
        // Splits 2 - ...: Bins in between
        for (int bin=1; bin<BinEdges.length; bin++) {
            splits.add(String.format("%.4e <= %s < %.4e", BinEdges[bin-1], 
                    AttributeName, BinEdges[bin]));
        }
        
        // Split N: Greater than last edge
        splits.add(String.format("%s >= %.4e", AttributeName, BinEdges[BinEdges.length - 1]));
        
        return splits;
    }
    
}
