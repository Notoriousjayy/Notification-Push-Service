package com.example.NotificationPushService;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class NotificationService {
    private AmazonSNS snsClient;
    private String topicArn; // SNS topic ARN

    public NotificationService(String accessKey, String secretKey, String topicArn) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        snsClient = AmazonSNSClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        this.topicArn = topicArn;
    }

    public void sendNotification(String message) {
        PublishRequest publishRequest = new PublishRequest(topicArn, message);
        PublishResult publishResult = snsClient.publish(publishRequest);
        System.out.println("Message sent with MessageId: " + publishResult.getMessageId());
    }

//    public static void main(String[] args) {
//        String accessKey = "your-access-key";
//        String secretKey = "your-secret-key";
//        String snsTopicArn = "your-sns-topic-arn";
//
//        NotificationService notificationService = new NotificationService(accessKey, secretKey, snsTopicArn);
//
//        // Send a notification
//        notificationService.sendNotification("Hello, World!");
//    }
}

