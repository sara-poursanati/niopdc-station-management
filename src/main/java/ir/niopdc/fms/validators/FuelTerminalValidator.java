package ir.niopdc.fms.validators;

import ir.niopdc.fms.domain.dto.FuelTerminalCreateUpdateDTO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class FuelTerminalValidator {

    public static BindingResult createUpdateValidator(FuelTerminalCreateUpdateDTO theFuelTerminal, BindingResult result) {
        // checking validations
        if (theFuelTerminal.getId().isEmpty()) {
            result.addError(new FieldError("theFuelStation", "tableId", "The Id is required."));
        }
        if(theFuelTerminal.getFuelStationId().isEmpty()) {
            result.addError(new FieldError("theFuelTerminal", "fuelStationId", "The Fuel Station Id is required"));
        }

        return result;
    }

}
