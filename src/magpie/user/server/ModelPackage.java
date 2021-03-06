package magpie.user.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import magpie.data.Dataset;
import magpie.models.BaseModel;
import magpie.models.classification.AbstractClassifier;
import magpie.user.server.thrift.ModelInfo;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Holds information about a model.
 * @author Logan Ward
 */
public class ModelPackage {
    /** Dataset used to generate attributes */
    final public Dataset Dataset;
    /** Model to be evaluated */
    final public BaseModel Model;
    /** Name of property being modeled. HTML format suggested */
    public String Property = "Unspecified";
    /** Units for property */
    public String Units  = "Unspecified";
    /** Training set description */
    public String TrainingSet  = "Unspecified";
    /** Author of this model */
    public String Author = "Unspecified";
    /** Citation for this model */
    public String Citation = "Unspecified";
    /** Short description of this model */
    public String Description = "";
    /** Long form description of model */
    public String Notes;

    /**
     * Initialize model package
     * @param data Dataset used to generate attributes
     * @param model Model to be evaluated
     */
    public ModelPackage(Dataset data, BaseModel model) {
        this.Dataset = data;
        this.Model = model;
    }
    
    /**
     * Generate model info in a format suitable for Thrift interface
     * @return Model info in Thrift format
     */
    public ModelInfo generateInfo() { 
        ModelInfo info = new ModelInfo();
        
        // Store basic info
        info.author = Author;
        info.citation = Citation;
        info.description = Description;
        info.property = Property;
        info.training = Model.TrainingStats.NumberTested + " entries: " + TrainingSet;
        info.notes = Notes;
        info.trainTime = new SimpleDateFormat("dMMMyy HH:mm z").format(Model.getTrainTime());
        
        // Store units or class names
        if (Model instanceof AbstractClassifier) {
            info.classifier = true;
            info.units = "";
            AbstractClassifier clfr = (AbstractClassifier) Model;
            boolean started = false;
            for (String name : clfr.getClassNames()) {
                if (started) {
                    info.units += ";";
                }
                info.units += name;
                started = true;
            }
        } else {
            info.classifier = false;
            info.units = Units;
        }
        
        // Store names of models
        info.dataType = Dataset.printDescription(true);
        info.modelType = Model.printDescription(true);
        
        // Store validation performance data
        info.valMethod = Model.getValidationMethod();
        if (Model.isValidated()) {
            info.valScore = Model.ValidationStats.getStatistics();
        }
   
        return info;
    }
}
