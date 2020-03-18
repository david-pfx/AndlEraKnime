package org.andl.ra.rename;

import org.knime.core.data.DataTableSpec;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentString;
import org.knime.core.node.defaultnodesettings.DialogComponentStringSelection;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.core.node.defaultnodesettings.SettingsModelStringArray;

/**
 * <code>NodeDialog</code> for the "RaRename" node.
 * 
 * @author David Bennett -- Andl
 */
public class RaRenameNodeDialog extends DefaultNodeSettingsPane {
	
	SettingsModelString _oldColName = new SettingsModelString("old-name", "");
	SettingsModelString _newColName = new SettingsModelString("new-name", "");

    /**
     * New pane for configuring the RaRename node.
     * @throws NotConfigurableException 
     */
    protected RaRenameNodeDialog() {
		addDialogComponent(new DialogComponentStringSelection(_oldColName, "Attribute to rename",
				RaRenameNodeModel.getColumNames()));
		addDialogComponent(new DialogComponentString(_newColName, "New attribute name"));
    }
    
    // deconstruct loaded string array values
    @Override
	public void loadSettingsFrom(NodeSettingsRO settings, DataTableSpec[] specs) throws NotConfigurableException {
    	if (specs == null || specs[0] == null || specs[0].getNumColumns() == 0)
            throw new NotConfigurableException("No attributes available for selection.");

    	SettingsModelStringArray oldcolsettings = RaRenameNodeModel.createSettingsOldColumnNames();
    	try {
    		oldcolsettings.loadSettingsFrom(settings);
    		String[] oldcols = oldcolsettings.getStringArrayValue();
    		_oldColName = new SettingsModelString("old", oldcols.length > 0 ? oldcols[0] : "");
    	} catch(InvalidSettingsException e) {
    		_oldColName = new SettingsModelString("old", "");
    	}

    	SettingsModelStringArray newcolsettings = RaRenameNodeModel.createSettingsNewColumnNames();
    	try {
    		newcolsettings.loadSettingsFrom(settings);
    		String[] newcols = newcolsettings.getStringArrayValue();
    		_newColName = new SettingsModelString("new", newcols.length > 0 ? newcols[0] : "");
    	} catch(InvalidSettingsException e) {
    		_newColName = new SettingsModelString("new", "");
    	}

//		String[] newcols = RaRenameNodeModel.createSettingsNewColumnNames().getStringArrayValue();
//		_newColName = new SettingsModelString("new", newcols.length > 0 ? newcols[0] : "");
//		_newColName.loadSettingsFrom(settings);
    }    

    // construct and save string array values
    @Override
	public void saveAdditionalSettingsTo(final NodeSettingsWO settings) throws InvalidSettingsException {

    	SettingsModelStringArray oldcols = RaRenameNodeModel.createSettingsOldColumnNames();
    	oldcols.setStringArrayValue(new String[] { _oldColName.getStringValue() });
		oldcols.saveSettingsTo(settings);
		
		SettingsModelStringArray newcols = RaRenameNodeModel.createSettingsNewColumnNames();
    	newcols.setStringArrayValue(new String[] { _newColName.getStringValue() });
    	newcols.saveSettingsTo(settings);
    }
    
}

