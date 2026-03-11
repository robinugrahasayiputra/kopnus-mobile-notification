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
import com.kopnus.mobile.notification.entity.EmailNotificationEntity;
import com.kopnus.mobile.notification.otherenum.NotificationTypeEnum;
import com.kopnus.mobile.notification.repository.EmailNotificationRepository;

@ExtendWith(MockitoExtension.class)
public class SendEmailServiceImplTest {

    @Mock
    private EmailNotificationRepository emailNotificationRepository;

    @InjectMocks
    private SendEmailServiceImpl service;

    private NotificationRequest request;
    
    @BeforeEach
    void setUp() {
        request = new NotificationRequest();
        request.setReceiver("rob@mail.com");
        request.setMessage("message");
        request.setSubject("subject");
        request.setUserId(1);
    }

    @Test
    void emailTypeTest() {
        NotificationTypeEnum result = service.getType();

        assertEquals(NotificationTypeEnum.EMAIL, result);
    }
    
    @Test
    void receiverValidTest() {
        service.send(request);

        ArgumentCaptor<EmailNotificationEntity> captor =
                ArgumentCaptor.forClass(EmailNotificationEntity.class);

        verify(emailNotificationRepository, times(1)).save(captor.capture());

        EmailNotificationEntity entity = captor.getValue();

        assertEquals(request.getReceiver(), entity.getReceiver());
        assertEquals(request.getMessage(), entity.getMessage());
        assertEquals(request.getSubject(), entity.getSubject());
        assertEquals(request.getUserId(), entity.getUserId());
        assertEquals("EMAIL SERVICE", entity.getCreatedBy());
        assertTrue(entity.isValid());
    }
    
    @Test
    void receiverNullTest() {
        request.setReceiver(null);
        service.send(request);
        ArgumentCaptor<EmailNotificationEntity> captor =
                ArgumentCaptor.forClass(EmailNotificationEntity.class);

        verify(emailNotificationRepository).save(captor.capture());

        EmailNotificationEntity entity = captor.getValue();
        assertFalse(entity.isValid());
    }
    
    @Test
    void receiverEmptyTest() {
        request.setReceiver("");
        service.send(request);

        ArgumentCaptor<EmailNotificationEntity> captor =
                ArgumentCaptor.forClass(EmailNotificationEntity.class);

        verify(emailNotificationRepository).save(captor.capture());

        EmailNotificationEntity entity = captor.getValue();
        assertFalse(entity.isValid());
    }
    
    @Test
    void alwaysSaveNotificationTest() {
        service.send(request);

        verify(emailNotificationRepository, times(1)).save(org.mockito.ArgumentMatchers.any());
    }
}
