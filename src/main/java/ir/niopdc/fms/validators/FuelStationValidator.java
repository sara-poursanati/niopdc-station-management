package ir.niopdc.fms.validators;

import ir.niopdc.fms.entity.dto.FuelStationCreateUpdateDTO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class FuelStationValidator {

    public static BindingResult createUpdateValidator(FuelStationCreateUpdateDTO theFuelStation, BindingResult result) {
        // checking validations
        if (theFuelStation.getId().isEmpty()) {
            result.addError(new FieldError("theFuelStation", "id", "The Id is required."));
        }
        if(theFuelStation.getName().isEmpty()) {
            result.addError(new FieldError("theFuelStation", "name", "The Name is required"));
        }
        if(theFuelStation.getInitCount() < 0 || theFuelStation.getInitCount() > 5) {
            result.addError(new FieldError(
                    "theFuelStation", "initCount", "The init Count should be equal greater than zero and equal less than 5")
            );
        }

        return result;
    }

}
