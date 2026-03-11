package com.kopnus.mobile.notification.service.sender.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kopnus.mobile.notification.dto.NotificationRequest;
import com.kopnus.mobile.notification.entity.SmsNotificationEntity;
import com.kopnus.mobile.notification.otherenum.NotificationTypeEnum;
import com.kopnus.mobile.notification.repository.SmsNotificationRepository;

@ExtendWith(MockitoExtension.class)
public class SendSmsServiceImplTest {

    @Mock
    private SmsNotificationRepository smsNotificationRepository;

    @InjectMocks
    private SendSmsServiceImpl service;

    private NotificationRequest request;
    
    @BeforeEach
    void setUp() {
        request = new NotificationRequest();
        request.setReceiver("081378973737");
        request.setMessage("message");
        request.setSubject("subject");
        request.setUserId(1);
    }

    @Test
    void smsTypeTest() {
        NotificationTypeEnum result = service.getType();

        assertEquals(NotificationTypeEnum.SMS, result);
    }
    
    @Test
    void receiverValidTest() {
        service.send(request);

        ArgumentCaptor<SmsNotificationEntity> captor =
                ArgumentCaptor.forClass(SmsNotificationEntity.class);

        verify(smsNotificationRepository, times(1)).save(captor.capture());

        SmsNotificationEntity entity = captor.getValue();

        assertEquals(request.getReceiver(), entity.getReceiver());
        assertEquals(request.getMessage(), entity.getMessage());
        assertEquals(request.getSubject(), entity.getSubject());
        assertEquals(request.getUserId(), entity.getUserId());
        assertEquals("SMS SERVICE", entity.getCreatedBy());
        assertTrue(entity.isValid());
    }
    
    @Test
    void receiverNullTest() {
        request.setReceiver(null);
        service.send(request);
        ArgumentCaptor<SmsNotificationEntity> captor =
                ArgumentCaptor.forClass(SmsNotificationEntity.class);

        verify(smsNotificationRepository).save(captor.capture());

        SmsNotificationEntity entity = captor.getValue();
        assertFalse(entity.isValid());
    }
    
    @Test
    void receiverEmptyTest() {
        request.setReceiver("");
        service.send(request);

        ArgumentCaptor<SmsNotificationEntity> captor =
                ArgumentCaptor.forClass(SmsNotificationEntity.class);

        verify(smsNotificationRepository).save(captor.capture());

        SmsNotificationEntity entity = captor.getValue();
        assertFalse(entity.isValid());
    }
    
    @Test
    void alwaysSaveNotificationTest() {
        service.send(request);

        verify(smsNotificationRepository, times(1)).save(org.mockito.ArgumentMatchers.any());
    }
}
