package dk.dataforsyningen.vanda_hydrometry_data.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.dataforsyningen.vanda_hydrometry_data.service.VandahDmpApiService;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesExaminationTypeResponse;

/**
 * Command to retrieve the Examination Types.
 * 
 * @author Radu Dudici
 */
@Component
@CommandQualifier(command = "examinationtypes")
public class ExaminationTypesCommand  implements CommandInterface {
	
	private static final Logger log = LoggerFactory.getLogger(ExaminationTypesCommand.class);
	
	private DmpHydroApiResponsesExaminationTypeResponse[] data = null;
	
	@Autowired
	VandahDmpApiService vandahService;
	
	@Autowired
	private VandaHydrometryDataConfig config;
	
	@Override
	public int getData() {
		data = vandahService.getExaminationTypes();
		return data != null ? data.length : 0;
	}

	@Override
	public void mapData() {
		//nothing to map
	}

	@Override
	public int saveData() {
		VandaHUtility.logAndPrint(log, Level.WARN, false, "Save to DB is not relevant for this data");
		return 0;
	}

	@Override
	public void displayData(boolean raw) {
		if (raw && data != null) {
			VandaHUtility.logAndPrint(null, null, config.isVerbose(), "Number of items: " + data.length);
			for(DmpHydroApiResponsesExaminationTypeResponse item : data) {
				System.out.println(item);
			}
		}
	}

	@Override
	public void showShortHelp() {
		System.out.println(VandaHUtility.BOLD_ON + "examinationTypes" + VandaHUtility.FORMAT_OFF + " : retrieves the examination types with mapping and constraints.");
		
	}

	@Override
	public void showHelp() {
		showShortHelp();
	}
}
