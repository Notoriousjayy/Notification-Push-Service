package com.example.NotificationPushService;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;

import java.util.List;

public class NotificationListener {
    private AmazonSQS sqsClient;
    private String queueUrl; // SQS queue URL

    public NotificationListener(String accessKey, String secretKey, String queueUrl) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        sqsClient = AmazonSQSClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        this.queueUrl = queueUrl;
    }

    public void startListening() {
        while (true) {
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl)
                    .withWaitTimeSeconds(10)
                    .withMaxNumberOfMessages(10);

            List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();

            for (Message message : messages) {
                // Process the incoming notification
                String notificationMessage = message.getBody();
                System.out.println("Received Notification: " + notificationMessage);

                // Delete the message from the queue
                sqsClient.deleteMessage(queueUrl, message.getReceiptHandle());
            }
        }
    }
}

