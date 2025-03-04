package com.project.physio_backend.Services.Prize;

import java.util.List;

import com.project.physio_backend.Entities.Prize.Prize;

public interface PrizeService {
    List<Prize> getAllPrizees();

  Prize getPrizeById(Long id);

  Prize createPrize(Long userID, Long problemID, Prize Prize);

  Prize updatePrize(Long id, Prize Prize);

  void deletePrize(Long id);

}
