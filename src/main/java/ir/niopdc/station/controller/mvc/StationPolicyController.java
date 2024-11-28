package ir.niopdc.station.controller.mvc;

import ir.niopdc.common.entity.policy.PolicyEnum;
import ir.niopdc.domain.fuelstationpolicy.FuelStationPolicy;
import ir.niopdc.domain.fuelstationpolicy.FuelStationPolicyKey;
import ir.niopdc.domain.fuelstationpolicy.FuelStationPolicyService;
import ir.niopdc.domain.policy.Policy;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/station-policies")
public class StationPolicyController {

    private static final String POLICY_KEY = "policy";

    private FuelStationPolicyService fuelStationPolicyService;

    @Autowired
    private void setFuelStationPolicy(FuelStationPolicyService fuelStationPolicyService) {
        this.fuelStationPolicyService = fuelStationPolicyService;
    }

    @GetMapping("/list")
    public String showListFuelStation(@RequestParam("stationId") String stationId, Model theModel) {
        // get the fuelStations from DB
        List<FuelStationPolicy> policies = fuelStationPolicyService.findByFuelStationId(stationId);

        theModel.addAttribute("stationId", stationId);

        // add to the spring model
        theModel.addAttribute("policies", policies);

        return "stationPolicies/station-policy-list";
    }

    @GetMapping("/add")
    public String showFormForAdd(@Nullable @RequestParam("stationId") String fuelStationId, Model theModel) {

        // create model attribute to bind form data
        FuelStationPolicy policy = new FuelStationPolicy();

        // create model attribute for id to bind from data
        FuelStationPolicyKey policyKey = new FuelStationPolicyKey();

        PolicyEnum[] choices = PolicyEnum.class.getEnumConstants();

        theModel.addAttribute(POLICY_KEY, policy);

        theModel.addAttribute("policyKey", policyKey);

        theModel.addAttribute("theFuelStationId", fuelStationId);

        theModel.addAttribute("choices", choices);

        return "stationPolicies/station-policy-create";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("policyId") int policyId, @RequestParam("stationId") String stationId) {

        fuelStationPolicyService.deleteByPolicyId(policyId);

        return "redirect:/station-policies/list?stationId=%s".formatted(stationId);
    }

    @PostMapping("/save")
    public String createFuelStationPolicy(
            @Valid @ModelAttribute(POLICY_KEY) FuelStationPolicy policy,
            @ModelAttribute("policyKey") FuelStationPolicyKey policyKey
    ) throws SQLException {

        // set the id
        policy.setId(policyKey);

        // save the new station policy in DB
        fuelStationPolicyService.save(policy);

        // use a redirect to prevent duplicate submissions
        return "redirect:/station-policies/list?stationId=%s".formatted(policy.getId().getFuelStationId());
    }

    @PostMapping("/update")
    public String updateFuelStationPolicy(
            @Valid @ModelAttribute(POLICY_KEY) FuelStationPolicy policy
    )
    {
        // save the changed fuel station in DB
        fuelStationPolicyService.update(policy);

        // use a redirect to prevent duplicate submissions
        return "redirect:/station-policies/list?stationId=%s".formatted(policy.getId().getFuelStationId());
    }

    @GetMapping("/update")
    public String showFormForUpdate(@RequestParam("policyId") int theId, Model theModel) {
        // get the fuel station from svc
        FuelStationPolicy policy = fuelStationPolicyService.findByPolicyId(theId);

        // set fuel station in model to populate the form
        theModel.addAttribute(POLICY_KEY, policy);

        // send over to our form
        return "stationPolicies/station-policy-update";
    }


    @GetMapping("/info")
    public String showInfoPage(@RequestParam("policyId") int policyId, Model theModel) {
        // get the station policy from svc
        FuelStationPolicy policy = fuelStationPolicyService.findByPolicyId(policyId);

        // set station policy in model to provide a ctx
        theModel.addAttribute(POLICY_KEY, policy);

        // send over to our form
        return "stationPolicies/station-policy-info";
    }

}
