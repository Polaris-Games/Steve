package com.polaris.engine;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager
{

	private SoundSystem soundSystem;
	
	public SoundManager()
	{
		try
		{
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("wav", CodecWav.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
			SoundSystemConfig.setSoundFilesPackage("resources/sounds/");
			//soundSystem = new SoundSystem(LibraryLWJGLOpenAL.class);
		}
		catch(SoundSystemException sse) 
		{ 
			sse.printStackTrace(); 
			return; 
		}
	}
	
	public void streamAudio(String name, String location, float x, float y)
	{
		soundSystem.newStreamingSource(true, name, location, false, x, y, 0, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
	}
	
	public void streamAudio(String name, String location, boolean toLoop, float x, float y)
	{
		soundSystem.newStreamingSource(true, name, location, toLoop, x, y, 0, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
	}
	
	public void streamAudio(String name, String location, float x, float y, float z)
	{
		soundSystem.newStreamingSource(true, name, location, false, x, y, z, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
	}
	
	public void streamAudio(String name, String location, boolean toLoop, float x, float y, float z)
	{
		soundSystem.newStreamingSource(true, name, location, toLoop, x, y, z, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
	}

	public void setMasterVolume(float f)
	{
		soundSystem.setMasterVolume(f);
	}
	
	protected void flush()
	{
		//soundSystem.cleanup();
	}
	
}
