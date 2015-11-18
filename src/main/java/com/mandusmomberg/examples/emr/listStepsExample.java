package com.mandusmomberg.examples.emr;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClient;
import com.amazonaws.services.elasticmapreduce.model.ListStepsRequest;
import com.amazonaws.services.elasticmapreduce.model.ListStepsResult;
import com.amazonaws.services.elasticmapreduce.model.StepSummary;

public class listStepsExample {
    public static void main( String[] args )
    {
        System.out.println( "EMR List Steps Example" );
        AmazonElasticMapReduce client = null;
		try {
			client = new AmazonElasticMapReduceClient();
		} catch (Exception e) {
		    System.out.println("Could not Create Client");
		    e.printStackTrace();
		    System.exit(-1);
		}
		
		String clusterId = "j-35HPSF9BRH2SA";
		String stepState = "COMPLETED";
		String awsRegion = "eu-west-1";
		
		client.setRegion(Region.getRegion(Regions.fromName(awsRegion)));
		ListStepsResult steps = client.listSteps(new ListStepsRequest()
								.withClusterId(clusterId)
								.withStepStates(stepState)
		);
		int i = 0;
		int totalSteps = steps.getSteps().size();
		while( i < totalSteps ){
			StepSummary step = steps.getSteps().get(i);
			System.out.println("This is step " + (i+1) + " of " + totalSteps + " and is called " + step.getName() + " and has Step ID " + step.getId() + " Which has State " + step.getStatus().getState());
			i++;
		}
		
    }
}
