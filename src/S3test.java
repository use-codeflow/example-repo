package com;

import java.io.ByteArrayInputStream;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class App 
{
    public static String BUCKET_NAME = "cloudp-51864-bucket";
    public static String BUCKET_KEY = "abc123";
    public static void main( String[] args ) throws Exception
    {
        final InstanceProfileCredentialsProvider instanceProfileProvider = new InstanceProfileCredentialsProvider(true);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
              .withCredentials(instanceProfileProvider)
              .build();
        
        createBucket(s3);
        printBucketExists(s3);
        uploadToBucket(s3, new byte[] { (byte)0xd0, (byte)0xe0, 0x4f, 0x20,
            (byte)0xea, (byte)0xd8, 0x3a, 0x69, 0x10, (byte)0xa2 });
        readFromBucket(s3);
        removeFromBucket(s3);
        deleteBucket(s3);

        printBucketExists(s3);

        instanceProfileProvider.close();
    }

    public static void createBucket(final AmazonS3 pS3) {
        System.out.println("Creating bucket");
        pS3.createBucket(BUCKET_NAME);
    }

    public static void printBucketExists(final AmazonS3 pS3) {
        System.out.println(String.format("Bucket exists: %b", pS3.doesBucketExistV2(BUCKET_NAME)));
    }

    public static void uploadToBucket(final AmazonS3 pS3, final byte[] pBytes) {
        final int numBytes = pBytes.length;
        System.out.println(String.format("Uploading %d bytes to bucket", numBytes));
        
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(pBytes.length);
        pS3.putObject(BUCKET_NAME, BUCKET_KEY, new ByteArrayInputStream(pBytes), metadata);
    }

    // Returns number of bytes found in bucket according to metadata
    public static void readFromBucket(final AmazonS3 pS3) throws Exception {
        System.out.println("Reading from bucket");
        final long numBytesRead = pS3.getObject(BUCKET_NAME, BUCKET_KEY).getObjectMetadata().getContentLength();
        System.out.println(String.format("Number of bytes read: %d", numBytesRead));
    }

    public static void removeFromBucket(final AmazonS3 pS3) {
        System.out.println("Removing bytes from bucket");
        pS3.deleteObject(BUCKET_NAME, BUCKET_KEY);
    }

    public static void deleteBucket(final AmazonS3 pS3) {
        System.out.println("Deleting bucket");
        pS3.deleteBucket(BUCKET_NAME);
    }
}
