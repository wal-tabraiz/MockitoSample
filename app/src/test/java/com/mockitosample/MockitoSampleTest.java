package com.mockitosample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.times;

@PrepareForTest({Puppy.class})
@RunWith(PowerMockRunner.class)
public class MockitoSampleTest {

    //Mocking
    @Test
    public void testCreatePuppy() throws Exception {
        //Mocking
        Human human = Mockito.mock(Human.class);
        Puppy puppy = Puppy.createPuppy("Gatsby", human);
        assert (puppy != null);
        assert (puppy.getName().equals("Gatsby"));
        //Verifying
        Mockito.verify(human, times(1)).isSoHappy();

    }

    //Spying
    @Test
    public void testChaseTail() throws Exception {
        Human human = Mockito.mock(Human.class);
        //Spying
        Puppy puppy = Mockito.spy(new Puppy("Gatsby", human));
        Mockito.doNothing().when(puppy).bark();
        puppy.bark();
        Mockito.verify(puppy, times(1)).bark();
    }


    @Test
    public void testChaseTailFailure() throws Exception {
        Human human = Mockito.mock(Human.class);
        //Spying
        Puppy puppy = Mockito.spy(new Puppy("Gatsby", human));
        Mockito.doNothing().when(puppy).bark();

        //calling bark method explicitely without defining any condition to Mockito Framework
        puppy.bark();
        puppy.chaseTail();
        //expecting one invocation on bark method.
        Mockito.verify(puppy, times(1)).bark();
    }


    @Test
    public void testGoOnWalk() throws Exception {
        Human human = Mockito.mock(Human.class);
        //Power Spying
        Puppy puppy = PowerMockito.spy(new Puppy("Gatsby", human));

        //on any private method
        PowerMockito.doNothing().when(puppy, "performBusiness");

        //Can combine regular and power
        Mockito.doNothing().when(puppy).wipeOffFeet();
        puppy.goOnWalk(15);
        Mockito.verify(puppy, times(1)).wipeOffFeet();
    }

    @Test
    public void testBuyPuppy() throws Exception {
        //Mocking static
        PowerMockito.mockStatic(Puppy.class);
        Human human = new Human("John");
        Puppy puppy = Mockito.mock(Puppy.class);
        //Static mocking and matchers
        PowerMockito.when(Puppy.createPuppy(Mockito.eq("Gatsby"), Mockito.any(Human.class))).thenReturn(puppy);
        human.buyPuppy("Gatsby");
        assert (human.puppy != null);
    }

    @Test
    public void testEat() throws Exception {
        Human human = Mockito.mock(Human.class);
        Puppy puppy = PowerMockito.spy(new Puppy("Gatsby", human));
        //Get private variables
        int energy = Whitebox.getInternalState(puppy, "energyLevel");
        //Call private methods
        Whitebox.invokeMethod(puppy, "eat");
        int energyAfterwards = Whitebox.getInternalState(puppy, "energyLevel");
        System.out.println(energy + " > " + energyAfterwards);
        assert (energy <= energyAfterwards);
    }

}