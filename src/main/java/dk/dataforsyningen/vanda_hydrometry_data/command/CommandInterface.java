package dk.dataforsyningen.vanda_hydrometry_data.command;

/**
 * Interface to define the commands provided by this application.
 * The interface defines the command flow/behaviour.
 * 
 * @author Radu Dudici
 */
public interface CommandInterface {

	public void getData();
	
	public void mapData();
	
	public void saveData();
}
