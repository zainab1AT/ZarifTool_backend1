package com.project.physio_backend.Services.Prize;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Prize.Prize;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Exceptions.Problems.ProblemNotFound;
import com.project.physio_backend.Exceptions.Prize.PrizeNotFoundException;
import com.project.physio_backend.Exceptions.Users.UserNotFoundException;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Repositories.PrizeRepository;
import com.project.physio_backend.Repositories.UserRepository;

@Service
public class PrizeServiceImpl implements PrizeService {
  @Autowired
  private ProblemRepository problemRepository;

  @Autowired
  private PrizeRepository PrizeRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<Prize> getAllPrizees() {
    return PrizeRepository.findAll();
  }

  @Override
  public Prize getPrizeById(Long id) {
    return PrizeRepository.findById(id)
        .orElseThrow(() -> new PrizeNotFoundException(id));
  }

  @Override
  public Prize createPrize(Long userID, Long problemID, Prize Prize) {

    Problem problem = problemRepository.findById(problemID)
        .orElseThrow(() -> new ProblemNotFound("Problem not found with id " + problemID));

    User user = userRepository.findById(userID)
        .orElseThrow(() -> new UserNotFoundException(userID));

    Prize.setDay(LocalDate.now());
    Prize.setMonth(LocalDate.now());

    Prize.setProblem(problem);
    Prize.setUser(user);
    // problem.addPrize(Prize);
    // user.addPrize(Prize);
    return PrizeRepository.save(Prize);
  }

  @Override
  public Prize updatePrize(Long id, Prize Prize) {
    Prize existingPrize = getPrizeById(id);
    // existingPrize.setTimestamp(Prize.getTimestamp());
    existingPrize.setPrizeDescriprion(Prize.getPrizeDescriprion());
    return PrizeRepository.save(existingPrize);
  }

  @Override
  public void deletePrize(Long id) {
    Prize Prize = getPrizeById(id);
    PrizeRepository.delete(Prize);
  }

  @Override
  public List<Prize> getPrizesForUser(Long userID) {
    User user = userRepository.findById(userID)
        .orElseThrow(() -> new UserNotFoundException(userID));
    return user.getPrize();
  }

}
