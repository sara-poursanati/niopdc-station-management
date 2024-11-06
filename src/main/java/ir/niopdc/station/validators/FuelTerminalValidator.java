package ir.niopdc.station.validators;

import ir.niopdc.domain.fuelterminal.FuelTerminal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class FuelTerminalValidator {

    public static BindingResult createUpdateValidator(FuelTerminal theFuelTerminal, BindingResult result) {
        // checking validations
        if (theFuelTerminal.getId().getTerminalId().isEmpty()) {
            result.addError(new FieldError("theFuelStation", "tableId", "The Id is required."));
        }
        if(theFuelTerminal.getId().getStationId().isEmpty()) {
            result.addError(new FieldError("theFuelTerminal", "fuelStationId", "The Fuel Station Id is required"));
        }

        return result;
    }

}
