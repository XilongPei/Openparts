package com.openparts.utils.mongodb;

import org.bson.types.ObjectId;


public class ObjectIdCreator{

	public static String creator(){
		return new ObjectId().toString();
	}
}
