package com.mandusmomberg.examples.emr;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClient;
import com.amazonaws.services.elasticmapreduce.model.Cluster;
import com.amazonaws.services.elasticmapreduce.model.DescribeClusterRequest;
import com.amazonaws.services.elasticmapreduce.model.DescribeClusterResult;

public class describeClusterExample {
	public static void main(String args[]){
		System.out.println("Example of Fetching Information about an EMR Cluster");
		
		String clusterId = "j-35HPSF9BRH2SA";
		String awsRegion = "eu-west-1";
		
		AmazonElasticMapReduce client = null;
		try {
			client = new AmazonElasticMapReduceClient();
			client.setRegion(Region.getRegion(Regions.fromName(awsRegion)));
		} catch (Exception e) {
		    System.out.println("Could not Create Client");
		    e.printStackTrace();
		    System.exit(-1);
		}
		
		DescribeClusterResult clusterResult = client.describeCluster(new DescribeClusterRequest().withClusterId(clusterId));
		
		Cluster cluster = clusterResult.getCluster();
		System.out.println("Cluster Name: " + cluster.getName());
		System.out.println("Cluster State: " + cluster.getStatus().getState());
		System.out.println("Cluster Master DNS Name: " + cluster.getMasterPublicDnsName());
		System.out.println("Cluster Service Role: " + cluster.getServiceRole());
		System.out.print("Cluster Tags: ");
		int i = 0;
		while ( i < cluster.getTags().size() ){
			System.out.print(cluster.getTags().get(i).getKey() + " = " + cluster.getTags().get(i).getValue());
			i++;
			if (i < cluster.getTags().size()) { System.out.print(","); }	
		}
		
	}
}
