package com.wibmo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dto.CardDTO;
import com.wibmo.entity.Card;
import com.wibmo.repository.CardRepository;

/**
 * 
 */
@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CardRepository cardRepository;
	
	@Override
	public Boolean verfiy(CardDTO cardDTO) {
		Optional<Card> cardOptional = cardRepository
				.findByCardNumberAndExpiryMonthAndExpiryYearAndCvv(
						cardDTO.getCardNumber(), 
						cardDTO.getExpiryMonth(), 
						cardDTO.getExpiryYear(), 
						cardDTO.getCvv());
		return cardOptional.isPresent();
	}
}
