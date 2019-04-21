package test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import INFO6205_517.GenerationTool;
import INFO6205_517.GenerationTool_X;

public class Test_GA {

	@Test
	public void testCross() {
		GenerationTool gt = new GenerationTool();
		Random r = new Random();
		
		boolean[] b1 = new boolean[50];
		boolean[] b2 = new boolean[50];
		
		for(int i = 0;i<50;i++) {
			b1[i] = r.nextBoolean();
			b2[i] = r.nextBoolean();
		}
		
		boolean[] old1 = new boolean[50];
		boolean[] old2 = new boolean[50];
		System.arraycopy(b1, 0, old1, 0, 50);
		System.arraycopy(b2, 0, old2, 0, 50);
		
		int slice = gt.crossover(b1, b2);
		
		boolean result = true;
		
		if (slice < 25) {
            for (int i = 0; i < slice; i++) {
                if(b1[i] != old2[i]) 
                	result = false;
            }
            
           for (int i = slice; i < 50; i++) {
        	   if(b1[i] != old1[i]) 
               	result = false;
            }
        } else {
        	for (int i = 0; i < slice; i++) {
                if(b1[i] != old1[i]) 
                	result = false;
            }
            
           for (int i = slice; i < 50; i++) {
        	   if(b1[i] != old2[i]) 
               	result = false;
            }
        }
		
		assertTrue(result);
		
	}
	
	@Test
	public void testMutation() {
		GenerationTool gt = new GenerationTool();
		Random r = new Random();
		boolean[] b = new boolean[50];
		for(int i = 0;i<50;i++) {
			b[i] = r.nextBoolean();
		}
		boolean[] old = new boolean[50];
		System.arraycopy(b, 0, old, 0, 50);
		
		int i = gt.mutation(b);
		assertTrue(b[i]!= old[i]);
	}
	
	@Test
	public void testMutation_X() {
		GenerationTool_X gtX = new GenerationTool_X();
		boolean[] origin = {true,false,true,false,true,false,true,false,true,false};
		boolean[] ind = {true,true,true,true,true,true,true,true,true,true};
		ind = gtX.mutation_Xmen(ind, origin);
		
		int count = 0;
		for(int i = 0;i<ind.length;i++) {
			if(origin[i]!=ind[i]) {
				count++;
			}
		}
		
		assertTrue(count == ind.length*0.1);
	}
	
	@Test
	public void testSelection1() {
		GenerationTool gt = new GenerationTool();
		double[] arr = {0.2,0.8};
		Random r = new Random();
		
		int[] choose = new int[100];
		for(int i = 0;i<100;i++) {
			choose[i] = gt.BinarySearch(arr, r.nextDouble());
		}
		
		int count1 = 0;
		int count2 = 0;
		
		for(int i = 0;i<100;i++) {
			if(choose[i]==0) count1++;
			else count2++;
		}
		
		assertTrue(count1<count2);
		
	}
	
	@Test
	public void testSelection2() {
		GenerationTool gt = new GenerationTool();
		double[] arr = {0.4,0.6};
		Random r = new Random();
		
		int[] choose = new int[100];
		for(int i = 0;i<100;i++) {
			choose[i] = gt.BinarySearch(arr, r.nextDouble());
		}
		
		int count1 = 0;
		int count2 = 0;
		
		for(int i = 0;i<100;i++) {
			if(choose[i]==0) count1++;
			else count2++;
		}
		
		assertTrue(count1<count2);
		
	}
	
	@Test
	public void testFitness() {
		GenerationTool gt = new GenerationTool();
		boolean[] origin = {true,false,true,false,true,false,true,false,true,false};
		boolean[] test = {true,true,true,true,true,true,true,true,true,true};
		int len = origin.length;
		
		double fit  = gt.getFitness(test, origin, len);
		
		assertEquals(0.5,fit,0);
		
	}

}
