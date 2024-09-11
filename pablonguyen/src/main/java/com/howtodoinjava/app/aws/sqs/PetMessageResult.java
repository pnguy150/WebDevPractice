package com.howtodoinjava.app.aws.sqs;

import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.howtodoinjava.app.datastructures.Pet;

public class PetMessageResult {
    public Pet pet;
    public ReceiveMessageResult result;

    public PetMessageResult(Pet pet, ReceiveMessageResult result){
        this.pet = pet;
        this.result = result;
    }


}
