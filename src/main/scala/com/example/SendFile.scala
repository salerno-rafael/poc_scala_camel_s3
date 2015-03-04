package com.example

import org.jets3t.service.impl.rest.httpclient.RestS3Service
import org.jets3t.service.security.AWSCredentials
import org.jets3t.service.model.S3Object
import org.jets3t.service.acl.{ AccessControlList, GroupGrantee, Permission }
import java.io.InputStream

object SendFile extends App {

  def sendFile(fileName: String, classPath:String)  =  store(fileName, getClass.getResourceAsStream(s"$classPath"))
  
  private def store(key: String, inputStream: InputStream, contentType: String = "text/plain") = {

    val awsAccessKey = "acceskey_s3"
    val awsSecretKey = "secretKey_s3"
    val bucketName = "myBucket_s3"
    
    val awsCredentials = new AWSCredentials(awsAccessKey, awsSecretKey)
    val s3Service = new RestS3Service(awsCredentials)
   
    val bucket = s3Service.getOrCreateBucket(bucketName)
    val fileObject = s3Service.putObject(bucket, {

      val acl = s3Service.getBucketAcl(bucket)
      acl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_FULL_CONTROL)

      val tempObj = new S3Object(key)
      tempObj.setDataInputStream(inputStream)
      tempObj.setAcl(acl)
      tempObj.setContentType(contentType)
      tempObj
    })

    s3Service.createUnsignedObjectUrl(bucketName, fileObject.getKey, false, false, false)

    s3Service.listObjects(bucketName).map { println(_) }

  }
}