package ir.niopdc.station.controller;

import ir.niopdc.domain.mediagateway.MediaGateway;
import ir.niopdc.domain.mediagateway.MediaGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/media-gateway")
@RequiredArgsConstructor
public class MediaGatewayController {

    private final MediaGatewayService mediaGatewayService;


    @GetMapping("/")
    public List<MediaGateway> getAll() {
        return mediaGatewayService.findAll();
    }

    @PostMapping("/creat")
    public MediaGateway createMediaGateway(@RequestBody MediaGateway mediaGateway) {
        return mediaGatewayService.save(mediaGateway);
    }

    @DeleteMapping("/delete")
    public void deleteMediaGateway(@RequestParam String serialNumber) {
        mediaGatewayService.deleteById(serialNumber);
    }

}
