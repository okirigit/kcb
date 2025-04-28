package com.okiri_george.kcb;

import com.okiri_george.kcb.dto.*;
import com.okiri_george.kcb.service.*;
import com.okiri_george.kcb.repository.*;
import com.okiri_george.kcb.utils.RestTemplateHelper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
class KcbApplicationTests {

	@InjectMocks
	private MobileMoneyServiceImpl mobileMoneyService;

	@Mock
	private RestTemplateHelper restTemplateHelper;

	@Mock
	private NotificationServiceImpl notificationService;

	@Mock
	private B2CTransactionRepository b2CTransactionRepository;

	@Mock
	private B2CResponseRepository b2CResponseRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testProcessTransaction_Success() {
		// Given
		B2CRequest request = new B2CRequest();
		B2CResponse expectedResponse = new B2CResponse();
		expectedResponse.setSuccess(true);
		expectedResponse.setMessage("Success");

		// Mock RestTemplateHelper to simulate successful transaction
		//when(restTemplateHelper.postB2CRequest(anyString(), any(), anyString())).thenReturn(false);

		// Mock repository save behavior
		when(b2CTransactionRepository.save(any())).thenReturn(request);
		when(b2CResponseRepository.save(any())).thenReturn(expectedResponse);

		// When
		B2CResponse response = mobileMoneyService.processTransaction(request);

		// Then
		assertTrue(response.isSuccess());
		assertEquals("Success", response.getMessage());
//		verify(b2CTransactionRepository, times(1)).save(request);
//		verify(b2CResponseRepository, times(1)).save(expectedResponse);
//

	}

	@Test
	public void testProcessTransaction_Failure() {
		// Given
		B2CRequest request = new B2CRequest();
		B2CResponse expectedResponse = new B2CResponse();
		expectedResponse.setSuccess(false);
		expectedResponse.setMessage("Error");

		// Mock RestTemplateHelper to simulate failure
		when(restTemplateHelper.postB2CRequest(anyString(), any(), anyString())).thenThrow(new RuntimeException("Network error"));

		// When
		B2CResponse response = mobileMoneyService.processTransaction(request);

		// Then
		assertFalse(response.isSuccess());
		assertEquals("Error", response.getMessage());
//		verify(b2CTransactionRepository, times(1)).save(request);
//		verify(b2CResponseRepository, times(1)).save(expectedResponse);
//
	}

}
