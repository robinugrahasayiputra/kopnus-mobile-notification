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
import com.kopnus.mobile.notification.entity.FirebaseNotificationEntity;
import com.kopnus.mobile.notification.otherenum.NotificationTypeEnum;
import com.kopnus.mobile.notification.repository.FirebaseNotificationRepository;

@ExtendWith(MockitoExtension.class)
public class SendFirebaseServiceImplTest {

    @Mock
    private FirebaseNotificationRepository firebaseNotificationRepository;

    @InjectMocks
    private SendFirebaseServiceImpl service;

    private NotificationRequest request;
    
    @BeforeEach
    void setUp() {
        request = new NotificationRequest();
        request.setDeviceToken("device-token-123");
        request.setMessage("message");
        request.setSubject("subject");
        request.setUserId(1);
    }

    @Test
    void firebaseTypeTest() {
        NotificationTypeEnum result = service.getType();

        assertEquals(NotificationTypeEnum.FIREBASE, result);
    }
    
    @Test
    void deviceTokenValidTest() {
        service.send(request);

        ArgumentCaptor<FirebaseNotificationEntity> captor =
                ArgumentCaptor.forClass(FirebaseNotificationEntity.class);

        verify(firebaseNotificationRepository, times(1)).save(captor.capture());

        FirebaseNotificationEntity entity = captor.getValue();

        assertEquals(request.getDeviceToken(), entity.getDeviceToken());
        assertEquals(request.getMessage(), entity.getMessage());
        assertEquals(request.getSubject(), entity.getSubject());
        assertEquals(request.getUserId(), entity.getUserId());
        assertEquals("FIREBASE SERVICE", entity.getCreatedBy());
        assertTrue(entity.isValid());
    }
    
    @Test
    void deviceTokenNullTest() {
        request.setDeviceToken(null);
        service.send(request);
        ArgumentCaptor<FirebaseNotificationEntity> captor =
                ArgumentCaptor.forClass(FirebaseNotificationEntity.class);

        verify(firebaseNotificationRepository).save(captor.capture());

        FirebaseNotificationEntity entity = captor.getValue();
        assertFalse(entity.isValid());
    }
    
    @Test
    void deviceTokenEmptyTest() {
        request.setDeviceToken("");
        service.send(request);

        ArgumentCaptor<FirebaseNotificationEntity> captor =
                ArgumentCaptor.forClass(FirebaseNotificationEntity.class);

        verify(firebaseNotificationRepository).save(captor.capture());

        FirebaseNotificationEntity entity = captor.getValue();
        assertFalse(entity.isValid());
    }
    
    @Test
    void alwaysSaveNotificationTest() {
        service.send(request);

        verify(firebaseNotificationRepository, times(1)).save(org.mockito.ArgumentMatchers.any());
    }
}
