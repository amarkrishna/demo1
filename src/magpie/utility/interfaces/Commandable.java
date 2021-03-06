
package magpie.utility.interfaces;

import java.util.List;
import magpie.cluster.BaseClusterer;
import magpie.data.Dataset;
import magpie.user.CommandHandler;

/**
 * Provides interface for classes that can handle commands. Utilized by {@linkplain CommandHandler}.
 * 
 * <p>Commands are passed in the form of a list of objects. Under most conditions,
 *  these are simply text strings, but one must ensure that in order to be safe.
 * 
 * <p>Anything can be output by a command. If nothing needs to be output, the function
 *  should simply return <code>null</code>.
 * 
 * <p>Errors during command processing should throw Exceptions.
 * 
 * <p>After you are done adding a command, make sure to document it in the class's 
 * Javadoc (see {@linkplain BaseClusterer} for an example). Format should be:
 * 
 * <p>&lt;command>&lt;p><b>&lt;b>command usage&lt;/b></b> - Description of command
 * <br>&lt;br>&lt;pr><i>&lt;i>option name&lt;/i></i>: Description of option
 * <br>&lt;br>Details about command&lt;/command>
 * 
 * <p>Surround names of a user option with &lt;>'s. Signify that the parameter is optional
 * by surrounding it with []'s. If the parameter must be an object, place a $ in
 * front of the command name. As an example:
 * 
 * <p><b>crossvalidate $&lt;data> [&lt;folds>]</b> - Cross validate a model
 * <br><i>data</i>: {@linkplain Dataset} used during cross-validation
 * <br><i>folds</i>: How many folds to use in validation process (default = 10)
 * 
 * @author Logan Ward
 * @version 0.1
 */
public interface Commandable {
    /**
     * Process some command described by a list of Objects. 
     * @param Command Command as a list of objects
     * @return Any output (null if no output generated by command)
     * @throws java.lang.Exception If something goes wrong
     */
    public Object runCommand(List<Object> Command) throws Exception;
}
