package dk.dataforsyningen.vanda_hydrometry_data.command;

/**
 * Interface to define the commands provided by this application.
 * The interface defines the command flow/behaviour.
 * 
 * @author Radu Dudici
 */
public interface CommandInterface {

	/**
	 * Read data from API
	 */
	public void getData();
	
	/**
	 * Map data to local model
	 */
	public void mapData();
	
	/**
	 * Save data to DB
	 */
	public void saveData();
	
	/**
	 * Display data on the console
	 */
	public void displayData();
	
	/**
	 * Shows command short description
	 */
	public void showShortHelp();
	
	/**
	 * Shows command description and usage
	 */
	public void showHelp();
}
