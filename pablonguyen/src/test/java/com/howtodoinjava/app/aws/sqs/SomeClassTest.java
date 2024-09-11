package com.howtodoinjava.app.aws.sqs;

import com.howtodoinjava.app.unit.test.practice.EntityNotFoundException;
import com.howtodoinjava.app.unit.test.practice.SomeClass;
import com.howtodoinjava.app.unit.test.practice.SomeEntity;
import com.howtodoinjava.app.unit.test.practice.SomeInterface;
import jakarta.validation.constraints.Null;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SomeClassTest {
    SomeInterface someInterface;
    SomeClass someClass;

    @Before
    public void beforeEachTest(){
        someInterface = Mockito.mock(SomeInterface.class);
        someClass = new SomeClass(someInterface);
    }

    @Test
    public void testgetfirstObject() throws EntityNotFoundException {
        ArrayList<SomeEntity> map = new ArrayList<>();
        Long l = 1L;
        map.add(new SomeEntity("111", "111", 1L));
        map.add(new SomeEntity("222", "222", 2L));
        map.add(new SomeEntity("111", "222", 3L));

        Mockito.when(someInterface.getObjects(Mockito.anyMap())).thenReturn(map);
        SomeEntity testEntity = someClass.getEntity("111");
        assertEquals(testEntity.entityId, "111");
        assertEquals(testEntity.entityName, "111");
        assertEquals(testEntity.createdAt, l);
    }

    @Test
    public void testArgumentCapture() throws EntityNotFoundException {
        ArrayList<SomeEntity> map = new ArrayList<>();
        map.add(new SomeEntity("111", "111", 1L));
        Mockito.when(someInterface.getObjects(Mockito.anyMap())).thenReturn(map);
        ArgumentCaptor<HashMap> mapArgumentCaptor = ArgumentCaptor.forClass(HashMap.class);
        someClass.getEntity("111");
        Mockito.verify(someInterface).getObjects(mapArgumentCaptor.capture());
        HashMap<String, String> argumentVals = mapArgumentCaptor.getValue();
        assertEquals(argumentVals.size(), 1);
        assertTrue(argumentVals.containsKey("entityId"));
        assertTrue(argumentVals.containsValue("111"));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testNoObjectGet() throws EntityNotFoundException {
        ArrayList<SomeEntity> map = new ArrayList<>();
        Mockito.when(someInterface.getObjects(Mockito.anyMap())).thenReturn(map);
        someClass.getEntity("111");

    }

    @Test(expected = EntityNotFoundException.class)
    public void testReturnNull() throws EntityNotFoundException {
        Mockito.when(someInterface.getObjects(Mockito.anyMap())).thenReturn(null);
        someClass.getEntity("111");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRuntimeException() throws EntityNotFoundException {
        Mockito.when(someInterface.getObjects(Mockito.anyMap())).thenThrow(new IndexOutOfBoundsException());
        someClass.getEntity("111");
    }





}
