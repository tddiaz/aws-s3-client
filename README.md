# aws-s3-client
a java library that provides simple interface for uploading and retrieving files from specified amazon s3 bucket.

####Initializing the S3 Client

     var s3Client = S3ClientFactory.create(new S3Bucket("name", "folder-path"), 
                    AmazonS3ClientBuilder.builder()
                        .awsCredentials(new AWSCredentials("key", "secret"))
                        .build()
                    );

