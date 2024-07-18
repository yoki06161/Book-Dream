package com.bookdream.sbb.trade;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public Page<Trade> getList(int page, String kw) {
        Pageable pageable = PageRequest.of(page, 10);
        if (!kw.isEmpty()) {
            return tradeRepository.findAllByKeyword(kw, pageable);
        } else {
            return tradeRepository.findAllByOrderByPostdateDesc(pageable);
        }
    }

    public Trade getTradeById(int idx) {
        Optional<Trade> trade = tradeRepository.findById(idx);
        return trade.orElse(null);
    }

    @Transactional
    public void createTrade(Trade trade) {
        if (trade.getPostdate() == null) {
            trade.setPostdate(LocalDateTime.now());
        }
        try {
            tradeRepository.save(trade);
            System.out.println("Trade saved successfully: " + trade);
        } catch (Exception e) {
            System.out.println("Error while saving trade: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Trade updateTrade(int idx, Trade updatedTrade) {
        Optional<Trade> optionalTrade = tradeRepository.findById(idx);
        if (optionalTrade.isPresent()) {
            Trade trade = optionalTrade.get();
            trade.setTitle(updatedTrade.getTitle());
            trade.setPrice(updatedTrade.getPrice());
            trade.setInfo(updatedTrade.getInfo());
            trade.setIntro(updatedTrade.getIntro());
            trade.setImage(updatedTrade.getImage());
            trade.setOriginalPrice(updatedTrade.getOriginalPrice()); // 정가 업데이트
            try {
                return tradeRepository.save(trade);
            } catch (Exception e) {
                System.out.println("Error while updating trade: " + e.getMessage());
                throw e;
            }
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteTrade(int idx) {
        try {
            tradeRepository.deleteById(idx);
            System.out.println("Trade deleted successfully: " + idx);
        } catch (Exception e) {
            System.out.println("Error while deleting trade: " + e.getMessage());
            throw e;
        }
    }
    
    public Trade getTradeByTitle(String title) {
        return tradeRepository.findByTitle(title);
    }
}