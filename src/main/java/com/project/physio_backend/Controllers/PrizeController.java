package com.project.physio_backend.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.project.physio_backend.Entities.Prize.Prize;
import com.project.physio_backend.Services.Prize.PrizeService;

@RestController
@RequestMapping("/api/prizes")
@CrossOrigin
public class PrizeController {

  @Autowired
  private PrizeService prizeService;

  @GetMapping
  public List<Prize> getAllPrizees() {
    return prizeService.getAllPrizees();
  }

  @GetMapping("/{id}")
  public Prize getPrizeById(@PathVariable Long id) {
    return prizeService.getPrizeById(id);
  }

  @PostMapping("/{userID}/{problemID}")
  @ResponseStatus(HttpStatus.CREATED)
  public Prize createprize(@PathVariable Long userID, @PathVariable Long problemID, @RequestBody Prize prize) {
    return prizeService.createPrize(userID, problemID, prize);
  }

  @PutMapping("/{id}")
  public Prize updateprize(@PathVariable Long id, @RequestBody Prize prize) {
    return prizeService.updatePrize(id, prize);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePrize(@PathVariable Long id) {
    prizeService.deletePrize(id);
  }

  @GetMapping("/user/{userID}")
  public List<Prize> getPrizesForUser(@PathVariable Long userID) {
    return prizeService.getPrizesForUser(userID);
  }

}
