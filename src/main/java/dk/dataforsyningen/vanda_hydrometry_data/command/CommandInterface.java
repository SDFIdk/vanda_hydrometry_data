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
	 * @return number of read items
	 */
	public int getData();
	
	/**
	 * Map data to local model
	 */
	public void mapData();
	
	/**
	 * Save data to DB.
	 * @return number of saved items
	 */
	public int saveData();
	
	/**
	 * Display data on the console
	 * @param raw if true the retrieved aPI results are displayed otherwise the mapped data
	 */
	public void displayData(boolean raw);
	
	/**
	 * Shows command short description
	 */
	public void showShortHelp();
	
	/**
	 * Shows command description and usage
	 */
	public void showHelp();
}
