package com.wibmo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Card;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer>{
	
	Optional<Card> findByCardNumberAndExpiryMonthAndExpiryYearAndCvv(
			Long cardNumber,
			Integer expiryMonth,
			Integer expiryYear,
			Integer cvv);

}
