package dk.dataforsyningen.vanda_hydrometry_data.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	
	@Autowired
	VandahDmpApiService vandahService;
	
	@Override
	public void getData() {
		DmpHydroApiResponsesExaminationTypeResponse[] types = vandahService.getExaminationTypes();
		log.info("count types: " + types.length);
		log.info("1st type: " + types[0].toString());
	}

	@Override
	public void mapData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveData() {
		// TODO Auto-generated method stub

	}
}
