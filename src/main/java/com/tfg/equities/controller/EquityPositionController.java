package com.tfg.equities.controller;

import com.tfg.equities.model.dto.EquityPositionDTO;
import com.tfg.equities.services.EquityPositionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("equity-position")
public class EquityPositionController {

    private final EquityPositionService equityPositionService;

    public EquityPositionController(EquityPositionService equityPositionService) {
        this.equityPositionService = equityPositionService;
    }

    /**
     * Gets all equity positions traded so far
     *
     * @return: List of EquityPositionDTO
     */
    @GetMapping("/all")
    public List<EquityPositionDTO> getALLPositions() {
        return equityPositionService.getALLPositions();
    }

    /**
     * Gets all equity positions by tradeId
     *
     * @param tradeId
     * @return List of EquityPositionDTO
     */

    @GetMapping("/all/{tradeId}")
    public List<EquityPositionDTO> getALLPositionsByTradeId(@PathVariable Long tradeId) {
        return equityPositionService.getALLPositionsByTradeId(tradeId);
    }

    /**
     * @return: EquityPositionDTO
     */
    @PostMapping("/insert")
    public EquityPositionDTO insertPosition(@RequestBody EquityPositionDTO position) {
        return equityPositionService.insertPosition(position);
    }

    /**
     * @return: EquityPositionDTO
     */
    @PutMapping("/update")
    public EquityPositionDTO updatePosition(@RequestBody EquityPositionDTO position) throws Exception {
        return equityPositionService.updatePosition(position);
    }

    /**
     * @return: EquityPositionDTO
     */
    @PutMapping("/cancel")
    public EquityPositionDTO cancelPosition(@RequestBody EquityPositionDTO position) throws Exception {
        return equityPositionService.cancelPosition(position);
    }


}
