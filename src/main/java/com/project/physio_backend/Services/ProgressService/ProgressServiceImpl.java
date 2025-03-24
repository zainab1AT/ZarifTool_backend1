package com.project.physio_backend.Services.ProgressService;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Prize.Prize;
import com.project.physio_backend.Entities.Prize.PrizeType;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Exceptions.Problems.ProblemNotFound;
import com.project.physio_backend.Exceptions.Progress.ProgressNotFound;
import com.project.physio_backend.Exceptions.Users.UserNotFoundException;
import com.project.physio_backend.Repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProgressServiceImpl implements ProgressService {

  @Autowired
  private ProblemRepository problemRepository;

  @Autowired
  private ProgressRepository progressRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PrizeRepository prizeRepository;

  @Override
  public List<Progress> getAllProgresses() {
    return progressRepository.findAll();
  }

  @Override
  public Progress getProgressById(Long id) {
    return progressRepository.findById(id)
        .orElseThrow(() -> new ProgressNotFound("Progress not found with id " + id));
  }

  @Override
  public Progress createProgress(Long userID, Long problemID, Progress progress) {
    Problem problem = problemRepository.findById(problemID)
        .orElseThrow(() -> new ProblemNotFound("Problem not found with id " + problemID));

    User user = userRepository.findById(userID)
        .orElseThrow(() -> new UserNotFoundException(userID));

    // Check if user already has a progress entry for today
    LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
    LocalDateTime endOfDay = startOfDay.plusDays(1);

    boolean alreadyExists = progressRepository.existsByUserAndProblemAndTimestampBetween(user, problem, startOfDay,
        endOfDay);
    Optional<Progress> existingProgress = progressRepository.findByUserAndProblemAndTimestampBetween(user, problem,
        startOfDay, endOfDay);

    if (alreadyExists) {
      // throw new IllegalStateException("User already has progress for today.");
      return existingProgress.get();
    }

    Progress newProress = new Progress();
    // Create new progress
    newProress.setTimestamp(LocalDateTime.now());
    newProress.setPercentag(progress.getPercentag());
    newProress.setProblem(problem);
    newProress.setUser(user);
    List<Exercise> problemExercises = problem.getExercises();
    Collections.shuffle(problemExercises);
    List<Exercise> randomExercises = problemExercises.stream()
        .limit(5)
        .collect(Collectors.toList());
    newProress.setExercises(randomExercises);
    progress = progressRepository.save(newProress);
    problem.addProgress(progress);
    user.addProgress(progress);

    // Assign 5 random exercises
    assignPrizeIfEligible(user, problem);

    return progress;
  }

  private void assignPrizeIfEligible(User user, Problem problem) {
    long progressCount = progressRepository.countByUserAndProblem(user, problem);

    PrizeType prizeType = null;
    if (progressCount == 7) {
      prizeType = PrizeType.BRONZE;
    } else if (progressCount == 14) {
      prizeType = PrizeType.SILVER;
    } else if (progressCount == 21) {
      prizeType = PrizeType.GOLD;
    } else if (progressCount == 28) {
      prizeType = PrizeType.PRENIUM;
    }

    if (prizeType != null) {
      Prize prize = new Prize();
      prize.setUser(user);
      prize.setProblem(problem);
      prize.setPrizeType(prizeType);
      prize.setDay(LocalDate.now());
      prize.setMonth(LocalDate.now());

      prizeRepository.save(prize);
    }
  }

  @Override
  public Progress updateProgress(Long id, Progress progress) {
    Progress existingProgress = getProgressById(id);
    existingProgress.setTimestamp(existingProgress.getTimestamp());
    existingProgress.setPercentag(progress.getPercentag());
    return progressRepository.save(existingProgress);
  }

  @Override
  public void deleteProgress(Long id) {
    Progress progress = getProgressById(id);
    progressRepository.delete(progress);
  }

  @Override
  public Progress addProgressPercentage(Long id, double percentage) {
    Progress progress = getProgressById(id);
    progress.setTimestamp(progress.getTimestamp());
    progress.setPercentag(progress.getPercentag() + percentage);
    return progressRepository.save(progress);
  }

  @Override
  public List<Progress> getAllProgressesForAUserInAProblem(Long userID, Long problemID) {
    User user = userRepository.findById(userID)
        .orElseThrow(() -> new UserNotFoundException(userID));

    Problem problem = problemRepository.findById(problemID)
        .orElseThrow(() -> new ProblemNotFound("Problem not found with id " + problemID));

    return progressRepository.findByUserAndProblem(user, problem);
  }

  @Override
  public List<Exercise> getAllExercisesForProgress(Long id) {
    Progress progress = progressRepository.findById(id)
        .orElseThrow(() -> new ProgressNotFound("Progress not found with id " + id));

    return progress.getExercises();
  }

  @Override
  public Boolean progressExistTodayForUser(Long userID) {
    User user = userRepository.findById(userID)
        .orElseThrow(() -> new UserNotFoundException(userID));

    LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
    LocalDateTime endOfDay = startOfDay.plusDays(1);
    return progressRepository.existsByUserAndTimestampBetween(user, startOfDay, endOfDay);
  }

  private LocalDateTime getStartOfWeek() {
    LocalDate today = LocalDate.now();
    LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
    return startOfWeek.atStartOfDay();
  }

  private LocalDateTime getEndOfWeek() {
    LocalDate today = LocalDate.now();
    LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
    return endOfWeek.atTime(LocalTime.MAX);
  }

  @Override
  public List<Progress> getWeeklyProgress(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    LocalDateTime startOfWeek = getStartOfWeek();
    LocalDateTime endOfWeek = getEndOfWeek();

    return progressRepository.findByUserAndTimestampBetween(user, startOfWeek, endOfWeek);
  }

  @Override
  public List<Progress> getProgressesForTodayForUser(Long userID) {
    User user = userRepository.findById(userID)
        .orElseThrow(() -> new UserNotFoundException(userID));

    LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
    LocalDateTime endOfDay = startOfDay.plusDays(1);
    return progressRepository.findByUserAndTimestampBetween(user, startOfDay, endOfDay);
  }

  @Override
  public List<String> progressExistThisMonth(Long userID) {
    User user = userRepository.findById(userID)
        .orElseThrow(() -> new UserNotFoundException(userID));

    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
    LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

    List<java.sql.Date> progressDays = progressRepository
        .findDistinctDatesByUserAndTimestampBetween(user, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59));

    return progressDays.stream()
        .map(date -> date.toLocalDate().toString()) // Convert to LocalDate and then String
        .collect(Collectors.toList());
  }

  @Override
  public List<Progress> getAllProgressesForUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    return user.getProgresses();
  }

}