package com.inatel.quotationmanagement.service.validation;

import com.inatel.quotationmanagement.adapter.StockAdapter;
import com.inatel.quotationmanagement.exception.StockNotFoundException;
import com.inatel.quotationmanagement.model.entities.StockQuote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class DefaultStockValidatorTest {

    private StockAdapter stockAdapter;
    private DefaultStockValidator stockValidator;

    @BeforeEach
    public void setUp() {
        stockAdapter = Mockito.mock(StockAdapter.class);
        stockValidator = new DefaultStockValidator(stockAdapter);
    }

    @Test
    void givenStockNotFound_whenIsValidCalled_thenThrowStockNotFoundException() {
        Mockito.when(stockAdapter.getAllStock()).thenReturn(Collections.emptyList());

        StockQuote stockQuote = new StockQuote();
        stockQuote.setStockId("stockId");

        assertThrows(StockNotFoundException.class, () -> stockValidator.isValid(stockQuote));
    }
}