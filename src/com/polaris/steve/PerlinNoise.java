package com.polaris.steve;

import java.util.ArrayList;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

public class PerlinNoise {
	private ArrayList<IntegerArray> primes = new ArrayList<IntegerArray>();
	private int[][] primeArray;
	
	public enum InterpolationType {
	    Linear (0),
	    Cosine (1),
	    Cubic (10);

	    private final int index;   

	    InterpolationType(int index) {
	        this.index = index;
	    }

	    public int index() { 
	        return index; 
	    }

	}
	
	public PerlinNoise()
	{
		generatePrimes();
	}
	
	/**
	 * produces a pseudo-random number from 0.0 to 1.0 based on seed and the octave
	 * @param seed The seed to feed the generator
	 * @param octave Determines primes to use in
	 * @return
	 */
	public float noise(float seed, float octave)
    {
        int n = ((int)seed<<13) ^ (int)seed;
        int index = (int)octave;
        return (float) ( 1.0 - ( (n * (n * n * primeArray[index][0] + primeArray[index][1]) + primeArray[index][2]) & 0x7fffffff) / 1073741824.0f);   
    }
	
	/**
	 * Looks pretty bad, like those cheap things that people use to generate landscape, however it's
	 * a simple algorithm and would make sense to use if you are trying to generate perlin noise
	 * in real time
	 * @param LowerBound Lower bound of interpolation
	 * @param UpperBound Upper bound of interpolation
	 * @param noise Any value from 0.0 to 1.0
	 * @return Some value between the upper and lower bound
	 */
	public float LinearInterpolate(float LowerBound, float UpperBound, float noise)
	{
		return LowerBound * (1 - noise) + UpperBound * noise;
	}


	/**
	 * This method gives a much smoother curve than linear intorplation. There is a 
	 * slight cost in speed, however.
	 * @param LowerBound Lower bound of interpolation
	 * @param UpperBound Upper bound of interpolation
	 * @param noise Any value from 0.0 to 1.0
	 * @return Some value between the upper and lower bound
	 */
	public float CosineInterpolate(float LowerBound, float UpperBound, float noise)
	{
		double ft = noise * Math.PI;
		double f = (1 - Math.cos(ft)) * .5;

		return (float)(LowerBound * (1 - f) + UpperBound * f);
	}


	/**
	 * Gives even smoother results than cosine interpolation, however this
	 * method has an even higher cost in speed.
	 * @param beforeLowerBound The point before the lower bound
	 * @param LowerBound Lower bound of interpolation
	 * @param UpperBound Upper bound of interpolation
	 * @param afterUpperBound The point after the upper bound
	 * @param noise Any value from 0.0 to 1.0
	 * @return Some value between the upper and lower bound
	 */
	public float CubicInterpolate(float beforeLowerBound, float LowerBound, float UpperBound, float afterUpperBound, float noise)
	{
		float v0 = beforeLowerBound;        // \
		float v1 = LowerBound;              //  \
		float v2 = UpperBound;              //   |- I'm using the mathematical equation, therefore i want the algorithm to look like the equation
		float v3 = afterUpperBound;         //  /
		float x = noise;                    // /

		float P = (v3 - v2) - (v0 - v1);
		float Q = (v0 - v1) - P;
		float R = v2 - v0;
		float S = v1;

		double result = (P * Math.pow(x, 3)) + (Q * Math.pow(x, 2)) + (R * x) + S;
		return (float)result;
	}

	/**
	 * Smoothes out the noise for one dimensional perlin noise generation
	 * @param x Point on the x-axis
	 * @param octave the current octave (used during iteration)
	 * @return Smoothed out point
	 */
	public float SmoothNoiseOneDimensional(float x, float octave)
	{
		return (noise(x, octave) / 2) + (noise(x - 1, octave) / 4) + (noise(x + 1, octave) / 4);
	}

	/**
	 * Interpolates noise
	 * @param noise Any value from 0.0 to 1.0
	 * @param octave the current octave (used during iteration)
	 * @param interpolationType The type of interpolation to be performed
	 * @return The interpolated noise
	 */
	private float InterpolatedNoise(float noise, float octave, InterpolationType interpolationType)
	{
		int intNoise = (int)noise;
		float fractionalNoise = noise - intNoise;

		float LowerBound = SmoothNoiseOneDimensional(intNoise, octave);
		float UpperBound = SmoothNoiseOneDimensional(intNoise + 1, octave);

		float flag = 0.0f;

		switch (interpolationType)
		{
			case Linear:
				flag = LinearInterpolate(LowerBound, UpperBound, fractionalNoise);
				break;
			case Cosine:
				flag = CosineInterpolate(LowerBound, UpperBound, fractionalNoise);
				break;
			case Cubic:
				// I don't know if this will actually do what i want it to (I'm not even sure I know what I want it to do anyways)
				flag = CubicInterpolate(SmoothNoiseOneDimensional(intNoise - 1, octave), LowerBound, UpperBound, SmoothNoiseOneDimensional(intNoise + 2, octave), fractionalNoise);
				break;
			default:
				break;
		}

		return flag;

	}

	/**
	 * Generates one dimensional Perlin Noise
	 * @param noise	The noise (can be interpolated for smoother results)
	 * @param persistence	you know.. the persistence
	 * @param octaves	How many octaves to iterate through
	 * @param interpolationType	The type of interpolation to be done (or applied)
	 * @return Perlin Noise
	 */
	public float PerlinNoiseGeneration(float noise, float persistence, float octaves, InterpolationType interpolationType)
	{
		float total = 0.0f;
		for (int octave = 0; octave < octaves - 1; octave++)
		{
			float frequency = (float)Math.pow(2, octave);
			float amplitude = (float)Math.pow(persistence, octave);

			total += InterpolatedNoise((noise * frequency), octave, interpolationType) * amplitude;
		}
		return total;
	}
    
    private void generatePrimes()
    {
    	for (int i = 1; i < 10001; i++)
    	{
    		if (isPrime(i)){
    			primes.add(new IntegerArray(new int[]{i,i,i}));
    		}
    	}
    	
    	primeArray = primes.toArray(new int[primes.size()][]);
    }
    
    private boolean isPrime(int number)
    {
    	if (number % 2 == 0)
    		return false;
		for (int i = 3; i * i <= number; i += 2)
		{
			if (number % i == 0)
				return false;
		}
		return true;
    }
}
