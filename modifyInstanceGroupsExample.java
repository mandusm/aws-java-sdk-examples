package com.mandusmomberg.examples.emr;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClient;
import com.amazonaws.services.elasticmapreduce.model.ListInstanceGroupsRequest;
import com.amazonaws.services.elasticmapreduce.model.ListInstanceGroupsResult;
import com.amazonaws.services.elasticmapreduce.model.InstanceGroup;
import com.amazonaws.services.elasticmapreduce.model.InstanceGroupModifyConfig;
import com.amazonaws.services.elasticmapreduce.model.ModifyInstanceGroupsRequest;

/*****
modifyInstanceGroupsExample:
This class uses the ListInstanceGroup and ModifyInstanceGroup requests to find the the CORE node group 
in a cluster and increase its count by 1. It could be altered to find any arbitrary group and perform 
increments or decrements, or any other group modifications.
******/

public class modifyInstanceGroupsExample {
	
	static AmazonElasticMapReduceClient emr;
	
	private static void init() throws Exception {
	    
	    AWSCredentials credentials = null;
	    try {
	        credentials = new ProfileCredentialsProvider("default").getCredentials();
	    } catch (Exception e) {
	        throw new AmazonClientException(
	                "Cannot load the credentials from the credential profiles file. " +
	                "Please make sure that your credentials file is at the correct " +
	                "location and is in valid format.",
	                e);
	    }
	    emr = new AmazonElasticMapReduceClient(credentials);
	    Region usWest2 = Region.getRegion(Regions.US_WEST_2);
	    emr.setRegion(usWest2);
	}
	

	public static void main(String[] args) throws Exception {
        init();

        // Get the instance groups from the cluster
        ListInstanceGroupsRequest listRequest = new ListInstanceGroupsRequest().withClusterId("j-2YGCQ0URLUEHW");      
        
        ListInstanceGroupsResult listResult = emr.listInstanceGroups(listRequest);
        
        // Find the CORE group, get its ID, and increment its count by one. Then call modifyInstanceGroups to increase the cluster
        for (InstanceGroup group : listResult.getInstanceGroups())
        		{
        			if (group.getInstanceGroupType().equals("CORE"))
        			{        				 
        				 String groupId = group.getId();
        				 int count = group.getRequestedInstanceCount() + 1;
        				 
        				 InstanceGroupModifyConfig modify = new InstanceGroupModifyConfig()
        				 	.withInstanceCount(count)
        				 	.withInstanceGroupId(groupId);
        				 
        				 ModifyInstanceGroupsRequest modifyRequest = new ModifyInstanceGroupsRequest().withInstanceGroups(modify);
        						 
        				 emr.modifyInstanceGroups(modifyRequest);
        			}
        		}
	}
	
	
}
