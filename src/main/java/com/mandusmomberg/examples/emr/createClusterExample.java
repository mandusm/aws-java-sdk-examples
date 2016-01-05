package com.mandusmomberg.examples.emr;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClient;
import com.amazonaws.services.elasticmapreduce.model.JobFlowInstancesConfig;
import com.amazonaws.services.elasticmapreduce.model.RunJobFlowRequest;
import com.amazonaws.services.elasticmapreduce.model.RunJobFlowResult;

/*
 * This example will create a new EMR 4.2.0 cluster.
 * It will use the default EMR Service and Instance Profile Roles. 
 * It will enable visible to all IAM users
 * It will enable termination protection.
 */
public class createClusterExample {
	public static void main(String args[]){
		System.out.println("Example for Creating an EMR cluster");
		
		/* 
		 * 	Define Variables 
		 */
		String awsRegion = "eu-west-1";
		String sshKey = "yourSSHkeyName";
		String masterInstanceType = "m3.2xlarge";
		String slaveInstanceType = "m3.xlarge";
		String releaseLabel = "emr-4.2.0";
		String serviceRole = "EMR_DefaultRole";
		String instanceProfile = "EMR_EC2_DefaultRole";
		
		AmazonElasticMapReduce client = null;
		try {
			client = new AmazonElasticMapReduceClient();
			client.setRegion(Region.getRegion(Regions.fromName(awsRegion)));
		} catch (Exception e) {
		    System.out.println("Could not Create Client");
		    e.printStackTrace();
		    System.exit(-1);
		}
		
		RunJobFlowResult jobflow = null;
		try {
			
			JobFlowInstancesConfig instances = new JobFlowInstancesConfig()
					.withEc2KeyName(sshKey)
					.withKeepJobFlowAliveWhenNoSteps(true)
					.withMasterInstanceType(masterInstanceType)
					.withSlaveInstanceType(slaveInstanceType)
					.withTerminationProtected(true)
					.withInstanceCount(2);
			
			jobflow = client.runJobFlow(new RunJobFlowRequest("Example Cluster", instances)
					.withReleaseLabel(releaseLabel)
					.withJobFlowRole(instanceProfile)
					.withServiceRole(serviceRole)
					.withVisibleToAllUsers(true));
		} catch (Exception e){
			 System.out.println("Could not Create New Cluster");
			 e.printStackTrace();
			 System.exit(-1);
		}
		
		System.out.printf("New Cluster Created in %s with Cluster ID: %s", awsRegion, jobflow.getJobFlowId());
		
	}
}
