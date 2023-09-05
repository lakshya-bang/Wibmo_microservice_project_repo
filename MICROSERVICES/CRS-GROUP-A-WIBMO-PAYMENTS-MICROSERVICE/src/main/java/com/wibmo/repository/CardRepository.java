package com.wibmo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Card;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer>{
	/**
	 * This method is to find card Details
	 * @param cardNumber card Number of debit/credit card
	 * @param expiryMonth expiry month mentioned on card
	 * @param expiryYear expiry year mentioned on card
	 * @param cvv three digit cvv
	 * @return Optional card object or null
	 */
	Optional<Card> findByCardNumberAndExpiryMonthAndExpiryYearAndCvv(
			Long cardNumber,
			Integer expiryMonth,
			Integer expiryYear,
			Integer cvv);

}
