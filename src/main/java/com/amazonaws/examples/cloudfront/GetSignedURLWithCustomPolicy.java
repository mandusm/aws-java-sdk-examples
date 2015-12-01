/**
 * An example showing the use of the getSignedURLWithCustomPolicy(...) method
 * for generating signed CloudFront URLs. The above method uses a custom policy
 * defined by the user to generate the eventual URL.
 * 
 * Related:
 * http://amzn.to/1ThvsUc
 * http://amzn.to/1ThvwDh
 * 
 */
package com.amazonaws.examples.cloudfront;

import java.io.File;
import java.util.Date;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.CloudFrontUrlSigner.Protocol;
import com.amazonaws.util.DateUtils;

public class GetSignedURLWithCustomPolicy {

	public static void main(String[] args) {

		// Build the parameters for generation of the signed URL
		Protocol protocol = Protocol.https;
		String distributionDomain = "d260vhn3o2h8ng.cloudfront.net";
		String s3ObjectKey = "images/Chrysanthemum.jpg";
		
		// Each of the AWS accounts that you use to create CloudFront signed
		// URLs or signed cookies—your trusted signers—must have its own 
		// CloudFront key pair, and the key pair must be active.
		// More about CF certs and keys at: http://amzn.to/1ThvsUc
		File privateKeyFile = new File("c:\\keys\\pk-APKAI46CMRRTBEJHMIRA.pem");
		String keyPairId = "APKAI46CMRRTBEJHMIRA";
		
		Date dateLessThan = DateUtils.parseISO8601Date("2015-12-05T23:59:59.000Z");
		Date dateGreaterThan = DateUtils.parseISO8601Date("2015-12-01T00:00:00.000Z");
		
		String ipRange = "0.0.0.0/0";
		
		try {
			// 
			String urlString = CloudFrontUrlSigner.getSignedURLWithCustomPolicy(
					protocol, distributionDomain, privateKeyFile, s3ObjectKey,
					keyPairId, dateLessThan, dateGreaterThan, ipRange);
			
			System.out.println("Signed URL:\n");
			System.out.println(urlString);
			
		} catch (AmazonServiceException ase) {
			
			System.out.println("Caught an AmazonServiceException, which means your request made it " +
                    "to Amazon SQS, but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code: " + ase.getErrorCode());
            System.out.println("Error Type: " + ase.getErrorType());
            System.out.println("Request ID: " + ase.getRequestId());
            
		} catch (AmazonClientException ace) {
			
			System.out.println("Caught an AmazonClientException, which means the client encountered " +
                    "a serious internal problem while trying to communicate with SQS, such as not " +
                    "being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            
		} catch (Exception ex) {
			System.out.println("The following exception was raised: ");
			System.out.println(ex);
		}
	}
}
