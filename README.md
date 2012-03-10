Mapper
======


Environment
-----------
* Mapper uses the Google API Android OS 2.3.3 (API 10).  
* Other Jar dependences are included in the Google defined default directory,  
<code>/mapper/src/Mapper.java/libs</code>  
  
  
Building Application
--------------------  
* Mapper uses Apache Ant to build the project at the moment. In order to compile project,
navigate to the /mapper directory, and enter <code>ant help</code> to see a list of options
available via the native Google ant build scripts.  
  
  
Installing Application on Emulator
----------------------------------
  
* Mapper can be launched on the Android SDK Emulator following these steps,

<ol>
	
		<li> Open the 'Android Virtual Device Manager', which can be started via the command line within the
		Android SDK tools subdirectory. IE, on a Macbook,
		<code>/android-sdk-mac_x86/tools</code>
		'Android Virtual Device Manager' can also be opened from Eclipse if you have installed
		the [Android ADT plugin](http://developer.android.com/sdk/eclipse-adt.html).</li>  
   
		<li>Create an Android Virtual Device with the Google APIs v.10 OS if you have not already.</li>  
  
		<li>Start the Google API v.10 device (IE, highlight the device and press start).</li>  
  
		<li>In the command line, go to root of the project, /mapper, and enter,  
		<code>ant install</code>  
		Make sure virtual device / emulator has fully initialized before run the 'ant install' command.
		My virtual device took 4 minutes to boot up.</li>  
  
		<li>Once Emulator shows device home screen, it is ready. Go to main menu and find 'Mapper'
		application. Click on it, and you have successfully built and temporarily installed
		the app on the virtual device.</li>  
	
</ol>