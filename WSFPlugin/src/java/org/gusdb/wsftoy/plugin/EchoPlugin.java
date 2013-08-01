/**
 * 
 */
package org.gusdb.wsftoy.plugin;

import java.util.Map;

import org.gusdb.wsf.plugin.AbstractPlugin;
import org.gusdb.wsf.plugin.PluginResponse;
import org.gusdb.wsf.plugin.PluginRequest;
import org.gusdb.wsf.plugin.WsfServiceException;

/**
 * @author Jerric
 * @created Jan 4, 2006
 */
public class EchoPlugin extends AbstractPlugin {

  public static final String PARAM_ECHO = "message";

  public static final String COLUMN_OS_NAME = "OsName";
  public static final String COLUMN_OS_VERSION = "OsVersion";
  public static final String COLUMN_ECHO = "EchoString";

  /**
     * 
     */
  public EchoPlugin() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.gusdb.wsf.plugin.Plugin#getRequiredParameters()
   */
  @Override
  public String[] getRequiredParameterNames() {
    return new String[] { PARAM_ECHO };
  }

  @Override
  public void validateParameters(PluginRequest request) throws WsfServiceException {
    // do nothing
  }

  @Override
  protected String[] defineContextKeys() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.gusdb.wsf.plugin.Plugin#getColumns()
   */
  @Override
  public String[] getColumns() {
    return new String[] { COLUMN_OS_NAME, COLUMN_OS_VERSION, COLUMN_ECHO };
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.gusdb.wsf.WsfPlugin#execute(java.util.Map, java.lang.String[])
   */
  @Override
  public void execute(PluginRequest request, PluginResponse response)
      throws WsfServiceException {
    Map<String, String> params = request.getParams();
    String[] orderedColumns = request.getOrderedColumns();

    // get parameter
    String echo = params.get(PARAM_ECHO);

    // create a time zone and a Calendar
    String osName = System.getProperty("os.name");
    String osVersion = System.getProperty("os.version");

    // prepare the result
    String[] row = new String[orderedColumns.length];
    for (int i = 0; i < orderedColumns.length; i++) {
      if (orderedColumns[i].equalsIgnoreCase(COLUMN_OS_NAME)) {
        row[i] = osName;
      } else if (orderedColumns[i].equalsIgnoreCase(COLUMN_OS_VERSION)) {
        row[i] = osVersion;
      } else if (orderedColumns[i].equalsIgnoreCase(COLUMN_ECHO)) {
        row[i] = echo;
      }
    }
    response.addRow(row);
    response.setMessage(echo);
    response.setSignal(1);
    response.flush();
  }
}
