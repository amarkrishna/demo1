/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magpie.attributes.expansion;

import magpie.data.Dataset;
import magpie.utility.interfaces.Options;

/**
 * This class is a template for operations that expand the number of available 
 *  attributes based on what is already in the dataset
 * @author Logan Ward
 * @version 0.1
 */
abstract public class BaseAttributeExpander implements Options {
    /**
     * Generate new attributes based on the attributes already in Dataset
     * @param Data Dataset to be expanded
     */
    abstract public void expand(Dataset Data);
}
