package ir.niopdc.station.validators;

import ir.niopdc.domain.fuelstation.FuelStation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class FuelStationValidator {

    public static final String STATION_KEY = "theFuelStation";

    private FuelStationValidator(){}

    public static BindingResult createUpdateValidator(FuelStation theFuelStation, BindingResult result) {
        // checking validations
        if (theFuelStation.getId().isEmpty()) {
            result.addError(new FieldError(STATION_KEY, "id", "The Id is required."));
        }
        if(theFuelStation.getName().isEmpty()) {
            result.addError(new FieldError(STATION_KEY, "name", "The Name is required"));
        }
        if(theFuelStation.getInitCount() < 0 || theFuelStation.getInitCount() > 5) {
            result.addError(new FieldError(
                    STATION_KEY, "initCount", "The init Count should be equal greater than zero and equal less than 5")
            );
        }

        return result;
    }

}
