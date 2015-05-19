package com.bank.mvc.domain.service;

import com.bank.mvc.models.Credit;

import java.util.Collection;

/**
 * Created by Zalman on 19.05.2015.
 */
public interface CreditService {

    public Collection<Credit> getAllCredits();

    public Credit getCreditById(long creditId);

    public void saveCredit(Credit credit);

    public void deleteCredit(Credit credit);
}
