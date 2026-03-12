import http from 'k6/http';
import { check } from 'k6';

export const options = {
  scenarios: {
    notification_load: {
      executor: 'ramping-arrival-rate',

      startRate: 100,
      timeUnit: '1s',

      preAllocatedVUs: 1500,
      maxVUs: 4000,

      stages: [
        { target: 1000, duration: '2m' },
        { target: 1000, duration: '7m' },
        { target: 0, duration: '1m' }
      ]
    }
  },

  thresholds: {
    http_req_duration: ['p(95)<1000'],
    http_req_failed: ['rate<0.01']
  }
};

const url = 'http://localhost:8081/notification/send-bulk';

const payload = JSON.stringify([
  {
    type: "EMAIL",
    userId: 1001,
    subject: "Email Notification",
    message: "You receive an email notification",
    receiver: "test@mail",
    deviceToken: null
  },
  {
    type: "SMS",
    userId: 1001,
    subject: "SMS Notification",
    message: "You receive a sms notification",
    receiver: "000000000",
    deviceToken: null
  },
  {
    type: "FIREBASE",
    userId: 1001,
    subject: "Push Notification",
    message: "You receive a push notification",
    receiver: null,
    deviceToken: "test-token"
  }
]);

const params = {
  headers: {
    'Content-Type': 'application/json'
  }
};

export default function () {

  const res = http.post(url, payload, params);

  check(res, {
    'status is 200': (r) => r.status === 200
  });

}