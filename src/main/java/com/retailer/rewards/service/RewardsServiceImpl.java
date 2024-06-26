package com.retailer.rewards.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.rewards.constants.RewardConstants;
import com.retailer.rewards.entity.Transaction;
import com.retailer.rewards.model.Rewards;
import com.retailer.rewards.repository.TransactionRepository;

@Service
public class RewardsServiceImpl implements RewardsService {

	
	@Autowired
	TransactionRepository transactionRepository;

	public Rewards getRewardsByCustomerId(Long customerId) {

		Timestamp lastMonthTimestamp = getDateBasedOnOffSetDays(RewardConstants.daysInMonths);
		Timestamp lastSecondMonthTimestamp = getDateBasedOnOffSetDays(RewardConstants.twoPointRewards*RewardConstants.daysInMonths);
		Timestamp lastThirdMonthTimestamp = getDateBasedOnOffSetDays(RewardConstants.threePointRewards*RewardConstants.daysInMonths);

		List<Transaction> lastMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(
				customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
		List<Transaction> lastSecondMonthTransactions = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
		List<Transaction> lastThirdMonthTransactions = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp,
						lastSecondMonthTimestamp);

		Long lastMonthRewardPoints = getRewardsPerMonth(lastMonthTransactions);
		Long lastSecondMonthRewardPoints = getRewardsPerMonth(lastSecondMonthTransactions);
		Long lastThirdMonthRewardPoints = getRewardsPerMonth(lastThirdMonthTransactions);

		Rewards customerRewards = new Rewards();
		customerRewards.setCustomerId(customerId);
		customerRewards.setLastMonthRewardPoints(lastMonthRewardPoints);
		customerRewards.setLastSecondMonthRewardPoints(lastSecondMonthRewardPoints);
		customerRewards.setLastThirdMonthRewardPoints(lastThirdMonthRewardPoints);
		customerRewards.setTotalRewards(lastMonthRewardPoints + lastSecondMonthRewardPoints + lastThirdMonthRewardPoints);

		return customerRewards;

	}

	private Long getRewardsPerMonth(List<Transaction> transactions) {
		return transactions.stream().map(transaction -> calculateRewards(transaction))
				.collect(Collectors.summingLong(r -> r.longValue()));
	}

	private Long calculateRewards(Transaction t) {
		if (t.getTransactionAmount() > RewardConstants.firstRewardLimit && t.getTransactionAmount() <= RewardConstants.secondRewardLimit) {
			return Math.round(t.getTransactionAmount() - RewardConstants.firstRewardLimit);
		} else if (t.getTransactionAmount() > RewardConstants.secondRewardLimit) {
			return Math.round(t.getTransactionAmount() - RewardConstants.secondRewardLimit) * RewardConstants.twoPointRewards
					+ (RewardConstants.secondRewardLimit - RewardConstants.firstRewardLimit);
		} else
			return 0l;

	}

	public Timestamp getDateBasedOnOffSetDays(int days) {
		return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
	}

}